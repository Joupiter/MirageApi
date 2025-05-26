package fr.mirage.core.spigot.listener;

import fr.mirage.api.user.UserManager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record PlayerConnectionListener(UserManager userManager) implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();

        userManager.addUser(player.getUniqueId());
        event.setJoinMessage(null);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        var player = event.getPlayer();

        userManager.removeUser(player.getUniqueId());

        event.setQuitMessage(null);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        var player = event.getPlayer();
        var user = userManager.getUser(player.getUniqueId());

        if (user == null || user.equals(userManager.getFakeUser())) return;

        // NEED TO BE CHANGED
        event.setFormat(user.getRank().getPrefix() + " %1$s §r§7: §f%2$s");
    }

}
