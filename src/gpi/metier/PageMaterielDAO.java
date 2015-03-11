package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listePageMateriel;
	}
	
	public ObservableList<PageMateriel> getAllMateriel() throws ConnexionBDException{
		connexion = MaConnexion.getInstance().getConnexion();
		ObservableList<PageMateriel> listePageMateriel=FXCollections.observableArrayList();
		String requete="SELECT idMateriel,nomMateriel FROM MATERIEL M"
				+ " ORDER BY nomMateriel ASC";
		
		try {
			PreparedStatement ps=connexion.prepareStatement(requete);
			ResultSet resultat = ps.executeQuery();
			while(resultat.next()){
				listePageMateriel.add(new PageMateriel(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel")));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listePageMateriel;
	}
	
	public ObservableList<PageMateriel> getMaterielByIdUtilisateur(int idUtilisateur) throws ConnexionBDException{
		connexion = MaConnexion.getInstance().getConnexion();
		ObservableList<PageMateriel> listePageMateriel=FXCollections.observableArrayList();
	String requete="SELECT M.idMateriel,M.nomMateriel FROM MATERIEL M JOIN UTILISE U ON M.idMateriel = U.idMateriel WHERE "
			+ "U.idUtilisateur = ?" 
				+ " ORDER BY nomMateriel ASC";
		
		try {
			PreparedStatement ps=connexion.prepareStatement(requete);
			ps.setInt(1,idUtilisateur);
			ResultSet resultat = ps.executeQuery();
			while(resultat.next()){
				listePageMateriel.add(new PageMateriel(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel")));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listePageMateriel;
	}
	
	public ObservableList<PageMateriel> getMaterielMaintenu() throws ConnexionBDException{
		connexion = MaConnexion.getInstance().getConnexion();
		ObservableList<PageMateriel> listePageMateriel=FXCollections.observableArrayList();
		String requete="SELECT DISTINCT M.idMateriel,M.nomMateriel FROM MATERIEL M"
				+ " JOIN ESTMAINTENU E ON E.idMateriel=M.idMateriel"
				+ " ORDER BY M.nomMateriel ASC";
		
		try {
			PreparedStatement ps=connexion.prepareStatement(requete);
			ResultSet resultat = ps.executeQuery();
			while(resultat.next()){
				listePageMateriel.add(new PageMateriel(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel")));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listePageMateriel;
	}
	
	/**
	 * Récupère les matériels contenus dans la table estInstalle
	 * @return
	 * @throws ConnexionBDException
	 */
	public ObservableList<PageMateriel> getMaterielParInstallation() throws ConnexionBDException{
		connexion = MaConnexion.getInstance().getConnexion();
		ObservableList<PageMateriel> listePageMateriel=FXCollections.observableArrayList();
		String requete="SELECT DISTINCT M.idMateriel,M.nomMateriel FROM MATERIEL M"
				+ " JOIN ESTINSTALLE E ON E.idMateriel=M.idMateriel"
				+ " ORDER BY M.nomMateriel ASC";
		
		try {
			PreparedStatement ps=connexion.prepareStatement(requete);
			ResultSet resultat = ps.executeQuery();
			while(resultat.next()){
				listePageMateriel.add(new PageMateriel(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel")));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listePageMateriel;
	}
}
