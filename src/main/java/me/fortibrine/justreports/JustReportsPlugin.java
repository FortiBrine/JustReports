package me.fortibrine.justreports;

import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import me.fortibrine.justreports.command.CommandReport;
import me.fortibrine.justreports.command.CommandReports;
import me.fortibrine.justreports.command.CommandReputation;
import me.fortibrine.justreports.listeners.Listener;
import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.ReputationManager;
import me.fortibrine.justreports.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class JustReportsPlugin extends JavaPlugin {

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.liteCommands = LiteBukkitFactory.builder(getDescription().getName().toLowerCase())
                .commands(
                    new CommandReport(this),
                    new CommandReports(this),
                    new CommandReputation(this)
                )
                .build();

        Bukkit.getPluginManager().registerEvents(new Listener(this), this);

        MessageManager.init(this);
        VariableManager.init();
        ReputationManager.init(this);
;    }

    @Override
    public void onDisable() {
        if (this.liteCommands != null) {
            this.liteCommands.unregister();
        }
    }

}
