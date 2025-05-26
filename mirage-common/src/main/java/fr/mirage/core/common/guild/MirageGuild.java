package fr.mirage.core.common.guild;

import fr.mirage.api.guild.Guild;
import fr.mirage.api.guild.GuildAccessor;
import fr.mirage.api.guild.GuildMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MirageGuild implements Guild {

    private final UUID id;
    private final long createdAt;
    private final String name;

    private String tag;
    private String description;

    private GuildAccessor accessor;

    private GuildMember owner;

    private final Set<GuildMember> members;

    @Override
    public boolean addMember(GuildMember member) {
        return members.add(member);
    }

    @Override
    public boolean removeMember(GuildMember member) {
        return members.remove(member);
    }

    @Override
    public boolean containsMember(GuildMember member) {
        return members.contains(member);
    }

    @Override
    public boolean containsMember(UUID uuid) {
        for (GuildMember member : members)
            if (member.getUuid().equals(uuid))
                return true;

        return false;
    }

    @Override
    public boolean isOwner(UUID uuid) {
        return owner.getUuid().equals(uuid);
    }

    @Override
    public int getSize() {
        return members.size();
    }

    @Override
    public String toString() {
        return "MirageGuild{" +
                "accessor=" + accessor +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", owner=" + owner +
                ", members=" + members +
                '}';
    }

}