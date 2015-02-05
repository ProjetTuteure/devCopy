package gpi.metier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import gpi.exception.ConnexionBDException;
import utils.MaConnexion;

public class PrestataireDAO {
	private Connection connexion;

	public int ajouterPrestataire(Prestataire prestataire)
			throws ConnexionBDException {
		int nombreLigneAffectee = 0;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement = connexion
					.prepareStatement("INSERT INTO PRESTATAIRE(nomPrestataire,prenomPrestataire, telPrestataire, mobilePrestataire, faxPrestataire, emailPrestataire, societePrestataire) VALUES (?,?,?,?,?,?,?);");

			preparedStatement.setString(1, prestataire.getNomPrestataire()
					.getValue());
			preparedStatement.setString(2, prestataire.getPrenomPrestataire()
					.getValue());
			preparedStatement.setString(3, prestataire.getTelPrestataire()
					.getValue());
			preparedStatement.setString(4, prestataire.getMobilePrestataire()
					.getValue());
			preparedStatement.setString(5, prestataire.getFaxPrestataire()
					.getValue());
			preparedStatement.setString(6, prestataire.getEmailPrestataire()
					.getValue());
			preparedStatement.setString(7, prestataire.getSocieteePrestataire()
					.getValue());

			nombreLigneAffectee = preparedStatement.executeUpdate();
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

	public int modifierPrestataire(Prestataire prestataire)
			throws ConnexionBDException {
		int nombreLigneAffectee = 0;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement = connexion
					.prepareStatement("UPDATE PRESTATAIRE SET nomPrestataire=?,prenomPrestataire=?,telPrestataire=?,mobilePrestataire=?,faxPrestataire=?,emailPrestataire=?,societePrestataire=? WHERE idPrestataire=?");
			preparedStatement.setString(1, prestataire.getNomPrestataire()
					.getValue());
			preparedStatement.setString(2, prestataire.getPrenomPrestataire()
					.getValue());
			preparedStatement.setString(3, prestataire.getTelPrestataire()
					.getValue());
			preparedStatement.setString(4, prestataire.getMobilePrestataire()
					.getValue());
			preparedStatement.setString(5, prestataire.getFaxPrestataire()
					.getValue());
			preparedStatement.setString(6, prestataire.getEmailPrestataire()
					.getValue());
			preparedStatement.setString(7, prestataire.getSocieteePrestataire()
					.getValue());
			preparedStatement.setInt(8, prestataire.getIdPrestataire()
					.getValue());
			nombreLigneAffectee = preparedStatement.executeUpdate();

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

	public int supprimerPrestataire(Prestataire prestataire)
			throws ConnexionBDException {
		int nombreLigneAffectee = 0;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement = connexion
					.prepareStatement("DELETE FROM PRESTATAIRE WHERE idPrestataire=?;");
			preparedStatement.setInt(1, prestataire.getIdPrestataire()
					.getValue());
			nombreLigneAffectee = preparedStatement.executeUpdate();
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

	public List<Prestataire> recupererAllPrestataire()
			throws ConnexionBDException {
		List<Prestataire> prestataireList = new ArrayList<Prestataire>();
		ResultSet resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			Statement statement = connexion.createStatement();
			resultat = statement.executeQuery("SELECT * FROM PRESTATAIRE");
			while (resultat.next()) {
				prestataireList.add(new Prestataire(resultat
						.getInt("idPrestataire"), resultat
						.getString("nomPrestataire"), resultat
						.getString("prenomPrestataire"), resultat
						.getString("telPrestataire"), resultat
						.getString("mobilePrestataire"), resultat
						.getString("faxPrestataire"), resultat
						.getString("emailPrestataire"), resultat
						.getString("societePrestataire")));
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
		return prestataireList;
	}

	public Prestataire recupererPrestataireParId(int idPrestataire)
			throws ConnexionBDException {
		ResultSet resultat;
		Prestataire prestataire = null;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement preparedStatement = connexion
					.prepareStatement("SELECT * FROM PRESTATAIRE WHERE idPrestataire=?;");
			preparedStatement.setInt(1, idPrestataire);
			resultat = preparedStatement.executeQuery();
			resultat.next();
			prestataire = new Prestataire(resultat.getInt("idPrestataire"),
					resultat.getString("nomPrestataire"),
					resultat.getString("prenomPrestataire"),
					resultat.getString("telPrestataire"),
					resultat.getString("mobilePrestataire"),
					resultat.getString("faxPrestataire"),
					resultat.getString("emailPrestataire"),
					resultat.getString("societePrestataire"));
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
		return prestataire;
	}
	
	public List<Prestataire> recupererPrestataireParNom(String nomPrestataire) throws ConnexionBDException {
		Connection connexion=MaConnexion.getInstance().getConnexion();
		List<Prestataire> list=null;
		try {
			PreparedStatement ps= connexion.prepareStatement("SELECT * FROM PRESTATAIRE WHERE nomPrestataire=?");
			ps.setString(1, nomPrestataire);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				list=new ArrayList<Prestataire>();
				list.add(new Prestataire(rs.getInt("idPrestataire"),
						rs.getString("nomPrestataire"),
						rs.getString("prenomPrestataire"),
						rs.getString("telPrestataire"),
						rs.getString("faxPrestataire"),
						rs.getString("mobilePrestataire"),
						rs.getString("emailPrestataire"),
						rs.getString("societePrestataire")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				connexion.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
		}
		return list;
	}
}