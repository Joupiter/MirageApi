package fr.mirage.api.guild;

import fr.mirage.api.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.UUID;

public interface GuildManager {

    Logger log = LoggerFactory.getLogger(GuildManager.class);

    Map<UUID, Guild> getGuilds();

    void loadGuild(User user);

    void createGuild(User user);
    void deleteGuild(User user);

    Guild getGuild(UUID id);

    Guild getGuildOf(User user);

}