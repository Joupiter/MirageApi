package fr.mirage.api.user;

public interface UserSettings {

    State privateMessage();

    enum State {

        ENABLED,
        FRIEND_ONLY,
        DISABLED

    }

}