package me.fortibrine.justreports.gui;

import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ItemConfig;
import me.fortibrine.justreports.question.QuestionService;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jspecify.annotations.NonNull;

import java.util.*;

@RequiredArgsConstructor
public class ReportListMenu implements InventoryHolder {

    private final Player player;
    private final QuestionService questionService;
    private final ReportListMenuConfig config;
    private final Map<UUID, String> questions = new LinkedHashMap<>();

    private Inventory inventory;

    public void loadQuestions() {
        questions.clear();
        questions.putAll(questionService.getAllQuestions());
    }

    public void open(int page) {
        loadQuestions();

        String[] hologramLines = config.getHologram();
        Map<Character, ItemConfig> items = config.getItems();
        int slots = hologramLines.length * 9;

        inventory = Bukkit.createInventory(
                this,
                slots,
                config.getTitle()
        );

        int[] questionSlots = getQuestionSlots();
        Map<UUID, String> pageQuestions = getPageQuestions(page, questionSlots);

        renderDefaultItems();
        renderQuestionItems(questionSlots, pageQuestions);

        player.openInventory(inventory);
    }

    private int[] getQuestionSlots() {
        String[] hologramLines = config.getHologram();
        Map<Character, ItemConfig> items = config.getItems();

        List<Integer> questionSlots = new ArrayList<>();
        int slot = 0;
        for (String line : hologramLines) {
            for (int c : line.chars().toArray()) {
                if (c == ' ') continue;

                ItemConfig itemConfig = items.get((char) c);
                if (itemConfig == null) continue;

                if (ItemConfig.ItemType.QUESTION == itemConfig.getItemType().orElse(null)) {
                    questionSlots.add(slot);
                }
                slot++;
            }
        }

        return questionSlots.stream().mapToInt(Integer::intValue).toArray();
    }

    private Map<UUID, String> getPageQuestions(int page, int[] questionSlots) {
        return questions.entrySet().stream()
                .skip((long) questionSlots.length * page)
                .limit(questionSlots.length)
                .collect(
                        LinkedHashMap::new,
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()),
                        LinkedHashMap::putAll
                );
    }

    private void renderDefaultItems() {
        String[] hologramLines = config.getHologram();
        Map<Character, ItemConfig> items = config.getItems();

        int slot = 0;
        for (String line : hologramLines) {
            for (int c : line.chars().toArray()) {
                if (c == ' ') continue;

                ItemConfig itemConfig = items.get((char) c);

                if (itemConfig == null) {
                    slot++;
                    continue;
                }
                if (itemConfig.getItemType().orElse(null) == ItemConfig.ItemType.QUESTION) {
                    slot++;
                    continue;
                }

                ItemStack itemStack = new ItemStack(itemConfig.getMaterial());
                ItemMeta itemMeta = itemStack.getItemMeta();

                itemMeta.setDisplayName(itemConfig.getName());
                itemMeta.setLore(Arrays.asList(itemConfig.getLore()));

                itemStack.setItemMeta(itemMeta);
                inventory.setItem(slot, itemStack);
                slot++;
            }
        }
    }

    private void renderQuestionItems(int[] questionSlots, Map<UUID, String> pageQuestions) {
        int index = 0;

        ItemConfig itemConfig = config.getItems().values().stream()
                .filter(configItem -> configItem.getItemType().orElse(null) == ItemConfig.ItemType.QUESTION)
                .findFirst().orElse(null);

        if (itemConfig == null) return;

        for (Map.Entry<UUID, String> entry : pageQuestions.entrySet()) {
            if (index >= questionSlots.length) break;

            ItemStack itemStack = new ItemStack(itemConfig.getMaterial());
            ItemMeta itemMeta = itemStack.getItemMeta();

            Player targetPlayer = Bukkit.getPlayer(entry.getKey());
            if (targetPlayer == null) continue;

            String playerName = targetPlayer.getName();
            itemMeta.setDisplayName(itemConfig.getName().replace("%player%", playerName));
            itemMeta.setLore(Arrays.asList(itemConfig.getLore()));

            itemStack.setItemMeta(itemMeta);
            inventory.setItem(questionSlots[index], itemStack);

            index++;
        }
    }

    @Override
    public @NonNull Inventory getInventory() {
        return inventory;
    }

    public void handleClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        int row = slot / 9;
        if (row >= config.getHologram().length) return;

        int col = slot % 9;

        String line = config.getHologram()[row];
        if (col >= line.length()) return;

        char c = line.replace(' ', (char) 0).charAt(col);

        ItemConfig itemConfig = config.getItems().get(c);
        if (itemConfig == null) return;

        String[] actions = itemConfig.getActions().get(event.getClick());
        if (actions == null) return;

        int[] questionSlots = getQuestionSlots();

        for (String action : actions) {

        }
    }

}
