package fr.mirage.core.common.server;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import fr.mirage.api.server.Server;
import fr.mirage.api.server.ServerManager;
import fr.mirage.api.server.ServerType;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;

@Getter
public class InMemoryServerRepository implements ServerManager {

    private final Multimap<ServerType, Server> servers;

    public InMemoryServerRepository() {
        this.servers = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);
    }

    @Override
    public Server getServer(String name) {
        return servers.values().stream()
                .filter(server -> server.getFullName().equals(name))
                .findFirst().orElse(null);
    }

}