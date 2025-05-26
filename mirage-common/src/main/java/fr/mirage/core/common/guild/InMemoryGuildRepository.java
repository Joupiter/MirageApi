package fr.mirage.core.common.guild;

import fr.mirage.api.guild.Guild;
import fr.mirage.api.guild.GuildAccessor;
import fr.mirage.api.guild.GuildManager;
import fr.mirage.api.guild.GuildRank;
import fr.mirage.api.user.User;
import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Getter
public class InMemoryGuildRepository implements GuildManager {

    private static final UUID FAKE_GUILD_UUID = UUID.randomUUID();
    private static final int MAX_MEMBERS = 10;

    private final Map<UUID, Guild> guilds;

    public InMemoryGuildRepository() {
        this.guilds = new HashMap<>();
    }

    @Override
    public void loadGuild(User user) {

    }

    @Override
    public void createGuild(User user) {
        if (user.hasGuild() || guilds.containsKey(FAKE_GUILD_UUID)) return;

        var createdAt = System.currentTimeMillis();
        var owner = new MirageGuildMember(user.getUuid(), "", createdAt, GuildRank.OWNER, true);

        var guild = new MirageGuild(
                FAKE_GUILD_UUID,
                createdAt,
                "hello",
                "",
                "",
                GuildAccessor.CLOSED,
                owner,
                new HashSet<>(MAX_MEMBERS));

        guilds.put(FAKE_GUILD_UUID, guild);
        log.info("New guild has been created : {}", guild);
    }

    @Override
    public void deleteGuild(User user) {

    }

    @Override
    public Guild getGuild(UUID id) {
        return guilds.get(id);
    }

    @Override
    public Guild getGuildOf(User user) {
        var uuid = user.getUuid();

        if (!user.hasGuild()) return null;

        for (Guild guild : guilds.values())
            if (guild.isOwner(uuid) || guild.containsMember(uuid))
                return guild;

        return null;
    }

}