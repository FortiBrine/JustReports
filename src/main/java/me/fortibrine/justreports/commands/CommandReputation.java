package me.fortibrine.justreports.commands;

import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.ReputationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReputation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {

            if (args.length < 1) {
                double reputation = ReputationManager.getReputationByName(sender.getName());

                String reputationString = String.format("%.2f", reputation);

                String message = MessageManager.getStringFromConfig("messages.your-reputation");

                message = message.replace("%player", sender.getName()).replace("%reputation", reputationString);

                sender.sendMessage(message);
                return true;
            }

            double reputation = ReputationManager.getReputationByName(args[0]);

            String reputationString = String.format("%.2f", reputation);

            String message = MessageManager.getStringFromConfig("messages.other-reputation");

            message = message.replace("%player", args[0]).replace("%reputation", reputationString);

            sender.sendMessage(message);

            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            if (!player.hasPermission("justreports.reputation")) {
                player.sendMessage(MessageManager.getStringFromConfig("messages.permission"));

                return true;
            }

            double reputation = ReputationManager.getReputation(player);

            String reputationString = String.format("%.2f", reputation);

            String message = MessageManager.getStringFromConfig("messages.your-reputation");

            message = message.replace("%player", player.getName()).replace("%reputation", reputationString);

            player.sendMessage(message);

            return true;
        }

        if (!player.hasPermission("justreports.reputation.other")) {
            player.sendMessage(MessageManager.getStringFromConfig("messages.permission"));

            return true;
        }

        double reputation = ReputationManager.getReputationByName(args[0]);

        String reputationString = String.format("%.2f", reputation);

        String message = MessageManager.getStringFromConfig("messages.other-reputation");

        message = message.replace("%player", args[0]).replace("%reputation", reputationString);

        player.sendMessage(message);

        return true;
    }
}
