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

public class ComposantDAO {

	FabricantDAO fabricantDAO = new FabricantDAO();

	public ComposantDAO() {
	}

	public void ajouterComposant(Composant composant)
			throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement prep = connexion
					.prepareStatement("INSERT INTO COMPOSANT (nomComposant, caracteristiqueComposant, idFabricant) "
							+ "VALUES (?,?,?);");

			prep.setString(1, composant.getNomComposant());
			prep.setString(2, composant.getCaracteristiqueComposant());
			prep.setInt(3, composant.getFabricantComposant().getIdFabricant()
					.getValue());
			prep.executeUpdate();
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
	}

	public boolean supprimerComposant(Composant composant)
			throws ConnexionBDException {

		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement prep = connexion
					.prepareStatement("DELETE FROM COMPOSANT WHERE idComposant = ?;");

			prep.setInt(1, composant.getIdComposant());
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connexion.close();
			} catch (SQLException se) {
				se.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public void modifierComposant(Composant composant)
			throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement prep = connexion
					.prepareStatement("UPDATE COMPOSANT SET nomComposant=?, caracteristiqueComposant=?, idFabricant=? WHERE idComposant=?");

			prep.setString(1, composant.getNomComposant());
			prep.setString(2, composant.getCaracteristiqueComposant());
			prep.setInt(3, composant.getFabricantComposant().getIdFabricant()
					.getValue());
			prep.setInt(4, composant.getIdComposant());
			prep.executeUpdate();
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
	}

	public Composant recupererComposantParId(int idComposant)
			throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		FabricantDAO fabricantDAO = new FabricantDAO();
		Composant composantARetourner = null;
		try {
			PreparedStatement prep = connexion
					.prepareStatement("SELECT * from COMPOSANT WHERE idcomposant=?");

			prep.setInt(1, idComposant);
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				composantARetourner = new Composant(new SimpleIntegerProperty(
						resultat.getInt("idComposant")),
						resultat.getString("nomComposant"),
						resultat.getString("caracteristiqueComposant"),
						fabricantDAO.recupererFabricantParId(resultat
								.getInt("idFabricant")));
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
		return composantARetourner;
	}

	public List<Composant> recupererAllComposant() throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<Composant> listComposant = new ArrayList<Composant>();

		try {
			PreparedStatement prep = connexion
					.prepareStatement("SELECT * from COMPOSANT");
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listComposant.add(new Composant(new SimpleIntegerProperty(
						resultat.getInt("idComposant")), resultat
						.getString("nomComposant"), resultat
						.getString("caracteristiqueComposant"),
						fabricantDAO.recupererFabricantParId(resultat
								.getInt("idFabricant"))));
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
		return listComposant;
	}
	
	public List<Composant> recupererComposantParNom(String nomComposant) throws ConnexionBDException {
		Connection connexion=MaConnexion.getInstance().getConnexion();
		List<Composant> list = null;
		try {
			PreparedStatement ps=connexion.prepareStatement("SELECT * FROM COMPOSANT WHERE nomComposant=?");
			ps.setString(1,nomComposant);
			ResultSet rs=ps.executeQuery();
			list=new ArrayList<Composant>();
			while(rs.next())
			{
				list.add(new Composant(new SimpleIntegerProperty(rs.getInt("idComposant")),
						rs.getString("nomComposant"),
						rs.getString("caracteristiqueComposant"),
						fabricantDAO.recupererFabricantParId(rs
								.getInt("idFabricant"))));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (connexion != null){
					connexion.close();
				}
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
		}
		return list;
	}
	
	
	public List<String> recupererAllNomComposant() throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<String> listComposant = new ArrayList<String>();

		try {
			PreparedStatement prep = connexion
					.prepareStatement("SELECT Distinct nomComposant from COMPOSANT");
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listComposant.add(resultat.getString("nomComposant"));
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
		return listComposant;
	}
}

