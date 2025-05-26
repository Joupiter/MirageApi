package fr.mirage.core.common.server;

import fr.mirage.api.server.Server;
import fr.mirage.api.server.ServerState;
import fr.mirage.api.server.ServerType;
import fr.mirage.api.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class MirageServer implements Server {

    private final int id;

    private final String name;
    private final ServerType type;

    private final String host;
    private final int port;

    private final Set<User> players;

    private ServerState state;

    public MirageServer(int id, String name, String host, int port, ServerType type) {
        this(id, name, type, host, port, new HashSet<>(), ServerState.STARTING);
    }

    public MirageServer(int id, String name, String host, int port, ServerState state, ServerType type) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.host = host;
        this.port = port;
        this.state = state;
        this.players = new HashSet<>(type.getMaxPlayers());
    }

    @Override
    public String getFullName() {
        return type + "-" + name + "-" + id;
    }

    @Override
    public void addPlayer(User user) {
        players.add(user);
    }

    @Override
    public void removePlayer(User user) {
        players.remove(user);
    }

    @Override
    public int getPlayersCount() {
        return players.size();
    }

}