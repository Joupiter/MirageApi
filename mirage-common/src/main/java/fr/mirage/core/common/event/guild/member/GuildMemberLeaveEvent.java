package fr.mirage.core.common.event.guild.member;

import fr.mirage.api.guild.Guild;
import fr.mirage.api.user.User;

public record GuildMemberLeaveEvent(User user, Guild guild) {

}