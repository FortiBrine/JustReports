package me.fortibrine.justreports.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class RateInventory implements InventoryHolder {

    private Inventory inventory;

    public RateInventory() {

    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void onInventoryClick(InventoryClickEvent event) {

    }

}
