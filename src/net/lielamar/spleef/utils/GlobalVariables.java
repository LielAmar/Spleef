package net.lielamar.spleef.utils;

import net.lielamar.spleef.Main;

public class GlobalVariables {

	public static int MAX_PLAYERS_IN_GAME = 24;
	public static int MIN_PLAYERS_IN_GAME = 8;
	public static int COUNTDOWN_TIME = 30;
	public static int GAME_TIME = 300;
	public static int WIN_TIME = 10;
	public static int MAX_DISTANCE_FROM_MAP = 50;

	public GlobalVariables() {
		if(Main.getInstance().getConfig().contains("MaxPlayersInGame"))
			MAX_PLAYERS_IN_GAME = Main.getInstance().getConfig().getInt("MaxPlayersInGame");
		
		if(Main.getInstance().getConfig().contains("MinimumPlayersInGame"))
			MIN_PLAYERS_IN_GAME = Main.getInstance().getConfig().getInt("MinimumPlayersInGame");
		
		if(Main.getInstance().getConfig().contains("CountdownTime"))
			COUNTDOWN_TIME = Main.getInstance().getConfig().getInt("CountdownTime");
		
		if(Main.getInstance().getConfig().contains("GameTime"))
			GAME_TIME = Main.getInstance().getConfig().getInt("GameTime");
		
		if(Main.getInstance().getConfig().contains("WinTime"))
			WIN_TIME = Main.getInstance().getConfig().getInt("WinTime");
		
		if(Main.getInstance().getConfig().contains("MaxDistanceFromMap"))
			MAX_DISTANCE_FROM_MAP = Main.getInstance().getConfig().getInt("MaxDistanceFromMap");
	}
}
