package me.fortibrine.justreports.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.join.Join;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.question.QuestionService;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Command(name = "report")
@Permission("justreports.report")
public class CommandReport {

    private final QuestionService questionService;
    private final ConfigManager configManager;

    @Execute
    public void execute(@Context Player player, @Join String question) {
        if (questionService.hasQuestion(player)) {
            player.sendMessage(configManager.getMessageConfig().getAlreadySentReport());
            return;
        }

        player.sendMessage(configManager.getMessageConfig().getReportSent());
        questionService.setQuestion(player, question);
        questionService.notifyAdmins(player, question);
    }

}
