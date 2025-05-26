package fr.mirage.api.spigot.gui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.UUID;

public interface GuiManager extends Listener {

    Map<UUID, Gui> getGuis();

    Gui getGui(UUID uuid);
    Gui getGui(HumanEntity player);

    void open(HumanEntity humanEntity, Gui gui);
    void open(Player player, Gui gui);

}