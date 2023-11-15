package me.terramain.snowhuntergame;


import me.terramain.guiapi.GenItem;
import me.terramain.libs.Title;
import me.terramain.randomvalue.RandomValue;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredListener;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    public String name;
    public World world;
    public Location lobbySpawn;
    public Location playersSpawn;
    public Location huntersSpawn;
    public int playersNumber;
    public int huntersNumber;
    public long gameDuration;//sec
    public long durationForHide;//sec

    public boolean ofGame;
    public List<Player> players;
    public List<Player> hunters;
    public double timer;//tick

    public List<HunterRecharge> huntersRecharges;
    public List<SnowballShooterLoc> snowballShooterLocs;


    public Arena(String name, World world) {
        this.name = name;
        this.world = world;
        this.lobbySpawn = new Location(world, 0, 100, 0);
        this.playersSpawn = new Location(world, 0, 100, 0);
        this.huntersSpawn = new Location(world, 0, 100, 0);
        this.playersNumber = Config.defaultPlayersNumber;
        this.huntersNumber = Config.defaultHuntersNumber;
        gameDuration = Config.defaultGameDuration;
        durationForHide = Config.defaultDurationForHide;

        ofGame = false;
        players = new ArrayList<>();
        hunters = new ArrayList<>();

        Bukkit.getPluginManager().registerEvents(new Events(), Main.getPlugin());
    }

    private static void giveSnowball(Player player) {
        player.getInventory().setItem(4,new GenItem(Material.SNOW_BALL).compile());
    }
    private static void giveSnowBlockOnHead(Player player) {
        player.getInventory().setHelmet(new GenItem(Material.SNOW_BLOCK,"голова снеговика").compile());
    }

    public Boolean settingHaveParams(String settingName){
        switch (settingName) {
            case "lobbyspawn":
                return false;
            case "playersspawn":
                return false;
            case "huntersspawn":
                return false;
            case "playersnumber":
                return true;
            case "huntersnumber":
                return true;
            case "gameduration":
                return true;
            case "durationforhide":
                return true;
        }
        return null;
    }
    public void setting(String settingName,String value){
        if (settingName.equalsIgnoreCase("lobbyspawn")) lobbySpawn=Bukkit.getPlayer(value).getLocation(); //value - player who executed the command
        else if (settingName.equalsIgnoreCase("playersspawn")) playersSpawn=Bukkit.getPlayer(value).getLocation(); //value - player who executed the command
        else if (settingName.equalsIgnoreCase("huntersspawn")) huntersSpawn=Bukkit.getPlayer(value).getLocation(); //value - player who executed the command

        else if (settingName.equalsIgnoreCase("playersnumber")) playersNumber=Integer.parseInt(value); //value - players number
        else if (settingName.equalsIgnoreCase("huntersnumber")) huntersNumber=Integer.parseInt(value); //value - hunters number

        else if (settingName.equalsIgnoreCase("gameduration")) gameDuration=Integer.parseInt(value); //value - game duration of seconds
        else if (settingName.equalsIgnoreCase("durationforhide")) durationForHide=Integer.parseInt(value); //value - game duration of seconds
    }
    public boolean joinGame(Player player, boolean forced){
        if (!forced) {
            if (players.size()>=playersNumber+huntersNumber) return false;
            if (ofGame) return false;
        }
        players.remove(player);
        players.add(player);
        player.teleport(lobbySpawn);
        player.sendMessage("перемещение.");
        return true;
    }
    public void runGame(){
        ofGame=true;
        huntersRecharges = new ArrayList<>();
        snowballShooterLocs = new ArrayList<>();
        timer=gameDuration;
        for (int i = 0; i < huntersNumber; i++) {//часть игроков превращается в снеговиков.
            RandomValue randomValue = new RandomValue();
            for (Player player : players) {
                randomValue.addOption(player,1);
            }
            Player player = (Player) randomValue.getResult();
            hunters.add(player);
            players.remove(player);
        }
        Title playerTitle = new Title(ChatColor.GREEN+"Вы обычный игрок.",ChatColor.YELLOW+"остерегайся снеговика",0,1,2);
        Title hunterTitle = new Title(ChatColor.RED+"Вы снеговик!",ChatColor.GREEN+"скоро ты оживёшь.",0,1,2);
        players.forEach(player -> {
            player.teleport(playersSpawn);
            playerTitle.send(player);
        });
        hunters.forEach(player -> {
            player.teleport(huntersSpawn);
            hunterTitle.send(player);
            giveSnowBlockOnHead(player);
        });
    }

    public void tick(){
        if (ofGame) gameTick();
        else preGameTick();
    }
    public void preGameTick(){
        if (players.size()==playersNumber+huntersNumber){
            Title title = new Title("Игра скоро начнётся!","пару секунд...",0,40,20);
            players.forEach(title::send);

            Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> {
                if (players.size()==playersNumber+huntersNumber){
                    runGame();
                }
            }, 5*20);

        }
    }
    public void gameTick(){
        long gameSecond = (long) (gameDuration-timer);//sec
        if (durationForHide >= gameSecond){//блокировка снеговика
            Title hunterTitle = new Title(ChatColor.RED+"Вы снеговик!",ChatColor.GREEN+"до начала..."+(durationForHide-gameSecond),0,1,3);
            hunters.forEach(player -> {
                player.teleport(huntersSpawn);
                hunterTitle.send(player);
            });
        }
        if (//снежок
                durationForHide <= gameSecond &&
                gameSecond+1 >= durationForHide
        ) hunters.forEach(Arena::giveSnowball);

        for (int i = 0; i < huntersRecharges.size(); i++) {// таймер на снежки.
            HunterRecharge hunterRecharge = huntersRecharges.get(i);
            if (hunterRecharge.timer<=0){
                huntersRecharges.remove(hunterRecharge);
            }
            hunterRecharge.timer-=Config.getTickSecondLength();
        }
        if (Config.systemDamageVectorOn) {
            for (int i = 0; i < snowballShooterLocs.size(); i++) {// таймер на снежки.
                SnowballShooterLoc shooterLoc = snowballShooterLocs.get(i);
                if (shooterLoc.tick()) huntersRecharges.remove(shooterLoc);
            }
        }

        if (timer<=0 || players.size()==0){
            gameStop();
            return;
        }

        timer -= Config.getTickSecondLength();
    }

    public void playerDeath(Player player){
        players.remove(player);
        hunters.add(player);
        giveSnowBlockOnHead(player);
        Title title = new Title(ChatColor.RED+"Вы снеговик!",ChatColor.BLACK+"ловите игроков!",0,1,1);
        title.send(player);

        String massage = ChatColor.GREEN + player.getName() + ChatColor.RED + " стал снеговиком!";
        players.forEach(playerX -> {
            playerX.sendMessage(massage);
        });
        hunters.forEach(playerX -> {
            playerX.sendMessage(massage);
        });
    }

    public void gameStop(){
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.addAll(players);
        allPlayers.addAll(hunters);

        Title title;
        boolean playersWin;
        if (players.size()==0) {
            title = new Title(ChatColor.GOLD + "Победили снеговики!","",0,2,1);
            playersWin=false;
        }
        else {
            title = new Title(ChatColor.GOLD + "Победили игроки!","",0,2,1);
            playersWin=true;
        }

        allPlayers.forEach(player -> {
            title.send(player);
        });

        players.forEach(player -> {
            if (playersWin) {
                for (int i = 0; i < 100; i+=10) {
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(),() -> {
                        player.playSound(player.getLocation(),Sound.FIREWORK_LARGE_BLAST2,3,1);
                    },i);
                }
            }
            else player.playSound(player.getLocation(),Sound.WITHER_DEATH,3,1);
        });
        hunters.forEach(player -> {
            if (playersWin) player.playSound(player.getLocation(),Sound.WITHER_DEATH,3,1);
            else {
                for (int i = 0; i < 100; i+=10) {
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(),() -> {
                        player.playSound(player.getLocation(),Sound.FIREWORK_LARGE_BLAST2,3,1);
                    },i);
                }
            }
        });



        ofGame=false;
    }



    class Events implements Listener {

        @EventHandler
        public void snowballLaunch(PlayerInteractEvent e){
            Player player = e.getPlayer();

            if (!players.contains(player) && !hunters.contains(player)) return;//игрок участник арены?
            if (player.getItemInHand().getData().getItemType() != Material.SNOW_BALL) return;//снежок?
            e.setCancelled(true);//отмена настоящего запуска снежка
            for (HunterRecharge hunterRecharge : huntersRecharges) {//проверка перезарядки
                if (hunterRecharge.player.equals(player)) {
                    double printingTimer = (double)(int)(hunterRecharge.timer * 10) / 10;//оставить 1 знак после запятой
                    player.sendMessage(ChatColor.RED+"Перезарядка! Снежок доступен через " + printingTimer + " сек.");
                    return;
                }
            }


            Snowball snowball = player.getWorld().spawn(player.getEyeLocation(), Snowball.class);
            SnowballShooterLoc shooterLoc = null;
            if (Config.systemDamageVectorOn) shooterLoc = new SnowballShooterLoc(player.getLocation(), player, snowball);
            snowball.setShooter(player);
            snowball.setVelocity(player.getLocation().getDirection().multiply(
                    Config.globalSnowballSpeed
            ));
            if (Config.systemDamageVectorOn) snowballShooterLocs.add(shooterLoc);

            huntersRecharges.add(new HunterRecharge(player,Config.globalSnowballRecharge));//включение перезарядки
        }

        @EventHandler
        public void snowballHit(EntityDamageEvent e){
            Player player = (Player) e.getEntity();
            if (hunters.contains(player)) {
                e.setCancelled(true);
                return;
            }
            if (!players.contains(player)) return;

            EntityDamageEvent.DamageCause damageCause = e.getCause();
            if (!damageCause.equals(EntityDamageEvent.DamageCause.PROJECTILE)){//урон не от снаряда
                e.setCancelled(true);
                return;
            }

            playerDeath(player);
        }
    }



}
