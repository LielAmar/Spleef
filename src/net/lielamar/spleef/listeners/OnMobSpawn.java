package net.lielamar.spleef.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

import net.lielamar.spleef.Main;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.moduels.GameMap;

public class OnMobSpawn implements Listener {

	@EventHandler
	public void mobSpawn(EntitySpawnEvent e) {
		if(e.getEntity() instanceof LivingEntity) {
			for(GameMap map : GameManager.getInstance().getMaps()) {
				if(e.getLocation().distance(map.getSpawn()) < Main.getVars().getMaxDistanceFromMap()) {
					e.setCancelled(true);
					e.getEntity().remove();
				}
			}
		}
	}
	
	@EventHandler
	public void itemSpawn(ItemSpawnEvent e) {
		if(e.getEntity().getType() == EntityType.SNOWBALL || e.getEntity().getItemStack().getType() == Material.SNOWBALL) {
			for(GameMap map : GameManager.getInstance().getMaps()) {
				if(e.getLocation().distance(map.getSpawn()) < Main.getVars().getMaxDistanceFromMap()) {
					e.getEntity().remove();
				}
			}
		}
	}
}
