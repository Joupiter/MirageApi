package fr.mirage.api.user;

import fr.mirage.api.division.Division;
import fr.mirage.api.game.GameType;

public interface UserRanking {

    GameType getType();

    Division getDivision();

}