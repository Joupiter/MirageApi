package fr.mirage.api.spigot;

import org.bukkit.ChatColor;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.exception.BukkitExceptionHandler;
import revxrsal.commands.exception.MissingArgumentException;

public class CommandExceptionHandler extends BukkitExceptionHandler {

    @HandleException
    public void onMissingArgument(MissingArgumentException exception, BukkitCommandActor actor) {
        actor.reply(ChatColor.RED + "Argument manquant : ");
    }

}