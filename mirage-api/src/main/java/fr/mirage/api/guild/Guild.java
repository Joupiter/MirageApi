package fr.mirage.api.guild;

import java.util.Set;
import java.util.UUID;

public interface Guild {

    UUID getId();
    long getCreatedAt();

    String getName();
    String getTag();
    String getDescription();

    GuildAccessor getAccessor();

    GuildMember getOwner();
    Set<GuildMember> getMembers();

    void setOwner(GuildMember owner);
    void setTag(String tag);
    void setAccessor(GuildAccessor accessor);

    boolean addMember(GuildMember member);
    boolean removeMember(GuildMember member);

    boolean containsMember(GuildMember member);
    boolean containsMember(UUID uuid);

    boolean isOwner(UUID uuid);

    int getSize();

}