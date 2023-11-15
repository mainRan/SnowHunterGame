package me.terramain.guiapi.guiFunctions;

import me.terramain.guiapi.GUI;
import org.bukkit.entity.Player;

public class GUIOpenData {
    private GUI gui;
    private Player player;

    public GUIOpenData(GUI gui, Player player) {
        this.gui = gui;
        this.player = player;
    }

    public GUI getGui() {return gui;}
    public Player getPlayer() {return player;}
}
