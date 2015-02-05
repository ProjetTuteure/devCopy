package gpi.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class BDSqlServer implements IBD{
	private String url;
	private String mdp;
	private String user;
	private String bd;
	private Connection connexion;
	
	
	/**
	 * Constructeur de la classe BDSqlServer
	 * @param adresse le nom ou l'adresse de la base
	 * @param bd le base de donnee dans la quelle on veux se connecter
	 * @param port le port du server
	 * @param user l'identifiant de connexion
	 * @param mdp le mot de passe de connexion
	 */
	public BDSqlServer(String adresse, int port,String bd, String user, String mdp){
		this.url = "jdbc:sqlserver://"+adresse+":"+port+";databaseName="+bd+";";
		this.mdp = mdp;
		this.user = user;
		this.bd = bd;
	}
	
	
	/**
	 * fonction qui permet de se connecter a la base de donnee
	 * @return vrai si la connexion s'est bien passee faux sinon
	 */
	public boolean connexion(){
		return connexion(this.url,user,mdp);
	}
	
	/**
	 * fonction qui permet de se connecter a une base de donnee a partir d'une url
	 * @param url 
	 * @return vrai si la connexion s'est bien passee faux sinon
	 */
	@Override
	public boolean connexion(String url,String user,String mdp) {
		try {
			this.connexion= DriverManager.getConnection(url,user,mdp);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Fonction qui effectue une requete sur la base de donnee
	 * @parma requete la requete à effectuer sur la base
	 * @return une list d'objet contenant le resultat de la requete
	 */
	@Override
	public List query(String type ,String requete) {
		List<Object> result = new ArrayList<Object>();
		ResultSet resultat;
		ResultSetMetaData resultMeta;
		try {
			Statement state = this.connexion.createStatement();
			resultat = state.executeQuery(requete);
			
			if(type.toUpperCase().equals("SELECT")){
				resultMeta = resultat.getMetaData();
				while(resultat.next()){         
		        	for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		        		result.add(resultat.getObject(i));
				}
			}else{
				result = null;
			}
		} catch (SQLException e) {
		} 
		return result;
	}
	
	
	/**
	 * fonction qui permet de se deconnecter de la base
	 * @return vrai si la deconnexion s'est bien passee faux sinon
	 */
	@Override
	public boolean deconnexion() {
		try {
			this.connexion.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

}
