package fr.mirage.api.utils.json;

import com.google.gson.JsonObject;

import java.util.concurrent.CompletableFuture;

public interface GsonAdapter<T> {

    JsonObject serialize(T type);
    T deserialize(JsonObject jsonObject);

    default CompletableFuture<JsonObject> serializeAsync(T type) {
        return CompletableFuture.supplyAsync(() -> serialize(type));
    }

    default CompletableFuture<T> deserializeAsync(JsonObject jsonObject) {
        return CompletableFuture.supplyAsync(() -> deserialize(jsonObject));
    }

}