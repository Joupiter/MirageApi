package fr.mirage.api.sanction;

import fr.mirage.api.user.User;

import java.util.List;

public interface SanctionManager {

    Sanction getSanction(User user, String id);

    List<Sanction> getSanction(User user);
    List<Sanction> getSanction(User user, SanctionType type);

}