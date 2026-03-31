package me.fortibrine.justreports.gui;

import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import me.fortibrine.justreports.dialog.DialogService;
import me.fortibrine.justreports.question.QuestionService;
import me.fortibrine.justreports.reputation.ReputationService;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class MenuFactory {

    private final QuestionService questionService;
    private final ConfigManager configManager;
    private final MessagesConfigProvider messagesConfigProvider;
    private final DialogService dialogService;
    private final ReputationService reputationService;

    public void openReportListMenu(Player player) {
        new ReportListMenu(
                player,
                questionService,
                messagesConfigProvider,
                configManager.getReportListMenuConfig(),
                dialogService
        ).open(0);
    }

    public void openFeedbackRatingMenu(Player player, UUID targetPlayerId) {
        new FeedbackRatingMenu(
                player,
                targetPlayerId,
                reputationService,
                configManager.getFeedbackRatingMenuConfig()
        ).open();
    }

}
