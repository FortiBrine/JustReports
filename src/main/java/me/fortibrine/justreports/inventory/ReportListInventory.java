package me.fortibrine.justreports.inventory;

import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ReportListInventory implements InventoryHolder {

    private Inventory inventory;

    public ReportListInventory() {
        inventory = Bukkit.createInventory(this, 54, MessageManager.getStringFromConfig("reports.title"));

        for (Player player : VariableManager.getPlayers()) {
            ItemStack item = VariableManager.getItem(player);

            inventory.addItem(item);
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void onInventoryClick(InventoryClickEvent event) {
        Player admin = (Player) event.getWhoClicked();

        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        Player player = VariableManager.getFirstPlayerByItem(currentItem);

        if (player == null) return;

        if (!admin.hasPermission("justreports.reports.answer")) {
            admin.sendMessage(MessageManager.getStringFromConfig("messages.permission"));
            return;
        }

        if (VariableManager.containsAdmin(player) && !VariableManager.getAdmin(player).equals(admin)) {
            admin.sendMessage(MessageManager.getStringFromConfig("messages.player-has-admin"));
            return;
        }

        if (event.getClick() == ClickType.LEFT) {
            if (VariableManager.getAdmin(player) != null && VariableManager.getAdmin(player).equals(admin)) {
                admin.sendMessage(MessageManager.getStringFromConfig("messages.player-has-admin"));

                return;
            }

            VariableManager.setAdmin(player, admin);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("justreports.see")) continue;

                onlinePlayer.sendMessage(MessageManager.getStringFromConfig("messages.admin-open-report")
                        .replace("%player", player.getName())
                        .replace("%admin", admin.getName())
                        .replace("%question", VariableManager.getQuestion(player)));
            }

            player.sendMessage(MessageManager.getStringFromConfig("messages.answer")
                    .replace("%player", player.getName())
                    .replace("%admin", admin.getName())
                    .replace("%question", VariableManager.getQuestion(player)));

        }
        if (event.getClick() == ClickType.RIGHT) {

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission("justreports.see")) continue;

                onlinePlayer.sendMessage(MessageManager.getStringFromConfig("messages.admin-close-report")
                        .replace("%player", player.getName())
                        .replace("%admin", admin.getName())
                        .replace("%question", VariableManager.getQuestion(player)));
            }

            player.sendMessage(MessageManager.getStringFromConfig("messages.close")
                    .replace("%player", player.getName())
                    .replace("%admin", admin.getName())
                    .replace("%question", VariableManager.getQuestion(player)));

            VariableManager.removeQuestion(player);

            if (VariableManager.getAdmin(player) != null && VariableManager.getAdmin(player).equals(admin)) {
                RateInventory rateInventory = new RateInventory();
                player.closeInventory();
                player.openInventory(rateInventory.getInventory());
            } else {
                VariableManager.removeAdmin(player);
            }

            VariableManager.removeItem(player);

        }

    }
}
