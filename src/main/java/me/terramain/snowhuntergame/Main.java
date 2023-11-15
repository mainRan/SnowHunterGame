package me.terramain.snowhuntergame;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public final class Main extends JavaPlugin {

    private static Main plugin;
    public static Main getPlugin() {return plugin;}

    @Override
    public void onEnable() {
        plugin=this;
        new CMD();
        Config.load();
        ArenaManager.runTicking();
    }

    @Override
    public void onDisable() {
        try {
            Config.saveArenas();
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public static class Console{

        public static void print(String text){
            System.out.println("[SnowHunterGame]: " + text);
        }

    }
}
