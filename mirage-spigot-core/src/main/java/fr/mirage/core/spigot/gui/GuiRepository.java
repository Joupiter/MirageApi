package fr.mirage.core.spigot.gui;

import fr.mirage.api.spigot.gui.Gui;
import fr.mirage.api.spigot.gui.GuiButton;
import fr.mirage.api.spigot.gui.GuiManager;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class GuiRepository implements GuiManager {

    private final Map<UUID, Gui> guis;

    public GuiRepository(JavaPlugin plugin) {
        this.guis = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private void updateButtons() {
        for (Gui gui : guis.values()) {
            if (gui.isUpdatable())
                gui.onUpdate();
        }
    }

    @Override
    public Gui getGui(UUID uuid) {
        return guis.get(uuid);
    }

    @Override
    public Gui getGui(HumanEntity player) {
        return guis.get(player.getUniqueId());
    }

    @Override
    public void open(HumanEntity humanEntity, Gui gui) {
        var menu = guis.compute(humanEntity.getUniqueId(), (k, v) -> gui);

        menu.onOpen(humanEntity);
    }

    @Override
    public void open(Player player, Gui gui) {
        var menu = guis.compute(player.getUniqueId(), (k, v) -> gui);

        menu.onOpen(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent event) {
        var itemStack = event.getCurrentItem();
        var player = event.getWhoClicked();

        if (itemStack == null) return;

        var gui = guis.get(player.getUniqueId());

        if (gui == null) return;

        var guiInventory = gui.getInventory();

        if (!event.getInventory().equals(guiInventory)) return;

        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            event.setCancelled(true);
            return;
        }

        if (!event.getClickedInventory().equals(guiInventory)) return;

        if (itemStack.getType() == Material.SKULL_ITEM) {
            for (GuiButton button : gui.getButtons().values()) {
                var buttonItem = button.getItemStack();

                if (buttonItem.hasItemMeta() && buttonItem.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName())) {
                    button.getConsumer().accept(event);
                    break;
                }
            }
        }
        else {
            for (GuiButton button : gui.getButtons().values()) {
                if (button.getItemStack().equals(itemStack)) {
                    button.getConsumer().accept(event);
                    break;
                }
            }
        }

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDrag(InventoryDragEvent event) {
        var gui = guis.get(event.getWhoClicked().getUniqueId());

        if (gui == null || !event.getInventory().equals(gui.getInventory())) return;

        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            var uuid = player.getUniqueId();
            var gui = guis.get(uuid);

            if (gui == null) return;

            if (event.getInventory().equals(gui.getInventory()) && gui.getCloseConsumer() != null) {
                gui.onClose(event);
                guis.remove(uuid);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        var player = event.getPlayer();

        guis.remove(player.getUniqueId());
    }

}