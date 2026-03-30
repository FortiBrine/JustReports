package me.fortibrine.justreports.database;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import me.fortibrine.justreports.config.MainConfig;

import static me.fortibrine.justreports.config.MainConfig.DatabaseConfig.DatabaseType.*;

import java.sql.SQLException;

public class JdbcConnectionFactory {

    public ConnectionSource createConnection(MainConfig.DatabaseConfig databaseConfig) throws IllegalArgumentException, SQLException {
        MainConfig.DatabaseConfig.DatabaseType databaseType = databaseConfig.getType();

        if (SQLITE == databaseType) {
            return createSqliteConnection(databaseConfig.getPath().orElse(null));
        } else if (MYSQL == databaseType) {
            return createMysqlConnection(
                    databaseConfig.getHost().orElse(null),
                    databaseConfig.getPort().orElse(3306),
                    databaseConfig.getDatabase().orElse(null),
                    databaseConfig.getUsername().orElse(null),
                    databaseConfig.getPassword().orElse(null)
            );
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + databaseType);
        }
    }

    public ConnectionSource createSqliteConnection(String path) throws SQLException {
        return new JdbcPooledConnectionSource("jdbc:sqlite:" + path);
    }

    public ConnectionSource createMysqlConnection(
            String host,
            int port,
            String database,
            String username,
            String password
    ) throws SQLException {
        return new JdbcPooledConnectionSource("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
    }

}
