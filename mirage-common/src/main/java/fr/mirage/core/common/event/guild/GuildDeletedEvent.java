package fr.mirage.core.common.event.guild;

import fr.mirage.api.guild.Guild;
import fr.mirage.api.user.User;

public record GuildDeletedEvent(User owner, Guild guild) {

}