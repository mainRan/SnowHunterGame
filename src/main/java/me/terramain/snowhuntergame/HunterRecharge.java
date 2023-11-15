package me.terramain.snowhuntergame;

import org.bukkit.entity.Player;

public class HunterRecharge {
    public Player player;
    public double timer;

    public HunterRecharge(Player player, double timer) {
        this.player = player;
        this.timer = timer;
    }
}
