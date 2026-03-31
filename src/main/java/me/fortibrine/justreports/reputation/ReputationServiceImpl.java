package me.fortibrine.justreports.reputation;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;
import java.util.UUID;

public class ReputationServiceImpl implements ReputationService {

    private final Plugin plugin;
    private final Dao<PlayerReputationModel, UUID> reputationDao;

    public ReputationServiceImpl(Plugin plugin, ConnectionSource connectionSource) throws SQLException {
        this.plugin = plugin;

        TableUtils.createTableIfNotExists(connectionSource, PlayerReputationModel.class);
        reputationDao = DaoManager.createDao(connectionSource, PlayerReputationModel.class);
    }

    @Override
    public void addReputation(Player player, int value) {
        addReputationByUniqueId(player.getUniqueId(), value);
    }

    @Override
    public void addReputationByUniqueId(UUID playerId, int value) {
        try {
            PlayerReputationModel newModel = new PlayerReputationModel();
            newModel.setPlayerId(playerId);
            newModel.setReputation(value);
            reputationDao.create(newModel);
        } catch (SQLException e) {
            plugin.getLogger().warning("Failed to add reputation for player " + playerId + ": " + e.getMessage());
        }
    }

    @Override
    public double getReputation(Player player) {
        return getReputationByUniqueId(player.getUniqueId());
    }

    @Override
    public double getReputationByUniqueId(UUID playerId) {
        try {
            return reputationDao.queryForEq("player_id", playerId)
                    .stream()
                    .mapToInt(PlayerReputationModel::getReputation)
                    .average()
                    .orElse(0);
        } catch (SQLException e) {
            plugin.getLogger().warning("Failed to get reputation for player " + playerId.toString() + ": " + e.getMessage());
            return 0;
        }
    }

}
