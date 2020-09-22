package net.lielamar.spleef.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import net.lielamar.spleef.Main;
import net.lielamar.spleef.listeners.custom.GameStartEvent;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.utils.GlobalVariables;
import net.lielamar.spleef.utils.Messages;
import net.md_5.bungee.api.ChatColor;

public class OnGameStarts implements Listener {

	@EventHandler
	public void onStart(GameStartEvent e) {
		e.getGame().setStartedCountdown(false);
		e.getGame().setStartedGame(true);
		ItemStack item = null;
		
		try { item = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString("SpleefItem"))); }
		catch (Exception ex) { Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "SpleefItem in config is not valid!"); return; }

		for(Player pl : e.getGame().getPlayers()) {
			if(pl == null) continue;
			pl.teleport(e.getGame().getMap().getSpawn());
			pl.getInventory().setItem(0, item);
		}

        GameManager.getInstance().getThreads().put(e.getGame(), Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
			int i = GlobalVariables.GAME_TIME+1;

            @Override
            public void run() {
                i--;
                if(i < 60 && i%10 == 0 || i <= 5)
                	e.getGame().getGs().broadcastGameMessage(Messages.getInstance().gameEndsIn(i));
                if(i <= 1) {
                	e.getGame().getGs().broadcastGameMessage(Messages.getInstance().endingGame());
                	e.getGame().getGs().finishGame();
                    return;
                }
            }
        }, 0L, 20L));
	}
}
