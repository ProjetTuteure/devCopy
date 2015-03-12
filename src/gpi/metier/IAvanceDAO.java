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
import java.util.List;

import utils.MaConnexion;

public class IAvanceDAO {
	private Connection connexion;
	
	
	public List<IAvance> recupererAnciennete() throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<IAvance> listAvance = new ArrayList<IAvance>();

		try {
			PreparedStatement prep = connexion.prepareStatement(
					"Select m.idMateriel, m.numImmobMateriel, m.nomMateriel, s.nomSite, t.nomType, f.dateFacture,f.numFacture, r.nomRevendeur, fab.nomFabricant,m.modeleMateriel"+
							" FROM Materiel m"+ 
							" JOIN site s on m.idSite=s.idSite"+
							" JOIN type t on t.idType=m.idType"+
							" JOIN fabricant fab on m.idFabricant=fab.idfabricant"+
							" JOIN Facture f on m.idFacture=f.idFacture"+
							" JOIN REVENDEUR r on f.idRevendeur=r.idRevendeur");	
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listAvance.add(new IAvance(resultat.getString("idMateriel"), resultat.getString("numImmobMateriel"),
						resultat.getString("nomMateriel"), resultat.getString("nomSite"),resultat.getString("anciennete"),
						resultat.getString("typeMateriel"),resultat.getString("nomUtilisateur"), resultat.getString("dateAchatMateriel"),
						resultat.getString("numFactureMateriel"), resultat.getString("nomRevendeur"), resultat.getString("nomFabricant"),resultat.getString("modeleMateriel")));
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
		return listAvance;
	}
	
	public List<IAvance> recupererRechercheAvanceeMateriel() throws ConnexionBDException{
		List<IAvance> listMateriel=new ArrayList<IAvance>();
		int i=1;
		String sql="Select distinct m.idMateriel, m.numImmobMateriel, m.nomMateriel, s.nomSite, t.nomType,ut.nomUtilisateur, f.dateFacture,f.numFacture, r.nomRevendeur, fab.nomFabricant,m.modeleMateriel"+
		" FROM Materiel m"+ 
		" JOIN site s on m.idSite=s.idSite"+
		" JOIN type t on t.idType=m.idType"+
		" JOIN fabricant fab on m.idFabricant=fab.idfabricant"+
		" JOIN Facture f on m.idFacture=f.idFacture"+
		" JOIN Revendeur r on f.idRevendeur=r.idRevendeur"+
		" JOIN Utilise u on u.idMateriel=m.idMateriel"+
		" JOIN Utilisateur ut on ut.idUtilisateur=u.idUtilisateur";
		sql+=" WHERE ";
		if(!MainApp.getCritere(0).equals("")){
			sql+="m.numImmobMateriel = ? AND \n";
		}
		if(!MainApp.getCritere(1).equals("")){
			sql+="m.nomMateriel = ? AND \n";
		}
		if(!(MainApp.getCritere(2).equals(""))){
			sql+="s.idSite = ? AND \n";
		}
		if(!MainApp.getCritere(3).equals("")){;
	        if((Integer)(MainApp.getCritere(3))<8){
				sql+="f.dateFacture >= ? AND \n";
			}else{
				sql+="f.dateFacture < ? AND \n";
			}			
		}
		if(!(MainApp.getCritere(4).equals(""))){
			sql+="t.idType = ? AND \n";
		}
		if(!MainApp.getCritere(5).equals("")){
			sql+="ut.nomUtilisateur = ? AND \n";
		}
		if(!MainApp.getCritere(6).equals("")){
			sql+="f.dateFacture = ? AND \n";
		}
		if(!MainApp.getCritere(7).equals("")){
			sql+="f.idFacture = ? AND \n";
		}
		if(!MainApp.getCritere(8).equals("")){
			sql+="r.nomRevendeur = ? AND \n";
		}
		if(!MainApp.getCritere(9).equals("")){
			sql+="fab.nomFabricant = ? AND \n";
		}
		if(!MainApp.getCritere(10).equals("")){
			sql+="m.modeleMateriel = ? AND \n";
		}
		sql+=" u.dateUtilise >= ALL(SELECT dateUtilise from utilise u1 "
				+ "where u.idMateriel = u1.idMateriel)";
		
		
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement =connexion.prepareStatement(sql);
			if(!MainApp.getCritere(0).equals("")){
				preparedStatement.setString(i, (String) MainApp.getCritere(0));
				i++;
			}
			System.out.println(MainApp.getCritere(0));
			if(!MainApp.getCritere(1).equals("")){
				preparedStatement.setString(i, (String) MainApp.getCritere(1));
				i++;
			}
			if(!(MainApp.getCritere(2).equals(""))){
				preparedStatement.setInt(i, (Integer) MainApp.getCritere(2));
				i++;
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
				preparedStatement.setString(i, dateFacture);
				i++;		
			}
			if(!(MainApp.getCritere(4).equals(""))){
				preparedStatement.setInt(i, (Integer) MainApp.getCritere(4));
				i++;
			}
			if(!MainApp.getCritere(5).equals("")){
				preparedStatement.setString(i, (String) MainApp.getCritere(5));
				i++;
			}
			if(!MainApp.getCritere(6).equals("")){
				preparedStatement.setString(i, (String) MainApp.getCritere(6));
				i++;
			}
			if(!MainApp.getCritere(7).equals("")){
				preparedStatement.setString(i, (String) MainApp.getCritere(7));
				i++;
			}
			if(!MainApp.getCritere(8).equals("")){
				preparedStatement.setString(i, (String) MainApp.getCritere(8));
				i++;
			}
			if(!MainApp.getCritere(9).equals("")){
				preparedStatement.setString(i, (String) MainApp.getCritere(9));
				i++;
			}
			if(!MainApp.getCritere(10).equals("")){
				preparedStatement.setString(i, (String) MainApp.getCritere(10));
				i++;
			}
			ResultSet resultat = preparedStatement.executeQuery();
			while (resultat.next()) {
				String anciennete;
				if(MainApp.getCritere(3)==null){
					anciennete=null;
				}else{
					anciennete=MainApp.getCritere(3).toString();
				}
				IAvance iAvance=new IAvance(resultat.getString("idMateriel"), resultat.getString("numImmobMateriel"),
						resultat.getString("nomMateriel"), resultat.getString("nomSite"),anciennete,
						resultat.getString("nomType"),resultat.getString("nomUtilisateur"), resultat.getString("dateFacture"),
						resultat.getString("numFacture"), resultat.getString("nomRevendeur"), resultat.getString("nomFabricant"),resultat.getString("modeleMateriel"));
				listMateriel.add(iAvance);
				
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
