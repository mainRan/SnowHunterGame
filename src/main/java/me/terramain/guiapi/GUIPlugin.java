package me.terramain.guiapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class GUIPlugin {
    public GUIManager guiManager;

    public GUIPlugin(JavaPlugin plugin){
        guiManager = new GUIManager();
        Bukkit.getPluginManager().registerEvents(new GUIFunctionsInitialize(guiManager), plugin);
    }
}
