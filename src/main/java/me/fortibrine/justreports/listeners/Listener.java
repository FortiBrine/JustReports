package me.fortibrine.justreports.listeners;

import me.fortibrine.justreports.JustReports;
import me.fortibrine.justreports.inventory.RateInventory;
import me.fortibrine.justreports.inventory.ReportListInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
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

}
