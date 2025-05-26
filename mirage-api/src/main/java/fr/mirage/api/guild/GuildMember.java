package fr.mirage.api.guild;

import java.util.UUID;

public interface GuildMember {

    UUID getUuid();

    String getName();
    long getJoinedAt();

    GuildRank getRank();

    boolean isOnline();

    void setRank(GuildRank rank);
    void setOnline(boolean online);

}