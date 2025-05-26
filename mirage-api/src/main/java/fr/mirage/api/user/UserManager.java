package fr.mirage.api.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public interface UserManager {

    Logger log = LoggerFactory.getLogger(UserManager.class);

    Map<UUID, User> getUsers();

    void addUser(UUID uuid);
    void removeUser(UUID uuid);

    User getUser(UUID uuid);

    void shutdown();

    User getFakeUser();

}