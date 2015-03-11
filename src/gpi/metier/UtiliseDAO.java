package gpi.metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import gpi.exception.ConnexionBDException;
import gpi.exception.PrimaryKeyException;
import utils.MaConnexion;

public class UtiliseDAO {

	public UtiliseDAO() {
	}

	public int ajouterUtilise(Utilise utilise) throws ConnexionBDException, PrimaryKeyException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		int resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion
					.prepareStatement("INSERT INTO UTILISE (dateUtilise, idUtilisateur, idMateriel)  VALUES (?,?,?);");

			prep.setString(1,utilise.getDateUtiliseStringProperty().getValue());
			prep.setInt(2, utilise.getUtilisateurUtilise().getIdUtilisateur().getValue());
			prep.setInt(3, utilise.getMaterielUtilise().getIdMateriel()	.getValue());
			resultat = prep.executeUpdate();
			return resultat;
		} catch (SQLException e) {
			if(e.getMessage().contains("PRIMARY KEY")){
        		throw new PrimaryKeyException("L'utilisateur "+utilise.getUtilisateurUtilise().getNomUtilisateur().getValue()+" "+utilise.getUtilisateurUtilise().getPrenomUtilisateur().getValue()+" utilise déjà le matériel "+utilise.getMaterielUtilise().getNomMateriel().getValue());
        	}else{
                e.printStackTrace();	
        	}
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int supprimerUtilise(int idUtilisateur, int idMateriel,String date) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		int resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion
					.prepareStatement("DELETE FROM UTILISE WHERE idUtilisateur=? And idMateriel=? AND dateUtilise=?;");
			prep.setInt(1,idUtilisateur);
			prep.setInt(2,idMateriel);
			prep.setString(3,date);
			resultat = prep.executeUpdate();
			return resultat;
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
		return 0;
	}
	
	public List<String> recupererDateUtilisation(int idMateriel,int idUtilisateur) throws ConnexionBDException {
		List<String> listDate = new ArrayList<String>();
		Connection connexion = MaConnexion.getInstance().getConnexion();
		ResultSet resultat;
		try {
			PreparedStatement statement = connexion.prepareStatement("SELECT * FROM UTILISE WHERE idMateriel=? AND idUtilisateur=?");
            statement.setInt(1, idMateriel);
			statement.setInt(2, idUtilisateur);
			resultat=statement.executeQuery();
            while(resultat.next()){
            	listDate.add(resultat.getString("dateUtilise"));         
            }
		} catch (SQLException e) {
		}finally{
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				 e.printStackTrace();
			}
		}
		return listDate;
	}

	public List<Utilise> recupererMaterielUtiliseAll() throws ConnexionBDException {
		List<Utilise> listUtilise = new ArrayList<Utilise>();
		UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
		Connection connexion = MaConnexion.getInstance().getConnexion();
		MaterielDAO materielDAO = new MaterielDAO();
		ResultSet resultat;
		LocalDate date;
		Utilisateur utilisateur;
		Materiel materiel;
		try {
			Statement statement = connexion.createStatement();
            resultat=statement.executeQuery("SELECT * FROM UTILISE");
            while(resultat.next()){
            	date = LocalDate.parse(resultat.getString("dateUtilise"));
            	utilisateur = utilisateurDAO.recupererUtilisateurParId(resultat.getInt("idUtilisateur"));
				materiel = materielDAO.recupererMaterielParId(resultat.getInt("idMateriel"));
                listUtilise.add(new Utilise(date,utilisateur,materiel));
            }
		} catch (SQLException e) {
		}finally{
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				 e.printStackTrace();
			}
		}
		return listUtilise;
	}
	public List<Utilisateur> recupererAllUtilisateurUtilise() throws ConnexionBDException {
		List<Utilisateur> listUtilisateur = new ArrayList<Utilisateur>();
		UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
		Connection connexion = MaConnexion.getInstance().getConnexion();
		ResultSet resultat;
		Utilisateur utilisateur;
		try {
			Statement statement = connexion.createStatement();
            resultat=statement.executeQuery("SELECT DISTINCT idUtilisateur FROM UTILISE");
            while(resultat.next()){
            	utilisateur = utilisateurDAO.recupererUtilisateurParId(resultat.getInt("idUtilisateur"));
            	listUtilisateur.add(utilisateur);
            }
		} catch (SQLException e) {
		}finally{
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				 e.printStackTrace();
			}
		}
		return listUtilisateur;
	}
	
	public String recupererNomDernierUtilisateurMachine(Integer idMateriel){
		
		Connection connexion;
		ResultSet resultat;
		int idUtilisateur;
		Utilisateur utilisateur;
		UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
		String nomUtilisateur="";
		try {
			connexion =  MaConnexion.getInstance().getConnexion();
			PreparedStatement statement = connexion.prepareStatement("SELECT idUtilisateur FROM UTILISE WHERE idMateriel=? AND dateUtilise>=ALL(SELECT dateUtilise FROM UTILISE WHERE idMateriel=?) ");
			statement.setInt(1, idMateriel.intValue());
			statement.setInt(2, idMateriel.intValue());
			resultat=statement.executeQuery();
			if(resultat.next()){
				idUtilisateur=resultat.getInt(1);
				utilisateur=utilisateurDAO.recupererUtilisateurParId(idUtilisateur);
				nomUtilisateur=utilisateur.getNomUtilisateur().getValue()+" "+utilisateur.getPrenomUtilisateur().getValue();
			}else{
				nomUtilisateur="Aucun utilisateur";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnexionBDException e) {
			 e.printStackTrace();
		}
		return nomUtilisateur;
	}
	
public List<String> recupererUtilisateursByDateParMachine(Integer idMateriel) throws ConnexionBDException {
		
		Connection connexion;
		ResultSet resultat;
		String donnee;
		List<String> resultats=new ArrayList<String>();
		UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
		String nomUtilisateur="";
		try {
			connexion =  MaConnexion.getInstance().getConnexion();
			PreparedStatement statement = connexion.prepareStatement("SELECT idUtilisateur,dateUtilise FROM UTILISE WHERE idMateriel=? ORDER BY dateUtilise ASC");
			statement.setInt(1, idMateriel.intValue());
			resultat=statement.executeQuery();
			resultat.next();
			donnee=resultat.getString(1)+"_"+resultat.getString(2);
			while(resultat.next()){
				donnee+="_"+resultat.getString(2);
				resultats.add(donnee);
				donnee=resultat.getString(1)+"_"+resultat.getString(2);
			}
			donnee+="_NODATE";
			resultats.add(donnee);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnexionBDException e) {
			 e.printStackTrace();
		}
		return resultats;
	}

}