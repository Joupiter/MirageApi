package fr.mirage.api.spigot.event.user;

import fr.mirage.api.spigot.event.AlvaUserEvent;
import fr.mirage.api.user.User;
import org.bukkit.event.HandlerList;

public class UserLeaveEvent extends AlvaUserEvent {

    private static final HandlerList handlers = new HandlerList();

    public UserLeaveEvent(User user) {
        super(user);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}