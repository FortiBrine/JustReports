package me.fortibrine.justreports.listeners;

import me.fortibrine.justreports.JustReports;
import me.fortibrine.justreports.inventory.RateInventory;
import me.fortibrine.justreports.inventory.ReportListInventory;
import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Listener implements org.bukkit.event.Listener {

    private JustReports plugin;
    public Listener(JustReports plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();

        if (inventory == null) return;
        if (inventory.getHolder() == null) return;

        InventoryHolder inventoryHolder = inventory.getHolder();

        if (inventoryHolder instanceof RateInventory) {
            ((RateInventory) inventoryHolder).onInventoryClick(event);
        }
        if (inventoryHolder instanceof ReportListInventory) {
            ((ReportListInventory) inventoryHolder).onInventoryClick(event);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player playerOrAdmin = event.getPlayer();

        Player player = VariableManager.getPlayerByAdmin(playerOrAdmin);

        if (player != null) {
            event.setCancelled(true);

            playerOrAdmin.sendMessage(MessageManager.getStringFromConfig("messages.admin")
                    .replace("%player", player.getName())
                    .replace("%admin", playerOrAdmin.getName())
                    .replace("%question", VariableManager.getQuestion(player))
                    .replace("%message", event.getMessage()));
            player.sendMessage(MessageManager.getStringFromConfig("messages.admin")
                    .replace("%player", player.getName())
                    .replace("%admin", playerOrAdmin.getName())
                    .replace("%question", VariableManager.getQuestion(player))
                    .replace("%message", event.getMessage()));

        } else if (VariableManager.containsAdmin(playerOrAdmin)) {
            Player admin = VariableManager.getAdmin(playerOrAdmin);

            event.setCancelled(true);

            playerOrAdmin.sendMessage(MessageManager.getStringFromConfig("messages.player")
                    .replace("%player", playerOrAdmin.getName())
                    .replace("%admin", admin.getName())
                    .replace("%question", VariableManager.getQuestion(playerOrAdmin))
                    .replace("%message", event.getMessage()));
            admin.sendMessage(MessageManager.getStringFromConfig("messages.player")
                    .replace("%player", playerOrAdmin.getName())
                    .replace("%admin", admin.getName())
                    .replace("%question", VariableManager.getQuestion(playerOrAdmin))
                    .replace("%message", event.getMessage()));

        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (VariableManager.containsQuestion(player)) {

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("justreports.see")) continue;

                onlinePlayer.sendMessage(MessageManager.getStringFromConfig("messages.player-name")
                        .replace("%player", player.getName())
                        .replace("%question", VariableManager.getQuestion(player)));

            }

            VariableManager.removeItem(player);
            VariableManager.removeQuestion(player);
            VariableManager.removeAdmin(player);
        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        if (inventory.getHolder() == null) return;

        InventoryHolder inventoryHolder = inventory.getHolder();
        if (inventoryHolder instanceof RateInventory) {
            ((RateInventory) inventoryHolder).onInventoryClose(event);
        }
    }

}
