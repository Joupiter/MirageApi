package fr.mirage.core.common.rank;

import fr.mirage.api.rank.Rank;
import fr.mirage.api.rank.RankManager;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
public class RankRepository implements RankManager {

    private final Set<Rank> ranks;

    public RankRepository() {
        this.ranks = new HashSet<>();
        this.loadDefaultsRanks();
    }

    private void loadDefaultsRanks() {
        ranks.addAll(Arrays.asList(RRank.values()));
    }

    @Override
    public Rank getRank(String name) {
        for (Rank rank : ranks) {
            if (rank.getName().equals(name))
                return rank;
        }

        return null;
    }

    @Override
    public Rank getRank(int id) {
        for (Rank rank : ranks) {
            if (rank.getId() == id)
                return rank;
        }

        return null;
    }

}