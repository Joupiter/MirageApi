package fr.mirage.core.common.user;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import fr.mirage.api.game.GameHistory;
import fr.mirage.api.game.GameType;
import fr.mirage.api.user.UserHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
public class MirageUserHistory implements UserHistory {

    private final long createdAt;

    private long lastJoin;
    private long timePlayed;

    private final Set<String> lastNames;
    private final Multimap<GameType, GameHistory> games;

    public MirageUserHistory() {
        this.createdAt = System.currentTimeMillis();
        this.lastJoin = System.currentTimeMillis();
        this.timePlayed = System.currentTimeMillis();
        this.lastNames = new LinkedHashSet<>();
        this.games = Multimaps.newMultimap(new HashMap<>(), HashSet::new);
    }

    public MirageUserHistory(long createdAt, long lastJoin, Set<String> lastNames, Multimap<GameType, GameHistory> games) {
        this.createdAt = createdAt;
        this.lastNames = lastNames;
        this.games = games;
        this.lastJoin = lastJoin;
    }

    @Override
    public void addTimePlayed(long timePlayed) {
        this.timePlayed += timePlayed;
    }

}