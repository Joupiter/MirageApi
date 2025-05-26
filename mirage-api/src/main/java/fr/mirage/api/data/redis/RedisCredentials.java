package fr.mirage.api.data.redis;

public record RedisCredentials(
        String host,
        int port,
        String password
) { }