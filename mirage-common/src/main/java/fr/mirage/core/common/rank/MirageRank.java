package fr.mirage.core.common.rank;

import fr.mirage.api.rank.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MirageRank implements Rank {

    public static final Rank DEFAULT_RANK = new MirageRank(0, 1, "Joueur", "§7Joueur");

    private final int id;
    private final int power;

    private final String name;
    private String prefix;

}