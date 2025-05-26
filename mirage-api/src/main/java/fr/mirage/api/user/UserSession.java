package fr.mirage.api.user;

import fr.mirage.api.queue.MatchQueue;
import fr.mirage.api.server.Server;

public interface UserSession {

    long getConnectedAt();

    Server getServer();
    MatchQueue getQueue();

    boolean isInGame();
    boolean isInStaffMode();
    boolean isInQueue();

    void setServer(Server server);
    void setConnectedAt(long connectedAt);

    void setInGame(boolean inGame);
    void setInStaffMode(boolean inStaffMode);
    void setInQueue(boolean inQueue);

    void sendMessage(String message);

}