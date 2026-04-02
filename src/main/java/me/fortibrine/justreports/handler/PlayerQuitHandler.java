package me.fortibrine.justreports.handler;

import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import me.fortibrine.justreports.dialog.Dialog;
import me.fortibrine.justreports.dialog.DialogService;
import me.fortibrine.justreports.gui.MenuFactory;
import me.fortibrine.justreports.question.QuestionService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerQuitHandler implements Listener {
    private final QuestionService questionService;
    private final DialogService dialogService;
    private final MessagesConfigProvider messagesConfigProvider;
    private final MenuFactory menuFactory;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Optional<Dialog> dialogOpt = dialogService.getDialog(player);
        if (!dialogOpt.isPresent()) {
            questionService.removeQuestion(player);
            return;
        }

        Dialog dialog = dialogOpt.get();

        if (dialog.isAdmin()) {
            Player admin = player;
            Player target = Bukkit.getPlayer(dialog.getPlayerId());
            if (target == null) {
                dialogService.endDialog(player.getUniqueId());
                return;
            }

            String message = messagesConfigProvider.getConfig().getChat().getAdminQuit()
                    .replace("%admin%", admin.getName());
            target.sendMessage(message);
            dialogService.endDialog(target.getUniqueId());
            questionService.removeQuestion(target);

            menuFactory.openFeedbackRatingMenu(
                    target,
                    admin.getUniqueId()
            );

            return;
        }

        Player target = player;
        Player admin = Bukkit.getPlayer(dialog.getAdminId());

        if (admin == null) {
            dialogService.endDialog(player.getUniqueId());
            return;
        }

        String message = messagesConfigProvider.getConfig().getChat().getPlayerQuit()
                .replace("%player%", target.getName());
        admin.sendMessage(message);

        questionService.removeQuestion(target);
        dialogService.endDialog(admin.getUniqueId());
    }
}
