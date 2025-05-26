package fr.mirage.api.user;

import fr.mirage.api.game.GameType;
import fr.mirage.api.rank.Rank;

import java.util.Map;
import java.util.UUID;

public interface User {

    UUID getUuid();

    Rank getRank();

    long getEssences();
    long getAlvaris();

    UUID getGuildId();

    UserSession getSession();
    UserHistory getHistory();

    Map<GameType, UserRanking> getDivisions(); // ?? or Set<UserRanking> ??

    void setRank(Rank rank);
    void setGuildId(UUID uuid);

    boolean hasGuild();

}