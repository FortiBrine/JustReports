package me.fortibrine.justreports.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.ReputationManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
@Command(name = "reputation")
@Permission("justreports.reputation")
public class CommandReputation {

    private final Plugin plugin;

    @Execute
    public void execute(@Context CommandSender sender) {
        double reputation = ReputationManager.getReputationByName(sender.getName());

        String reputationString = String.format("%.2f", reputation);

        String message = MessageManager.getStringFromConfig("messages.your-reputation");

        message = message.replace("%player", sender.getName()).replace("%reputation", reputationString);

        sender.sendMessage(message);
    }

    @Execute
    @Permission("justreports.reputation.other")
    public void execute(@Context CommandSender sender, String target) {
        double reputation = ReputationManager.getReputationByName(target);

        String reputationString = String.format("%.2f", reputation);

        String message = MessageManager.getStringFromConfig("messages.other-reputation");

        message = message.replace("%player", target).replace("%reputation", reputationString);

        sender.sendMessage(message);
    }

}
