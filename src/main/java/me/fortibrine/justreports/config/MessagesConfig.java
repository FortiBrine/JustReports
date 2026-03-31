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
        private final String reportSent = "§a✓ §7Your report has been sent to the admins.";
        private final String permissionDenied = "§c✗ §7You don't have permission to use this command.";
        private final String alreadySentReport = "§e⚠ §7You have already sent a report.";
        private final String reportTakenByAdmin = "§6ℹ §7Your report has been taken by §b%admin%§7. Please wait for them to respond.";
        private final String otherPlayerReputation = "§b⭐ §7%player%'s current reputation is §e%reputation% stars§7.";
    }

    @Getter
    @ConfigSerializable
    public static class AdminMessages {
        private final String newReport = "§c🔔 §7New report from §b%player%§7: §c%question%";
        private final String cannotTakeOwnReport = "§c✗ §7You cannot take your own report.";
        private final String noReports = "§e✓ §7There are currently no reports.";
        private final String reportClosed = "§a✓ §7The report from §b%player% §7has been closed.";
        private final String reportTaken = "§a✓ §7You have taken the report from §b%player%§7.";
        private final String reportTakenByOtherAdmin = "§e⚠ §7%admin% §7has taken the report from §b%player%§7.";
        private final String receivedReputation = "§6✦ §7You have received §e%stars% stars §7from §b%player%§7. Your reputation is now §b%reputation% stars§7.";
        private final String playerNotFound = "§c✗ §7Player §b%player% §7not found.";
        private final String cannotStartDialogAlreadyInDialog = "§c✗ §7Cannot start dialog: you or this player is already in another dialog.";
        private final String currentReputation = "§b⭐ §7Your current reputation is §e%reputation% stars§7.";
    }

    @Getter
    @ConfigSerializable
    public static class ChatMessages {
        private final String adminMessageFormat = "§6[Admin] §b%player%§7: §f%message%";
        private final String playerMessageFormat = "§b[Player] §b%player%§7: §f%message%";
    }

    @Getter
    @ConfigSerializable
    public static class UsageMessages {
        @Setting("report")
        private final String reportUsage = "§e/report §7<§bquestion§7>";

        @Setting("reports")
        private final String reportsUsage = "§e/reports";

        @Setting("reputation")
        private final String reputationUsage = "§e/reputation §7[§bplayer§7]";
    }
}
