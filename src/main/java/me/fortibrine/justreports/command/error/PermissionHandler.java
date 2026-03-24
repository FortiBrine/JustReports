package me.fortibrine.justreports.command.error;

import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.permission.MissingPermissions;
import dev.rollczi.litecommands.permission.MissingPermissionsHandler;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class PermissionHandler implements MissingPermissionsHandler<CommandSender> {

    private final ConfigManager configManager;

    @Override
    public void handle(Invocation<CommandSender> invocation, MissingPermissions missingPermissions, ResultHandlerChain<CommandSender> chain) {
        invocation.sender().sendMessage(configManager.getMessageConfig().getPermissionDenied());
    }
}
