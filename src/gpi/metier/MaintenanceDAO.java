package gpi.metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utils.MaConnexion;
import gpi.exception.ConnexionBDException;

public class MaintenanceDAO {
	/**
	 * Permet d'inserer une maintenance dans la BD
	 * @param maintenance la maintenance a inserer dans la BD
	 */
	public void ajouterMaintenance(Maintenance maintenance) throws ConnexionBDException
	{
		Connection connexion=MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement ps=connexion.prepareStatement("INSERT INTO MAINTENANCE (dateMaintenance,objetMaintenance,descriptionMaintenance,coutMaintenance) "
					+ "VALUES (?,?,?,?)");
			ps.setString(1,maintenance.getdateMaintenanceStringProperty().getValue());
			ps.setString(2,maintenance.getObjetMaintenance());
			ps.setString(3,maintenance.getDescriptionMaintenance());
			ps.setFloat(4,maintenance.getCoutMaintenance());
			ps.executeUpdate();
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
	 * Recupere toutes les maintenance
	 * @return la liste contenant toutes les maintenances de la BD
	 * @throws ConnexionBDException si la bd n'est pas accessible
	 */
	public List<Maintenance> recupererAllMaintenance() throws ConnexionBDException
	{
		List<Maintenance> listeARetourner=new ArrayList<Maintenance>();
		Connection connexion=MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement ps=connexion.prepareStatement("SELECT * FROM MAINTENANCE");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				listeARetourner.add(new Maintenance(rs.getInt("idMaintenance"),
						LocalDate.parse(rs.getString("dateMaintenance")),
						rs.getString("objetMaintenance"),
						rs.getString("descriptionMaintenance"),
						rs.getFloat("coutMaintenance")
						));
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
		return listeARetourner;

	}

	/**
	 * Retourne un maintenance par son id
	 * @param idMaintenance
	 * @return la maintenance correspondant � l'id maintenance
	 * @throws ConnexionBDException si un probl�me de connexion a la bd survient
	 */
	public Maintenance recupererMaintenanceParId(int idMaintenance) throws ConnexionBDException {
		Connection connexion=MaConnexion.getInstance().getConnexion();
		Maintenance maintenance=null;
		PreparedStatement ps;
		try {
			ps = connexion.prepareStatement("SELECT * FROM MAINTENANCE WHERE idMaintenance=?");
			ps.setInt(1, idMaintenance);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				maintenance=new Maintenance(rs.getInt("idMaintenance"),
						LocalDate.parse(rs.getString("dateMaintenance")),
						rs.getString("objetMaintenance"),
						rs.getString("descriptionMaintenance"),
						rs.getFloat("coutMaintenance"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return maintenance;
	}
	
	/**
	 * R�cupere une maintenance par objet
	 * @param objetMaintenance
	 * @return une liste de maintenance correspondant a l'objet
	 * @throws ConnexionBDException
	 */
	public List<Maintenance> recupererMaintenancesParObjet(String objetMaintenance) throws ConnexionBDException
	{
		List<Maintenance> listeMaintenance=new ArrayList<Maintenance>();
		Connection connexion=MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement ps=connexion.prepareStatement("SELECT * FROM MAINTENANCE WHERE objetMaintenance=?");
			ps.setString(1,objetMaintenance);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				listeMaintenance.add(new Maintenance(rs.getInt("idMaintenance"),
						LocalDate.parse(rs.getString("dateMaintenance")),
						rs.getString("objetMaintenance"),
						rs.getString("descriptionMaintenance"),
						rs.getFloat("coutMaintenance")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		return listeMaintenance;
	}
	
	/**
	 * Modifie une maintenance
	 * @param maintenance la maintenance � modifier
	 * @throws ConnexionBDException si l'acc�s � la bd a �chou�
	 */
	public void modifierMaintenance(Maintenance maintenance) throws ConnexionBDException
	{
		Connection connexion=MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement ps=connexion.prepareStatement("UPDATE MAINTENANCE SET "
					+ "dateMaintenance=?,"
					+ "objetMaintenance=?,"
					+ "descriptionMaintenance=?,"
					+ "coutMaintenance=? WHERE idMaintenance=?");
			ps.setString(1,maintenance.getdateMaintenanceStringProperty().getValue());
			ps.setString(2, maintenance.getObjetMaintenance());
			ps.setString(3,maintenance.getDescriptionMaintenance());
			ps.setFloat(4,maintenance.getCoutMaintenance());
			ps.setInt(5, maintenance.getIdMaintenance().getValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
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
	 * Supprime une maintenance
	 * @param maintenance la maintenance � supprimer
	 * @throws ConnexionBDException si la connexion � la BD a �chou�
	 */
	public void supprimerMaintenance(Maintenance maintenance)throws ConnexionBDException
	{
		Connection connexion=MaConnexion.getInstance().getConnexion();
		PreparedStatement ps;
		try {
			ps = connexion.prepareStatement("DELETE FROM MAINTENANCE WHERE idMaintenance=?");
			ps.setInt(1, maintenance.getIdMaintenance().getValue());
			ps.executeUpdate();
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
}
