package me.fortibrine.justreports;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import lombok.Getter;
import me.fortibrine.justreports.command.CommandReport;
import me.fortibrine.justreports.command.CommandReports;
import me.fortibrine.justreports.command.CommandReputation;
import me.fortibrine.justreports.command.error.InvalidUsageHandlerImpl;
import me.fortibrine.justreports.command.error.PermissionHandler;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.config.MainConfig;
import me.fortibrine.justreports.dialog.DialogService;
import me.fortibrine.justreports.gui.ReportListMenu;
import me.fortibrine.justreports.question.QuestionService;
import me.fortibrine.justreports.question.QuestionServiceImpl;
import me.fortibrine.justreports.reputation.ReputationService;
import me.fortibrine.justreports.reputation.ReputationServiceImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import ru.boomearo.menuinv.api.Menu;

import java.io.IOException;
import java.sql.SQLException;

@Getter
public final class JustReportsPlugin extends JavaPlugin {

    @Getter
    private static JustReportsPlugin instance;

    private LiteCommands<CommandSender> liteCommands;
    private ConfigManager configManager;

    private QuestionService questionService;
    private ReputationService reputationService;
    private DialogService dialogService;

    @Override
    public void onLoad() {
        instance = this;

        Logger.setGlobalLogLevel(Level.OFF);

        try {
            configManager = new ConfigManager(getDataFolder());
            configManager.load();
        } catch (IOException e) {
            getLogger().severe("Failed to load configuration files: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        questionService = new QuestionServiceImpl(configManager);

        MainConfig.DatabaseConfig databaseConfig = configManager.getMainConfig().getDatabaseConfig();
        try {
            if (databaseConfig.getType().equals("sqlite")) {
                getLogger().info("Using SQLite database.");

                reputationService = new ReputationServiceImpl(
                        this,
                        new JdbcPooledConnectionSource("jdbc:sqlite:" + databaseConfig.getPath())
                );
            } else {
                getLogger().severe("Unsupported database type: " + databaseConfig.getType());
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        } catch (SQLException e) {
            getLogger().severe("Failed to initialize database connection: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        dialogService = new DialogService(this, configManager);

    }

    @Override
    public void onEnable() {
        Menu.initMenu(this);
        
        new ReportListMenu(
                questionService,
                reputationService,
                this,
                configManager.getReportListMenuConfig(),
                dialogService,
                configManager
        ).registerMenu();

        getServer().getPluginManager().registerEvents(dialogService, this);

        this.liteCommands = LiteBukkitFactory.builder(getDescription().getName().toLowerCase())
                .commands(
                    new CommandReport(questionService, configManager),
                    new CommandReports(),
                    new CommandReputation(reputationService, configManager)
                )
                .invalidUsage(new InvalidUsageHandlerImpl(configManager))
                .missingPermission(new PermissionHandler(configManager))
                .build();

    }

    @Override
    public void onDisable() {
        instance = null;

        dialogService = null;
        configManager = null;
        questionService = null;
        reputationService = null;

        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

}
