package me.fortibrine.justreports.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

@RequiredArgsConstructor
@Command(name = "report")
@Permission("justreports.report")
public class CommandReport {

    private final Plugin plugin;

    @Execute
    public void execute(@Context Player player, @Join String question) {
        if (VariableManager.containsQuestion(player)) {
            player.sendMessage(MessageManager.getStringFromConfig("messages.you-already-send-report"));

            return;
        }

        ItemStack item = new ItemStack(Material.matchMaterial(MessageManager.getStringFromConfig("reports.item.material")));

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(MessageManager.getStringFromConfig("reports.item.name").replace("%player", player.getName()).replace("%question", question));

        List<String> lore = plugin.getConfig().getStringList("reports.item.lore");

        lore.replaceAll(s -> MessageManager.supportMessagesJSON(MessageManager.supportColorsHEX(s)).replace("&", "§").replace("%player", player.getName()).replace("%question", question));

        meta.setLore(lore);

        item.setItemMeta(meta);

        VariableManager.putItem(player, item);
        VariableManager.setQuestion(player, question);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("justreports.see")) continue;

            onlinePlayer.sendMessage(MessageManager.getStringFromConfig("messages.player-send")
                    .replace("%player", player.getName())
                    .replace("%question", question));
        }
    }


}
