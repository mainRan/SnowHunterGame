package me.terramain.guiapi;

import me.terramain.guiapi.guiFunctions.GUIFunctions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUI {
    private GUIFunctions guiFunctions;
    private GUIType guiType;
    private Inventory inventory;

    public GUI(GUIFunctions guiFunctions, GUIType guiType) {
        this.guiFunctions = guiFunctions;
        this.guiType = guiType;
        this.inventory = Bukkit.createInventory(null,guiType.inventoryType);
    }

    public GUIFunctions getGuiFunctions() {return guiFunctions;}
    public GUIType getGuiType() {return guiType;}
    public Inventory getInventory() {return inventory;}

    public void setItem(int slot, ItemStack itemStack){
        inventory.setItem(slot,itemStack);
    }
    public void setItem(int slotLine, int slotColumn, ItemStack itemStack){
        int slot = (slotLine-1)*9 + slotColumn - 1;
        if (slot<0){
            try {
                throw new Exception("ArraySlotIndexOutOfBounds: " + slot + " less 0");
            } catch (Exception e) {e.printStackTrace();}
            return;
        }
        if (slot>=27){
            try {
                throw new Exception("ArraySlotIndexOutOfBounds: " + slot + " more 26");
            } catch (Exception e) {e.printStackTrace();}
            return;
        }
        inventory.setItem(slot,itemStack);
    }

    public ItemStack getItem(int slot){
        return inventory.getItem(slot);
    }
    public ItemStack getItem(int slotLine, int slotColumn){
        return inventory.getItem(slotColumn*slotLine-1);
    }


    public void openToPlayer(Player player){
        player.openInventory(inventory);
    }

    public GUI clone(){
        return new GUI(
                guiFunctions,
                guiType
        );
    }
}
