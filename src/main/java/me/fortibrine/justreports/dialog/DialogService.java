package me.fortibrine.justreports.dialog;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

@RequiredArgsConstructor
public class DialogService implements Listener {

    private final MessagesConfigProvider messagesConfigProvider;
    private final BiMap<UUID, UUID> activeDialog = HashBiMap.create();

    public boolean isInDialog(UUID playerId) {
        return activeDialog.containsKey(playerId) || activeDialog.containsValue(playerId);
    }

    public void beginDialog(UUID adminId, UUID playerId) {
        activeDialog.put(adminId, playerId);
    }

    public void endDialog(UUID adminId) {
        activeDialog.remove(adminId);
    }

    @EventHandler
    public void onMessage(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        String chatMessage = event.getMessage();

        if (activeDialog.containsValue(playerId)) {
            UUID adminId = activeDialog.inverse().get(playerId);
            Player admin = Bukkit.getPlayer(adminId);

            if (admin == null) {
                activeDialog.remove(adminId);
                return;
            }

            event.setCancelled(true);

            String playerMessage = messagesConfigProvider.getConfig().getChatSection().getPlayerMessageFormat()
                    .replace("%player%", admin.getName())
                    .replace("%message", chatMessage);

            player.sendMessage(playerMessage);
            admin.sendMessage(playerMessage);
        }

        if (activeDialog.containsKey(playerId)) {
            UUID targetPlayerId = activeDialog.get(playerId);
            Player targetPlayer = Bukkit.getPlayer(targetPlayerId);

            if (targetPlayer == null) {
                activeDialog.remove(playerId);
                return;
            }

            event.setCancelled(true);

            String adminMessage = messagesConfigProvider.getConfig().getChatSection().getAdminMessageFormat()
                    .replace("%player%", player.getName())
                    .replace("%message", chatMessage);

            player.sendMessage(adminMessage);
            targetPlayer.sendMessage(adminMessage);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        activeDialog.remove(playerId);

        if (activeDialog.containsValue(playerId)) {
            activeDialog.inverse().remove(playerId);
        }
    }

}
