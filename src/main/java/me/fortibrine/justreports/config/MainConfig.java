package me.fortibrine.justreports.config;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.Optional;

@Getter
@ConfigSerializable
public class MainConfig {

    @Comment("The database configuration for JustReports.")
    @Setting("database")
    private final DatabaseConfig databaseConfig = new DatabaseConfig();

    @Getter
    @ConfigSerializable
    public static class DatabaseConfig {
        private final DatabaseType type = DatabaseType.SQLITE;
        private final Optional<String> path = Optional.of("plugins/JustReports/database.db");

        private final Optional<String> host = Optional.empty();
        private final Optional<Integer> port = Optional.empty();
        private final Optional<String> database = Optional.empty();
        private final Optional<String> username = Optional.empty();
        private final Optional<String> password = Optional.empty();

        public enum DatabaseType {
            SQLITE,
            MYSQL
        }
    }

}
