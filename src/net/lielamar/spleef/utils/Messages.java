package net.lielamar.spleef.utils;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.lielamar.spleef.Main;

public class Messages {

	public static String noPermissions = ChatColor.RED + "You don't have enough permissions!";
	public static String mustBeAPlayer = ChatColor.RED + "You must be a player to do that!";
	
	public static String couldntFindPlayer(String name) {
		return ChatColor.RED + "Couldn't find player " + name;
	}
	
	private static Messages instance = new Messages();
	public static Messages getInstance() { return instance; }
	
	private static File file;
	private static YamlConfiguration config;
	
	private Messages() {
		file = new File(Main.getInstance().getDataFolder(), "messages.yml");
		if(!file.exists()) {
			Main.getInstance().saveResource("messages.yml", true);
			file = new File(Main.getInstance().getDataFolder(), "messages.yml");
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public String alreadyInGame() {
		return getFixed("alreadyInGame", null, null, null, -1, -1, -1);
	}
	
	public String youAreSpectating() {
		return getFixed("youAreSpectating", null, null, null, -1, -1, -1);
	}
	
	public String noAvailableGames() {
		return getFixed("noAvailableGames", null, null, null, -1, -1, -1);
	}
	
	public String joiningGame() {
		return getFixed("joiningGame", null, null, null, -1, -1, -1);
	}
	
	public String couldntAddToGame() {
		return getFixed("couldntAddToGame", null, null, null, -1, -1, -1);
	}
	
	public String youArentInGame() {
		return getFixed("youArentInGame", null, null, null, -1, -1, -1);
	}
	
	public String leavingGame() {
		return getFixed("leavingGame", null, null, null, -1, -1, -1);
	}
	
	public String couldntRemoveFromGame() {
		return getFixed("couldntRemoveFromGame", null, null, null, -1, -1, -1);
	}
	
	public String gameAlreadyStarted() {
		return getFixed("gameAlreadyStarted", null, null, null, -1, -1, -1);
	}
	
	public String forceStartingGame() {
		return getFixed("forceStartingGame", null, null, null, -1, -1, -1);
	}
	
	public String playerNotInGame(Player p) {
		return getFixed("playerNotInGame", null, p, null, -1, -1, -1);
	}
	
	public String spectatingPlayer(Player p) {
		return getFixed("spectatingPlayer", null, p, null, -1, -1, -1);
	}
	
	public String alreadyMapNamed(String mapName) {
		return getFixed("alreadyMapNamed", null, null, mapName, -1, -1, -1);
	}
	
	public String savedMapNamed(String mapName) {
		return getFixed("savedMapNamed", null, null, mapName, -1, -1, -1);
	}
	
	public String mapAddedCouldntSave(String mapName) {
		return getFixed("mapAddedCouldntSave", null, null, mapName, -1, -1, -1);
	}
	
	public String couldntFindMapNamed(String mapName) {
		return getFixed("couldntFindMapNamed", null, null, mapName, -1, -1, -1);
	}
	
	public String removedMapNamed(String mapName) {
		return getFixed("removedMapNamed", null, null, mapName, -1, -1, -1);
	}
	 
	public String mapRemovedCouldntSave(String mapName) {
		return getFixed("mapRemovedCouldntSave", null, null, mapName, -1, -1, -1);
	}
	
	public String setMapSpawn(String mapName) {
		return getFixed("setMapSpawn", null, null, mapName, -1, -1, -1);
	}
	
	public String setMapLocation(String mapName) {
		return getFixed("setMapLocation", null, null, mapName, -1, -1, -1);
	}
	
	public String setMapMinY(String mapName) {
		return getFixed("setMapMinY", null, null, mapName, -1, -1, -1);
	}
	
	public String setMapSpawnCouldntSave(String mapName) {
		return getFixed("setMapSpawnCouldntSave", null, null, mapName, -1, -1, -1);
	}
	
	public String setMapLocationCouldntSave(String mapName) {
		return getFixed("setMapLocationCouldntSave", null, null, mapName, -1, -1, -1);
	}

	public String setMapMinYCouldntSave(String mapName) {
		return getFixed("setMapMinYCouldntSave", null, null, mapName, -1, -1, -1);
	}
	
	public String playerJoinedTheGame(Player p, int amount) {
		return getFixed("playerJoinedTheGame", p, null, null, amount, GlobalVariables.MAX_PLAYERS_IN_GAME, -1);
	}
	
	public String playerLeftTheGame(Player p, int amount) {
		return getFixed("playerLeftTheGame", p, null, null, amount, GlobalVariables.MAX_PLAYERS_IN_GAME, -1);
	}
	
	public String playerLostTheGame(Player p, int left) {
		return getFixed("playerLostTheGame", p, null, null, left, -1, -1);
	}
	
	public String gameStartsIn(int seconds) {
		return getFixed("gameStartsIn", null, null, null, -1, -1, seconds);
	}
	
	public String gameStartsInForBroadcast(int seconds) {
		return getFixed("gameStartsInBroadcast", null, null, null, -1, -1, seconds);
	}
	
	public String startingGame() {
		return getFixed("startingGame", null, null, null, -1, -1, -1);
	}
	
	public String gameEndsIn(int seconds) {
		return getFixed("gameEndsIn", null, null, null, -1, -1, seconds);
	}
	
	public String endingGame() {
		return getFixed("endingGame", null, null, null, -1, -1, -1);
	}
	
	public  String wonTheGame(Player p) {
		return getFixed("wonTheGame", p, null, null, -1, -1, -1);
	}
	
	public  String getFixed(String value, Player p, Player t, String mapName, int playerAmount, int max, int seconds) {
		String message = config.getString(value);
		if(message == null)
			message = value + " is not set in messages.yml!";
		message = ChatColor.translateAlternateColorCodes('&', message);
		if(p != null) message = message.replaceAll("%player%", p.getName());
		if(t != null) message = message.replaceAll("%target%", t.getName());
		if(mapName != null) message = message.replaceAll("%mapname%", mapName);
		if(playerAmount != -1) message = message.replaceAll("%amount%", playerAmount + "").replaceAll("%left%", playerAmount + "");
		if(max != -1) message = message.replaceAll("%max%", max + "");
		if(seconds != -1) message = message.replaceAll("%seconds%", seconds + "");
		return message;
	}
}

