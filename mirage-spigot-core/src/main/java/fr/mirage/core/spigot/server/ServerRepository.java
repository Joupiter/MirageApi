package fr.mirage.core.spigot.server;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import fr.mirage.api.server.Server;
import fr.mirage.api.server.ServerManager;
import fr.mirage.api.server.ServerType;
import fr.mirage.api.utils.Utils;
import fr.mirage.core.common.server.MirageServer;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;

@Getter
public class ServerRepository implements ServerManager {

    private final Multimap<ServerType, Server> servers;

    public ServerRepository() {
        this.servers = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);
    }

    @Override
    public Server getServer(String name) {
        return servers.values().stream()
                .filter(server -> server.getFullName().equals(name))
                .findFirst()
                .orElse(generateFakeServer());
    }

    private Server generateFakeServer() {
        return new MirageServer(1, Utils.generateRandomString(6), "127.0.0.1", -1, ServerType.UNDEFINED);
    }

    void addServer(ServerType type, String name) {
        /*
            ONLY SEND A PACKET TO THE PROXY (ServerCreationRequestPacket)
         */
    }

    void removeServer(String name) {
        /*
            ONLY SEND A PACKET TO THE PROXY (ServerRemoveRequestPacket)
         */
    }

    void removeServer(ServerType type, int id) {
        /*
            ONLY SEND A PACKET TO THE PROXY (ServerRemoveRequestPacket)
         */
    }

}