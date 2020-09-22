package net.lielamar.spleef.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.moduels.GameMap;
import net.lielamar.spleef.utils.GlobalVariables;

public class SnowMeltEvent implements Listener {

	@EventHandler
	public void melt(BlockFadeEvent e) {
		if(e.getBlock().getType() == Material.SNOW_BLOCK) {
			for(GameMap map : GameManager.getInstance().getMaps()) {
				if(e.getBlock().getLocation().distance(map.getSpawn()) < GlobalVariables.MAX_DISTANCE_FROM_MAP)
					e.setCancelled(true);
			}
		}
	}
}
	