package me.fortibrine.justreports.dialog;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public interface DialogService {
    Optional<Dialog> getDialog(UUID playerId);
    Optional<Dialog> getDialog(Player player);
    boolean isInDialog(UUID playerId);
    boolean beginDialog(UUID playerId, UUID adminId);
    void endDialog(UUID playerId);
}
