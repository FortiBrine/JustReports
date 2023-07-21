package me.fortibrine.justreports.utils;

import me.fortibrine.justreports.JustReports;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ReputationManager {

    private static JustReports plugin;
    public static void init(JustReports plugin) {
        ReputationManager.plugin = plugin;
    }

    public static void upReputation(Player player, int reputation) {

        List<Integer> listOfReputations = plugin.getConfig().getIntegerList("player." + player.getName());

        listOfReputations.add(reputation);

        plugin.getConfig().set("player." + player.getName(), listOfReputations);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

}
