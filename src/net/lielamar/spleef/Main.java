package net.lielamar.spleef;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import net.lielamar.spleef.commands.spleef.SpleefCommand;
import net.lielamar.spleef.listeners.BlockEvents;
import net.lielamar.spleef.listeners.DistanceEvents;
import net.lielamar.spleef.listeners.OnCommandMidGame;
import net.lielamar.spleef.listeners.OnGameEnds;
import net.lielamar.spleef.listeners.OnGameStarts;
import net.lielamar.spleef.listeners.OnMobSpawn;
import net.lielamar.spleef.listeners.OnPlayerWin;
import net.lielamar.spleef.listeners.PickupDropEvents;
import net.lielamar.spleef.listeners.PlayerLoseEvents;
import net.lielamar.spleef.listeners.SnowMeltEvent;
import net.lielamar.spleef.listeners.StatusEvents;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.managers.StorageManager;
import net.lielamar.spleef.utils.GlobalVariables;
import net.lielamar.spleef.utils.SpleefPlayerUtils;

public class Main extends JavaPlugin {

	private static Main instance;
	
	private static GlobalVariables vars;
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		instance = this;
		saveDefaultConfig();
		
		setupManagers();
		
		registerCommands();
		registerEvents();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		instance = null;
		
		System.gc();
	}

	public void registerCommands() {
		SpleefCommand.getInstance().setup(this);
	}
	
	public void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new SnowMeltEvent(), this);
		pm.registerEvents(new BlockEvents(), this);
		pm.registerEvents(new PickupDropEvents(), this);
		pm.registerEvents(new StatusEvents(), this);
		pm.registerEvents(new PlayerLoseEvents(), this);
		pm.registerEvents(new DistanceEvents(), this);
		pm.registerEvents(new OnCommandMidGame(), this);
		pm.registerEvents(new OnPlayerWin(), this);
		pm.registerEvents(new OnGameStarts(), this);
		pm.registerEvents(new OnGameEnds(), this);
		pm.registerEvents(new OnMobSpawn(), this);
		
		pm.registerEvents(new SpleefPlayerUtils(), this);
	}
	
	public void setupManagers() {
		vars = new GlobalVariables(); // Loading critical variables for the game
		
		GameManager.getInstance();
		StorageManager.getInstance();
	}
	
	public static Main getInstance() { return instance; }
	public static GlobalVariables getVars() { return vars; }

	public WorldEditPlugin getWE() {
		Plugin p = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if(p == null) return null;
		if(p instanceof WorldEditPlugin) return (WorldEditPlugin)p;
		return null;
	}
}