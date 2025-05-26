package fr.mirage.api.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServerType {

    PROXY (300),

    HUB (200), // ??

    UNDEFINED (999);

    public static final ServerType[] TYPES = values(); // MAYBE USELESS ??

    private final int maxPlayers; // PRENDS EN COMPTE QUE LES JOUEURS PAS LES STAFFS

}