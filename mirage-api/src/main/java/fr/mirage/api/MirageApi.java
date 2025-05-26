package fr.mirage.api;

import fr.mirage.api.data.DatabaseManager;
import fr.mirage.api.game.GameManager;
import fr.mirage.api.guild.GuildManager;
import fr.mirage.api.queue.QueueManager;
import fr.mirage.api.rank.RankManager;
import fr.mirage.api.server.Server;
import fr.mirage.api.server.ServerManager;
import fr.mirage.api.user.UserManager;
import lombok.Getter;

public interface MirageApi {

    DatabaseManager getDatabaseManager();

    ServerManager getServerManager();

    QueueManager getQueueManager();
    GameManager getGameManager();

    RankManager getRankManager();
    GuildManager getGuildManager();
    UserManager getUserManager();

    Server getServer();

    void init();
    void shutdown();

    static MirageApi getInstance() {
        return Provider.getProvider();
    }

    static MirageApi setProvider(MirageApi api) {
        return Provider.setProvider(api);
    }

    class Provider {

        @Getter
        private static MirageApi provider;

        public static MirageApi setProvider(MirageApi provider) {
            return Provider.provider = provider;
        }

    }

}