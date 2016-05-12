package pl.lvlup.pro.luxdev.mcSmsShop.yamler;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class Yamler {
	
	public static HashMap<String, FileConfiguration> data = new HashMap<String, FileConfiguration>();
	private File f;

	public Yamler(File f) {
		this.f = f;
		if (!data.containsKey(f.getAbsolutePath())) {
			data.put(f.getAbsolutePath(), YamlConfiguration.loadConfiguration(f));
		}
	}

	public FileConfiguration getCfg() {
		return (FileConfiguration) data.get(this.f.getAbsolutePath());
	}

	public void save() {
		try {
			((FileConfiguration) data.get(this.f.getAbsolutePath())).save(this.f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Set<String> getSection(String section) {
		return getCfg().getKeys(false);
	}

	public void reload() {
		data.remove(this.f.getAbsolutePath());
		data.put(this.f.getAbsolutePath(), YamlConfiguration.loadConfiguration(this.f));
	}
}
