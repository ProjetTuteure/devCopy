package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import utils.MaConnexion;
import utils.Propriete;

public class MaterielDAO {
	private Connection connexion;

	/**
	 * Permet d'ajouter un materiel dans la base de donnees
	 * 
	 * @param materiel
	 *            le materiel ï¿½ ajouter dans la base de donnees
	 */
	public int ajouterMateriel(Materiel materiel) throws ConnexionBDException {
		int nombreLigneAffectee = 0;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prepareStatement = connexion
					.prepareStatement("INSERT INTO MATERIEL (numImmobMateriel,numeroSerieMateriel,systemeExploitationMateriel,nomMateriel,dateExpirationGarantieMateriel,"
							+ "repertoireDrivers,modeleMateriel,etat,idFacture,idFabricant,idSite,idType) "
							+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			prepareStatement.setString(1, materiel.getNumImmobMateriel().getValue());
			prepareStatement.setString(2, materiel.getNumeroSerieMateriel().getValue());
			prepareStatement.setString(3, materiel.getSystemeExploitationMateriel().getValue());
			prepareStatement.setString(4, materiel.getNomMateriel().getValue());
			if(materiel.getDateExpirationGarantie()==null){
				prepareStatement.setString(5, null);
			}else{
				prepareStatement.setString(5, materiel.getDateExpirationGarantieMaterielStringProperty().getValue());
			}
			prepareStatement.setString(6, materiel.getRepertoireDriverMateriel().getValue());
			prepareStatement.setString(7, materiel.getModeleMateriel());
			
			if(materiel.getEtatMateriel()==null){
				prepareStatement.setString(8, null);
			}else{
				prepareStatement.setString(8, materiel.getEtatMateriel().toString());
			}
			if(materiel.getFactureMateriel()==null){
				prepareStatement.setString(9, null);
			}else{
				prepareStatement.setInt(9, materiel.getFactureMateriel().getIdFacture().getValue());
			}
			if(materiel.getFabricantMateriel()==null){
				prepareStatement.setString(10, null);
			}else{
				prepareStatement.setInt(10, materiel.getFabricantMateriel().getIdFabricant().getValue());
			}
			if(materiel.getSiteMateriel()==null){
				prepareStatement.setString(11,null);
			}else{
				prepareStatement.setInt(11, materiel.getSiteMateriel().getIdSiteProperty().getValue());
			}
			if(materiel.getTypeMateriel()==null){
				prepareStatement.setString(12, null);
			}else{
				prepareStatement.setInt(12, materiel.getTypeMateriel().getIdType());
			}
			nombreLigneAffectee = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nombreLigneAffectee;
	}

	/**
	 * Permet de modifier un materiel
	 * 
	 * @param materiel
	 *            le materiel ï¿½ modifier
	 */
	public void modifierMateriel(Materiel materiel) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement preparedStatement = connexion
					.prepareStatement("UPDATE MATERIEL SET numImmobMateriel=?,nomMateriel=?,numeroSerieMateriel=?,systemeExploitationMateriel=?,dateExpirationGarantieMateriel=?,"
							+ "repertoireDrivers=?,modeleMateriel=?,etat=?,idFacture=?,idFabricant=?,idSite=?,idType=?  "
							+ "WHERE idMateriel=?");
			preparedStatement.setString(1, materiel.getNumImmobMateriel().getValue());
			preparedStatement.setString(2, materiel.getNomMateriel().getValue());
			preparedStatement.setString(3, materiel.getNumeroSerieMateriel().getValue());
			preparedStatement.setString(4, materiel.getSystemeExploitationMateriel().getValue());
			if(materiel.getDateExpirationGarantie()==null){
				preparedStatement.setString(5, null);
			}else{
				preparedStatement.setString(5, materiel.getDateExpirationGarantieMaterielStringProperty().getValue());
			}
			preparedStatement.setString(6, materiel.getRepertoireDriverMateriel().getValue());
			preparedStatement.setString(7, materiel.getModeleMateriel());
			if(materiel.getEtatMateriel()==null){
				preparedStatement.setString(8, null);
			}else{
				preparedStatement.setString(8, materiel.getEtatMateriel().toString());
			}		
			if(materiel.getFactureMateriel()==null){
				preparedStatement.setString(9, null);
			}else{
				preparedStatement.setInt(9, materiel.getFactureMateriel().getIdFacture().getValue());
			}
			if(materiel.getFabricantMateriel()==null){
				preparedStatement.setString(10, null);
			}else{
				preparedStatement.setInt(10, materiel.getFabricantMateriel().getIdFabricant().getValue());
			}
			if(materiel.getSiteMateriel()==null){
				preparedStatement.setString(11, null);
			}else{
				preparedStatement.setInt(11, materiel.getSiteMateriel().getIdSiteProperty().getValue());
			}
			if(materiel.getTypeMateriel()==null){
				preparedStatement.setString(12, null);
			}else{
				preparedStatement.setInt(12, materiel.getTypeMateriel().getIdType());
			}
			preparedStatement.setInt(13, materiel.getIdMateriel().getValue());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Permet de supprimer un materiel
	 * 
	 * @param materiel
	 *            le materiel a supprimer
	 */
	public void supprimerMateriel(Materiel materiel) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM MATERIEL WHERE idMateriel=?");
			preparedStatement.setInt(1, materiel.getIdMateriel().getValue());
			preparedStatement.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Materiel recupererMaterielParId(int idMateriel) throws ConnexionBDException {
		ResultSet resultat;
		Materiel materiel = null;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM MATERIEL WHERE idMateriel=?;");
			preparedStatement.setInt(1, idMateriel);
			resultat = preparedStatement.executeQuery();
			resultat.next();
			TypeDAO typeDAO = new TypeDAO();
			Type typeMateriel=null;
			if(resultat.getString("idType")!=null){
				typeMateriel = typeDAO.recupererTypeParId(resultat.getInt("idType"));
			}		
			
			LocalDate dateExpirationGarantieMateriel = null;
			if(resultat.getString("dateExpirationGarantieMateriel")!=null){
				dateExpirationGarantieMateriel=LocalDate.parse(resultat.getString("dateExpirationGarantieMateriel"));
			}
			FactureDAO factureDAO = new FactureDAO();
			Facture factureMateriel = null;
			if(resultat.getString("idFacture")!=null){
				factureMateriel = factureDAO.recupererFactureParId(resultat.getInt("idFacture"));
			}
			SiteDAO siteDAO = new SiteDAO();
			Site siteMateriel = null;
			if(resultat.getString("idSite")!=null){
				siteMateriel = siteDAO.recupererSiteParId(resultat.getInt("idSite"));
			}
			FabricantDAO fabricantDAO = new FabricantDAO();
			Fabricant fabricantMateriel = null;
			if(resultat.getString("idFabricant")!=null){
				fabricantMateriel = fabricantDAO.recupererFabricantParId(resultat.getInt("idFabricant"));
			}
			
			Etat etatMateriel=null;
			if(resultat.getString("etat")!=null){
				etatMateriel=Etat.valueOf(resultat.getString("etat"));
			}
			materiel = new Materiel(resultat.getInt("idMateriel"),resultat.getString("numImmobMateriel"),resultat.getString("numeroSerieMateriel"),resultat.getString("systemeExploitationMateriel"),resultat.getString("nomMateriel"), typeMateriel, etatMateriel, dateExpirationGarantieMateriel, resultat.getString("repertoireDrivers"), factureMateriel, siteMateriel, fabricantMateriel, resultat.getString("modeleMateriel"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return materiel;
	}

	public List<Materiel> recupererAllMateriel() throws ConnexionBDException {
		ResultSet resultat;
		List<Materiel> listMateriel = new ArrayList<Materiel>();
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			Statement statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT * FROM MATERIEL");
			while (resultat.next()) {
				TypeDAO typeDAO = new TypeDAO();
				Type typeMateriel = typeDAO.recupererTypeParId(resultat
						.getInt("idType"));
				LocalDate dateExpirationGarantieMateriel = LocalDate.parse(resultat.getString("dateExpirationGarantieMateriel"));
				FactureDAO factureDAO = new FactureDAO();
				Facture factureMateriel = factureDAO.recupererFactureParId(resultat.getInt("idFacture"));
				SiteDAO siteDAO = new SiteDAO();
				Site siteMateriel = siteDAO.recupererSiteParId(resultat.getInt("idSite"));
				FabricantDAO fabricantDAO = new FabricantDAO();
				Fabricant fabricantMateriel = fabricantDAO.recupererFabricantParId(resultat.getInt("idFabricant"));
				listMateriel.add(new Materiel(resultat.getInt("idMateriel"),resultat.getString("numImmobMateriel"),resultat.getString("numeroSerieMateriel"),resultat.getString("systemeExploitationMateriel"),resultat.getString("nomMateriel"), typeMateriel, Etat.valueOf(resultat.getString("etat")), dateExpirationGarantieMateriel, resultat.getString("repertoireDrivers"), factureMateriel, siteMateriel, fabricantMateriel, resultat.getString("modeleMateriel")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion !=null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listMateriel;
	}

	public List<Materiel> recupererMaterielParSiteEtType(Site site, Type type)
			throws ConnexionBDException {
		List<Materiel> listMateriel = new ArrayList<Materiel>();
		ResultSet resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM MATERIEL WHERE idType=? And idSite=?;");
			preparedStatement.setInt(1, type.getIdType());
			preparedStatement.setInt(2, site.getIdSite());
			resultat = preparedStatement.executeQuery();
			TypeDAO typeDAO = new TypeDAO();
			SiteDAO siteDAO = new SiteDAO();
			while (resultat.next()) {
				Type typeMateriel = typeDAO.recupererTypeParId(resultat.getInt("idType"));
				LocalDate dateExpirationGarantieMateriel = LocalDate.parse(resultat.getString("dateExpirationGarantieMateriel"));
				FactureDAO factureDAO = new FactureDAO();
				Facture factureMateriel = factureDAO.recupererFactureParId(resultat.getInt("idFacture"));
				Site siteMateriel = siteDAO.recupererSiteParId(resultat.getInt("idSite"));
				FabricantDAO fabricantDAO = new FabricantDAO();
				Fabricant fabricantMateriel = fabricantDAO.recupererFabricantParId(resultat.getInt("idFabricant"));
				listMateriel.add(new Materiel(resultat.getInt("idMateriel"),resultat.getString("numImmobMateriel"),resultat.getString("numeroSerieMateriel"),resultat.getString("systemeExploitationMateriel"),resultat.getString("nomMateriel"), typeMateriel, Etat.valueOf(resultat.getString("etat")), dateExpirationGarantieMateriel, resultat.getString("repertoireDrivers"), factureMateriel, siteMateriel, fabricantMateriel, resultat.getString("modeleMateriel")));
			}
			return listMateriel;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listMateriel;
	}
	
	public List<Materiel> recupererMaterielParSite(Site site)
			throws ConnexionBDException {
		List<Materiel> listMateriel = new ArrayList<Materiel>();
		ResultSet resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM MATERIEL WHERE idSite=?;");
			preparedStatement.setInt(1, site.getIdSite());
			resultat = preparedStatement.executeQuery();
			TypeDAO typeDAO = new TypeDAO();
			SiteDAO siteDAO = new SiteDAO();
			while (resultat.next()) {
				Type typeMateriel = typeDAO.recupererTypeParId(resultat.getInt("idType"));
				LocalDate dateExpirationGarantieMateriel = LocalDate.parse(resultat.getString("dateExpirationGarantieMateriel"));
				FactureDAO factureDAO = new FactureDAO();
				Facture factureMateriel = factureDAO.recupererFactureParId(resultat.getInt("idFacture"));
				Site siteMateriel = siteDAO.recupererSiteParId(resultat.getInt("idSite"));
				FabricantDAO fabricantDAO = new FabricantDAO();
				Fabricant fabricantMateriel = fabricantDAO.recupererFabricantParId(resultat.getInt("idFabricant"));
				listMateriel.add(new Materiel(resultat.getInt("idMateriel"),resultat.getString("numImmobMateriel"),resultat.getString("numeroSerieMateriel"),resultat.getString("systemeExploitationMateriel"),resultat.getString("nomMateriel"), typeMateriel, Etat.valueOf(resultat.getString("etat")), dateExpirationGarantieMateriel, resultat.getString("repertoireDrivers"), factureMateriel, siteMateriel, fabricantMateriel, resultat.getString("modeleMateriel")));
			}
			return listMateriel;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listMateriel;
	}
	
	public List<Materiel> recupererMaterielParType( Type type)	throws ConnexionBDException {
		List<Materiel> listMateriel = new ArrayList<Materiel>();
		ResultSet resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM MATERIEL WHERE idType=?;");
			preparedStatement.setInt(1, type.getIdType());
			resultat = preparedStatement.executeQuery();
			TypeDAO typeDAO = new TypeDAO();
			SiteDAO siteDAO = new SiteDAO();
			while (resultat.next()) {
				Type typeMateriel = typeDAO.recupererTypeParId(resultat.getInt("idType"));
				LocalDate dateExpirationGarantieMateriel = LocalDate.parse(resultat.getString("dateExpirationGarantieMateriel"));
				FactureDAO factureDAO = new FactureDAO();
				Facture factureMateriel = factureDAO.recupererFactureParId(resultat.getInt("idFacture"));
				Site siteMateriel = siteDAO.recupererSiteParId(resultat.getInt("idSite"));
				FabricantDAO fabricantDAO = new FabricantDAO();
				Fabricant fabricantMateriel = fabricantDAO.recupererFabricantParId(resultat.getInt("idFabricant"));
				listMateriel.add(new Materiel(resultat.getInt("idMateriel"),resultat.getString("numImmobMateriel"),resultat.getString("numeroSerieMateriel"),resultat.getString("systemeExploitationMateriel"),resultat.getString("nomMateriel"), typeMateriel, Etat.valueOf(resultat.getString("etat")), dateExpirationGarantieMateriel, resultat.getString("repertoireDrivers"), factureMateriel, siteMateriel, fabricantMateriel, resultat.getString("modeleMateriel")));
			}
			return listMateriel;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listMateriel;
	}
	
	
	
	public void ajouterRepertoireDriverMateriel() throws ConnexionBDException{
		connexion = MaConnexion.getInstance().getConnexion();
		ResultSet resultat;
		int idMateriel=-1;
		try {
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT IDENT_CURRENT('materiel');");
			resultat = preparedStatement.executeQuery();
			resultat.next();
			idMateriel=resultat.getInt(1);
			Properties p = Propriete.getInstance().getProperties();
			String newRepertoire=p.getProperty("repertoire")+"/"+idMateriel;
			PreparedStatement preparedStatementAjout = connexion.prepareStatement("UPDATE materiel SET repertoireDrivers=? WHERE idMateriel=?;");
			preparedStatementAjout.setString(1, newRepertoire);
			preparedStatementAjout.setInt(2, idMateriel);
			preparedStatementAjout.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String recupererNomMaterielParId(int idMateriel) throws ConnexionBDException {
		ResultSet resultat;
		String nomMateriel="";
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement Preparedstatement = connexion.prepareStatement("SELECT nomMateriel FROM MATERIEL WHERE idMateriel=?");
			Preparedstatement.setInt(1, idMateriel);
			resultat = Preparedstatement.executeQuery();
			resultat.next();
			nomMateriel=resultat.getString("nomMateriel");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connexion !=null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nomMateriel;
	}
}