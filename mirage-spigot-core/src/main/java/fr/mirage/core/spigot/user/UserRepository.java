package fr.mirage.core.spigot.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import fr.mirage.api.data.DatabaseManager;
import fr.mirage.api.data.mongo.MongoManager;
import fr.mirage.api.data.redis.RedisManager;
import fr.mirage.api.rank.RankManager;
import fr.mirage.api.spigot.event.user.UserJoinEvent;
import fr.mirage.api.spigot.event.user.UserLeaveEvent;
import fr.mirage.api.spigot.utils.BukkitThreading;
import fr.mirage.api.spigot.utils.BukkitUtils;
import fr.mirage.api.user.User;
import fr.mirage.api.user.UserManager;
import fr.mirage.api.utils.json.GsonAdapter;
import fr.mirage.api.utils.json.GsonUtils;
import fr.mirage.core.common.json.UserAdapter;
import fr.mirage.core.common.user.MirageUser;
import lombok.Getter;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Getter
public class UserRepository implements UserManager {

    private static final User FAKE_USER = new MirageUser(new UUID(0L, 0L));
    private static final String DATABASE_KEY = "users";

    private final Map<UUID, User> users;

    private final MongoManager mongoManager;
    private final RedisManager redisManager;

    private final MongoCollection<Document> collection;

    private final Gson gson;
    private final GsonAdapter<User> userAdapter;

    public UserRepository(DatabaseManager databaseManager, RankManager rankManager) {
        this.users = new HashMap<>();
        this.mongoManager = databaseManager.getMongoManager();
        this.redisManager = databaseManager.getRedisManager();
        this.gson = GsonUtils.GSON;
        this.userAdapter = new UserAdapter(rankManager);
        this.collection = mongoManager.getDatabase().getCollection(DATABASE_KEY);
    }

    @Override
    public void addUser(UUID uuid) {
        users.put(uuid, FAKE_USER);

        redisManager.hget(DATABASE_KEY, uuid.toString())
                .thenApplyAsync(json -> json != null ? gson.fromJson(json, JsonObject.class) : null)
                .thenComposeAsync(userAdapter::deserializeAsync)
                .thenApplyAsync(user -> user != null ? user : new MirageUser(uuid))
                .thenAcceptAsync(this::onUserConnect, BukkitThreading::runTask)
                .exceptionally(throwable -> {
                    log.error("Error when try to add user '{}' cause : ", uuid, throwable);
                    return null;
                });
    }

    private void onUserConnect(User user) {
        var uuid = user.getUuid();

        users.put(uuid, user);
        BukkitUtils.callEvent(new UserJoinEvent(user));
    }

    @Override
    public void removeUser(UUID uuid) {
        var user = users.remove(uuid);

        BukkitUtils.callEvent(new UserLeaveEvent(user));
        userAdapter.serializeAsync(user)
                .thenAcceptAsync(json -> redisManager.hset(DATABASE_KEY, uuid.toString(), gson.toJson(json)));
    }

    @Override
    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    @Override
    public void shutdown() {
        List<CompletableFuture<Void>> futures = new ArrayList<>(users.size());
        var copy = Set.copyOf(users.keySet());

        copy.stream()
                .map(users::remove)
                .filter(Objects::nonNull)
                .forEach(user -> saveUser(futures, user));

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();
    }

    private void saveUser(List<CompletableFuture<Void>> futures, User user) {
        var id = user.getUuid().toString();

        futures.add(userAdapter
                .serializeAsync(user)
                .thenAcceptAsync(json -> redisManager.hset(DATABASE_KEY, id, gson.toJson(json))));

        log.info("Saved user {} in redis cache", id);
    }

    @Override
    public User getFakeUser() {
        return FAKE_USER;
    }

}