package me.fortibrine.justreports;

import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import lombok.Getter;
import me.fortibrine.justreports.command.CommandReload;
import me.fortibrine.justreports.command.CommandReport;
import me.fortibrine.justreports.command.CommandReports;
import me.fortibrine.justreports.command.CommandReputation;
import me.fortibrine.justreports.command.error.InvalidUsageHandlerImpl;
import me.fortibrine.justreports.command.error.PermissionHandler;
import me.fortibrine.justreports.config.ConfigManager;
import me.fortibrine.justreports.config.MainConfig;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import me.fortibrine.justreports.database.JdbcConnectionFactory;
import me.fortibrine.justreports.dialog.DialogService;
import me.fortibrine.justreports.gui.MenuFactory;
import me.fortibrine.justreports.gui.handler.InventoryHandler;
import me.fortibrine.justreports.question.QuestionService;
import me.fortibrine.justreports.question.QuestionServiceImpl;
import me.fortibrine.justreports.reputation.ReputationService;
import me.fortibrine.justreports.reputation.ReputationServiceImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;

@Getter
public class JustReportsPlugin extends JavaPlugin {
    private LiteCommands<CommandSender> liteCommands;
    private ConfigManager configManager;
    private MessagesConfigProvider messagesConfigProvider;

    private QuestionService questionService;
    private ReputationService reputationService;
    private DialogService dialogService;
    private MenuFactory menuFactory;

    @Override
    public void onLoad() {
        Logger.setGlobalLogLevel(Level.OFF);

        try {
            configManager = new ConfigManager(getDataFolder());
            messagesConfigProvider = configManager.getMessagesConfigProvider();
            configManager.load();
        } catch (IOException e) {
            getLogger().severe("Failed to load configuration files: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        questionService = new QuestionServiceImpl(messagesConfigProvider);

        MainConfig.DatabaseConfig databaseConfig = configManager.getMainConfig().getDatabaseConfig();
        JdbcConnectionFactory connectionFactory = new JdbcConnectionFactory();

        try {
            reputationService = new ReputationServiceImpl(this, connectionFactory.createConnection(databaseConfig));
        } catch (SQLException e) {
            getLogger().severe("Failed to connect to the database: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        } catch (IllegalArgumentException e) {
            getLogger().severe("Invalid database configuration: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        dialogService = new DialogService(messagesConfigProvider);
        menuFactory = new MenuFactory(questionService, configManager, messagesConfigProvider);

    }

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(dialogService, this);

        this.liteCommands = LiteBukkitFactory.builder(getDescription().getName().toLowerCase())
                .commands(
                    new CommandReport(questionService, messagesConfigProvider),
                    new CommandReports(menuFactory),
                    new CommandReputation(reputationService, messagesConfigProvider),
                    new CommandReload(configManager, messagesConfigProvider)
                )
                .invalidUsage(new InvalidUsageHandlerImpl(configManager))
                .missingPermission(new PermissionHandler(configManager))
                .build();

        getServer().getPluginManager().registerEvents(new InventoryHandler(), this);

    }

    @Override
    public void onDisable() {
        dialogService = null;
        configManager = null;
        questionService = null;
        reputationService = null;
        menuFactory = null;

        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

}
