package me.fortibrine.justreports.gui;

import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ItemConfig;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportListMenu implements InventoryHolder {

    private final Player player;
    private final QuestionService questionService;
    private final MessagesConfigProvider messagesConfigProvider;
    private final ReportListMenuConfig config;

    private int page;
    private int[] questionSlots;
    private Map<UUID, String> pageQuestions;

    private Inventory inventory;

    public void open(int page) {
        String[] hologramLines = config.getHologram();
        int slots = hologramLines.length * 9;

        inventory = Bukkit.createInventory(
                this,
                slots,
                config.getTitle()
        );

        load(page);

        renderDefaultItems();
        renderQuestionItems();

        player.openInventory(inventory);
    }

    private void load(int page) {
        this.page = page;

        String[] hologramLines = config.getHologram();
        Map<Character, ItemConfig> items = config.getItems();

        int[] temp = new int[hologramLines.length * 9];
        int count = 0;
        int slot = 0;

        for (String line : hologramLines) {
            for (int c : line.chars().toArray()) {
                if (c == ' ') continue;

                ItemConfig itemConfig = items.get((char) c);
                if (itemConfig == null) continue;

                if (ItemConfig.ItemType.QUESTION == itemConfig.getItemType().orElse(null)) {
                    temp[count++] = slot;
                }
                slot++;
            }
        }

        questionSlots = Arrays.copyOf(temp, count);

        pageQuestions = questionService.getAllQuestions().entrySet().stream()
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
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == ' ') continue;

                ItemConfig itemConfig = items.get(c);

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

    private void renderQuestionItems() {
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
            if (targetPlayer == null) {
                index++;
                continue;
            }

            String playerName = targetPlayer.getName();
            itemMeta.setDisplayName(itemConfig.getName().replace("%player%", playerName));
            itemMeta.setLore(Arrays.stream(itemConfig.getLore())
                    .map(line -> line
                            .replace("%player%", playerName)
                            .replace("%question%", entry.getValue()))
                    .collect(Collectors.toList()));

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

        for (String action : actions) {
            if ("[previous_page]".equals(action) && hasPreviousPage()) {
                open(page - 1);
            } else if ("[next_page]".equals(action) && hasNextPage()) {
                open(page + 1);
            } else if ("[close_inventory]".equals(action)) {
                player.closeInventory();
            } else if (action.startsWith("[answer_report]")) {
                Player targetPlayer = Bukkit.getPlayer(getQuestionAtSlot(slot));

                if (targetPlayer == null) {
                    player.sendMessage(messagesConfigProvider.getConfig().getAdmin().getPlayerNotFound());
                    return;
                }

                if (!questionService.hasQuestion(targetPlayer)) {
                    return;
                }

                if (true) {
                    player.sendMessage(messagesConfigProvider.getConfig().getAdmin().getCannotStartDialogAlreadyInDialog());
                    targetPlayer.sendMessage(messagesConfigProvider.getConfig().getPlayer().getReportTakenByAdmin()
                            .replace("%admin%", player.getName()));
                    return;
                }

                targetPlayer.sendMessage(messagesConfigProvider.getConfig().getPlayer().getReportTakenByAdmin());
                player.sendMessage(messagesConfigProvider.getConfig().getAdmin().getReportTaken()
                        .replace("%player%", targetPlayer.getName()));
                //questionService.assignAdmin(targetPlayer, player);
            } else if (action.startsWith("[close_report]")) {
                Player targetPlayer = Bukkit.getPlayer(getQuestionAtSlot(slot));
                if (targetPlayer != null) {
                    player.sendMessage(messagesConfigProvider.getConfig().getAdmin().getReportClosed()
                            .replace("%player%", targetPlayer.getName()));
                    questionService.closeQuestion(targetPlayer);
                }
            }
        }
    }

    public boolean hasPreviousPage() {
        return page > 0;
    }

    public boolean hasNextPage() {
        int totalQuestions = questionService.getQuestionCount();
        int questionsPerPage = questionSlots.length;
        return (page + 1) * questionsPerPage < totalQuestions;
    }

    public UUID getQuestionAtSlot(int slot) {
        int index = Arrays.binarySearch(questionSlots, slot);
        if (index < 0) return null;

        List<UUID> questionIds = new ArrayList<>(pageQuestions.keySet());
        if (index >= questionIds.size()) return null;

        return questionIds.get(index);
    }

}
