package me.terramain.guiapi.guiFunctions;

import me.terramain.guiapi.GUI;
import org.bukkit.entity.Player;

public class GUIClickData {
    private GUI gui;
    private int slot;
    private int uniqueSlot;
    private ClickType clickType;
    private Player whoClicked;
    private boolean cancel;

    public GUIClickData(GUI gui, int slot, int uniqueSlot, ClickType clickType, Player whoClicked) {
        this.gui = gui;
        this.slot = slot;
        this.uniqueSlot = uniqueSlot;
        this.clickType = clickType;
        this.whoClicked = whoClicked;
        cancel=false;
    }

    public GUI getGui() {return gui;}
    public int getSlot() {return slot;}
    public int getUniqueSlot() {return uniqueSlot;}
    public boolean ofPlayerInventory(){return slot!=uniqueSlot;}
    public ClickType getClickType() {return clickType;}
    public Player getWhoClicked() {return whoClicked;}

    public boolean isCancel() {return cancel;}
    public void setCancelled(boolean cancel) {this.cancel = cancel;}
}
