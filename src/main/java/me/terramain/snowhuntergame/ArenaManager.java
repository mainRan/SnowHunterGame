package me.terramain.snowhuntergame;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {
    public static List<Arena> arenaList = new ArrayList<>();

    public static Arena getArena(String name){
        for (Arena arena : arenaList) {
            if (arena.name.equals(name)){
                return arena;
            }
        }
        return null;
    }
    public static Arena createArena(String name, String world){
        if (getArena(name)!=null){
            return null;
        }
        Arena arena = new Arena(name, Bukkit.getWorld(world));
        arenaList.add(arena);
        return arena;
    }
    public static boolean deleteArena(String name){
        for (Arena arena : arenaList) {
            if (arena.name.equals(name)){
                arenaList.remove(arena);
                return true;
            }
        }
        return false;
    }
    public static void addArena(Arena arena){
        for (Arena arenaOfList:arenaList) {
            if (arenaOfList.name.equals(arena.name)) return;
        }
        arenaList.add(arena);
    }
    public static void runTicking(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {@Override public void run() {
            for (Arena arena : ArenaManager.arenaList) arena.tick();
        }}, Config.getTickSpeed(), Config.getTickSpeed());
    }
}
