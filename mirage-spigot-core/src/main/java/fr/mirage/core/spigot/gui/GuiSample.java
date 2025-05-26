package fr.mirage.core.spigot.gui;

import fr.mirage.api.spigot.gui.Gui;
import fr.mirage.api.spigot.gui.GuiButton;
import fr.mirage.api.spigot.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class GuiSample extends Gui {

    public GuiSample() {
        super("&6Menu", 6, false);
    }

    @Override
    public void setup() {
        setItem(1, new GuiButton(new ItemBuilder(Material.ARROW).build(), event -> {
            event.getWhoClicked().sendMessage("Test !!");
        }));

        setHorizontalLine(10, 16, new ItemStack(Material.SAND));
        setVerticalLine(2, 26, new ItemStack(Material.SAND));
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        System.out.println("closed");
    }

    @Override
    public void onUpdate() {
        System.out.println("update");
    }

}