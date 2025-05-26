package fr.mirage.core.spigot;

import fr.mirage.api.server.ServerState;
import fr.mirage.api.server.ServerType;
import fr.mirage.api.spigot.MirageSpigotApi;
import fr.mirage.api.utils.Utils;
import fr.mirage.core.common.database.DatabaseRepository;
import fr.mirage.core.common.game.GameRepository;
import fr.mirage.core.common.game.InMemoryGameRepository;
import fr.mirage.core.common.guild.InMemoryGuildRepository;
import fr.mirage.core.common.rank.RankRepository;
import fr.mirage.core.common.server.InMemoryServerRepository;
import fr.mirage.core.common.server.MirageServer;
import fr.mirage.core.common.user.InMemoryUserRepository;
import fr.mirage.core.spigot.gui.GuiRepository;
import fr.mirage.core.spigot.guild.GuildRepository;
import fr.mirage.core.spigot.queue.QueueRepository;
import fr.mirage.core.spigot.server.ServerRepository;
import fr.mirage.core.spigot.user.UserRepository;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

public class MirageApiProvider extends MirageSpigotApi {

    public MirageApiProvider(JavaPlugin plugin) {
        super(plugin, ApiMode.DEV_NODB, new DatabaseRepository(), new GuiRepository(plugin));
        this.server = new MirageServer(1, Utils.generateRandomString(6), "127.0.0.1", -1, ServerType.UNDEFINED);
        this.audiences = BukkitAudiences.create(plugin);
        /*

            RETRIEVE SERVER INFO:

            GENERATE AN info.json IN SERVER FOLDER AFTER COPYING TEMPLATE FILE OR JUST GET IN REDIS CACHE ??

            {
                name: "hub-dsf9a2d-1",
                type: "HUB",
                host: "127.0.0.1",
                port: 25565
            }
         */
    }

    @Override
    public void init() {
        switch (mode) {
            case DEV_NODB -> {
                this.serverManager = new InMemoryServerRepository();
                this.queueManager = new QueueRepository();
                this.gameManager = new InMemoryGameRepository();
                this.rankManager = new RankRepository();
                this.guildManager = new InMemoryGuildRepository();
                this.userManager = new InMemoryUserRepository();
            }
            case DEV, PROD -> {
                databaseManager.connect();

                this.serverManager = new ServerRepository();
                this.queueManager = new QueueRepository();
                this.gameManager = new GameRepository();
                this.rankManager = new RankRepository();
                this.guildManager = new GuildRepository(databaseManager);
                this.userManager = new UserRepository(databaseManager, rankManager);
            }
        }

        server.setState(ServerState.OPEN);
    }

    @Override
    public void shutdown() {
        server.setState(ServerState.CLOSED);

        userManager.shutdown();

        if (mode == ApiMode.DEV_NODB) return;

        databaseManager.disconnect();
        // EVACUATE TO HUB ?? OR DISCONNECT
    }

}