package net.lielamar.spleef.managers;

import net.lielamar.spleef.storage.FilesStorage;
import net.lielamar.spleef.storage.Storage;
import net.lielamar.spleef.storage.StorageType;

public class StorageManager {

	private static StorageManager instance = new StorageManager();
	public static StorageManager getInstance() { return instance; }
	
	private StorageType type;
	private Storage storage;
	
	private StorageManager() {
		this.type = StorageType.FILES;
		
		setup();
	}

	public void setup() {
		if(this.type == StorageType.FILES) this.storage = new FilesStorage();
	}
	
	public Storage getStorage() { return this.storage; }
}
