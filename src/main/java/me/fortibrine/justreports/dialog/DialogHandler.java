package me.fortibrine.justreports.dialog;

import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.MessagesConfig;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class DialogHandler implements Listener {
    private final DialogService dialogService;
    private final MessagesConfigProvider messagesConfigProvider;

    @EventHandler
    public void handlePlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Optional<Dialog> dialogOpt = dialogService.getDialog(player);

        if (!dialogOpt.isPresent()) return;
        Dialog dialog = dialogOpt.get();

        MessagesConfig.ChatMessages chatMessages = messagesConfigProvider.getConfig().getChat();

        if (dialog.isAdmin()) {
            Player target = Bukkit.getPlayer(dialog.getPlayerId());
            Player admin = player;
            if (target == null) {
                dialogService.endDialog(player.getUniqueId());
                return;
            }

            String message = chatMessages.getAdminMessageFormat()
                    .replace("%player%", admin.getName())
                    .replace("%message%", event.getMessage());

            target.sendMessage(message);
            admin.sendMessage(message);
            return;
        }

        Player target = player;
        Player admin = Bukkit.getPlayer(dialog.getAdminId());

        if (admin == null) {
            dialogService.endDialog(player.getUniqueId());
            return;
        }

        String message = chatMessages.getPlayerMessageFormat()
                .replace("%player%", target.getName())
                .replace("%message%", event.getMessage());

        target.sendMessage(message);
        admin.sendMessage(message);
    }
}
