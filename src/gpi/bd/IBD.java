package gpi.bd;

import java.util.List;

public interface IBD {
	
	/**
	 * fonction qui permet de se connecter a une base de donnee a partir d'une url
	 * @param url 
	 * @return vrai si la connexion s'est bien passee faux sinon
	 */
	public boolean connexion(String url,String user,String mdp);
	
	/**
	 * Fonction qui effectue une requete sur la base de donnee
	 * @param requete la requete à effectuer sur la base
	 * @param type le type de la requete : SELECT, INSERT, UPDATE
	 * @return une list d'objet contenant le resultat de la requete
	 */
	public List query(String type, String requete);
	
	/**
	 * fonction qui permet de se deconnecter de la base
	 * @return vrai si la deconnexion s'est bien passee faux sinon
	 */
	public boolean deconnexion();
}
