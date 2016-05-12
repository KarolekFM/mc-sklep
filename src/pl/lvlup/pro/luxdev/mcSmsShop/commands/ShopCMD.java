package pl.lvlup.pro.luxdev.mcSmsShop.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pl.lvlup.pro.luxdev.mcSmsShop.Main;
import pl.lvlup.pro.luxdev.mcSmsShop.service.Service;
import pl.lvlup.pro.luxdev.mcSmsShop.service.ServiceData;
import pl.lvlup.pro.luxdev.mcSmsShop.utils.StringUtils;
import pl.lvlup.pro.luxdev.mcSmsShop.yamler.Yamler;

public class ShopCMD implements CommandExecutor {
	
	private static List<ItemStack> itemStacks = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("sklep")) {
			if(!(sender instanceof Player)){
				sender.sendMessage("konsola nie moze wykonywac komend");
				return true;
			}
			Player p = (Player) sender;
			openMenu(p);
			p.sendMessage("§8§m-------------------§7[§6ITEMSHOP§7]§8§m-------------------");
			p.sendMessage("§8» §c§lWAZNE! ");
			p.sendMessage("§8» §6Reklamacja Uslug: http://microsms.pl/customer/complain/ ");
			p.sendMessage("§8» §6Regulamin Uslug: http://microsms.pl/parner/documents/ ");
			p.sendMessage("§8§m-------------------§7[§6ITEMSHOP§7]§8§m-------------------");
		}
		return false;
	}
	public static void openMenu(Player p) {
		p.openInventory(fillInv(Bukkit.getServer().createInventory(p, 27, "§eSklep"), p));
	}
	public static Inventory fillInv(Inventory inv, Player p) {
		Yamler y = new Yamler(new File(Main.getInst().getDataFolder(), "uslugi.yml"));
		
		for(int i = 0; i < ServiceData.getAllServices().length; i++) {
			Service service = ServiceData.getAllServices()[i];
			Material mat = Material.getMaterial(y.getCfg().getString("uslugi." + service.getName() + ".material").toUpperCase());
			ItemStack item = new ItemStack(mat);
			ItemMeta itemMeta = item.getItemMeta();
			itemMeta.setDisplayName(StringUtils.fixColor(service.getDisplayName()));
			itemMeta.setLore(Arrays.asList(StringUtils.fixColor("&8» &7Nazwa: &6" + service.getName()), StringUtils.fixColor("&8» &7Koszt: &6" + service.getCost()), StringUtils.fixColor("&8» &7Dlugosc uslugi:&6 " + service.getDays()), StringUtils.fixColor("&8» &7Text SMS:&6 " + service.getSmsText()), StringUtils.fixColor("&8» &7Numer:&6 " + service.getSmsnumber()), "idUslugi: " + service.getServiceID()));
			item.setItemMeta(itemMeta);
			itemStacks.add(item);
			inv.setItem(i, item);
		}

		return inv;
	}
	
}