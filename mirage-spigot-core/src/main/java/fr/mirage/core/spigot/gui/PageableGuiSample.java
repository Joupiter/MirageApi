package fr.mirage.core.spigot.gui;

import fr.mirage.api.spigot.gui.GuiButton;
import fr.mirage.api.spigot.gui.PageableGui;
import fr.mirage.api.spigot.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.List;

public class PageableGuiSample extends PageableGui<String> {

    public PageableGuiSample(String inventoryName, boolean updatable, int rows, int maxItems) {
        super(inventoryName, updatable, rows, maxItems);

        List.of("hello", "world", "!")
                .forEach(pagination::addElement);
    }

    @Override
    public void setup() {
        setItem(1, nextPageButton());
        setItem(2, previousPageButton());
    }

    @Override
    public GuiButton nextPageButton() {
        return new GuiButton(new ItemBuilder(Material.ARROW).setName("§8» §7Page suivante").build(), this::nextPage);
    }

    @Override
    public GuiButton previousPageButton() {
        return new GuiButton(new ItemBuilder(Material.ARROW).setName("§8» §7Page suivante").build(), this::previousPage);
    }

}