package fr.mirage.core.proxy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import fr.mirage.api.user.UserManager;
import org.slf4j.Logger;

public record ProxyConnectionListener(Logger logger, UserManager userManager) {

    @Subscribe
    public void onLogin(LoginEvent event) {
        var player = event.getPlayer();

        userManager.addUser(player.getUniqueId());
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        var player = event.getPlayer();

        userManager.removeUser(player.getUniqueId());
    }

}