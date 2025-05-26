package fr.mirage.core.spigot.command;

import fr.mirage.api.rank.Rank;
import fr.mirage.api.rank.RankManager;
import fr.mirage.api.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public record RankCommand(UserManager userManager, RankManager rankManager) implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("§cUtilisation: /rank <user> <rank>");
            return false;
        }

        var username = args[0];
        var rankName = args[1];

        var player = Bukkit.getPlayer(username);

        if (player == null || !player.isOnline()) {
            sender.sendMessage("§cLe joueur " + username + " n'existe pas");
            return false;
        }

        var user = userManager.getUser(player.getUniqueId());

        if (user == null) {
            sender.sendMessage("§cLe joueur " + username + " n'existe pas");
            return false;
        }

        var rank = rankManager.getRank(rankName);

        if (rank == null) {
            sender.sendMessage("§cRank invalide §7(" + rankManager.getRanks().stream()
                    .sorted(Comparator.comparingInt(Rank::getId))
                    .map(Rank::getName)
                    .collect(Collectors.joining(", ")) + ")");
            return false;
        }

        user.setRank(rank);
        sender.sendMessage("§aVous avez mis le rank §b" + rank.getName() + " §aa " + player.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                    suggestions.add(player.getName());
                }
            }
        }

        if (args.length == 2) {
            for (Rank rank : rankManager.getRanks()) {
                if (rank.getName().toLowerCase().startsWith(args[1].toLowerCase())) {
                    suggestions.add(rank.getName());
                }
            }
        }

        return suggestions;
    }

}