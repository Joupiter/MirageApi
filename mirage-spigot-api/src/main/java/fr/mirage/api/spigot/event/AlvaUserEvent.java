package fr.mirage.api.spigot.event;

import fr.mirage.api.user.User;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerEvent;

@Getter
public abstract class AlvaUserEvent extends PlayerEvent {

    private final User user;

    public AlvaUserEvent(User user) {
        super(Bukkit.getPlayer(user.getUuid()));
        this.user = user;
    }

}