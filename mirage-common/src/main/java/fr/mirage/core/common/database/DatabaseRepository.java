package fr.mirage.core.common.database;

import fr.mirage.api.data.DatabaseManager;
import fr.mirage.api.data.mongo.MongoCredentials;
import fr.mirage.api.data.mongo.MongoManager;
import fr.mirage.api.data.redis.RedisCredentials;
import fr.mirage.api.data.redis.RedisManager;
import fr.mirage.core.common.database.mongo.MongoRepository;
import fr.mirage.core.common.database.redis.RedisRepository;
import lombok.Getter;

@Getter
public class DatabaseRepository implements DatabaseManager {

    private final MongoManager mongoManager;
    private final RedisManager redisManager;

    public DatabaseRepository() {
        // MOVE CREDENTIALS IN JSON FILE
        this.mongoManager = new MongoRepository(new MongoCredentials("mongodb://127.0.0.1:27017/", "mirage"));
        this.redisManager = new RedisRepository(new RedisCredentials("127.0.0.1", 6379, "1234"));
    }

    @Override
    public void connect() {
        mongoManager.connect();
        redisManager.connect();
    }

    @Override
    public void disconnect() {
        redisManager.disconnect();
        mongoManager.disconnect();
    }

}
