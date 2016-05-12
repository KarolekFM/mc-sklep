package pl.lvlup.pro.luxdev.mcSmsShop;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pl.lvlup.pro.luxdev.mcSmsShop.commands.ShopCMD;
import pl.lvlup.pro.luxdev.mcSmsShop.listeners.InventoryListener;
import pl.lvlup.pro.luxdev.mcSmsShop.service.Service;
import pl.lvlup.pro.luxdev.mcSmsShop.service.ServiceData;
import pl.lvlup.pro.luxdev.mcSmsShop.yamler.Yamler;

public class Main extends JavaPlugin {
	
	private static SignGUI signgui;
	public static HashMap<Player, String> serwis = new HashMap<Player, String>();
	
	public static SignGUI getSignGui() {
		return signgui;
	}
	private static Main instance;

	public void onEnable() {
		instance = this;
		getCommand("sklep").setExecutor(new ShopCMD());
		createConfig();
		loadAllServices();
		registerListeners();
		signgui = new SignGUI(this);
	}
	public void onDisable() {

	}

	public static Main getInst() {
		return instance;
	}

	public void createConfig() {
		File f = new File("plugins/LC_SMS");
		if(!f.exists()){
			Yamler y = new Yamler(new File(getDataFolder(), "uslugi.yml"));
			y.getCfg().addDefault("uslugi.vip.nazwaWys", "&6Vip");
			y.getCfg().addDefault("uslugi.vip.cena", "7.14zl");
			y.getCfg().addDefault("uslugi.vip.waznosc", 7);
			y.getCfg().addDefault("uslugi.vip.tresc", "TEST.VIP");
			y.getCfg().addDefault("uslugi.vip.sms", 1234);
			y.getCfg().addDefault("uslugi.vip.material", "DIAMOND");
			y.getCfg().addDefault("uslugi.vip.komenda", "ban {PLAYER} Tez cie kocham :)");
			y.getCfg().addDefault("uslugi.vip.idUslugi", "vip");
			y.getCfg().options().copyDefaults(true);
			y.save();
		}
	}
	
	public void loadAllServices() {
		Yamler y = new Yamler(new File(getDataFolder(), "uslugi.yml"));
		for(String s : y.getCfg().getConfigurationSection("uslugi").getKeys(false)) {
			Service service = new Service(s, y.getCfg().getString("uslugi." + s + ".nazwaWys"),
							y.getCfg().getString("uslugi." + s + ".tresc"),
							y.getCfg().getInt("uslugi." + s + ".sms"),
							y.getCfg().getString("uslugi." + s + ".cena"),
							y.getCfg().getString("uslugi." + s + ".waznosc"),
							Material.getMaterial(y.getCfg().getString("uslugi." + s + ".material")),
							y.getCfg().getString("uslugi." + s + ".komenda"),
							y.getCfg().getString("uslugi." + s + ".idUslugi"));
			ServiceData.addService(service);
		}
		
	}
	
	public void registerListeners() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InventoryListener(), this);
	}
	
	public static void openSign(Player p) {    
        Main.getSignGui().open(p, new String[] { "", "§4^ WPISZ KOD ^", "", "" }, new SignGUI.SignGUIListener() {
            @Override
            public void onSignDone(Player p, String[] lines) {
	           	String kod = lines[0];
	           	String magia = serwis.get(p.getName());
	           	Service service = ServiceData.getService(magia);
	           	Bukkit.broadcastMessage("DEBUG-01 MAGIA: " + magia + " SERVICE: " + service.getName());
	           	int numer = service.getSmsnumber();
	           	int code = CodeChecker.checkCode(kod, numer, service);
	           	if(lines[0].isEmpty()){
            		p.sendMessage("§8§m-------------------§7[§6ITEMSHOP§7]§8§m-------------------");
	           		p.sendMessage("§8» §cPole na kod jest puste!");
	            	p.sendMessage("§8§m-------------------§7[§6ITEMSHOP§7]§8§m-------------------");
	            }else{
	            	if(code == 1){
	            		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), service.getCommandToRun().replace("{PLAYER}", p.getName()));
		           		Bukkit.broadcastMessage("§8§m-------------------§7[§6ITEMSHOP§7]§8§m-------------------");
		           		Bukkit.broadcastMessage("§8» §7Gracz§6 " + p.getName() + "§6 Zakupil usluge " + service.getName());
		           		Bukkit.broadcastMessage("§8§m-------------------§7[§6ITEMSHOP§7]§8§m-------------------");
		           		serwis.remove(p.getName());
	            	}else{
		           		p.sendMessage("§8§m-------------------§7[§6ITEMSHOP§7]§8§m-------------------");
		           		p.sendMessage("§8» §cPodany kod jest nieprawidlowy badz nie wyslales sms");
		           		p.sendMessage("§8§m-------------------§7[§6ITEMSHOP§7]§8§m-------------------");
	            	}
	            }
            }
        });
	}
	public static void test(Player p){
		String magia = serwis.get(p.getPlayer());
       	Service service = ServiceData.getService(magia);
       	Bukkit.broadcastMessage("DEBUG-01 MAGIA: " + magia + " SERVICE: " + service.getName());
	}
}

