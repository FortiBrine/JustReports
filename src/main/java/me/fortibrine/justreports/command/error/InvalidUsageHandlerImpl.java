package me.fortibrine.justreports.command.error;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.MessagesConfig;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class InvalidUsageHandlerImpl implements InvalidUsageHandler<CommandSender> {

    private final MessagesConfigProvider messagesConfigProvider;

    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        MessagesConfig.UsageMessages usageMessagesSection = messagesConfigProvider.getConfig().getUsage();
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
