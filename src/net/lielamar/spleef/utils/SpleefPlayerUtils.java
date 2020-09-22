package net.lielamar.spleef.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;

import net.lielamar.spleef.Main;
import net.lielamar.spleef.managers.GameManager;
import net.lielamar.spleef.moduels.Game;

public class SpleefPlayerUtils implements Listener {

	public static List<Player> players = new ArrayList<Player>();

	public static void clearInventory(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.setExp(0);
		p.setLevel(0);
		p.setTotalExperience(0);
		p.updateInventory();
	}

	public static void teleport(Player p, Location loc) {
		p.teleport(loc);
	}

	public static void fixPlayer(Player p) {
		p.setGameMode(GameMode.SURVIVAL);
		p.setAllowFlight(false);
		p.setFlying(false);
		p.setFoodLevel(20);
		p.setHealth(20);

		for (PotionEffect effect : p.getActivePotionEffects())
			p.removePotionEffect(effect.getType());

		p.setWalkSpeed(0.2F);
	}

	public static void fixSpectator(Player p) {
		fixPlayer(p);
		p.setAllowFlight(true);
		p.setFlying(true);
	}

	public static void hidePlayersThatArentInGame(Player p, Game game) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(Main.getInstance(), pl);

			for (Player gamePL : game.getPlayers()) {
				if (gamePL == null)
					continue;
				if (gamePL == pl) {
					p.showPlayer(Main.getInstance(), pl);
					pl.showPlayer(Main.getInstance(), p);
				}
			}
		}
	}

	public static void showAllPlayers(Player p) {
		for (Player pl : Bukkit.getOnlinePlayers()) {
			p.showPlayer(Main.getInstance(), pl);
		}
	}

	public static void respawnPlayer(Player p) {
		clearInventory(p);
		players.remove(p);
		
		if (GameManager.getInstance().getPlayersInventory().containsKey(p.getUniqueId())) {
			p.getInventory().setContents(GameManager.getInstance().getPlayersInventory().get(p.getUniqueId()));
			GameManager.getInstance().getPlayersInventory().remove(p.getUniqueId());
		}

		if (GameManager.getInstance().getPlayersArmor().containsKey(p.getUniqueId())) {
			p.getInventory().setArmorContents(GameManager.getInstance().getPlayersArmor().get(p.getUniqueId()));
			GameManager.getInstance().getPlayersArmor().remove(p.getUniqueId());
		}

		if (GameManager.getInstance().getPlayersEXP().containsKey(p.getUniqueId())) {
			SpleefPlayerUtils.setTotalExperience(p, GameManager.getInstance().getPlayersEXP().get(p.getUniqueId()));
			GameManager.getInstance().getPlayersEXP().remove(p.getUniqueId());
		}
		
		if (GameManager.getInstance().getPlayersLocation().containsKey(p.getUniqueId())) {
			p.teleport(GameManager.getInstance().getPlayersLocation().get(p.getUniqueId()));
		}
		
		p.setAllowFlight(false);
		p.setFlying(false);
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if (players.contains(e.getEntity()))
			e.setDeathMessage(null);
	}

	public static int getTotalExperience(Player p) {
		int experience = 0;
		int level = p.getLevel();
		if (level >= 0 && level <= 15) {
			experience = (int) Math.ceil(Math.pow(level, 2) + (6 * level));
			int requiredExperience = 2 * level + 7;
			double currentExp = Double.parseDouble(Float.toString(p.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;
		} else if (level > 15 && level <= 30) {
			experience = (int) Math.ceil((2.5 * Math.pow(level, 2) - (40.5 * level) + 360));
			int requiredExperience = 5 * level - 38;
			double currentExp = Double.parseDouble(Float.toString(p.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;
		} else {
			experience = (int) Math.ceil(((4.5 * Math.pow(level, 2) - (162.5 * level) + 2220)));
			int requiredExperience = 9 * level - 158;
			double currentExp = Double.parseDouble(Float.toString(p.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;
		}
	}

	public static void setTotalExperience(Player p, int xp) {
		if (xp >= 0 && xp < 351) {
			int a = 1;
			int b = 6;
			int c = -xp;
			int level = (int) (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
			int xpForLevel = (int) (Math.pow(level, 2) + (6 * level));
			int remainder = xp - xpForLevel;
			int experienceNeeded = (2 * level) + 7;
			float experience = (float) remainder / (float) experienceNeeded;
			experience = round(experience, 2);

			p.setLevel(level);
			p.setExp(experience);
		} else if (xp >= 352 && xp < 1507) {
			double a = 2.5;
			double b = -40.5;
			int c = -xp + 360;
			double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
			int level = (int) Math.floor(dLevel);
			int xpForLevel = (int) (2.5 * Math.pow(level, 2) - (40.5 * level) + 360);
			int remainder = xp - xpForLevel;
			int experienceNeeded = (5 * level) - 38;
			float experience = (float) remainder / (float) experienceNeeded;
			experience = round(experience, 2);

			p.setLevel(level);
			p.setExp(experience);
		} else {
			double a = 4.5;
			double b = -162.5;
			int c = -xp + 2220;
			double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
			int level = (int) Math.floor(dLevel);
			int xpForLevel = (int) (4.5 * Math.pow(level, 2) - (162.5 * level) + 2220);
			int remainder = xp - xpForLevel;
			int experienceNeeded = (9 * level) - 158;
			float experience = (float) remainder / (float) experienceNeeded;
			experience = round(experience, 2);

			p.setLevel(level);
			p.setExp(experience);
		}
	}

	private static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_DOWN);
		return bd.floatValue();
	}
}
