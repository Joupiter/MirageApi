package fr.mirage.core.common.event.guild;

import fr.mirage.api.guild.Guild;

public record GuildUpdateEvent(GuildUpdateType type, Guild guild) {

}