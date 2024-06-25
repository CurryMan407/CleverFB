package org.curryman.cleverfb;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CleverFB extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("cfb").setExecutor(new CFBCommand(this));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (isInConfiguredWorld(player.getWorld()) && isFireDamage(event.getCause())) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isInConfiguredWorld(World world) {
        return getConfig().getStringList("disabled-fire-damage-worlds").contains(world.getName());
    }

    private boolean isFireDamage(EntityDamageEvent.DamageCause cause) {
        return cause == EntityDamageEvent.DamageCause.FIRE ||
                cause == EntityDamageEvent.DamageCause.FIRE_TICK ||
                cause == EntityDamageEvent.DamageCause.LAVA;
    }

    public void reloadPluginConfig() {
        reloadConfig();
    }

    public String translateColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text).replaceAll("(?i)&(#[a-f0-9]{6})", "§x§$1§$2§$3§$4§$5§$6");
    }
}