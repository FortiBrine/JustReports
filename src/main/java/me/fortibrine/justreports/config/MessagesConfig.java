package me.fortibrine.justreports.config;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@ConfigSerializable
public class MessagesConfig {
    private final PlayerMessages player = new PlayerMessages();
    private final AdminMessages admin = new AdminMessages();
    private final ChatMessages chat = new ChatMessages();
    private final UsageMessages usage = new UsageMessages();

    @Getter
    @ConfigSerializable
    public static class PlayerMessages {
        private final String reportSent = "Your report has been sent to the admins.";
        private final String permissionDenied = "You don't have permission to use this command.";
        private final String alreadySentReport = "You have already sent a report.";
        private final String reportTakenByAdmin = "Your report has been taken by %admin%. Please wait for them to respond.";
        private final String otherPlayerReputation = "%player%'s current reputation is %reputation% stars.";
    }

    @Getter
    @ConfigSerializable
    public static class AdminMessages {
        private final String newReport = "New report from %player%: %question%";
        private final String cannotTakeOwnReport = "You cannot take your own report.";
        private final String noReports = "There are currently no reports.";
        private final String reportClosed = "The report from %player% has been closed.";
        private final String reportTaken = "You have taken the report from %player%.";
        private final String reportTakenByOtherAdmin = "%admin% has taken the report from %player%.";
        private final String receivedReputation = "You have received %stars% stars from %player%. Your current reputation is %reputation% stars.";
        private final String playerNotFound = "Player %player% not found.";
        private final String cannotStartDialogAlreadyInDialog = "Cannot start dialog: you or this player is already in another dialog.";
        private final String currentReputation = "Your current reputation is %reputation% stars.";
    }

    @Getter
    @ConfigSerializable
    public static class ChatMessages {
        private final String adminMessageFormat = "[Admin] %player%: %message%";
        private final String playerMessageFormat = "[Player] %player%: %message%";
    }

    @Getter
    @ConfigSerializable
    public static class UsageMessages {
        @Setting("report")
        private final String reportUsage = "Usage: /report <question>";

        @Setting("reports")
        private final String reportsUsage = "Usage: /reports";

        @Setting("reputation")
        private final String reputationUsage = "Usage: /reputation [player]";
    }
}
