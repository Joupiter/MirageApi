package fr.mirage.core.common.user;

import fr.mirage.api.queue.MatchQueue;
import fr.mirage.api.server.Server;
import fr.mirage.api.user.UserSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MirageUserSession implements UserSession {

    private Server server;

    @Override
    public long getConnectedAt() {
        return 0;
    }

    @Override
    public MatchQueue getQueue() {
        return null;
    }

    @Override
    public boolean isInGame() {
        return false;
    }

    @Override
    public boolean isInStaffMode() {
        return false;
    }

    @Override
    public boolean isInQueue() {
        return false;
    }

    @Override
    public void setConnectedAt(long connectedAt) {

    }

    @Override
    public void setInGame(boolean inGame) {

    }

    @Override
    public void setInStaffMode(boolean inStaffMode) {

    }

    @Override
    public void setInQueue(boolean inQueue) {

    }

    @Override
    public void sendMessage(String message) {

    }

}