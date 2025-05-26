package fr.mirage.api.leaderboard;

import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;

public interface Leaderboard<T extends  LeaderboardEntry<?>> {

    NavigableSet<T> getLeaderboard();
    Comparator<T> getComparator();

    int getMax();

    void addEntry(T entry);
    void removeEntry(T entry);

    List<T> getTop(int top);

    T get(int position);
    <ID> T get(ID identifier);

    boolean contains(T entry);
    <ID> boolean contains(ID identifier);

    void update();
    void clear();

}