package gpi.metier;

import gpi.MainApp;
import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import utils.MaConnexion;

public class MaterielDAO {
	private Connection connexion;

	/**
	 * Permet d'ajouter un materiel dans la base de donnees
	 * 
	 * @param materiel
	 *            le materiel à ajouter dans la base de donnees
	 */
	public int ajouterMateriel(Materiel materiel) throws ConnexionBDException {
		int nombreLigneAffectee = 0;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prepareStatement = connexion
					.prepareStatement("INSERT INTO MATERIEL (numImmobMateriel,numeroSerieMateriel,systemeExploitationMateriel,nomMateriel,dateExpirationGarantieMateriel,"
							+ "repertoireDrivers,modeleMateriel,etat,idFacture,idFabricant,idSite,idType) "
							+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			prepareStatement.setString(1, materiel.getNumImmobMateriel()
					.getValue());
			prepareStatement.setString(2, materiel.getNumeroSerieMateriel()
					.getValue());
			prepareStatement.setString(3, materiel
					.getSystemeExploitationMateriel().getValue());
			prepareStatement.setString(4, materiel.getNomMateriel().getValue());
			prepareStatement.setString(5, materiel.getDateExpirationGarantie()
					.toString());
			prepareStatement.setString(6, materiel
					.getRepertoireDriverMateriel().getValue());
			prepareStatement.setString(7, materiel.getModeleMateriel());
			prepareStatement
					.setString(8, materiel.getEtatMateriel().toString());
			prepareStatement.setString(9, materiel.getFactureMateriel()
					.getNumFacture());
			prepareStatement.setInt(10, materiel.getFabricantMateriel()
					.getIdFabricant().getValue());
			prepareStatement.setInt(11, materiel.getSiteMateriel()
					.getIdSiteProperty().getValue());
			prepareStatement.setInt(12, materiel.getTypeMateriel()
					.getIdType());
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
	 *            le materiel à modifier
	 */
	public void modifierMateriel(Materiel materiel) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement preparedStatement = connexion
					.prepareStatement("UPDATE MATERIEL SET numImmobMateriel=?,nomMateriel=?,numeroSerieMateriel=?,systemeExploitation=?,dateExpirationGarantieMateriel=?,"
							+ "repertoireDrivers=?,modeleMateriel=?,etat=?,idFacture=?,idFabricant=?,idSite=?,idType=?  "
							+ "WHERE idMateriel=?");
			preparedStatement.setString(1, materiel.getNumImmobMateriel()
					.getValue());
			preparedStatement
					.setString(2, materiel.getNomMateriel().getValue());
			preparedStatement.setString(3, materiel.getNumeroSerieMateriel()
					.getValue());
			preparedStatement.setString(4, materiel	.getSystemeExploitationMateriel().getValue());
			preparedStatement.setString(5, materiel.getDateExpirationGarantieMaterielStringProperty().getValue());
			preparedStatement.setString(6, materiel.getRepertoireDriverMateriel().getValue());
			preparedStatement.setString(7, materiel.getModeleMateriel());
			preparedStatement.setString(8, materiel.getEtatMateriel().toString());
			preparedStatement.setString(9, materiel.getFactureMateriel().getNumFacture());
			preparedStatement.setInt(10, materiel.getFabricantMateriel().getIdFabricant().getValue());
			preparedStatement.setInt(11, materiel.getSiteMateriel().getIdSiteProperty().getValue());
			preparedStatement.setInt(12, materiel.getTypeMateriel().getIdType());
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
	public void supprimerMateriel(Materiel materiel)
			throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement preparedStatement = connexion
					.prepareStatement("DELETE FROM MATERIEL WHERE idMateriel=?");
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
			Type typeMateriel = typeDAO.recupererTypeParId(resultat.getInt("idType"));
			LocalDate dateExpirationGarantieMateriel = LocalDate.parse(resultat.getString("dateExpirationGarantieMateriel"));
			FactureDAO factureDAO = new FactureDAO();
			Facture factureMateriel = factureDAO.recupererFactureParId(resultat.getInt("idFacture"));
			SiteDAO siteDAO = new SiteDAO();
			Site siteMateriel = siteDAO.recupererSiteParId(resultat.getInt("idSite"));
			FabricantDAO fabricantDAO = new FabricantDAO();
			Fabricant fabricantMateriel = fabricantDAO.recupererFabricantParId(resultat.getInt("idFabricant"));
			materiel = new Materiel(resultat.getInt("idMateriel"),resultat.getString("numImmobMateriel"),resultat.getString("numeroSerieMateriel"),resultat.getString("systemeExploitationMateriel"),resultat.getString("nomMateriel"), typeMateriel, Etat.valueOf(resultat.getString("etat")), dateExpirationGarantieMateriel, resultat.getString("repertoireDrivers"), factureMateriel, siteMateriel, fabricantMateriel, resultat.getString("modeleMateriel"));
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
		Materiel materiel = null;
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
	
	public List<Materiel> recupererRechercheAvanceeMateriel() throws ConnexionBDException{
		List<Materiel> listMateriel=new ArrayList<Materiel>();
		String sql;
		sql="SELECT * FROM MATERIEL M ";
		if(!MainApp.getCritere(9).equals("")){
			sql+="JOIN FABRICANT FAB ON FAB.idFabricant=M.idFabricant \n";
		}
		if(!MainApp.getCritere(3).equals("") || !MainApp.getCritere(6).equals("") || !MainApp.getCritere(7).equals("") || !MainApp.getCritere(8).equals("")){
			sql+="JOIN FACTURE FAC ON FAC.idFacture=M.idFacture \n";
		}
		if(!MainApp.getCritere(8).equals("")){
			sql+="JOIN REVENDEUR R ON R.idRevendeur=FAC.idRevendeur \n";
		}
		if(!MainApp.getCritere(5).equals("")){
			sql+="JOIN UTILISE U ON U.idMateriel=M.idMateriel \n";
			sql+="JOIN UTILISATEUR UT ON UT.idUtilisateur=U.idUtilisateur \n";
		}
		sql+=" WHERE ";		
		if(!MainApp.getCritere(0).equals("")){
			sql+="numImmobMateriel = '"+MainApp.getCritere(0)+"' AND \n";
		}
		if(!MainApp.getCritere(1).equals("")){
			sql+="nomMateriel = '"+MainApp.getCritere(1)+"' AND \n";
		}
		if(!(MainApp.getCritere(2).equals(""))){
			sql+="idSite = '"+MainApp.getCritere(2)+"' AND \n";
		}
		if(!MainApp.getCritere(3).equals("")){
			LocalDate localDate=LocalDate.now();
			localDate=localDate.minusYears((Integer)(MainApp.getCritere(3)));
			String chaine1="";
	        String chaine2="";
	        if(localDate.getDayOfMonth()<10){
	            chaine1="0";
	        }
	        if(localDate.getMonthValue()<10){
	            chaine2="0";
	        }
	        String dateFacture=localDate.getYear()+"-"+chaine2+localDate.getMonthValue()+"-"+chaine1+localDate.getDayOfMonth();
	        if((Integer)(MainApp.getCritere(3))<8){
				sql+="dateFacture >= '"+dateFacture+"' AND \n";
			}else{
				sql+="dateFacture < '"+dateFacture+"' AND \n";
			}			
		}
		if(!(MainApp.getCritere(4).equals(""))){
			sql+="idType = '"+MainApp.getCritere(4)+"' AND \n";
		}
		if(!MainApp.getCritere(5).equals("")){
			sql+="nomUtilisateur = '"+MainApp.getCritere(5)+"' AND \n";
		}
		if(!MainApp.getCritere(6).equals("")){
			sql+="dateFacture = '"+MainApp.getCritere(6)+"' AND \n";
		}
		if(!MainApp.getCritere(7).equals("")){
			sql+="FAC.idFacture = '"+MainApp.getCritere(7)+"' AND \n";
		}
		if(!MainApp.getCritere(8).equals("")){
			sql+="nomRevendeur = '"+MainApp.getCritere(8)+"' AND \n";
		}
		if(!MainApp.getCritere(9).equals("")){
			sql+="nomFabricant = '"+MainApp.getCritere(9)+"' AND \n";
		}
		if(!MainApp.getCritere(10).equals("")){
			sql+="modeleMateriel = '"+MainApp.getCritere(10) +"' AND \n";
		}
		sql+=" 1=1 ";
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery(sql);
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
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listMateriel;		
	}

}
