package fr.mirage.core.spigot;

import fr.mirage.api.MirageApi;
import fr.mirage.api.spigot.MirageSpigotApi;
import fr.mirage.api.spigot.utils.BukkitThreading;
import fr.mirage.core.spigot.command.AudiCommand;
import fr.mirage.core.spigot.command.RankCommand;
import fr.mirage.core.spigot.listener.PlayerConnectionListener;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class MirageSpigotPlugin extends JavaPlugin {

    private MirageSpigotApi alvaApi;

    @Override
    public void onEnable() {
        BukkitThreading.setPlugin(this);

        var apiProvider = new MirageApiProvider(this);
        this.alvaApi = (MirageSpigotApi) MirageApi.setProvider(apiProvider);

        alvaApi.init();

        getLogger().info("MirageCore started.");
        getLogger().info("Server: " + alvaApi.getServer().getFullName());

        registerListeners(
                new PlayerConnectionListener(alvaApi.getUserManager())
        );

        getCommand("rank").setExecutor(new RankCommand(alvaApi.getUserManager(), alvaApi.getRankManager()));
        getCommand("audi").setExecutor(new AudiCommand(alvaApi.getAudiences()));
    }


    @Override
    public void onDisable() {
        alvaApi.shutdown();
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }

}