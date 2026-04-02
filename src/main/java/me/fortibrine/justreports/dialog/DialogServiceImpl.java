package me.fortibrine.justreports.dialog;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {
    private final Map<UUID, UUID> adminToPlayer = new ConcurrentHashMap<>();
    private final Map<UUID, UUID> playerToAdmin = new ConcurrentHashMap<>();
    private final Object dialogLock = new Object();

    @Override
    public Optional<Dialog> getDialog(UUID playerId) {
        UUID adminId = playerToAdmin.get(playerId);

        if (adminId != null) {
            return Optional.of(new Dialog(
                    playerId,
                    adminId,
                    false
            ));
        }

        UUID targetPlayerId = adminToPlayer.get(playerId);
        if (targetPlayerId != null) {
            return Optional.of(new Dialog(
                    targetPlayerId,
                    playerId,
                    true
            ));
        }

        return Optional.empty();
    }

    @Override
    public Optional<Dialog> getDialog(Player player) {
        return getDialog(player.getUniqueId());
    }

    @Override
    public boolean isInDialog(UUID playerId) {
        return getDialog(playerId).isPresent();
    }

    @Override
    public boolean beginDialog(UUID playerId, UUID adminId) {
        synchronized (dialogLock) {
            if (adminToPlayer.containsKey(adminId) || playerToAdmin.containsKey(playerId)) {
                return false;
            }

            if (adminToPlayer.containsValue(playerId) || playerToAdmin.containsValue(adminId)) {
                return false;
            }

            adminToPlayer.put(adminId, playerId);
            playerToAdmin.put(playerId, adminId);
            return true;
        }
    }

    @Override
    public void endDialog(UUID playerId) {
        synchronized (dialogLock) {
            UUID targetPlayerId = adminToPlayer.remove(playerId);
            if (targetPlayerId != null) {
                playerToAdmin.remove(targetPlayerId);
                return;
            }

            UUID adminId = playerToAdmin.remove(playerId);
            if (adminId != null) {
                adminToPlayer.remove(adminId);
            }
        }
    }
}
