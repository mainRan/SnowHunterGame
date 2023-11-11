package me.terramain.throwsnowballs;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main plugin;
    public static Main getPlugin() {return plugin;}

    @Override
    public void onEnable() {
        plugin=this;
    }

    @Override
    public void onDisable() {

    }
}
