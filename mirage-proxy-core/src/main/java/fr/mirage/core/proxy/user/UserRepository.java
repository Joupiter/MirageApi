package fr.mirage.core.proxy.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import fr.mirage.api.data.DatabaseManager;
import fr.mirage.api.data.mongo.MongoManager;
import fr.mirage.api.data.redis.RedisManager;
import fr.mirage.api.rank.RankManager;
import fr.mirage.api.user.User;
import fr.mirage.api.user.UserManager;
import fr.mirage.api.utils.json.GsonAdapter;
import fr.mirage.api.utils.json.GsonUtils;
import fr.mirage.core.common.json.UserAdapter;
import fr.mirage.core.common.user.MirageUser;
import lombok.Getter;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class UserRepository implements UserManager {

    private static final User FAKE_USER = new MirageUser(new UUID(0L, 0L));
    private static final String DATABASE_KEY = "users";
    private static final ReplaceOptions UPSERT_OPTIONS = new ReplaceOptions().upsert(true);

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
        this.collection = mongoManager.getDatabase().getCollection(DATABASE_KEY);
        this.gson = GsonUtils.GSON;
        this.userAdapter = new UserAdapter(rankManager);
    }

    @Override
    public void addUser(UUID uuid) {
        /*

            GET IN REDIS FIRST
            IF NULL TRY TO GET IN MONGO
            IF NULL CREATE NEW USER

         */
        var id = uuid.toString();
        var json = redisManager.hgetSync(DATABASE_KEY, id);
        var user = deserializeUser(json, uuid);

        log.info("Try to add user '{}'", id);

        if (user == null) {
            var document = collection.find(Filters.eq("uuid", id)).first();

            log.info("Try to get user '{}' from mongo", id);
            user = document != null ? deserializeUser(document.toJson(), uuid) : null;

            if (user == null) {
                user = new MirageUser(uuid);

                userAdapter.serializeAsync(user)
                        .thenApplyAsync(gson::toJson)
                        .thenAccept(s -> {
                            log.info("({}) New user '{}' to the cache", Thread.currentThread().getName(), id);
                            redisManager.hset(DATABASE_KEY, id, s);
                        })
                        .exceptionally(ex -> {
                            log.error("Error when put user '{}' in redis cache cause {}", id, ex.getMessage());
                            return null;
                        });
            }
        }

        users.put(uuid, user);
    }

    private User deserializeUser(String json, UUID uuid) {
        if (json == null) return null;

        var jsonObject = gson.fromJson(json, JsonObject.class);
        var user = userAdapter.deserialize(jsonObject);

        return user != null ? user : new MirageUser(uuid);
    }

    @Override
    public void removeUser(UUID uuid) {
        var user = users.remove(uuid);
        var id = uuid.toString();

        userAdapter.serializeAsync(user)
                .thenApplyAsync(jsonObject -> gson.toJson(jsonObject, JsonObject.class))
                .thenApplyAsync(Document::parse)
                .thenAcceptAsync(document -> collection.replaceOne(Filters.eq("uuid", id), document, UPSERT_OPTIONS))
                .exceptionallyAsync(throwable -> {
                    log.error("Error when remove user '{}' cause : ", id, throwable);
                    return null;
                });
    }

    @Override
    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public User getFakeUser() {
        return FAKE_USER;
    }

}