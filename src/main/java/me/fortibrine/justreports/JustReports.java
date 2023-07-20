package me.fortibrine.justreports;

import me.fortibrine.justreports.commands.CommandReport;
import me.fortibrine.justreports.commands.CommandReports;
import me.fortibrine.justreports.listeners.Listener;
import me.fortibrine.justreports.utils.MessageManager;
import me.fortibrine.justreports.utils.VariableManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class JustReports extends JavaPlugin {

    @Override
    public void onEnable() {
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }

        this.getCommand("report").setExecutor(new CommandReport(this));
        this.getCommand("reports").setExecutor(new CommandReports(this));

        Bukkit.getPluginManager().registerEvents(new Listener(this), this);

        MessageManager.init(this);
        VariableManager.init();
;    }

}
