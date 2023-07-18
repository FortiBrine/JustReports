package me.fortibrine.justreports.commands;

import me.fortibrine.justreports.JustReports;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReports implements CommandExecutor {

    private JustReports plugin;
    public CommandReports(JustReports plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
