package fr.mirage.api.spigot.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@UtilityClass
public class BukkitUtils {

    public void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

}