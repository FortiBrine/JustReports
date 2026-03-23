package me.fortibrine.justreports.utils;

import me.fortibrine.justreports.JustReportsPlugin;
import org.bukkit.entity.Player;

import java.util.List;

public class ReputationManager {

    private static JustReportsPlugin plugin;
    public static void init(JustReportsPlugin plugin) {
        ReputationManager.plugin = plugin;
    }

    public static void upReputation(Player player, int reputation) {

        List<Integer> listOfReputations = plugin.getConfig().getIntegerList("player." + player.getName());

        listOfReputations.add(reputation);

        plugin.getConfig().set("player." + player.getName(), listOfReputations);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public static double getReputation(Player player) {
        return getReputationByName(player.getName());
    }

    public static double getReputationByName(String playerName) {
        List<Integer> listOfReputations = plugin.getConfig().getIntegerList("player." + playerName);

        if (listOfReputations.isEmpty()) {
            return 0;
        }

        return 1.0 * listOfReputations.stream().reduce(0, Integer::sum) / listOfReputations.size();

    }

}
