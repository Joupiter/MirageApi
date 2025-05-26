package fr.mirage.core.common.user;

import fr.mirage.api.user.User;
import fr.mirage.api.user.UserManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class InMemoryUserRepository implements UserManager {

    private static final User FAKE_USER = new MirageUser(new UUID(0L, 0L));

    private final Map<UUID, User> users;

    public InMemoryUserRepository() {
        this.users = new HashMap<>();
    }

    @Override
    public void addUser(UUID uuid) {
        users.put(uuid, new MirageUser(uuid));
        // NEED TO CALL USER LOADED EVENT
    }

    @Override
    public void removeUser(UUID uuid) {
        users.remove(uuid);
    }

    @Override
    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    @Override
    public void shutdown() {
        users.clear();
    }

    @Override
    public User getFakeUser() {
        return FAKE_USER;
    }

}