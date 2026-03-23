package me.fortibrine.justreports.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.reputation.ReputationService;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
@Command(name = "reputation")
@Permission("justreports.reputation")
public class CommandReputation {

    private final ReputationService reputationService;
    private final ConfigManager configManager;

    @Execute
    public void execute(@Context Player sender) {
        double reputation = reputationService.getReputation(sender);
        String message = configManager.getMessageConfig().getCurrentReputation()
                .replace("%reputation%", String.format("%.2f", reputation));
        sender.sendMessage(message);
    }

    @Execute
    @Permission("justreports.reputation.other")
    public void execute(@Context CommandSender sender, @Arg String target) {
        OfflinePlayer targetPlayer = sender.getServer().getOfflinePlayer(target);
        UUID targetUUID = targetPlayer.getUniqueId();

        if (targetPlayer.getName() == null) {
            String message = configManager.getMessageConfig().getPlayerNotFound()
                    .replace("%player%", target);
            sender.sendMessage(message);
            return;
        }

        double reputation = reputationService.getReputationByUniqueId(targetUUID);
        String message = configManager.getMessageConfig().getOtherPlayerReputation()
                .replace("%player%", targetPlayer.getName())
                .replace("%reputation%", String.format("%.2f", reputation));
        sender.sendMessage(message);

    }

}
