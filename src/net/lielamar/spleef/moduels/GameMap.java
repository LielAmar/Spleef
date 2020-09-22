package net.lielamar.spleef.moduels;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

import net.lielamar.spleef.Main;

public class GameMap {

	private static File schematicFile = null;
	
	private String name;
	private Location schem;
	private Location spawn;
	
	private double minY;
	
	public GameMap(String name, Location schem, Location spawn, double minY) {
		if(schematicFile == null) schematicFile = checkFile();
		
		this.name = name;
		this.schem = schem;
		this.setSpawn(spawn);
		
		this.minY = minY;;
		
		loadMap();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getminY() {
		return minY;
	}

	public void setminY(double minY) {
		this.minY = minY;
	}
	
	public Location getSchem() {
		return schem;
	}

	public void setSchem(Location schem) {
		this.schem = schem;
	}
	
	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	/**
	 * Pastes a schematic
	 */
	@SuppressWarnings("deprecation")
	public void loadMap() {
		if(schematicFile == null) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Couldn't find the schematic file in the map folder. Please set it up first!");
			Bukkit.getPluginManager().disablePlugin(Main.getInstance());
			return;
		}
		
		ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
		try {
			ClipboardReader reader = format.getReader(new FileInputStream(schematicFile));
			Clipboard clipboard = reader.read();
			
			com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(schem.getWorld());
			EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld, -1);

			// Saves our operation and builds the paste - ready to be completed.
			Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(schem.getX(), schem.getY(), schem.getZ())).ignoreAirBlocks(true).build();

			try { // This simply completes our paste and then cleans up.
				Operations.complete(operation);
			    editSession.flushSession();
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded schematic for map!");
			} catch (WorldEditException e) { // If worldedit generated an exception it will go here
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Couldn't load the schematic file in the map folder. Please set it up again!");
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
			}
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Couldn't load the schematic file in the map folder. Please set it up again!");
			Bukkit.getPluginManager().disablePlugin(Main.getInstance());
		}
	}
	
	/**
	 * Checks if the file is valid
	 * 
	 * @return      The schematic file
	 */
	private static File checkFile() {
		File schematic = new File(Main.getInstance().getDataFolder(), Main.getInstance().getConfig().getString("Schematic"));
		if(!schematic.exists()) return null;
		
		return schematic;
	}
}
