package me.fortibrine.justreports.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VariableManager {

    private static Map<Player, String> questions;
    private static Map<Player, Player> playerAdmin;
    private static Map<Player, ItemStack> playerItem;

    public static void init() {
        questions = new HashMap<>();
        playerAdmin = new HashMap<>();
        playerItem = new HashMap<>();
    }

    public static String getQuestion(Player player) {
        return questions.get(player);
    }

    public static boolean containsQuestion(Player player) {
        return questions.containsKey(player);
    }

    public static void setQuestion(Player player, String question) {
        questions.put(player, question);
    }

    public static void removeQuestion(Player player) {
        questions.remove(player);
    }

    public static Player getAdmin(Player player) {
        return playerAdmin.get(player);
    }

    public static void setAdmin(Player player, Player admin) {
        playerAdmin.put(player, admin);
    }

    public static void removeAdmin(Player player) {
        playerAdmin.remove(player);
    }

    public static ItemStack getItem(Player player) {
        return playerItem.get(player);
    }

    public static void putItem(Player player, ItemStack item) {
        playerItem.put(player, item);
    }

    public static void removeItem(Player player) {
        playerItem.remove(player);
    }

    public static Player getFirstPlayerByItem(ItemStack item) {
        Player player = null;
        for (Player everyPlayer : playerItem.keySet()) {
            if (playerItem.get(everyPlayer).equals(item)) {
                player = everyPlayer;
                break;
            }
        }

        return player;
    }

    public static boolean containsAdmin(Player player) {
        return playerAdmin.containsKey(player);
    }

    public static Set<Player> getPlayers() {
        return playerItem.keySet();
    }

    public static Player getPlayerByAdmin(Player admin) {
        for (Player player : playerAdmin.keySet()) {
            if (playerAdmin.get(player).equals(admin)) {
                return player;
            }
        }
        return null;
    }
}
