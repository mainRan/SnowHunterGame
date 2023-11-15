package me.terramain.guiapi;

import me.terramain.guiapi.guiFunctions.ClickType;
import me.terramain.guiapi.guiFunctions.GUIClickData;
import me.terramain.guiapi.guiFunctions.GUICloseData;
import me.terramain.guiapi.guiFunctions.GUIOpenData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIFunctionsInitialize implements Listener {

    private GUIManager guiManager;

    public GUIFunctionsInitialize(GUIManager guiManager){
        this.guiManager=guiManager;
    }

    @EventHandler
    public void openGUI(InventoryOpenEvent e){
        System.out.println(guiManager.guiList.size());
        GUI gui = guiManager.getGUIFromInventory(e.getInventory());
        if (gui==null) return;

        gui.getGuiFunctions().onOpen(
                new GUIOpenData(
                        gui,
                        (Player) e.getPlayer()
                )
        );
    }
    @EventHandler
    public void clickGUI(InventoryClickEvent e){
        System.out.println(guiManager.guiList.size());
        GUI gui = guiManager.getGUIFromInventory(e.getInventory());
        if (gui==null) return;

        GUIClickData guiClickData = new GUIClickData(
                gui,
                e.getSlot(),
                e.getRawSlot(),
                ClickType.clickType(e.getClick()),
                (Player) e.getWhoClicked()
        );
        gui.getGuiFunctions().onClick(guiClickData);
        e.setCancelled(guiClickData.isCancel());
    }
    @EventHandler
    public void closeGUI(InventoryCloseEvent e){
        System.out.println(guiManager.guiList.size());
        GUI gui = guiManager.getGUIFromInventory(e.getInventory());
        if (gui==null) return;

        gui.getGuiFunctions().onClose(
                new GUICloseData(
                        gui,
                        (Player) e.getPlayer()
                )
        );
    }
}
