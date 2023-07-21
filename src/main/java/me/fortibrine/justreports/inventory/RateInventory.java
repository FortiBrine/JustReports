package me.fortibrine.justreports.inventory;

import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.ReputationManager;
import me.fortibrine.justreports.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RateInventory implements InventoryHolder {

    private Inventory inventory;

    public RateInventory() {
        inventory = Bukkit.createInventory(this, 27, MessageManager.getStringFromConfig("rate.title"));

        ItemStack item1 = new ItemStack(Material.matchMaterial(MessageManager.getStringFromConfig("rate.1.material")));
        ItemStack item2 = new ItemStack(Material.matchMaterial(MessageManager.getStringFromConfig("rate.2.material")));
        ItemStack item3 = new ItemStack(Material.matchMaterial(MessageManager.getStringFromConfig("rate.3.material")));
        ItemStack item4 = new ItemStack(Material.matchMaterial(MessageManager.getStringFromConfig("rate.4.material")));
        ItemStack item5 = new ItemStack(Material.matchMaterial(MessageManager.getStringFromConfig("rate.5.material")));

        ItemMeta itemMeta1 = item1.getItemMeta();
        ItemMeta itemMeta2 = item2.getItemMeta();
        ItemMeta itemMeta3 = item3.getItemMeta();
        ItemMeta itemMeta4 = item4.getItemMeta();
        ItemMeta itemMeta5 = item5.getItemMeta();

        itemMeta1.setDisplayName(MessageManager.getStringFromConfig("rate.1.name"));
        itemMeta2.setDisplayName(MessageManager.getStringFromConfig("rate.2.name"));
        itemMeta3.setDisplayName(MessageManager.getStringFromConfig("rate.3.name"));
        itemMeta4.setDisplayName(MessageManager.getStringFromConfig("rate.4.name"));
        itemMeta5.setDisplayName(MessageManager.getStringFromConfig("rate.5.name"));

        itemMeta1.setLore(MessageManager.getStringListFromConfig("rate.1.lore"));
        itemMeta2.setLore(MessageManager.getStringListFromConfig("rate.2.lore"));
        itemMeta3.setLore(MessageManager.getStringListFromConfig("rate.3.lore"));
        itemMeta4.setLore(MessageManager.getStringListFromConfig("rate.4.lore"));
        itemMeta5.setLore(MessageManager.getStringListFromConfig("rate.5.lore"));

        item1.setItemMeta(itemMeta1);
        item2.setItemMeta(itemMeta2);
        item3.setItemMeta(itemMeta3);
        item4.setItemMeta(itemMeta4);
        item5.setItemMeta(itemMeta5);

        inventory.setItem(11, item1);
        inventory.setItem(12, item2);
        inventory.setItem(13, item3);
        inventory.setItem(14, item4);
        inventory.setItem(15, item5);

    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);

        switch (event.getSlot()) {
            case 11:
                ReputationManager.upReputation(VariableManager.getAdmin(player), 1);
                player.closeInventory();
                break;
            case 12:
                ReputationManager.upReputation(VariableManager.getAdmin(player), 2);
                player.closeInventory();
                break;
            case 13:
                ReputationManager.upReputation(VariableManager.getAdmin(player), 3);
                player.closeInventory();
                break;
            case 14:
                ReputationManager.upReputation(VariableManager.getAdmin(player), 4);
                player.closeInventory();
                break;
            case 15:
                ReputationManager.upReputation(VariableManager.getAdmin(player), 5);
                player.closeInventory();
                break;
        }
    }

    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        VariableManager.removeAdmin(player);
        VariableManager.removeItem(player);
        VariableManager.removeQuestion(player);
    }

}
