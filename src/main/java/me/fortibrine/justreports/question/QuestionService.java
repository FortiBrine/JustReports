package me.fortibrine.justreports.question;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface QuestionService {
    boolean hasQuestion(Player player);
    Optional<String> getQuestion(Player player);
    Map<UUID, String> getAllQuestions();
    void setQuestion(Player player, String question);
    void removeQuestion(Player player);
    void removeQuestion(UUID playerId);
    Set<Player> getPlayersWithQuestions();
    void notifyAdmins(Player player, String message);
    int getQuestionCount();
}
