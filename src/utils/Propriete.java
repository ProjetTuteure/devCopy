package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class Propriete {
	private static Propriete p;
	private static Properties pro;
	private static File fichier;
	
	public static synchronized Propriete getInstance(){
		if (p == null) p = new Propriete();
		return p;
	}
	
	public Properties getProperties(){
		return pro;
	}
	
	public void setPropertiesDriver(String driver,String chemin){
		this.pro.setProperty(driver,chemin);
		FileOutputStream oStream=null;
		try {
			oStream = new FileOutputStream(this.fichier);
			try {
				this.pro.store(oStream,"Modification chemin repertoire driver") ;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		
	}
	
	private Propriete() {
		this.pro= new Properties();
		this.fichier=new File(Constante.CHEMIN_CONF_PROPERTIES);
		try {
			pro.load(new FileInputStream(fichier));
		} catch (IOException e) {
			Popup.getInstance().afficherPopup("Fichier Configuration.properties manquant.");
		}
	}

}
