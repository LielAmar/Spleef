package net.lielamar.spleef.moduels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.lielamar.spleef.Main;
import net.lielamar.spleef.listeners.custom.GameEndEvent;
import net.lielamar.spleef.listeners.custom.GameStartEvent;
import net.lielamar.spleef.listeners.custom.PlayerWinEvent;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.utils.GlobalVariables;
import net.lielamar.spleef.utils.Messages;
import net.lielamar.spleef.utils.SpleefPlayerUtils;

public class GameSteps {

	private Game game;
	public GameSteps(Game game) {
		this.game = game;
	}
	
	public void broadcastGameMessage(String message) {
		for(Player p : this.game.getPlayers()) {
			if(p == null) continue;
			p.sendMessage(message);
		}
		for(Player p : this.game.getSpectators())
			p.sendMessage(message);
	}
	
	public int getPlayersInGame() {
		int amount = 0;
		for(int i = 0; i < this.game.getPlayers().length; i++) {
			if(this.game.getPlayers()[i] != null) amount++;
		}
		
		return amount;
	}
	
	public boolean addPlayer(Player p) {
		for(int i = 0; i < this.game.getPlayers().length; i++) {
			if(this.game.getPlayers()[i] == null) {
				this.game.getPlayers()[i] = p;
				
				if(!GameManager.getInstance().getPlayersInventory().containsKey(p.getUniqueId())) {
					ItemStack[] inv = p.getInventory().getContents();
					GameManager.getInstance().getPlayersInventory().put(p.getUniqueId(), inv);
				}
				
				if(!GameManager.getInstance().getPlayersArmor().containsKey(p.getUniqueId())) {
					ItemStack[] armor = p.getInventory().getArmorContents();
					GameManager.getInstance().getPlayersArmor().put(p.getUniqueId(), armor);
				}
				
				if(!GameManager.getInstance().getPlayersEXP().containsKey(p.getUniqueId())) {
					GameManager.getInstance().getPlayersEXP().put(p.getUniqueId(), SpleefPlayerUtils.getTotalExperience(p));
				}
				
				if(!GameManager.getInstance().getPlayersLocation().containsKey(p.getUniqueId())) {
					GameManager.getInstance().getPlayersLocation().put(p.getUniqueId(), p.getLocation());
				}
				
				SpleefPlayerUtils.teleport(p, this.game.getMap().getSpawn());
				SpleefPlayerUtils.clearInventory(p);
				SpleefPlayerUtils.fixPlayer(p);
				SpleefPlayerUtils.hidePlayersThatArentInGame(p, this.game);
				
				broadcastGameMessage(Messages.getInstance().playerJoinedTheGame(p, getPlayersInGame()));
				tryToStartCountdown();
				return true;
			}
		}
		return false;
	}
	
	public boolean addSpectator(Player p) {
		this.game.getSpectators().add(p);
		
		if(!GameManager.getInstance().getPlayersInventory().containsKey(p.getUniqueId())) {
			ItemStack[] inv = p.getInventory().getContents();
			GameManager.getInstance().getPlayersInventory().put(p.getUniqueId(), inv);
		}
		
		if(!GameManager.getInstance().getPlayersArmor().containsKey(p.getUniqueId())) {
			ItemStack[] armor = p.getInventory().getArmorContents();
			GameManager.getInstance().getPlayersArmor().put(p.getUniqueId(), armor);
		}
		
		if(!GameManager.getInstance().getPlayersEXP().containsKey(p.getUniqueId())) {
			GameManager.getInstance().getPlayersEXP().put(p.getUniqueId(), SpleefPlayerUtils.getTotalExperience(p));
		}
		
		if(!GameManager.getInstance().getPlayersLocation().containsKey(p.getUniqueId())) {
			GameManager.getInstance().getPlayersLocation().put(p.getUniqueId(), p.getLocation());
		}
			
		SpleefPlayerUtils.teleport(p, this.game.getMap().getSpawn());
		SpleefPlayerUtils.clearInventory(p);
		SpleefPlayerUtils.fixSpectator(p);

		for(int i = 0; i < game.getPlayers().length; i++) {
			if(game.getPlayers()[i] != null) {
				game.getPlayers()[i].hidePlayer(Main.getInstance(), p);
			}
		}
		
		return true;
	}
	
	public boolean removePlayer(Player p, LoseReason reason) {
		for(int i = 0; i < this.game.getPlayers().length; i++) {
			if(this.game.getPlayers()[i] == null) continue;
			if(this.game.getPlayers()[i] == p) {
				
				this.game.getPlayers()[i] = null;
				SpleefPlayerUtils.showAllPlayers(p);
				
				if(reason == LoseReason.QUIT_GAME) {
					
				} else if(reason == LoseReason.TELEPORTING) {
					SpleefPlayerUtils.respawnPlayer(p);
				} else if(reason == LoseReason.LEAVE_MATCH) {
					SpleefPlayerUtils.respawnPlayer(p);
				} else if(reason == LoseReason.FALL || reason == LoseReason.WIN) {
					addSpectator(p);
				}
			}
		}
		
		if(!this.game.startedGame()) {
			broadcastGameMessage(Messages.getInstance().playerLeftTheGame(p, getPlayersInGame()));
			tryToStopCountdown();
		} else {    
			int amount = getPlayersInGame();
			if(amount != 0)
				broadcastGameMessage(Messages.getInstance().playerLostTheGame(p, amount));
			tryWin();
		}
		return false;
	}
	
	public boolean removeSpectator(Player p) {
		this.game.getSpectators().remove(p);
		
		SpleefPlayerUtils.showAllPlayers(p);
		
		if(GameManager.getInstance().getPlayersInventory().containsKey(p.getUniqueId())) {
			p.getInventory().setContents(GameManager.getInstance().getPlayersInventory().get(p.getUniqueId()));
			GameManager.getInstance().getPlayersInventory().remove(p.getUniqueId());
		}
		
		if(GameManager.getInstance().getPlayersArmor().containsKey(p.getUniqueId())) {
			p.getInventory().setArmorContents(GameManager.getInstance().getPlayersArmor().get(p.getUniqueId()));
			GameManager.getInstance().getPlayersArmor().remove(p.getUniqueId());
		}
		
		if(GameManager.getInstance().getPlayersEXP().containsKey(p.getUniqueId())) {
			SpleefPlayerUtils.setTotalExperience(p, GameManager.getInstance().getPlayersEXP().get(p.getUniqueId()));
			GameManager.getInstance().getPlayersEXP().remove(p.getUniqueId());
		}
		
		if (GameManager.getInstance().getPlayersLocation().containsKey(p.getUniqueId())) {
			p.teleport(GameManager.getInstance().getPlayersLocation().get(p.getUniqueId()));
		}
		
		p.setAllowFlight(false);
		p.setFlying(false);
		
		return true;
	}
	
	public void tryToStartCountdown() {
		if(getPlayersInGame() >= GlobalVariables.MIN_PLAYERS_IN_GAME) {
			startCountdown();
		}
	}

	public void tryToStopCountdown() {
		if(getPlayersInGame() < GlobalVariables.MIN_PLAYERS_IN_GAME) {
			stopCountdown();
		}
	}

	public void startCountdown() {
		if(!this.game.startedGame() && !this.game.startedCountdown()) {
			
			final Game myGame = this.game;
			this.game.setStartedCountdown(true);
            GameManager.getInstance().getThreads().put(this.game, Bukkit.getServer().getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
    			int i = GlobalVariables.COUNTDOWN_TIME+1;

                @Override
                public void run() {
                    i--;
                    if(i%10 == 0)
                    	Bukkit.broadcastMessage(Messages.getInstance().gameStartsInForBroadcast(i));
                    if(i%10 == 0 || i <= 5)
                    	broadcastGameMessage(Messages.getInstance().gameStartsIn(i));
                    if(i <= 1) {
                    	GameManager.getInstance().getThreads().get(myGame).cancel();
                    	broadcastGameMessage(Messages.getInstance().startingGame());
                    	startGame();
                        return;
                    }
                }
            }, 0L, 20L));
		}
	}

	public void stopCountdown() {
		if(this.game.startedCountdown()) {
			GameManager.getInstance().getThreads().get(this.game).cancel();
			GameManager.getInstance().getThreads().remove(this.game);
		}
	}
	
	public void startGame() {
		GameStartEvent event = new GameStartEvent(game);
        Bukkit.getPluginManager().callEvent(event);
	}

	public void tryWin() {
		int amount = getPlayersInGame();
		if(amount == 1) {
			for(Player pl : this.game.getPlayers()) {
				if(pl == null) continue;

				PlayerWinEvent event = new PlayerWinEvent(pl, game);
		        Bukkit.getPluginManager().callEvent(event);
			}
		}
		
		if(amount < 1)
			finishGame();
	}
	
	public void finishGame() {
		if(GameManager.getInstance().getThreads().containsKey(this.game) && GameManager.getInstance().getThreads().get(this.game) != null)
			GameManager.getInstance().getThreads().get(this.game).cancel();
    	
		for(Player pl : this.game.getPlayers()) {
			if(pl != null) {
				addSpectator(pl);
			}
		}
		
		this.game.setPlayers(new Player[1]);
		Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				endGame();
			}
		}, GlobalVariables.WIN_TIME*20);
	}

	public void endGame() {
		GameEndEvent event = new GameEndEvent(this.game);
        Bukkit.getPluginManager().callEvent(event);
	}
}
