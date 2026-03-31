package me.fortibrine.justreports.gui.config;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import me.fortibrine.justreports.config.ItemConfig;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.Map;

@Getter
@ConfigSerializable
public class FeedbackRatingMenuConfig {
    private final String title = "§6✦ Feedback Rating ✦";
    private final String[] hologram = new String[] {
            ". . . . . . . . .",
            "A . B . C . D . E",
            ". . . . . . . . .",
            ". . . . X . . . .",
            ". . . . . . . . .",
    };

    private final Map<Character, ItemConfig> items = ImmutableMap.<Character, ItemConfig>builder()
            .put('A', new ItemConfig() {{
                setName("§c✦ 1 Star ✦");
                setLore(new String[] {
                        "§7Click to rate 1 star",
                        "§cVery Poor"
                });
                setMaterial(Material.RED_TERRACOTTA);
                setActions(ImmutableMap.of(
                        ClickType.LEFT, new String[]{
                                "[star] 1"
                        }
                ));
            }})
            .put('B', new ItemConfig() {{
                setName("§6✦ 2 Stars ✦");
                setLore(new String[] {
                        "§7Click to rate 2 stars",
                        "§6Poor"
                });
                setMaterial(Material.ORANGE_TERRACOTTA);
                setActions(ImmutableMap.of(
                        ClickType.LEFT, new String[]{
                                "[star] 2"
                        }
                ));
            }})
            .put('C', new ItemConfig() {{
                setName("§e✦ 3 Stars ✦");
                setLore(new String[] {
                        "§7Click to rate 3 stars",
                        "§eAverage"
                });
                setMaterial(Material.YELLOW_TERRACOTTA);
                setActions(ImmutableMap.of(
                        ClickType.LEFT, new String[]{
                                "[star] 3"
                        }
                ));
            }})
            .put('D', new ItemConfig() {{
                setName("§a✦ 4 Stars ✦");
                setLore(new String[] {
                        "§7Click to rate 4 stars",
                        "§aGood"
                });
                setMaterial(Material.LIME_TERRACOTTA);
                setActions(ImmutableMap.of(
                        ClickType.LEFT, new String[]{
                                "[star] 4"
                        }
                ));
            }})
            .put('E', new ItemConfig() {{
                setName("§b✦ 5 Stars ✦");
                setLore(new String[] {
                        "§7Click to rate 5 stars",
                        "§bExcellent"
                });
                setMaterial(Material.CYAN_TERRACOTTA);
                setActions(ImmutableMap.of(
                        ClickType.LEFT, new String[]{
                                "[star] 5"
                        }
                ));
            }})
            .put('X', new ItemConfig() {{
                setName("§4✕ No Rating");
                setLore(new String[] {
                        "§7Click to cancel the rating",
                        "§4Skip feedback"
                });
                setMaterial(Material.BARRIER);
                setActions(ImmutableMap.of(
                        ClickType.LEFT, new String[]{
                                "[close_inventory]"
                        }
                ));
            }})
            .build();

}
