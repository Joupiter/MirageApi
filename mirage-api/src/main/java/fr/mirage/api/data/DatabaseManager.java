package fr.mirage.api.data;

import fr.mirage.api.data.mongo.MongoManager;
import fr.mirage.api.data.redis.RedisManager;

public interface DatabaseManager {

    RedisManager getRedisManager();
    MongoManager getMongoManager();

    void connect();
    void disconnect();

}
