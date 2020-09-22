package net.lielamar.spleef.listeners.custom;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.lielamar.spleef.moduels.Game;

public class GameEndEvent extends Event implements Cancellable {
	
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private Game game;
    private boolean isCancelled;

    public GameEndEvent(Game game) {
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

    public Game getGame() {
    	return this.game;
    }
}