package net.lielamar.spleef.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.lielamar.spleef.listeners.custom.PlayerWinEvent;
import net.lielamar.spleef.utils.Messages;

public class OnPlayerWin implements Listener {

	@EventHandler
	public void onWin(PlayerWinEvent e) {
		e.getGame().getGs().broadcastGameMessage(Messages.getInstance().wonTheGame(e.getPlayer()));
		e.getGame().getGs().finishGame();
	}
	
}
