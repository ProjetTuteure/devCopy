package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utils.MaConnexion;

public class LogicielDAO {

	
	public LogicielDAO(){}
	
	public int ajouterLogiciel(Logiciel logiciel) throws ConnexionBDException{
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("INSERT INTO LOGICIEL(nomLogiciel,versionLogiciel,dateExpirationLogiciel,idFacture) VALUES (?,?,?,?);");
			prep.setString(1, logiciel.getNomLogiciel());
			prep.setString(2, logiciel.getVersionLogiciel());
			if(logiciel.getDateExpirationLogiciel()==null){
				prep.setString(3, null);
			}else{
				prep.setString(3, logiciel.getDateExpirationLogicielStringProperty().getValue());
			}			
			prep.setInt(4, logiciel.getFactureLogiciel().getIdFacture().getValue());
			
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
	
	
	public int modifierLogiciel(Logiciel logiciel) throws ConnexionBDException{
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("UPDATE LOGICIEL SET nomLogiciel=?, versionLogiciel=?, dateExpirationLogiciel=?, idFacture=? WHERE idLogiciel=? ;");
			prep.setString(1, logiciel.getNomLogiciel());
			prep.setString(2, logiciel.getVersionLogiciel());
			if(logiciel.getDateExpirationLogiciel()==null){
				prep.setString(3, null);
			}else{
				prep.setString(3, logiciel.getDateExpirationLogiciel().toString());
			}
			prep.setInt(4, logiciel.getFactureLogiciel().getIdFacture().getValue());
			prep.setInt(5, logiciel.getIdLogiciel());
			
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
	
	public int supprimerLogiciel(Logiciel logiciel) throws ConnexionBDException{
		Connection connexion=null;
		int resultat;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("DELETE FROM LOGICIEL WHERE idLogiciel=?;");
			
			prep.setInt(1, logiciel.getIdLogiciel());
			
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
	
	public Logiciel recupererLogicielParId(int idLogiciel) throws ConnexionBDException{
		Connection connexion=null;
		ResultSet resultat;
		LocalDate dateExpirationLogiciel;
		String nomLogiciel,versionLogiciel;
		int idFacture;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			FactureDAO factureDAO=new FactureDAO();
			PreparedStatement prep = connexion.prepareStatement("SELECT * FROM LOGICIEL WHERE idLogiciel=?;");
		
			prep.setInt(1, idLogiciel);
			
			resultat=prep.executeQuery();
			resultat.next();
			nomLogiciel=resultat.getString("nomLogiciel");
			versionLogiciel=resultat.getString("versionLogiciel");
			dateExpirationLogiciel=LocalDate.parse(resultat.getString("dateExpirationLogiciel"));
			idFacture=resultat.getInt("idFacture");
		
			return new Logiciel(idLogiciel,nomLogiciel,versionLogiciel,dateExpirationLogiciel,factureDAO.recupererFactureParId(idFacture));
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
		
	public List<Logiciel> recupererAllLogiciel() throws ConnexionBDException{
		Connection connexion=null;
		FactureDAO factureDAO=new FactureDAO();
		List<Logiciel> listLogiciel=new ArrayList<Logiciel>();
		ResultSet resultat;
		LocalDate dateExpirationLogiciel;
		String nomLogiciel,versionLogiciel;
		int idFacture,idLogiciel;
		Logiciel logiciel;
		try{
			connexion=MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion.prepareStatement("SELECT * FROM LOGICIEL;");
			
			
			resultat=prep.executeQuery();
			while(resultat.next()){
				idLogiciel=resultat.getInt(1);
				nomLogiciel=resultat.getString(2);
				versionLogiciel=resultat.getString(3);
				if(resultat.getString(4)!=null){
					dateExpirationLogiciel=LocalDate.parse(resultat.getString(4));
				}else{
					dateExpirationLogiciel=null;
				}
				idFacture=resultat.getInt(5);
				logiciel=new Logiciel(idLogiciel,nomLogiciel,versionLogiciel,dateExpirationLogiciel,factureDAO.recupererFactureParId(idFacture));
				listLogiciel.add(logiciel);
			}
			return listLogiciel;
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


