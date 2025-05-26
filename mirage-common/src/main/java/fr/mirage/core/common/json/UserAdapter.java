package fr.mirage.core.common.json;

import com.google.gson.JsonObject;
import fr.mirage.api.rank.RankManager;
import fr.mirage.api.user.User;
import fr.mirage.api.user.UserHistory;
import fr.mirage.api.utils.json.GsonAdapter;
import fr.mirage.api.utils.json.JsonObjectBuilder;
import fr.mirage.core.common.user.MirageUser;
import fr.mirage.core.common.user.MirageUserHistory;

import java.util.HashSet;
import java.util.UUID;

public record UserAdapter(RankManager rankManager) implements GsonAdapter<User> {

    @Override
    public JsonObject serialize(User user) {
        if (user == null) return null;

        return new JsonObjectBuilder()
                .add("uuid", user.getUuid().toString())
                .add("rank", user.getRank().getId())
                .add("essences", user.getEssences())
                .add("alvaris", user.getAlvaris())
                .add("guildId", user.getGuildId().toString())
                .add("history", serializeHistory(user.getHistory()))
                .build();
    }

    @Override
    public User deserialize(JsonObject jsonObject) {
        if (jsonObject == null) return null;

        try {
            return new MirageUser(
                    UUID.fromString(jsonObject.get("uuid").getAsString()),
                    rankManager.getRank(jsonObject.get("rank").getAsInt()),
                    null,
                    deserializeHistory(jsonObject.getAsJsonObject("history")),
                    jsonObject.get("essences").getAsLong(),
                    jsonObject.get("alvaris").getAsLong(),
                    UUID.fromString(jsonObject.get("guildId").getAsString())
            );
        } catch (Exception exception) {
            return null;
        }
    }

    public UserHistory deserializeHistory(JsonObject jsonObject) {
        if (jsonObject == null)
            return new MirageUserHistory(System.currentTimeMillis(), System.currentTimeMillis(), null, null);

        return new MirageUserHistory(
                jsonObject.get("createdAt").getAsLong(),
                jsonObject.get("lastJoin").getAsLong(),
                new HashSet<>(),
                null
        );
    }

    public JsonObject serializeHistory(UserHistory history) {
        return new JsonObjectBuilder()
                .add("createdAt", history.getCreatedAt())
                .add("lastJoin", history.getLastJoin())
                .build();
    }

}
