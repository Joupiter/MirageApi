package fr.mirage.core.proxy.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import net.kyori.adventure.text.Component;

public class ProxyPingListener {

    private final ProxyServer server;
    private final ServerPing.Builder pingBuilder;

    public ProxyPingListener(ProxyServer server) {
        this.server = server;
        this.pingBuilder = ServerPing.builder()
                .maximumPlayers(200)
                .version(new ServerPing.Version(0, ""))
                .description(Component.text("§5§lMirage §8▪ §dServeur Mini-Jeux"));
    }

    @Subscribe
    public void onPing(ProxyPingEvent event) {
        var protocol = event.getConnection().getProtocolVersion().getProtocol();
        var ping = pingBuilder
                .onlinePlayers(server.getPlayerCount())
                .version(new ServerPing.Version(protocol, ""))
                .build();

        event.setPing(ping);
    }

}