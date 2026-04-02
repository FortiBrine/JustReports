package me.fortibrine.justreports.gui;

import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ItemConfig;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import me.fortibrine.justreports.gui.config.FeedbackRatingMenuConfig;
import me.fortibrine.justreports.reputation.ReputationService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class FeedbackRatingMenu implements InventoryHolder {
    private final Player player;
    private final UUID targetPlayerId;
    private final ReputationService reputationService;
    private final MessagesConfigProvider messagesConfigProvider;
    private final FeedbackRatingMenuConfig config;

    private Inventory inventory;

    public void open() {
        String[] hologramLines = config.getHologram();
        int slots = hologramLines.length * 9;

        inventory = Bukkit.createInventory(
                this,
                slots,
                config.getTitle()
        );

        renderItems();

        player.openInventory(inventory);
    }

    public void renderItems() {
        String[] hologramLines = config.getHologram();
        Map<Character, ItemConfig> items = config.getItems();

        int slot = 0;
        for (String line : hologramLines) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == ' ') continue;

                ItemConfig itemConfig = items.get(c);

                if (itemConfig == null) {
                    slot++;
                    continue;
                }

                ItemStack itemStack = new ItemStack(itemConfig.getMaterial());
                ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setDisplayName(itemConfig.getName());
                itemMeta.setLore(Arrays.asList(itemConfig.getLore()));

                itemStack.setItemMeta(itemMeta);
                inventory.setItem(slot++, itemStack);
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void handleClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        int row = slot / 9;
        if (row >= config.getHologram().length) return;

        int col = slot % 9;

        String line = config.getHologram()[row];
        if (col >= line.length()) return;

        char c = line.replace(" ", "").charAt(col);

        ItemConfig itemConfig = config.getItems().get(c);
        if (itemConfig == null) return;

        String[] actions = itemConfig.getActions().get(event.getClick());
        if (actions == null) return;

        for (String action : actions) {
            if (action.startsWith("[player]")) {
                String command = action.substring("[player]".length()).trim();
                player.performCommand(command);
            } else if (action.startsWith("[console]")) {
                String command = action.substring("[console]".length()).trim()
                        .replace("%player%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            } else if ("[close_inventory]".equals(action)) {
                player.closeInventory();
            } else if (action.startsWith("[star]")) {
                int rating;
                try {
                    rating = Integer.parseInt(action.substring("[star]".length()).trim());
                } catch (NumberFormatException e) {
                    continue;
                }

                CompletableFuture.runAsync(() -> {
                    reputationService.addReputationByUniqueId(targetPlayerId, rating);
                    String currentReputation = String.format("%.2f", reputationService.getReputationByUniqueId(targetPlayerId));
                    Player targetPlayer = Bukkit.getPlayer(targetPlayerId);
                    if (targetPlayer != null) {
                        targetPlayer.sendMessage(messagesConfigProvider.getConfig().getAdmin().getReceivedReputation()
                                .replace("%stars%", String.valueOf(rating))
                                .replace("%player%", player.getName())
                                .replace("%reputation%", currentReputation));
                    }
                });

                player.closeInventory();
            }
        }
    }
}
