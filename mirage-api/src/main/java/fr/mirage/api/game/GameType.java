package fr.mirage.api.game;

public enum GameType {

    RUSH;

    private static final GameType[] TYPES = values();

    public static GameType[] getTypes() {
        return TYPES;
    }

}