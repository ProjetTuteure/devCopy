package utils;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class MaConnexion {
	static private MaConnexion maConnexion;
	
	public static synchronized MaConnexion getInstance(){
		if(maConnexion==null){
			maConnexion=new MaConnexion();
		}
		return maConnexion;
	}
	
	public Connection getConnexion() throws ConnexionBDException{
		Connection connexion = null;
		Properties p = Propriete.getInstance().getProperties();
		String url = "jdbc:sqlserver://"+p.getProperty("ipBaseDeDonnees")+":"+p.getProperty("portBaseDeDonnees")+";databaseName="+p.getProperty("nomBaseDeDonnees")+";";
		String mdp = p.getProperty("motDePasseBaseDeDonnees");
		String user= p.getProperty("userBaseDeDonnees");
		try {
			DriverManager.setLoginTimeout(2);
			connexion = DriverManager.getConnection(url,user,mdp);
		} catch (SQLException e) {
			throw new ConnexionBDException("Problème de connexion à la bd");
		}
		return connexion;
	}
	
	private MaConnexion(){
	
	}
}
