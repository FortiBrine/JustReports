package me.fortibrine.justreports.gui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.dialog.DialogService;
import me.fortibrine.justreports.question.QuestionService;
import me.fortibrine.justreports.reputation.ReputationService;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;
import ru.boomearo.menuinv.api.Menu;
import ru.boomearo.menuinv.api.MenuType;
import ru.boomearo.menuinv.api.frames.PagedIconsBuilder;
import ru.boomearo.menuinv.api.icon.IconBuilder;
import ru.boomearo.menuinv.api.icon.IconHandler;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ReportListMenu {

    private final QuestionService questionService;
    private final ReputationService reputationService;
    private final Plugin plugin;
    private final Config config;
    private final DialogService dialogService;
    private final ConfigManager configManager;

    public void registerMenu() {
        Menu.registerPages(plugin)
                .createTemplatePage(MenuPage.REPORT_LIST)
                .setInventoryTitle(inventoryPage -> config.getTitle())
                .setMenuType(MenuType.CHEST_9X6)
                .setStructure(
                        "1 . . . . . . . .",
                        ". . . . . . . . .",
                        ". . . . . . . . .",
                        ". . . . . . . . .",
                        ". . . . . . . . .",
                        ". . . . . . . . 2"
                )
                .setPagedIconsIngredients("players", '1', '2', new PagedIconsBuilder()
                        .setPagedItemsUpdate((inventoryPage, player) -> {
                            List<IconHandler> icons = new ArrayList<>();

                            for (Player playerWithQuestion : questionService.getPlayersWithQuestions()) {
                                icons.add(buildReportIcon(playerWithQuestion));
                            }

                            return icons;
                        }));
    }

    public IconHandler buildReportIcon(Player targetPlayer) {
        IconBuilder iconBuilder = new IconBuilder();

        iconBuilder.setIconClick((inventoryPage, itemIcon, adminPlayer, clickType) -> {
            if (clickType == ClickType.RIGHT || targetPlayer == null || !targetPlayer.isOnline()) {
                dialogService.endDialog(adminPlayer.getUniqueId());
            } else if (clickType == ClickType.LEFT) {
                if (dialogService.isInDialog(adminPlayer.getUniqueId()) || dialogService.isInDialog(targetPlayer.getUniqueId())) {
                    adminPlayer.sendMessage(configManager.getMessageConfig().getCannotStartDialogAlreadyInDialog());
                    return;
                }

                if (!adminPlayer.hasPermission("justreports.reports.answer")) {
                    adminPlayer.sendMessage(configManager.getMessageConfig().getPermissionDenied());
                    inventoryPage.close();
                    return;
                }

                targetPlayer.sendMessage(configManager.getMessageConfig().getReportTakenByAdmin()
                        .replace("%admin%", adminPlayer.getName()));

                adminPlayer.sendMessage(configManager.getMessageConfig().getAdminMessagesSection().getReportTaken()
                        .replace("%player%", targetPlayer.getName()));

                dialogService.beginDialog(adminPlayer.getUniqueId(), targetPlayer.getUniqueId());
            }
        });

        iconBuilder.setIconUpdate((inventoryPage, player) -> {
            ItemStack item = new ItemStack(config.getItemSection().getMaterial());
            String name = config.getItemSection().getName()
                    .replace("%player%", targetPlayer.getName());
            List<String> lore = new ArrayList<>();
            for (String line : config.getItemSection().getLore()) {
                lore.add(line.replace("%player%", targetPlayer.getName()));
            }
            item.getItemMeta().setDisplayName(name);
            item.getItemMeta().setLore(lore);
            item.setItemMeta(item.getItemMeta());
            return item;
        });

        return iconBuilder.build().create();
    }

    @Getter
    @ConfigSerializable
    public static class Config {
        private String title = "Reports";

        @Comment("Item settings for the report list menu")
        @Setting("item")
        private ItemSection itemSection = new ItemSection();

        @Getter
        @ConfigSerializable
        public static class ItemSection {
            private Material material = Material.NAME_TAG;
            private String name = "%player%";
            private String[] lore = new String[] {
                    "§7Question: &f%question%",
                    "§7Left-click to start a conversation",
                    "§7Right-click to delete the report"
            };
        }
    }

}
