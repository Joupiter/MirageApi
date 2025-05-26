package fr.mirage.api.leaderboard;

public interface LeaderboardEntry<ID> {

    ID getIdentifier();

    int getPosition();

    void setPosition(int position);

}