package fr.mirage.core.common.rank;

import fr.mirage.api.rank.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RRank implements Rank {

    /*
        DEBUG CLASS FOR SIMPLIFY DATABASE ENTRY
     */

    OWNER (11, 100, "owner", "§5✦ Architecte"),
    MODERATOR (10, 80, "moderator", "§6✶ Exécuteur"),

    COMMANDER (5, 6, "commander", "§b✪ Commandant"),
    WARDEN (4, 5, "warden", "§3⚒ Gardien"),
    WATCHER (3, 4, "watcher", "§9❖ Veilleur"),
    SENTINEL (2, 3, "sentinel", "§e★ Sentinelle"),
    STALKER (2, 3, "stalker", "§2☠ Traqueur"),
    RAIDER (1, 2, "raider", "§c☣ Pillard"),

    SURVIVOR (0, 1, "default", "§7Survivant");

    private final int id;
    private final int power;

    private final String name;
    private String prefix;

    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

}