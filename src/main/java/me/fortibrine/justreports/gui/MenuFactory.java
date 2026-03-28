package me.fortibrine.justreports.gui;

import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.question.QuestionService;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MenuFactory {

    private final QuestionService questionService;
    private final ConfigManager configManager;

    public void openReportListMenu(Player player) {
        new ReportListMenu(
                player,
                questionService,
                configManager.getReportListMenuConfig()
        ).open(0);
    }

}
