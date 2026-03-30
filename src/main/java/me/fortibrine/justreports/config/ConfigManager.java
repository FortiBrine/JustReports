package me.fortibrine.justreports.config;

import lombok.Getter;
import me.fortibrine.justreports.config.provider.MessagesConfigProvider;
import me.fortibrine.justreports.gui.ReportListMenuConfig;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final File mainConfigFile;
    private final ConfigurationLoader<?> mainConfigLoader;

    private final File messageConfigFile;
    private final ConfigurationLoader<?> messageConfigLoader;

    private final File reportListMenuConfigFile;
    private final ConfigurationLoader<?> reportListMenuConfigLoader;

    @Getter
    private MainConfig mainConfig;

    @Getter
    private MessagesConfigProvider messagesConfigProvider;

    @Getter
    private ReportListMenuConfig reportListMenuConfig;

    public ConfigManager(File dataFolder) throws IOException {
        mainConfigFile = new File(dataFolder, "config.yml");
        messageConfigFile = new File(dataFolder, "messages.yml");
        reportListMenuConfigFile = new File(dataFolder, "menu/report_list.yml");

        int indent = 2;

        mainConfigLoader = YamlConfigurationLoader.builder()
                .path(mainConfigFile.toPath())
                .nodeStyle(NodeStyle.BLOCK)
                .indent(indent)
                .build();

        messageConfigLoader = YamlConfigurationLoader.builder()
                .path(messageConfigFile.toPath())
                .nodeStyle(NodeStyle.BLOCK)
                .indent(indent)
                .build();

        reportListMenuConfigLoader = YamlConfigurationLoader.builder()
                .path(reportListMenuConfigFile.toPath())
                .nodeStyle(NodeStyle.BLOCK)
                .indent(indent)
                .build();
    }

    public void load() throws IOException {
        // messages.yml
        ConfigurationNode messagesConfigRoot = messageConfigLoader.load();
        messagesConfigProvider.setConfig(messagesConfigRoot.get(MessagesConfig.class, new MessagesConfig()));

        if (!messageConfigFile.exists()) {
            messagesConfigRoot.set(MessagesConfig.class, messagesConfigProvider.getConfig());
            messageConfigLoader.save(messagesConfigRoot);
        }

        // config.yml
        ConfigurationNode mainConfigRoot = mainConfigLoader.load();
        mainConfig = mainConfigRoot.get(MainConfig.class, new MainConfig());

        if (!mainConfigFile.exists()) {
            mainConfigRoot.set(MainConfig.class, mainConfig);
            mainConfigLoader.save(mainConfigRoot);
        }

        // menu/report_list.yml
        ConfigurationNode reportListMenuConfigRoot = reportListMenuConfigLoader.load();
        reportListMenuConfig = reportListMenuConfigRoot.get(ReportListMenuConfig.class, new ReportListMenuConfig());
        if (!reportListMenuConfigFile.exists()) {
            reportListMenuConfigRoot.set(ReportListMenuConfig.class, reportListMenuConfig);
            reportListMenuConfigLoader.save(reportListMenuConfigRoot);
        }

    }

}
