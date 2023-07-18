package me.fortibrine.justreports.commands;

import me.fortibrine.justreports.JustReports;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReport implements CommandExecutor {

    private JustReports plugin;
    public CommandReport(JustReports plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            return false;
        }

        return true;
    }

}
