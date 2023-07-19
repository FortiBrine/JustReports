package me.fortibrine.justreports.commands;

import me.fortibrine.justreports.JustReports;
import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CommandReport implements CommandExecutor {

    private JustReports plugin;
    public CommandReport(JustReports plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        if (args.length < 1) {
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("justreports.report")) {
            player.sendMessage(MessageManager.getStringFromConfig("messages.permission"));
            return true;
        }

        if (VariableManager.containsQuestion(player)) {
            player.sendMessage(MessageManager.getStringFromConfig("messages.you-already-send-report"));

            return true;
        }

        String question = String.join(" ", args);

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

        return true;
    }

}
