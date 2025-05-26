package fr.mirage.api.leaderboard;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

@Getter
@Setter
public abstract class AbstractLeaderboard<T extends  LeaderboardEntry<?>> implements Leaderboard<T> {

    protected final NavigableSet<T> leaderboard;
    protected final Comparator<T> comparator;

    private final int max;

    public AbstractLeaderboard(int max, Comparator<T> entryComparator) {
        this.max = max;
        this.comparator = (entry1, entry2) -> {
            int score = entryComparator.compare(entry1, entry2);
            return score != 0
                    ? score : entry1.getIdentifier().equals(entry2.getIdentifier())
                    ? 0 : entry1.getIdentifier().toString().compareTo(entry2.getIdentifier().toString());
        };
        this.leaderboard = new TreeSet<>(comparator);
    }

    @Override
    public void addEntry(T entry) {
        if (contains(entry.getIdentifier())) return;

        if (leaderboard.size() < max)
            leaderboard.add(entry);
        else {
            var worstEntry = leaderboard.last();

            if (comparator.compare(entry, worstEntry) < 0) {
                leaderboard.pollLast();
                leaderboard.add(entry);
            }
        }

        updatePositions();
    }

    private void updatePositions() {
        int position = 1;

        for (T entry : leaderboard)
            entry.setPosition(position++);
    }

    @Override
    public void removeEntry(T entry) {
        leaderboard.remove(entry);
        updatePositions();
    }

    @Override
    public List<T> getTop(int top) {
        return leaderboard.stream()
                .sorted(Comparator.comparingInt(T::getPosition))
                .limit(Math.min(top, max)).toList();
    }

    @Override
    public T get(int position) {
        for (T entry : leaderboard)
            if (entry.getPosition() == position)
                return entry;

        return null;
    }

    @Override
    public <ID> T get(ID identifier) {
        for (T entry : leaderboard)
            if (entry.getIdentifier() == identifier)
                return entry;

        return null;
    }

    @Override
    public boolean contains(T entry) {
        return leaderboard.contains(entry);
    }

    @Override
    public <ID> boolean contains(ID identifier) {
        for (T entry : leaderboard)
            if (entry.getIdentifier().equals(identifier))
                return true;

        return false;
    }

    @Override
    public void clear() {
        leaderboard.clear();
    }

    @Override
    public void update() {
        updatePositions();
    }

}