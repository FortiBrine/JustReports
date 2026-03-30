package me.fortibrine.justreports.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;

import java.io.IOException;

@RequiredArgsConstructor
@Command(name = "justreports")
public class CommandReload {

    private final ConfigManager configManager;
    private final MessagesConfigProvider messagesConfigProvider;

    @Execute(name = "reload")
    @Permission("justreports.reload")
    public void execute() {
        try {
            configManager.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
