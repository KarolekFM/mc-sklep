package pl.lvlup.pro.luxdev.mcSmsShop.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.lvlup.pro.luxdev.mcSmsShop.Main;
import pl.lvlup.pro.luxdev.mcSmsShop.service.Service;
import pl.lvlup.pro.luxdev.mcSmsShop.service.ServiceData;

public class InventoryListener implements Listener{
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
		if(e.getInventory().getName().startsWith("§eSklep")){
			ItemStack is = new ItemStack(e.getCurrentItem().clone());
			ItemMeta im = is.getItemMeta();
			String idUslugi = im.getLore().get(5).substring(0, 10);
			if(idUslugi == null){
				Bukkit.broadcastMessage("DEBUG - DISPLAYNAME JEST NULLEM " + idUslugi);
			}
			Service service = ServiceData.getService(idUslugi);
			if(service == null){
				Bukkit.broadcastMessage("DEBUG-1 - SERWIS JEST NULLEM " + service + " DISPLAYNAME: " + idUslugi);
			}
			Main.serwis.put(p.getPlayer(), service.getName());
			Main.openSign(p);
			Main.test(p);
		}
	}
}
