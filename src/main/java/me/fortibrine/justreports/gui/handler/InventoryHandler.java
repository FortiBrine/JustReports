package me.fortibrine.justreports.gui.handler;

import me.fortibrine.justreports.gui.ReportListMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class InventoryHandler implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;

        InventoryHolder holder = inventory.getHolder();
        if (holder == null) return;

        if (holder instanceof ReportListMenu) {
            event.setCancelled(true);

            ReportListMenu menu = (ReportListMenu) holder;
            menu.handleClick(event);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        Inventory inventory = event.getInventory();

        InventoryHolder holder = inventory.getHolder();
        if (holder == null) return;

        if (holder instanceof ReportListMenu) {
            event.setCancelled(true);
        }
    }

}
