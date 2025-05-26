package fr.mirage.core.common.guild;

import fr.mirage.api.guild.GuildMember;
import fr.mirage.api.guild.GuildRank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MirageGuildMember implements GuildMember {

    private final UUID uuid;
    private final String name;
    private final long joinedAt;

    private GuildRank rank;

    private boolean online;

}