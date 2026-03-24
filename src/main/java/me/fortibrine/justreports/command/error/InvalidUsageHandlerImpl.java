package me.fortibrine.justreports.command.error;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.config.MessagesConfig;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class InvalidUsageHandlerImpl implements InvalidUsageHandler<CommandSender> {

    private final ConfigManager configManager;

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        MessagesConfig.UsageMessagesSection usageMessagesSection = configManager.getMessageConfig().getUsageMessagesSection();
        switch (invocation.name()) {
            case "report":
                invocation.sender().sendMessage(usageMessagesSection.getReportUsage());
                break;
            case "reports":
                invocation.sender().sendMessage(usageMessagesSection.getReportsUsage());
                break;
            case "reputation":
                invocation.sender().sendMessage(usageMessagesSection.getReputationUsage());
                break;
        }
    }
}
