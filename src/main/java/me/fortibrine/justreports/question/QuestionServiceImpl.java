package me.fortibrine.justreports.question;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final ConfigManager configManager;

    private final Map<UUID, String> questions = Collections.synchronizedMap(new LinkedHashMap<>());
    private final BiMap<UUID, UUID> adminAssignments = HashBiMap.create();

    @Override
    public boolean hasQuestion(Player player) {
        return questions.containsKey(player.getUniqueId());
    }

    @Override
    public Optional<String> getQuestion(Player player) {
        return Optional.ofNullable(questions.get(player.getUniqueId()));
    }

    @Override
    public Map<UUID, String> getAllQuestions() {
        synchronized (questions) {
            return Collections.unmodifiableMap(new LinkedHashMap<>(questions));
        }
    }

    @Override
    public void setQuestion(Player player, String question) {
        questions.put(player.getUniqueId(), question);
    }

    @Override
    public void removeQuestion(Player player) {
        questions.remove(player.getUniqueId());
    }

    @Override
    public boolean hasAssignedAdmin(Player player) {
        return adminAssignments.containsKey(player.getUniqueId());
    }

    @Override
    public Optional<Player> getAssignedAdmin(Player player) {
        UUID playerId = player.getUniqueId();
        if (!adminAssignments.containsKey(playerId)) {
            return Optional.empty();
        }

        UUID adminId = adminAssignments.get(playerId);
        if (adminId == null) {
            return Optional.empty();
        }

        Player admin = Bukkit.getPlayer(adminId);
        return Optional.ofNullable(admin);
    }

    @Override
    public void assignAdmin(Player player, Player admin) {
        adminAssignments.put(player.getUniqueId(), admin.getUniqueId());
    }

    @Override
    public void unassignAdmin(Player player) {
        adminAssignments.remove(player.getUniqueId());
    }

    @Override
    public Optional<Player> getPlayerByAdmin(Player admin) {
        UUID adminId = admin.getUniqueId();
        if (!adminAssignments.containsValue(adminId)) {
            return Optional.empty();
        }

        UUID playerId = adminAssignments.inverse().get(adminId);
        if (playerId == null) {
            return Optional.empty();
        }

        Player player = Bukkit.getPlayer(playerId);
        return Optional.ofNullable(player);
    }

    @Override
    public Set<Player> getPlayersWithQuestions() {
        synchronized (questions) {
            return questions.keySet().stream()
                    .map(Bukkit::getPlayer)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isAdminBusy(Player admin) {
        return adminAssignments.containsValue(admin.getUniqueId());
    }

    @Override
    public void closeQuestion(Player player) {
        removeQuestion(player);
        unassignAdmin(player);
    }

    @Override
    public void notifyAdmins(Player player, String message) {
        String formattedMessage = configManager.getMessageConfig().getAdminMessagesSection().getNewReport()
                .replace("%player%", player.getName())
                .replace("%question%", message);

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("justreports.see"))
                .forEach(p -> p.sendMessage(formattedMessage));
    }

    @Override
    public int getQuestionCount() {
        return questions.size();
    }
}
