package fr.mirage.api.spigot.gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@Getter
@Setter
@AllArgsConstructor
public class GuiButton {

    protected final ItemStack itemStack;
    protected Consumer<InventoryClickEvent> consumer;

    public GuiButton(ItemStack  itemStack) {
        this(itemStack, event -> {});
    }

}