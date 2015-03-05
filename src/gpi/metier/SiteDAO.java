package gpi.metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gpi.exception.ConnexionBDException;
import utils.MaConnexion;

public class SiteDAO {

	public SiteDAO(){}
	
	public int ajouterSite(Site site) throws ConnexionBDException {
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("INSERT INTO SITE(nomSite,cheminImageSite) VALUES (?,?);");
			
			prep.setString(1, site.getNomSiteString());
			prep.setString(2, site.getCheminImageSiteProperty().getValue());
			
			resultat=prep.executeUpdate();
			return resultat;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public int modifierSite(Site site) throws ConnexionBDException {
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("UPDATE SITE SET nomSite=?, cheminImageSite=? WHERE idSite=?;");
			
			prep.setString(1, site.getNomSiteString());
			prep.setString(2, site.getCheminImageSite());
			prep.setInt(3, site.getIdSite());
			
			resultat=prep.executeUpdate();
			return resultat;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public int supprimerSite(Site site) throws ConnexionBDException {
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("DELETE FROM SITE WHERE idSite=?;");
			
			prep.setInt(1, site.getIdSite());
			
			resultat=prep.executeUpdate();
			return resultat;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	public Site recupererSiteParId(int idSite) throws ConnexionBDException {
		Connection connexion=null;
		ResultSet resultat;
		String nomSite,cheminImageSite;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("SELECT * FROM SITE WHERE idSite=?;");
			
			prep.setInt(1, idSite);
			
			resultat=prep.executeQuery();
			resultat.next();
			nomSite=resultat.getString("nomSite");
			cheminImageSite=resultat.getString("cheminImageSite");

			return new Site(idSite,nomSite,cheminImageSite);
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public List<Site> recupererAllSite() throws ConnexionBDException {
		Connection connexion=null;
		List<Site> listSite=new ArrayList<Site>();
		ResultSet resultat;
		String nomSite,cheminImageSite;
		int idSite;
		Site site;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("SELECT * FROM SITE;");
			
			
			resultat=prep.executeQuery();
			while(resultat.next()){
				
				idSite=resultat.getInt("idSite");
				nomSite=resultat.getString("nomSite");
				cheminImageSite=resultat.getString("cheminImageSite");
				site=new Site(idSite,nomSite,cheminImageSite);
				listSite.add(site);
			}
			return listSite;
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}
