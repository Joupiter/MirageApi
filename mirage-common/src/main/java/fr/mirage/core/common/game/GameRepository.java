package fr.mirage.core.common.game;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import fr.mirage.api.game.GameInfo;
import fr.mirage.api.game.GameManager;
import fr.mirage.api.game.GameType;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;

@Getter
public class GameRepository implements GameManager {

    private final Multimap<GameType, GameInfo> games;

    public GameRepository() {
        this.games = Multimaps.newMultimap(new HashMap<>(), HashSet::new);
    }

}