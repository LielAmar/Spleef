package net.lielamar.spleef.moduels;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

import net.lielamar.spleef.utils.GlobalVariables;

public class Game {
	
	private Player[] players;
	private List<Player> spectators;
	private GameMap map;
	
	private boolean startedCountdown;
	private boolean startedGame;
	
	private GameSteps gs;
	
	public Game(GameMap map) {
		this.players = new Player[GlobalVariables.MAX_PLAYERS_IN_GAME];
		this.spectators = new LinkedList<Player>();
		this.map = map;
		
		this.startedCountdown = false;
		this.startedGame = false;
		
		this.gs = new GameSteps(this);
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public List<Player> getSpectators() {
		return spectators;
	}

	public void setSpectators(List<Player> spectators) {
		this.spectators = spectators;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}
	public boolean startedCountdown() { 
		return startedCountdown;
	}
	
	public void setStartedCountdown(boolean choice) {
		this.startedCountdown = choice;
	}
	
	public boolean startedGame() { 
		return startedGame;
	}
	
	public void setStartedGame(boolean choice) {
		this.startedGame = choice;
	}
	
	public int getAvailableSpots() {
		int amount = 0;
		for(int i = 0; i < players.length; i++) {
			if(players[i] == null) amount++;
		}
		
		return amount;
	}

	public GameSteps getGs() {
		return gs;
	}

	public void setGs(GameSteps gs) {
		this.gs = gs;
	}
}
