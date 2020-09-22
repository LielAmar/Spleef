package net.lielamar.spleef.storage;

import java.util.List;

import net.lielamar.spleef.moduels.GameMap;

public interface Storage {

	public List<GameMap> getMaps();
	
	public boolean saveMap(GameMap map);
	
	public boolean removeMap(String mapName);
	
	public GameMap getMap(String mapName);
}
