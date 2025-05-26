package fr.mirage.core.common.user;

import fr.mirage.api.game.GameType;
import fr.mirage.api.rank.Rank;
import fr.mirage.api.user.User;
import fr.mirage.api.user.UserHistory;
import fr.mirage.api.user.UserRanking;
import fr.mirage.api.user.UserSession;
import fr.mirage.core.common.rank.MirageRank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class MirageUser implements User {

    private static final UUID DEFAULT_GUILD_ID = new UUID(0L, 0L);

    private final UUID uuid;

    private Rank rank;

    private final UserSession session;
    private final UserHistory history;

    private final Map<GameType, UserRanking> divisions;

    private long essences;
    private long alvaris;

    private UUID guildId;

    public MirageUser(UUID uuid) {
        this.uuid = uuid;
        this.rank = MirageRank.DEFAULT_RANK;
        this.session = new MirageUserSession(null);
        this.history = new MirageUserHistory();
        this.essences = 0;
        this.alvaris = 0;
        this.guildId = DEFAULT_GUILD_ID;
        this.divisions = new HashMap<>(GameType.getTypes().length);
    }

    public MirageUser(UUID uuid, Rank rank, UserSession session, UserHistory history, long essences, long alvaris, UUID guildId) {
        this.uuid = uuid;
        this.rank = rank;
        this.session = session;
        this.history = history;
        this.essences = essences;
        this.alvaris = alvaris;
        this.guildId = guildId;
        this.divisions = new HashMap<>(GameType.getTypes().length);
    }

    @Override
    public boolean hasGuild() {
        return guildId != DEFAULT_GUILD_ID;
    }

}