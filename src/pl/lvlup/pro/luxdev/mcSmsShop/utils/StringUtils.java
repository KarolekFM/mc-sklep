package pl.lvlup.pro.luxdev.mcSmsShop.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class StringUtils {
	
	public static String fixColor(String string) {
		if (string == null) {
			return "";
		}
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static List<String> fixColor(List<String> s) {
		List<String> colored = new ArrayList<String>();
		for (String str : s) {
			colored.add(fixColor(str));
		}
		return colored;
	}


}
