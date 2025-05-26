package fr.mirage.api.data.redis;

import fr.mirage.api.data.Database;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public interface RedisManager extends Database {

    Logger log = LoggerFactory.getLogger(RedisManager.class);

    RedisCredentials getCredentials();

    RedisClient getClient();

    StatefulRedisConnection<String, String> getConnection();

    RedisCommands<String, String> getSyncCommands();
    RedisAsyncCommands<String, String> getAsyncCommands();

    RedisFuture<String> get(String key);
    RedisFuture<String> hget(String key, String identifier);

    RedisFuture<Map<String, String>> hgetall(String key);

    RedisFuture<String> set(String key, String value);

    RedisFuture<Boolean> hset(String key, String identifier, String value);
    RedisFuture<Long> hset(String key, Map<String, String> map);

    RedisFuture<Long> del(String key);
    RedisFuture<Long> hdel(String key, String identifier);

    String getSync(String key);
    String hgetSync(String key, String identifier);

    Map<String, String> hgetallSync(String key);

    String setSync(String key, String value);

    boolean hsetSync(String key, String identifier, String value);
    long hsetSync(String key, Map<String, String> map);

    long delSync(String key);
    long hdelSync(String key, String identifier);

}