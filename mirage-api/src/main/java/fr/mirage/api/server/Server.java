package fr.mirage.api.server;

import fr.mirage.api.user.User;

import java.util.Set;

public interface Server {

    int getId();

    ServerType getType();
    ServerState getState();

    String getName();
    String getFullName();

    String getHost();
    int getPort();

    Set<User> getPlayers();

    int getPlayersCount();

    void setState(ServerState state);

    void addPlayer(User user);
    void removePlayer(User user);

}