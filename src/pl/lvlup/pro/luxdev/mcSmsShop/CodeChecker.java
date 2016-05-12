package pl.lvlup.pro.luxdev.mcSmsShop;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import pl.lvlup.pro.luxdev.mcSmsShop.service.Service;


public class CodeChecker {
	
	public static int checkCode(String code, int numer, Service service){
		try{
			URL url = new URL("http://microsms.pl/api/check.php?userid=1066" + "&number=" + numer + "&code=" + code + "&serviceid=1415");
			URLConnection urlConnection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			StringBuilder string = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				string.append(inputLine);
			}
			in.close();
			return string.toString().substring(0, 1).equals("1") ? 1 : 0;
		}catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}
}
