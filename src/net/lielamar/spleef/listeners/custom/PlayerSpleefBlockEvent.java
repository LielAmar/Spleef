package net.lielamar.spleef.listeners.custom;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.lielamar.spleef.moduels.Game;

public class PlayerSpleefBlockEvent extends Event implements Cancellable {
	
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private Player p;
	private Game game;
	private Block block;
    private boolean isCancelled;

    public PlayerSpleefBlockEvent(Player p, Game game, Block block) {
        this.p = p;
        this.game = game;
        this.block = block;
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
    
    public Block getBlock() {
    	return this.block;
    }
}