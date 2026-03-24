package me.fortibrine.justreports.gui;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.JustReportsPlugin;
import org.bukkit.plugin.Plugin;
import ru.boomearo.menuinv.api.PluginPage;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MenuPage implements PluginPage {

    REPORT_LIST(JustReportsPlugin.getInstance(), "report_list"),
    REPUTATION(JustReportsPlugin.getInstance(), "reputation");

    private final Plugin plugin;
    private final String page;

    @Override
    public @NonNull Plugin getPlugin() {
        return plugin;
    }

    @Override
    public @NonNull String getPage() {
        return page;
    }
}
