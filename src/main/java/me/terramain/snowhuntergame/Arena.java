package me.terramain.throwsnowballs;

import me.terramian.randomvalue.RandomValue;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    public String name;
    public World world;
    public Location lobbySpawn;
    public Location playersSpawn;
    public Location hunterSpawn;
    public int playersNumber;
    public int huntersNumber;


    public boolean ofGame;
    public List<Player> players;
    public List<Player> hunters;
    public long gameDuration;

    public Arena(String name, World world) {
        this.name = name;
        this.world = world;
        this.lobbySpawn = new Location(world, 0, 100, 0);
        this.playersSpawn = new Location(world, 0, 100, 0);
        this.hunterSpawn = new Location(world, 0, 100, 0);
        this.playersNumber = Config.defaultPlayersNumber;
        this.huntersNumber = Config.defaultHuntersNumber;

        ofGame = false;
        players = new ArrayList<>();
        hunters = new ArrayList<>();
        gameDuration = 20L * Config.defaultGameDuration;
    }

    public boolean joinGame(Player player){
        if (players.size()<playersNumber){
            players.add(player);
            return true;
        }
        return false;
    }
    public void runGame(){
        RandomValue randomValue = new RandomValue();
        for (Player player : players) {
            randomValue.addOption(player,1);
        }
        runGame(
                (Player) randomValue.getResult()
        );
    }
    public void runGame(Player hunter){
        ofGame=true;
        hunters.add(hunter);

        int process = 0;process = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {@Override public void run() {
                if (ofGame) tick();
                else {
                    gameStop();
                    Bukkit.getScheduler().cancelTask(process);
                }
        }}, 5, 5);
        //if (ofGame) tick();
        //            else {
        //                gameStop();
        //                Bukkit.getScheduler().cancelTask(finalProcess);
        //            }

    }

    public void tick(){
        gameDuration-=5;

    }

    public void gameStop(){

    }

}
