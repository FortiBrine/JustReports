package me.fortibrine.justreports.config;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@ConfigSerializable
public class MainConfig {

    @Comment("The database configuration for JustReports.")
    @Setting("database")
    private DatabaseConfig databaseConfig = new DatabaseConfig();

    @Getter
    @ConfigSerializable
    public static class DatabaseConfig {
        private String type = "sqlite";
        private String path = "plugins/JustReports/database.db";
    }

}
