package me.fortibrine.justreports.gui;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import me.fortibrine.justreports.config.ItemConfig;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;
import java.util.Optional;

@Getter
@ConfigSerializable
public class ReportListMenuConfig {
    private final String title = "Reports";
    private final String[] hologram = new String[] {
            "X X X X X X X X X",
            "X X X X X X X X X",
            "X X X X X X X X X",
            "X X X X X X X X X",
            "X X X X X X X X X",
            "P . . . . . . . N",
    };

    private final Map<Character, ItemConfig> items = ImmutableMap.of(
            'X', new ItemConfig() {{
                setName("§f%player%");
                setItemType(Optional.of(ItemType.QUESTION));
                setLore(new String[] {
                        "§7Left-click to view report",
                        "§7Right-click to close report"
                });
                setMaterial(Material.PAPER);
                setActions(ImmutableMap.of(
                        ClickType.LEFT, new String[]{
                                "[answer_report]",
                                "[close_inventory]"
                        },
                        ClickType.RIGHT, new String[]{
                                "[close_report]",
                                "[close_inventory]"
                        }
                ));
            }},
            'P', new ItemConfig() {{
                setName("§aPrevious Page");
                setMaterial(Material.ARROW);
                setItemType(Optional.empty());
                setActions(
                        ImmutableMap.of(
                                ClickType.LEFT, new String[]{"[previous_page]"}
                        )
                );
            }},
            'N', new ItemConfig() {{
                setName("§aNext Page");
                setMaterial(Material.ARROW);
                setItemType(Optional.empty());
                setActions(
                        ImmutableMap.of(
                                ClickType.LEFT, new String[]{"[next_page]"}
                        )
                );
            }}
    );

}
