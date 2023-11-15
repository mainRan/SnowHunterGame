package me.terramain.guiapi;

import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {

    protected List<GUI> guiList;

    public GUIManager(){
        guiList=new ArrayList<>();
    }


    public GUI registerGUI(GUI gui){
        System.out.println(guiList.size());
        guiList.add(gui);
        gui.getGuiFunctions().onCreate(gui);
        return gui;
    }
    public GUI getGUIFromInventory(Inventory inventory){
        for (GUI gui : guiList) {
            if (gui.getInventory().equals(inventory)){
                return gui;
            }
        }
        return null;
    }
}
