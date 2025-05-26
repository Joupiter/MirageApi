package fr.mirage.api.rank;

import java.util.Set;

public interface RankManager {

    Set<Rank> getRanks();

    Rank getRank(String name);
    Rank getRank(int id);

}