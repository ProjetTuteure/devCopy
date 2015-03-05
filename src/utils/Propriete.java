package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Propriete {
	private static Propriete p;
	private static Properties pro;
	
	public static synchronized Propriete getInstance(){
		if (p == null) p = new Propriete();
		return p;
	}
	
	public Properties getProperties(){
		return pro;
	}
	
	private Propriete() {
	this.pro= new Properties();
		try {
			pro.load(new FileInputStream("C:\\Program Files\\Gpi\\Configuration.properties"));
		} catch (IOException e) {
			Popup.getInstance().afficherPopup("Fichier Configuration.properties manquant.");
		}
	}

}
