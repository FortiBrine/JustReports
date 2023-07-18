package me.fortibrine.justreports.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ReportListInventory implements InventoryHolder {

    private Inventory inventory;

    public ReportListInventory() {

    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void onInventoryClick(InventoryClickEvent event) {

    }
}
