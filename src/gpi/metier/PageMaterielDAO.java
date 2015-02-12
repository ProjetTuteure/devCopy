package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.MaConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PageMaterielDAO {
	private Connection connexion;
	
	public PageMaterielDAO(){}
	
	public ObservableList<PageMateriel> getAllMaterielByTypeAndSite(Type type,Site site) throws ConnexionBDException{
		connexion = MaConnexion.getInstance().getConnexion();
		ObservableList<PageMateriel> listePageMateriel=FXCollections.observableArrayList();
		String requete="SELECT idMateriel,nomMateriel,cheminImageType FROM MATERIEL M"
				+ " JOIN TYPE T ON M.idType=T.idType"
				+ " JOIN SITE S ON M.idSite=S.idSite "
				+ "WHERE M.idType=? AND M.idSite=?"
				+ " ORDER BY nomMateriel ASC";
		
		try {
			PreparedStatement ps=connexion.prepareStatement(requete);
			ps.setInt(1,type.getIdType());
			ps.setInt(2,site.getIdSite());
			ResultSet resultat = ps.executeQuery();
			while(resultat.next()){
				listePageMateriel.add(new PageMateriel(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel"),
						resultat.getString("cheminImageType")));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally {
			try {
				if (connexion !=null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listePageMateriel;
	}
}