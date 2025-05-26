package fr.mirage.core.proxy;

import fr.mirage.api.MirageApi;
import fr.mirage.api.data.DatabaseManager;
import fr.mirage.api.game.GameManager;
import fr.mirage.api.guild.GuildManager;
import fr.mirage.api.queue.QueueManager;
import fr.mirage.api.rank.RankManager;
import fr.mirage.api.server.Server;
import fr.mirage.api.server.ServerManager;
import fr.mirage.api.user.UserManager;
import fr.mirage.core.common.database.DatabaseRepository;
import fr.mirage.core.common.game.GameRepository;
import fr.mirage.core.common.game.InMemoryGameRepository;
import fr.mirage.core.common.guild.InMemoryGuildRepository;
import fr.mirage.core.common.rank.RankRepository;
import fr.mirage.core.common.server.InMemoryServerRepository;
import fr.mirage.core.common.user.InMemoryUserRepository;
import fr.mirage.core.proxy.guild.GuildRepository;
import fr.mirage.core.proxy.queue.QueueRepository;
import fr.mirage.core.proxy.server.ServerRepository;
import fr.mirage.core.proxy.user.UserRepository;
import lombok.Getter;

@Getter
public class MirageApiProvider implements MirageApi {

    protected final ApiMode mode;

    private final DatabaseManager databaseManager;

    private ServerManager serverManager;

    private QueueManager queueManager;
    private GameManager gameManager;

    private RankManager rankManager;
    private GuildManager guildManager;
    private UserManager userManager;

    public MirageApiProvider(ApiMode mode) {
        this.mode = mode;
        this.databaseManager = new DatabaseRepository();
    }

    @Override
    public Server getServer() {
        return null;
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
    }

    @Override
    public void shutdown() {
        userManager.shutdown();

        if (mode == ApiMode.DEV_NODB) return;

        databaseManager.disconnect();
    }

    public enum ApiMode {

        DEV,
        DEV_NODB,
        PROD

    }

}