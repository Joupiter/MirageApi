package fr.mirage.core.proxy.guild;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import fr.mirage.api.data.DatabaseManager;
import fr.mirage.api.data.mongo.MongoManager;
import fr.mirage.api.data.redis.RedisManager;
import fr.mirage.api.guild.Guild;
import fr.mirage.api.guild.GuildManager;
import fr.mirage.api.user.User;
import fr.mirage.api.utils.json.GsonAdapter;
import fr.mirage.api.utils.json.GsonUtils;
import fr.mirage.core.common.json.GuildAdapter;
import lombok.Getter;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public class GuildRepository implements GuildManager {

    private static final String DATABASE_KEY = "guilds";

    private static final int
            MAX_CHAR_NAME = 10,
            MAX_CHAR_TAG = 10,
            MAX_CHAR_DESCRIPTION = 25,
            MAX_MEMBERS = 15;

    private final Map<UUID, Guild> guilds;

    private final MongoManager mongoManager;
    private final RedisManager redisManager;

    private final MongoCollection<Document> collection;

    private final Gson gson;
    private final GsonAdapter<Guild> guildAdapter;

    public GuildRepository(DatabaseManager databaseManager) {
        this.guilds = new HashMap<>();
        this.mongoManager = databaseManager.getMongoManager();
        this.redisManager = databaseManager.getRedisManager();
        this.gson = GsonUtils.GSON;
        this.guildAdapter = new GuildAdapter();
        this.collection = mongoManager.getDatabase().getCollection(DATABASE_KEY);
    }

    /*

        WHEN USER JOIN CHECK IF THE GUILD IS NOT ALREADY IN MEMORY CACHE ?
        ELSE GET IN REDIS AND PUT IN MEMORY CACHE
        IF NOT CONTAINS IN REDIS LOAD IT FROM MONGO AND PUT IN MEMORY CACHE

     */
    @Override
    public void loadGuild(User user) {
        if (!user.hasGuild()) return;

        var guildId = user.getGuildId().toString();
        var guild = getGuildOf(user);

        if (guild != null) return;
        // NOT IN MEMORY CHECK REDIS !

        redisManager.hget(DATABASE_KEY, guildId)
                .thenApplyAsync(this::deserializeGuild)
                .thenComposeAsync(loaded -> loaded != null
                        ? CompletableFuture.completedFuture(loaded)
                        : findGuildInMongo(guildId))
                .thenAcceptAsync(this::addGuild) // NEED TO BE FORCE IN SYNC
                .exceptionallyAsync(ex -> {
                    log.error("Error when try to load guild '{}' cause {}", guildId, ex.getMessage());
                    return null;
                });

    }

    @Override
    public void createGuild(User user) {
        //new GuildCreatedEvent(user, guild);
    }

    @Override
    public void deleteGuild(User user) {
        //new GuildDeletedEvent(user, guild);
    }

    private void addGuild(Guild guild) {
        // print error here if null
        if (guild == null) return;

        guilds.put(guild.getId(), guild);
        //new GuildLoadedEvent(guild);
    }

    private void removeGuild(Guild guild) {
        guilds.remove(guild.getId());
    }

    private Guild deserializeGuild(String json) {
        if (json == null) return null;

        return guildAdapter.deserialize(gson.fromJson(json, JsonObject.class));
    }

    private CompletableFuture<Guild> findGuildInMongo(String guildId) {
        return CompletableFuture
                .supplyAsync(() -> collection.find(Filters.eq("uuid", guildId)).first())
                .thenApplyAsync(document -> document != null ? document.toJson() : null)
                .thenApplyAsync(this::deserializeGuild);
    }

    @Override
    public Guild getGuild(UUID id) {
        return guilds.get(id);
    }

    @Override
    public Guild getGuildOf(User user) {
        var uuid = user.getUuid();

        for (Guild guild : guilds.values())
            if (guild.isOwner(uuid) || guild.containsMember(uuid))
                return guild;

        return null;
    }

}