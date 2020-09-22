package net.lielamar.spleef.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import net.lielamar.spleef.Main;
import net.lielamar.spleef.listeners.custom.PlayerSpleefBlockEvent;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.moduels.Game;

public class BlockEvents implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Game game = GameManager.getInstance().getPlayerGame(p);
		
		if(game == null) {
			game = GameManager.getInstance().getPlayerGameSpectator(p);
			
			if(game == null) return;
		}
		
		if(p.getInventory().getItemInMainHand().getType() != Material.valueOf(Main.getInstance().getConfig().getString("SpleefItem"))
				&& p.getInventory().getItemInOffHand().getType() != Material.valueOf(Main.getInstance().getConfig().getString("SpleefItem"))) {
			e.setCancelled(true);
			return;
		}
		
		if(!game.startedGame()) {
			e.setCancelled(true);
			return;
		}
		
		PlayerSpleefBlockEvent event = new PlayerSpleefBlockEvent(p, game, e.getBlock());
        Bukkit.getPluginManager().callEvent(event);
		
        if(!event.isCancelled()) {
        	if(e.getBlock().getType() == Material.SNOW_BLOCK || e.getBlock().getType() == Material.SNOW || e.getBlock() instanceof Snow) {
        		e.getBlock().getDrops().clear();
        		event.getBlock().getDrops().clear();
        		e.getBlock().setType(Material.AIR);
        		e.setCancelled(true);
        	}
        } else {
        	e.setCancelled(true);
        }
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Game game = GameManager.getInstance().getPlayerGame(p);
		if(game == null) {
			game = GameManager.getInstance().getPlayerGameSpectator(p);
			
			if(game == null) return;
		}
		e.setCancelled(true);
	}
}
