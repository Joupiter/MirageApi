package fr.mirage.core.spigot.command;

import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

public record AudiCommand(BukkitAudiences audiences) implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            var audience = audiences.player(player);
            Component bookTitle = Component.text("Encyclopedia of cats");
            Component bookAuthor = Component.text("kashike");
            Collection<Component> bookPages = List.of(Component.text("lorem"), Component.text("oui"));

            Book myBook = Book.book(bookTitle, bookAuthor, bookPages);
            audience.openBook(myBook);
        }

        return false;
    }

}