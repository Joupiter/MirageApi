package fr.mirage.core.common.database.redis;

import fr.mirage.api.data.redis.RedisCredentials;
import fr.mirage.api.data.redis.RedisManager;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.support.caching.CacheAccessor;
import io.lettuce.core.support.caching.CacheFrontend;
import io.lettuce.core.support.caching.ClientSideCaching;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RedisRepository implements RedisManager {

    private RedisClient client;

    private StatefulRedisConnection<String, String> connection;

    private RedisCommands<String, String> syncCommands;
    private RedisAsyncCommands<String, String> asyncCommands;

    private final RedisCredentials credentials;

    private boolean connected;

    public RedisRepository(RedisCredentials credentials) {
        // LOAD CREDENTIALS FROM JSON
        this.credentials = credentials;
        this.connected = false;
    }

    @Override
    public void connect() {
        try {
            this.client = RedisClient.create(RedisURI.Builder.redis(
                    credentials.host(), credentials.port())
                    .withPassword(credentials.password().toCharArray())
                    .build());

            this.connection = client.connect();
            this.syncCommands = connection.sync();
            this.asyncCommands = connection.async();
            this.connected = true;

            Configurator.setLevel("io.lettuce.core", Level.WARN);

            log.info("Redis client successfully connected");
        } catch (Exception exception) {
            log.error("Failed to connect to redis cause : ", exception);
        }
    }

    @Override
    public RedisFuture<String> get(String key) {
        return asyncCommands.get(key);
    }

    @Override
    public RedisFuture<String> hget(String key, String identifier) {
        return asyncCommands.hget(key, identifier);
    }

    @Override
    public RedisFuture<Map<String, String>> hgetall(String key) {
        return asyncCommands.hgetall(key);
    }

    @Override
    public RedisFuture<String> set(String key, String value) {
        return asyncCommands.set(key, value);
    }

    @Override
    public RedisFuture<Boolean> hset(String key, String identifier, String value) {
        return asyncCommands.hset(key, identifier, value);
    }

    @Override
    public RedisFuture<Long> hset(String key, Map<String, String> map) {
        return asyncCommands.hset(key, map);
    }

    @Override
    public RedisFuture<Long> del(String key) {
        return asyncCommands.del(key);
    }

    @Override
    public RedisFuture<Long> hdel(String key, String identifier) {
        return asyncCommands.hdel(key, identifier);
    }

    @Override
    public String getSync(String key) {
        return syncCommands.get(key);
    }

    @Override
    public String hgetSync(String key, String identifier) {
        return syncCommands.hget(key, identifier);
    }

    @Override
    public Map<String, String> hgetallSync(String key) {
        return syncCommands.hgetall(key);
    }

    @Override
    public String setSync(String key, String value) {
        return syncCommands.set(key, value);
    }

    @Override
    public boolean hsetSync(String key, String identifier, String value) {
        return syncCommands.hset(key, identifier, value);
    }

    @Override
    public long hsetSync(String key, Map<String, String> map) {
        return syncCommands.hset(key, map);
    }

    @Override
    public long delSync(String key) {
        return syncCommands.del(key);
    }

    @Override
    public long hdelSync(String key, String identifier) {
        return syncCommands.hdel(key, identifier);
    }

    @Override
    public void disconnect() {
        this.connected = false;

        connection.close();
        client.connect();
    }

}