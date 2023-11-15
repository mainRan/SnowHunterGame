package me.terramain.snowhuntergame;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SnowballShooterLoc {
    public Location location;
    public Player player;
    public Snowball snowball;
    public double timer;

    public Rabbit shooter;

    public SnowballShooterLoc(Location location, Player player, Snowball snowball) {
        this.location = location;
        this.player = player;
        this.snowball = snowball;

        timer = 5;
        shooter = (Rabbit) location.getWorld().spawnEntity(location, EntityType.RABBIT);
        shooter.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY,200,10,false,false
        ));
        shooter.setMaxHealth(100);
        shooter.setHealth(100);
        shooter.setBaby();
    }

    public boolean tick(){
        shooter.teleport(location);
        shooter.setVelocity(new Vector());
        timer-=Config.getTickSecondLength();
        if (timer<0){
            shooter.remove();
            return true;
        }
        if (timer<=Config.systemShooterSplit) snowball.setShooter(shooter);
        else snowball.setShooter(player);
        return false;
    }
}
