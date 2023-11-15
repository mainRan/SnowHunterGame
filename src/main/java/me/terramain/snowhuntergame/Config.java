package me.terramain.snowhuntergame;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    public static int defaultPlayersNumber = 5;
    public static int defaultHuntersNumber = 1;
    public static long defaultGameDuration = 210;
    public static long defaultDurationForHide = 15;
    public static double globalSnowballRecharge = 2.5;
    public static double globalSnowballSpeed = 0.7;
    public static double globalBonusSpawnSpeed = 5;
    public static int globalBonusBatchSize = 3;
    public static int systemTickSpeed = 4;
    public static double systemShooterSplit = 1.2;
    public static boolean systemDamageVectorOn = false;



    private static final String pluginFolderPath = "plugins"+File.separator+"snowHunterGame";

    public static void load(){
        try {
            regenFolders();
            loadSettings();
            loadArenas();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    public static void regenFolders() throws IOException {
        File pluginFolder = new File(pluginFolderPath);
        if (!pluginFolder.mkdir()){
            pluginFolder.mkdirs();
        }

        File arenasFolder = new File(pluginFolderPath+File.separator+"arenas");
        if (!arenasFolder.mkdir()) {
            arenasFolder.mkdirs();
        }
    }

    public static void loadSettings() throws IOException {
        File file = new File(pluginFolderPath+File.separator+"settings.yml");
        if (!file.exists()) {
            file.createNewFile();

            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("default.playersNumber",defaultPlayersNumber);
            config.set("default.huntersNumber",defaultHuntersNumber);
            config.set("default.gameDuration",defaultGameDuration);
            config.set("default.durationForHide",defaultDurationForHide);
            config.set("global.snowballRecharge", globalSnowballRecharge);
            config.set("global.globalSnowballSpeed", globalSnowballSpeed);
            config.set("system.tickSpeed", systemTickSpeed);
            config.set("system.shooterSplit", systemShooterSplit);
            config.set("system.damageVectorOn", systemDamageVectorOn);
            config.save(file);
            return;
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        defaultPlayersNumber = config.getInt("default.playersNumber");
        defaultHuntersNumber = config.getInt("default.huntersNumber");
        defaultGameDuration = config.getInt("default.gameDuration");
        defaultDurationForHide = config.getInt("default.durationForHide");

        globalSnowballRecharge = config.getDouble("global.snowballRecharge");
        globalSnowballSpeed = config.getDouble("global.globalSnowballSpeed");

        systemTickSpeed = config.getInt("system.tickSpeed");
        systemShooterSplit = config.getDouble("system.shooterSplit");
        systemDamageVectorOn = config.getBoolean("system.damageVectorOn");
    }

    public static void loadArenas(){
        File arenasFolder = new File(pluginFolderPath+File.separator+"arenas"+File.separator);
        File[] arenas = arenasFolder.listFiles();
        Main.Console.print("load arenas: " + arenas.length + " arenas");

        //arenaConfig;
        FileConfiguration arenaConfig;
        for (File file : arenas){
            arenaConfig=YamlConfiguration.loadConfiguration(file);
            World world = Bukkit.getWorld( arenaConfig.getString("world") );
            Location lobbySpawn = new Location(
                    world,
                    arenaConfig.getInt("lobbySpawn.x"),
                    arenaConfig.getInt("lobbySpawn.y"),
                    arenaConfig.getInt("lobbySpawn.z")
            );
            Location playersSpawn = new Location(
                    world,
                    arenaConfig.getInt("playersSpawn.x"),
                    arenaConfig.getInt("playersSpawn.y"),
                    arenaConfig.getInt("playersSpawn.z")
            );
            Location huntersSpawn = new Location(
                    world,
                    arenaConfig.getInt("huntersSpawn.x"),
                    arenaConfig.getInt("huntersSpawn.y"),
                    arenaConfig.getInt("huntersSpawn.z")
            );
            int playersNumber = arenaConfig.getInt("playersNumber");
            int huntersNumber = arenaConfig.getInt("huntersNumber");
            int gameDuration = arenaConfig.getInt("gameDuration");
            int durationForHide = arenaConfig.getInt("durationForHide");



            Arena arena = new Arena( file.getName().split("\\.")[0] , world );
            arena.lobbySpawn=lobbySpawn;
            arena.playersSpawn=playersSpawn;
            arena.huntersSpawn=huntersSpawn;
            arena.playersNumber=playersNumber;
            arena.huntersNumber=huntersNumber;
            arena.gameDuration=gameDuration;
            arena.durationForHide=durationForHide;
            ArenaManager.addArena(arena);
        }
    }
    public static void saveArenas() throws IOException {
        File arenasFolder = new File(pluginFolderPath+File.separator+"arenas"+File.separator);
        File[] arenas = arenasFolder.listFiles();
        for (File file : arenas) file.delete();

        FileConfiguration arenaConfig;
        for (Arena arena : ArenaManager.arenaList) {
            File file = new File(pluginFolderPath+File.separator+"arenas"+File.separator+arena.name+".yml");
            file.createNewFile();
            arenaConfig=YamlConfiguration.loadConfiguration(file);


            Location lobbySpawn = arena.lobbySpawn;
            Location playersSpawn = arena.playersSpawn;
            Location huntersSpawn = arena.huntersSpawn;

            arenaConfig.set("world",arena.world.getName());

            arenaConfig.set("lobbySpawn.x",lobbySpawn.getBlockX());
            arenaConfig.set("lobbySpawn.y",lobbySpawn.getBlockY());
            arenaConfig.set("lobbySpawn.z",lobbySpawn.getBlockZ());

            arenaConfig.set("playersSpawn.x",playersSpawn.getBlockX());
            arenaConfig.set("playersSpawn.y",playersSpawn.getBlockY());
            arenaConfig.set("playersSpawn.z",playersSpawn.getBlockZ());

            arenaConfig.set("huntersSpawn.x",huntersSpawn.getBlockX());
            arenaConfig.set("huntersSpawn.y",huntersSpawn.getBlockY());
            arenaConfig.set("huntersSpawn.z",huntersSpawn.getBlockZ());

            arenaConfig.set("playersNumber",arena.playersNumber);
            arenaConfig.set("huntersNumber",arena.huntersNumber);
            arenaConfig.set("durationForHide",arena.durationForHide);

            arenaConfig.save(file);
        }
    }


    public static int getTickSpeed(){
        return systemTickSpeed;
    }
    public static double getTickSecondLength(){
        return (double) systemTickSpeed / 20;
    }

}
