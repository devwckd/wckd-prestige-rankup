package me.devwckd.prestigerankup.lifecycle;
import co.wckd.boilerplate.lifecycle.Lifecycle;
import lombok.Getter;
import me.devwckd.prestigerankup.RankUpPlugin;
import me.devwckd.prestigerankup.database.MongoDataProvider;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Level;

@Getter
public class DatabaseLifecycle extends Lifecycle {

    private final RankUpPlugin plugin;
    private FileConfiguration fileConfiguration;
    private MongoDataProvider dataProvider;

    public DatabaseLifecycle(RankUpPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {

        fileConfiguration = plugin.getFileLifecycle().getConfiguration();
        setupMongo();

    }

    private void setupMongo() {

        String hostname = fileConfiguration.getString("mongo_connection.hostname");
        int port = fileConfiguration.getInt("mongo_connection.port", 27017);
        boolean srv = fileConfiguration.getBoolean("mongo_connection.srv", false);
        String username = fileConfiguration.getString("mongo_connection.username");
        String password = fileConfiguration.getString("mongo_connection.password");
        String database = fileConfiguration.getString("mongo_connection.database");

        if(hostname == null || username == null || password == null || database == null) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Mongo connection info missing. Shutting down...");
            plugin.disablePlugin();
            return;
        }

        dataProvider = new MongoDataProvider(
                hostname, port, srv, username, password, database, Level.FINEST
        );

        try {
            dataProvider.connect();
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Could not connect to MongoDB. Shutting down...");
            Bukkit.getConsoleSender().sendMessage("[WickedRankUp] Cause: " + exception.getMessage());
            plugin.disablePlugin();
        }

    }
}
