package me.fortibrine.justreports.question;

import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final MessagesConfigProvider messagesConfigProvider;

    private final Map<UUID, String> questions = Collections.synchronizedMap(new LinkedHashMap<>());

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
    public Set<Player> getPlayersWithQuestions() {
        synchronized (questions) {
            return questions.keySet().stream()
                    .map(Bukkit::getPlayer)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public void closeQuestion(Player player) {
        removeQuestion(player);
    }

    @Override
    public void notifyAdmins(Player player, String message) {
        String formattedMessage = messagesConfigProvider.getConfig().getAdmin().getNewReport()
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
