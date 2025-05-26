package fr.mirage.api.game;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface GameManager {

    Logger log = LoggerFactory.getLogger(GameManager.class);

    Multimap<GameType, GameInfo> getGames();

}