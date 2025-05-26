package fr.mirage.api.user;

import com.google.common.collect.Multimap;
import fr.mirage.api.game.GameHistory;
import fr.mirage.api.game.GameType;

import java.util.Set;

public interface UserHistory {

    long getCreatedAt();
    long getLastJoin();

    long getTimePlayed();

    Set<String> getLastNames();
    Multimap<GameType, GameHistory> getGames();

    void setLastJoin(long lastJoin);

    void addTimePlayed(long timePlayed);

}