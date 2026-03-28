package me.fortibrine.justreports.config;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@ConfigSerializable
public class ItemConfig {
    private String name;
    private Optional<ItemType> itemType;
    private String[] lore;
    private Material material;
    private Map<ClickType, String[]> actions;

    public enum ItemType {
        QUESTION,
    }
}
