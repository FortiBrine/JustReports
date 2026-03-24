package me.fortibrine.justreports.config;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@ConfigSerializable
public class MessagesConfig {
    private String reportSent = "Your report has been sent to the admins.";
    private String permissionDenied = "You don't have permission to use this command.";
    private String alreadyInChat = "You are already in the chat with an admin.";
    private String alreadySentReport = "You have already sent a report. Please wait for an admin to respond.";
    private String alreadyHasAdmin = "An admin is already responding to your report. You cannot take the report.";
    private String cannotTakeOwnReport = "You cannot take your own report.";
    private String reportTakenByAdmin = "Your report has been taken by %admin%. Please wait for them to respond.";

    @Comment("The format for messages in the admin-player chat. Use %admin% for the admin's name, %player% for the player's name, and %message% for the message content.")
    @Setting("chat")
    private ChatSection chatSection = new ChatSection();

    @Getter
    @ConfigSerializable
    public static class ChatSection {
        private String adminMessageFormat = "[Admin] %admin%: %message%";
        private String playerMessageFormat = "[Player] %player%: %message%";
    }

    @Comment("The messages sent to admins when a new report is submitted, taken, or closed. Use %player% for the player's name, %question% for the report question, and %admin% for the admin's name.")
    @Setting("admin")
    private AdminMessagesSection adminMessagesSection = new AdminMessagesSection();

    @Getter
    @ConfigSerializable
    public static class AdminMessagesSection {
        private String newReport = "New report from %player%: %question%";
        private String reportClosed = "The report from %player% has been closed.";
        private String reportTaken = "You have taken the report from %player%.";
        private String reportTakenByOtherAdmin = "%admin% has taken the report from %player%.";
    }

    @Comment("The usage messages for the commands.")
    @Setting("usage")
    private UsageMessagesSection usageMessagesSection = new UsageMessagesSection();

    @Getter
    @ConfigSerializable
    public static class UsageMessagesSection {
        private String reportUsage = "Usage: /report <question>";
        private String reportsUsage = "Usage: /reports";
        private String reputationUsage = "Usage: /reputation [player]";
    }

    private String receivedReputation = "You have received %stars% stars from %player%. Your current reputation is %reputation% stars.";
    private String currentReputation = "Your current reputation is %reputation% stars.";
    private String otherPlayerReputation = "%player%'s current reputation is %reputation% stars.";
    private String playerNotFound = "Player %player% not found.";
}
