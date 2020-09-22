package net.lielamar.spleef.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.lielamar.spleef.moduels.Game;

public class PlayerWinEvent extends Event implements Cancellable {
	
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private Player p;
	private Game game;
    private boolean isCancelled;

    public PlayerWinEvent(Player p, Game game) {
        this.p = p;
        this.game = game;
        this.isCancelled = false;
    }
    
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
    
    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    public Player getPlayer() {
    	return this.p;
    }
    
    public Game getGame() {
    	return this.game;
    }
}