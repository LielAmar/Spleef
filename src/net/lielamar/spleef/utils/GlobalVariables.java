package net.lielamar.spleef.utils;

import net.lielamar.spleef.Main;

public class GlobalVariables {

	public static final int MAX_PLAYERS_IN_GAME = Main.getInstance().getConfig().getInt("MaxPlayersInGame"); // Maximum players in a single game (map)
	public static final int MIN_PLAYERS_IN_GAME = Main.getInstance().getConfig().getInt("MinimumPlayersInGame"); // Minimum players in order for a game to start counting down
	public static final int COUNTDOWN_TIME = Main.getInstance().getConfig().getInt("CountdownTime"); // Countdown time in seconds
	public static final int GAME_TIME = Main.getInstance().getConfig().getInt("GameTime"); // Game time in seconds
	public static final int WIN_TIME = Main.getInstance().getConfig().getInt("WinTime"); // Win time in seconds
	public static final int MAX_DISTANCE_FROM_MAP = Main.getInstance().getConfig().getInt("MaxDistanceFromMap"); // Max distance from the maps' spawn
}
