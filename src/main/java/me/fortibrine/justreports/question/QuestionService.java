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
    boolean hasAssignedAdmin(Player player);
    Optional<Player> getAssignedAdmin(Player player);
    void assignAdmin(Player player, Player admin);
    void unassignAdmin(Player player);
    Optional<Player> getPlayerByAdmin(Player admin);
    Set<Player> getPlayersWithQuestions();
    boolean isAdminBusy(Player admin);
    void closeQuestion(Player player);
    void notifyAdmins(Player player, String message);
}
