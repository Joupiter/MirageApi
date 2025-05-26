package fr.mirage.core.common.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.mirage.api.guild.Guild;
import fr.mirage.api.guild.GuildAccessor;
import fr.mirage.api.guild.GuildMember;
import fr.mirage.api.guild.GuildRank;
import fr.mirage.api.utils.json.GsonAdapter;
import fr.mirage.api.utils.json.JsonObjectBuilder;
import fr.mirage.core.common.guild.MirageGuild;
import fr.mirage.core.common.guild.MirageGuildMember;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record GuildAdapter() implements GsonAdapter<Guild> {

    @Override
    public JsonObject serialize(Guild guild) {
        var owner = guildMemberToJson(guild.getOwner());
        var members = new JsonArray();

        guild.getMembers().stream()
                .map(this::guildMemberToJson)
                .forEach(members::add);

        return new JsonObjectBuilder()
                .add("id", guild.getId().toString())
                .add("createdAt", guild.getCreatedAt())
                .add("name", guild.getName())
                .add("tag", guild.getTag())
                .add("description", guild.getDescription())
                .add("description", guild.getAccessor().name())
                .add("owner", owner)
                .add("members", members)
                .build();
    }

    @Override
    public Guild deserialize(JsonObject jsonObject) {
        var owner = guildMemberFromJson(jsonObject.get("owner").getAsJsonObject());
        var membersArray = jsonObject.get("members").getAsJsonArray();

        Set<GuildMember> members = new HashSet<>();

        membersArray.asList().stream()
                .map(JsonElement::getAsJsonObject)
                .map(this::guildMemberFromJson)
                .forEach(members::add);

        return new MirageGuild(
                UUID.fromString(jsonObject.get("id").getAsString()),
                jsonObject.get("createdAt").getAsLong(),
                jsonObject.get("name").getAsString(),
                jsonObject.get("tag").getAsString(),
                jsonObject.get("description").getAsString(),
                GuildAccessor.valueOf(jsonObject.get("accessor").getAsString()),
                owner,
                members
        );
    }

//    @Override
//    public Document toDocument(Guild guild) {
//        List<Document> membersDoc = new ArrayList<>();
//
//        guild.getMembers().stream()
//                .map(this::guildMemberToDocument)
//                .forEach(membersDoc::add);
//
//        return new Document()
//                .append("id", guild.getId().toString())
//                .append("createdAt", guild.getCreatedAt())
//                .append("name", guild.getName())
//                .append("tag", guild.getTag())
//                .append("description", guild.getDescription())
//                .append("owner", guildMemberToDocument(guild.getOwner()))
//                .append("members", membersDoc);
//    }
//
//    @Override
//    public Guild fromDocument(Document document) {
//        var owner = guildMemberFromDocument(document.get("owner", Document.class));
//        Set<GuildMember> members = new HashSet<>();
//
//        document.getList("members", Document.class).stream()
//                .filter(Objects::nonNull)
//                .map(this::guildMemberFromDocument)
//                .forEach(members::add);
//
//        return new MirageGuild(
//                UUID.fromString(document.getString("id")),
//                document.getLong("createdAt"),
//                document.getString("name"),
//                document.getString("tag"),
//                document.getString("description"),
//                owner,
//                members
//        );
//    }

    private GuildMember guildMemberFromJson(JsonObject jsonObject) {
        return new MirageGuildMember(
                UUID.fromString(jsonObject.get("uuid").getAsString()),
                jsonObject.get("name").getAsString(),
                jsonObject.get("joinedAt").getAsLong(),
                GuildRank.valueOf(jsonObject.get("rank").getAsString()),
                jsonObject.get("online").getAsBoolean()
        );
    }

    private JsonObject guildMemberToJson(GuildMember guildMember) {
        return new JsonObjectBuilder()
                .add("uuid", guildMember.getUuid().toString())
                .add("name", guildMember.getName())
                .add("rank", guildMember.getRank().name())
                .add("joinedAt", guildMember.getJoinedAt())
                .add("online", guildMember.isOnline())
                .build();
    }

//    private GuildMember guildMemberFromDocument(Document document) {
//        return new MirageGuildMember(
//                UUID.fromString(document.getString("uuid")),
//                document.getString("name"),
//                document.getLong("joinedAt"),
//                GuildRank.valueOf(document.getString("rank")),
//                document.getBoolean("online")
//        );
//    }
//
//    private Document guildMemberToDocument(GuildMember guildMember) {
//        return new Document()
//                .append("uuid", guildMember.getUuid().toString())
//                .append("name", guildMember.getName())
//                .append("rank", guildMember.getRank().name())
//                .append("joinedAt", guildMember.getJoinedAt())
//                .append("online", guildMember.isOnline());
//    }

}