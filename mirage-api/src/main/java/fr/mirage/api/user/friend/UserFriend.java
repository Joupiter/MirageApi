package fr.mirage.api.user.friend;

import java.util.UUID;

public interface UserFriend {

    UUID getUuid();
    String getName();

    long getSince();
    boolean isOnline();

}