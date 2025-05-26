package fr.mirage.api.spigot;

import fr.mirage.api.MirageApi;
import fr.mirage.api.data.DatabaseManager;
import fr.mirage.api.game.GameManager;
import fr.mirage.api.guild.GuildManager;
import fr.mirage.api.queue.QueueManager;
import fr.mirage.api.rank.RankManager;
import fr.mirage.api.server.Server;
import fr.mirage.api.server.ServerManager;
import fr.mirage.api.spigot.gui.Gui;
import fr.mirage.api.spigot.gui.GuiManager;
import fr.mirage.api.user.User;
import fr.mirage.api.user.UserManager;
import lombok.Getter;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public abstract class MirageSpigotApi implements MirageApi {

    protected final JavaPlugin plugin;
    protected final ApiMode mode;

    protected final DatabaseManager databaseManager;
    protected final GuiManager guiManager;

    protected ServerManager serverManager;

    protected QueueManager queueManager;
    protected GameManager gameManager;

    protected RankManager rankManager;
    protected GuildManager guildManager;
    protected UserManager userManager;

    protected BukkitAudiences audiences;

    protected Server server;

    public MirageSpigotApi(JavaPlugin plugin, ApiMode mode, DatabaseManager databaseManager, GuiManager guiManager) {
        this.plugin = plugin;
        this.mode = mode;
        this.databaseManager = databaseManager;
        this.guiManager = guiManager;
    }

    public void openGui(Player player, Gui gui) {
        guiManager.open(player, gui);
    }

    public Audience audience(User user) {
        return audiences.player(user.getUuid());
    }

    public Audience audience(Player player) {
        return audiences.player(player);
    }

    public static MirageSpigotApi getInstance() {
        return (MirageSpigotApi) Provider.getProvider();
    }

    public enum ApiMode {

        DEV,
        DEV_NODB,
        PROD

    }

}