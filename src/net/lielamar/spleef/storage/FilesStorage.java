package net.lielamar.spleef.storage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import net.lielamar.spleef.Main;
import net.lielamar.spleef.moduels.GameMap;

public class FilesStorage implements Storage {

	private File file;
	private YamlConfiguration config;

	public FilesStorage() {
		this.file = new File(Main.getInstance().getDataFolder(), "maps.yml");
		
		if(!this.file.exists()) {
			try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
		}
		
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	@Override
	public List<GameMap> getMaps() {
		List<GameMap> list = new LinkedList<GameMap>();
		
		int failed = 0;
		
		for(String s : this.config.getConfigurationSection("").getKeys(false)) {
			try {
				Location schem = new Location(
						Bukkit.getWorld(this.config.getString(s + ".world")),
						this.config.getDouble(s + ".x"),
						this.config.getDouble(s + ".y"),
						this.config.getDouble(s + ".z"));
				
				Location spawn = new Location(
						Bukkit.getWorld(this.config.getString(s + ".spawn.world")),
						this.config.getDouble(s + ".spawn.x"),
						this.config.getDouble(s + ".spawn.y"),
						this.config.getDouble(s + ".spawn.z"));
				
				double minY = this.config.getDouble(s + ".minY");
				
				list.add(new GameMap(s, schem, spawn, minY));
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded map " + s);
			} catch(Exception e) {
				failed++;
			}
		}
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to load " + failed + " maps!");
		return list;
	}
	
	public boolean saveMap(GameMap map) {
		this.config.set(map.getName().toLowerCase() + ".world", map.getSchem().getWorld().getName());
		this.config.set(map.getName().toLowerCase() + ".x", map.getSchem().getX());
		this.config.set(map.getName().toLowerCase() + ".y", map.getSchem().getY());
		this.config.set(map.getName().toLowerCase() + ".z", map.getSchem().getZ());
		this.config.set(map.getName().toLowerCase() + ".spawn.world", map.getSpawn().getWorld().getName());
		this.config.set(map.getName().toLowerCase() + ".spawn.x", map.getSpawn().getX());
		this.config.set(map.getName().toLowerCase() + ".spawn.y", map.getSpawn().getY());
		this.config.set(map.getName().toLowerCase() + ".spawn.z", map.getSpawn().getZ());
		this.config.set(map.getName().toLowerCase() + ".minY", map.getminY());
		
		try {
			this.config.save(this.file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public boolean removeMap(String mapName) {
		this.config.set(mapName.toLowerCase(), null);
		try {
			this.config.save(this.file);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public GameMap getMap(String mapName) {
		mapName = mapName.toLowerCase();
		for(String s : this.config.getConfigurationSection("").getKeys(false)) {
			if(!s.equalsIgnoreCase(mapName)) continue;
	
			Location schem = new Location(
					Bukkit.getWorld(this.config.getString(s + ".world")),
					this.config.getDouble(s + ".x"),
					this.config.getDouble(s + ".y"),
					this.config.getDouble(s + ".z"));
				
			Location spawn = new Location(
					Bukkit.getWorld(this.config.getString(s + ".spawn.world")),
					this.config.getDouble(s + ".spawn.x"),
					this.config.getDouble(s + ".spawn.y"),
					this.config.getDouble(s + ".spawn.z"));
				
			double minY = this.config.getDouble(s + ".minY");
				
			return new GameMap(s, schem, spawn, minY);
		}
		return null;
	}
}
