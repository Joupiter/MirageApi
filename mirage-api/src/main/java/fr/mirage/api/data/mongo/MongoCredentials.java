package fr.mirage.api.data.mongo;

public record MongoCredentials(
        String url,
        String database
) { }