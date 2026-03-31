package me.fortibrine.justreports.gui.config;

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
    private final String title = "§6✦ Reports Management ✦";
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
                setName("§b📋 %player%");
                setItemType(Optional.of(ItemType.QUESTION));
                setLore(new String[] {
                        "§7━━━━━━━━━━━━━━━━━━━━",
                        "§6❓ Question§7:",
                        "§f%question%",
                        "§7━━━━━━━━━━━━━━━━━━━━",
                        "§f✓ §7Left-click to view report",
                        "§c✗ §7Right-click to close report",
                        "§7━━━━━━━━━━━━━━━━━━━━"
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
                setName("§6◀ Previous Page");
                setMaterial(Material.ARROW);
                setItemType(Optional.empty());
                setLore(new String[] {
                        "§7Click to go to previous page"
                });
                setActions(
                        ImmutableMap.of(
                                ClickType.LEFT, new String[]{"[previous_page]"}
                        )
                );
            }},
            'N', new ItemConfig() {{
                setName("§6Next Page ▶");
                setMaterial(Material.ARROW);
                setItemType(Optional.empty());
                setLore(new String[] {
                        "§7Click to go to next page"
                });
                setActions(
                        ImmutableMap.of(
                                ClickType.LEFT, new String[]{"[next_page]"}
                        )
                );
            }}
    );

}
