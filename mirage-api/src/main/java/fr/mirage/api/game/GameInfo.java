package fr.mirage.api.game;

import fr.mirage.api.server.Server;

public interface GameInfo {

    GameType getType();
    GameState getState();

    String getName();
    Server getServer();

}