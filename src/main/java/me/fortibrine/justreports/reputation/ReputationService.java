package me.fortibrine.justreports.reputation;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface ReputationService {
    void addReputation(Player player, int value);
    void addReputationByUniqueId(UUID playerId, int value);
    double getReputation(Player player);
    double getReputationByUniqueId(UUID playerId);
}
