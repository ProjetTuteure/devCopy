package gpi.metier;


import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utils.MaConnexion;
import utils.Popup;

public class FactureDAO {

	public FactureDAO(){}
	
	public int ajouterFacture(Facture facture) throws ConnexionBDException{
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("INSERT INTO FACTURE(numFacture,dateFacture,montantFacture,idRevendeur) VALUES (?,?,?,?);");
			prep.setString(1, facture.getNumFacture());
			prep.setString(2, facture.getDateFacture().toString());
			prep.setFloat(3, facture.getMontantFacture().get());
			prep.setInt(4, facture.getRevendeurFacture().getIdRevendeur().get());
			
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
	
	public int modifierFacture(Facture facture) throws ConnexionBDException{
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("UPDATE FACTURE SET numFacture=?, dateFacture=?, montantFacture=? , idRevendeur=? WHERE idFacture=? ;");
			prep.setString(1, facture.getNumFacture());
			prep.setString(2, facture.getDateFacture().toString());
			prep.setFloat(3, facture.getMontantFacture().get());
			prep.setInt(4, facture.getRevendeurFacture().getIdRevendeur().getValue());
			prep.setInt(5, facture.getIdFacture().getValue());
			
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
	
	public int supprimerFacture(Facture facture) throws ConnexionBDException{
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("DELETE FROM FACTURE WHERE idFacture=?;");
			
			prep.setInt(1, facture.getIdFacture().getValue());
			
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
	
	public Facture recupererFactureParId(int idFacture) throws ConnexionBDException{
		Connection connexion=null;
		ResultSet resultat;
		LocalDate dateFacture;
		String numFacture;
		float montantFacture;
		int idRevendeur;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			RevendeurDAO revendeurDAO=new RevendeurDAO();
			PreparedStatement prep = connexion.prepareStatement("SELECT * FROM FACTURE WHERE idFacture=?;");
			
			prep.setInt(1, idFacture);
			
			resultat=prep.executeQuery();
			resultat.next();
			numFacture=resultat.getString("numFacture");
			dateFacture=LocalDate.parse(resultat.getString("dateFacture"));
			montantFacture=resultat.getFloat("montantFacture");
			idRevendeur=resultat.getInt("idRevendeur");
			
			return new Facture(idFacture,numFacture,dateFacture,montantFacture,revendeurDAO.recupererRevendeurParId(idRevendeur));
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
	
	
	public List<Facture> recupererAllFacture() throws ConnexionBDException{
		Connection connexion=null;
		RevendeurDAO revendeurDAO=new RevendeurDAO();
		List<Facture> listFacture=new ArrayList<Facture>();
		ResultSet resultat;
		String numFacture;
		LocalDate dateFacture;
		int idFacture;
		float montantFacture;
		int idRevendeur;
		Facture facture;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("SELECT * FROM FACTURE;");
			
			
			resultat=prep.executeQuery();
			while(resultat.next()){
				idFacture=resultat.getInt("idFacture");
				numFacture=resultat.getString("numFacture");
				dateFacture=LocalDate.parse(resultat.getString("dateFacture"));
				montantFacture=resultat.getFloat("montantFacture");
				idRevendeur=resultat.getInt("idRevendeur");
				facture=new Facture(idFacture,numFacture,dateFacture,montantFacture,revendeurDAO.recupererRevendeurParId(idRevendeur));
				listFacture.add(facture);
			}
			return listFacture;
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
