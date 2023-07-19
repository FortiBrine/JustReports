package me.fortibrine.justreports.commands;

import me.fortibrine.justreports.JustReports;
import me.fortibrine.justreports.inventory.ReportListInventory;
import me.fortibrine.justreports.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReports implements CommandExecutor {

    private JustReports plugin;
    public CommandReports(JustReports plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("justreports.reports")) {
            player.sendMessage(MessageManager.getStringFromConfig("messages.permission"));
            return true;
        }

        ReportListInventory reportListInventory = new ReportListInventory();

        player.openInventory(reportListInventory.getInventory());

        return true;
    }
}
