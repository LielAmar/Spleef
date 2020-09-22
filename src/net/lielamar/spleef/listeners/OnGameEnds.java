package net.lielamar.spleef.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.lielamar.spleef.listeners.custom.GameEndEvent;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.utils.SpleefPlayerUtils;

public class OnGameEnds implements Listener {

	@EventHandler
	public void onEnd(GameEndEvent e) {
		for(Player pl : e.getGame().getSpectators())
			SpleefPlayerUtils.respawnPlayer(pl);
		GameManager.getInstance().reloadGame(e.getGame());
	}
}
