package fr.mirage.api.rank;

public interface Rank {

    int getId();
    int getPower();

    String getName();
    String getPrefix();

    void setPrefix(String prefix);

}