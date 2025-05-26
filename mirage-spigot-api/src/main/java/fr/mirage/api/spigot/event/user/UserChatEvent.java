package fr.mirage.api.spigot.event.user;

import fr.mirage.api.spigot.event.AlvaUserEvent;
import fr.mirage.api.user.User;
import lombok.Getter;
import org.bukkit.event.HandlerList;

@Getter
public class UserChatEvent extends AlvaUserEvent {

    private static final HandlerList handlers = new HandlerList();

    private final String message;

    public UserChatEvent(User user, String message) {
        super(user);
        this.message = message;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}