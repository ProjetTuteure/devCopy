package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import utils.MaConnexion;

public class IAncienneteDAO {

	public IAncienneteDAO(){}
	
	public List<IAnciennete> recupererAnciennete() throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<IAnciennete> listAnciennete = new ArrayList<IAnciennete>();

		try {
			PreparedStatement prep = connexion.prepareStatement(
					"Select m.idMateriel, m.nomMateriel, m.numeroSerieMateriel, f.dateFacture, m.etat, m.dateExpirationGarantieMateriel, r.nomRevendeur, fab.nomFabricant,s.nomSite"+
							" FROM Materiel m"+ 
							" JOIN site s on m.idSite=s.idSite"+
							" JOIN fabricant fab on m.idFabricant=fab.idfabricant"+
							" JOIN Facture f on m.idFacture=f.idFacture"+
							" JOIN REVENDEUR r on f.idRevendeur=r.idRevendeur");	
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listAnciennete.add(new IAnciennete(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel"),resultat.getString("numeroSerieMateriel"),
						resultat.getString("dateFacture"),resultat.getString("etat"),
						resultat.getString("dateExpirationGarantieMateriel"),resultat.getString("nomRevendeur"),
						resultat.getString("nomFabricant"),null,resultat.getString("nomSite")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return listAnciennete;
	}
	
	public List<IAnciennete> recupererAncienneteParSiteEtType(Site site, Type type) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<IAnciennete> listAnciennete = new ArrayList<IAnciennete>();

		try {
			PreparedStatement prep = connexion.prepareStatement(
					"Select m.idMateriel, m.nomMateriel, m.numeroSerieMateriel, f.dateFacture, m.etat, m.dateExpirationGarantieMateriel, r.nomRevendeur, fab.nomFabricant,s.nomSite"+
							" FROM Materiel m"+ 
							" JOIN site s on m.idSite=s.idSite"+
							" JOIN fabricant fab on m.idFabricant=fab.idfabricant"+
							" JOIN Facture f on m.idFacture=f.idFacture"+
							" JOIN REVENDEUR r on f.idRevendeur=r.idRevendeur"+
							" WHERE s.idSite=? AND m.idType=?;");
			prep.setInt(1, site.getIdSite());
			prep.setInt(2, type.getIdType());
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listAnciennete.add(new IAnciennete(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel"),resultat.getString("numeroSerieMateriel"),
						resultat.getString("dateFacture"),resultat.getString("etat"),
						resultat.getString("dateExpirationGarantieMateriel"),resultat.getString("nomRevendeur"),
						resultat.getString("nomFabricant"),null,resultat.getString("nomSite")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return listAnciennete;
	}
	
	public List<IAnciennete> recupererAncienneteParSite(Site site) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<IAnciennete> listAnciennete = new ArrayList<IAnciennete>();

		try {
			PreparedStatement prep = connexion.prepareStatement(
					"Select m.idMateriel, m.nomMateriel, m.numeroSerieMateriel, f.dateFacture, m.etat, m.dateExpirationGarantieMateriel, r.nomRevendeur, fab.nomFabricant,s.nomSite"+
							" FROM Materiel m"+ 
							" JOIN site s on m.idSite=s.idSite"+
							" JOIN fabricant fab on m.idFabricant=fab.idfabricant"+
							" JOIN Facture f on m.idFacture=f.idFacture"+
							" JOIN REVENDEUR r on f.idRevendeur=r.idRevendeur"+
							" WHERE s.idSite=?;");
			prep.setInt(1, site.getIdSite());
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listAnciennete.add(new IAnciennete(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel"),resultat.getString("numeroSerieMateriel"),
						resultat.getString("dateFacture"),resultat.getString("etat"),
						resultat.getString("dateExpirationGarantieMateriel"),resultat.getString("nomRevendeur"),
						resultat.getString("nomFabricant"),null,resultat.getString("nomSite")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return listAnciennete;
	}
	
	public List<IAnciennete> recupererAncienneteParType(Type type) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<IAnciennete> listAnciennete = new ArrayList<IAnciennete>();

		try {
			PreparedStatement prep = connexion.prepareStatement(
					"Select m.idMateriel, m.nomMateriel, m.numeroSerieMateriel, f.dateFacture, m.etat, m.dateExpirationGarantieMateriel, r.nomRevendeur, fab.nomFabricant,s.nomSite"+
							" FROM Materiel m"+ 
							" JOIN site s on m.idSite=s.idSite"+
							" JOIN fabricant fab on m.idFabricant=fab.idfabricant"+
							" JOIN Facture f on m.idFacture=f.idFacture"+
							" JOIN REVENDEUR r on f.idRevendeur=r.idRevendeur"+
							" WHERE m.idType=?;");
			prep.setInt(1, type.getIdType());
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listAnciennete.add(new IAnciennete(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel"),resultat.getString("numeroSerieMateriel"),
						resultat.getString("dateFacture"),resultat.getString("etat"),
						resultat.getString("dateExpirationGarantieMateriel"),resultat.getString("nomRevendeur"),
						resultat.getString("nomFabricant"),null,resultat.getString("nomSite")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return listAnciennete;
	}
}
