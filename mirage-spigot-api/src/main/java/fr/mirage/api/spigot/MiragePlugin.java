package fr.mirage.api.spigot;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.NoSuchElementException;

public abstract class MiragePlugin extends JavaPlugin {

    protected MirageSpigotApi api;

    protected abstract void enable();
    protected abstract void disable();

    @Override
    public void onEnable() {
        this.api = MirageSpigotApi.getInstance();

        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    protected void registerListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    protected void registerListeners(Listener... listeners) {
        for (Listener listener : listeners)
            registerListener(listener);
    }

    public MirageSpigotApi getApi() {
        if (api == null) throw new NoSuchElementException("AlvaApi is null");
        return api;
    }

}