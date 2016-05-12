package pl.lvlup.pro.luxdev.mcSmsShop.service;

import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceData {
	
	private static volatile CopyOnWriteArrayList<Service> services = new CopyOnWriteArrayList<>();
	
	public static Service[] getAllServices() {
		return services.toArray(new Service[services.size()]);
	}
	
	public static void addService(Service service) {
		services.addIfAbsent(service);
	}
	
	public static void removeService(Service service) {
		services.remove(service);
	}
	public static Service getService(String name){
		for (Service service : services) {
			if (service.getName().equalsIgnoreCase(name)) {
				return service;
			}
		}
		return null;
	}
}
