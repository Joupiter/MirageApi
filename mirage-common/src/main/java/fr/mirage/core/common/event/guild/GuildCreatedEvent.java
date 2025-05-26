package fr.mirage.core.common.event.guild;

import fr.mirage.api.guild.Guild;
import fr.mirage.api.user.User;

public record GuildCreatedEvent(User owner, Guild guild) {

}
