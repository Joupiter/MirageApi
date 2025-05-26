package fr.mirage.api.server;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ServerManager {

    Logger log = LoggerFactory.getLogger(ServerManager.class);

    Multimap<ServerType, Server> getServers();

    Server getServer(String name);

}