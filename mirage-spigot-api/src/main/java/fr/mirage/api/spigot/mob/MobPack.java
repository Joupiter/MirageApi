package fr.mirage.api.spigot.mob;

import java.util.List;

public interface MobPack {

    List<Mob> getMobs();

    List<Mob> getAliveMobs();

    int getCount();
    int getSize();

}