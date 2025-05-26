package fr.mirage.core.proxy.server;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import fr.mirage.api.server.Server;
import fr.mirage.api.server.ServerManager;
import fr.mirage.api.server.ServerType;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;

@Getter
public class ServerRepository implements ServerManager {

    private final Multimap<ServerType, Server> servers;

    public ServerRepository() {
        this.servers = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);
    }

    void addServer(ServerType type, String name) {
        /*
            CHECK IF THE SERVER DOESNT EXIST
            FIND AN OPEN PORT
            CHECK IF THE PORT DOESNT ALREADY ALLOCATED
            CREATE NEW FOLDER WITH SERVER NAME
            COPYING FILE FROM THE TEMPLATE
            START SERVER
            SEND PACKET FOR NOTICE THE PROXY
            ADD SERVER TO LIST
         */
    }

    void removeServer(String name) {
        /*
            MAYBE SAVE LOGS BEFORE DELETE ?? OR CREATED A LOG SYSTEM
         */
    }

    void removeServer(ServerType type, int id) {
        /*
            MAYBE SAVE LOGS BEFORE DELETE ?? OR CREATED A LOG SYSTEM
         */
    }

    @Override
    public Server getServer(String name) {
        return null;
    }

}