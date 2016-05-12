package pl.lvlup.pro.luxdev.mcSmsShop.service;

import org.bukkit.Material;

public class Service {
	
	private int smsnumber;
	private String name;
	private String displayName;
	private String cost;
	private String days;
	private Material mat;
	private String smsText;
	private String komenda;
	private String idUslugi;
	
	public Service(String nazwa, String displayname,String text, int numersms, String koszt, String dni, Material mat, String komenda, String idUslugi) {
		this.displayName = displayname;
		this.smsnumber = numersms;
		this.name = nazwa;
		this.cost = koszt;
		this.days = dni;
		this.mat = mat;
		this.smsText = text;
		this.komenda = komenda;
		this.idUslugi = idUslugi;
	}
	
	public String getSmsText() {
		return smsText;
	}
	public String getServiceID(){
		return idUslugi;
	}
	public String getCommandToRun(){
		return komenda;
	}

	public String getDisplayName() {
		return displayName;
	}

	public Material getMat() {
		return mat;
	}

	public Service() {
	}

	public int getSmsnumber() {
		return smsnumber;
	}

	public String getName() {
		return name;
	}

	public String getCost() {
		return cost;
	}

	public String getDays() {
		return days;
	}
	
	
	

}
