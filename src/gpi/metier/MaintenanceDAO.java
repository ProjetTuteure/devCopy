package gpi.metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DateConverter;
import utils.MaConnexion;
import utils.Popup;
import gpi.exception.ConnexionBDException;

public class MaintenanceDAO {
	private Connection connexion;
	/**
	 * Permet d'inserer une maintenance dans la BD
	 * @param maintenance la maintenance a inserer dans la BD
	 */
	public int ajouterMaintenance(Maintenance maintenance) throws ConnexionBDException
	{
		int idMaintenance=0;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement ps=connexion.prepareStatement("INSERT INTO MAINTENANCE (dateMaintenance,objetMaintenance,descriptionMaintenance,coutMaintenance) "
					+ "VALUES (?,?,?,?)");
			ps.setString(1,maintenance.getdateMaintenanceStringProperty().getValue());
			ps.setString(2,maintenance.getObjetMaintenance());
			ps.setString(3,maintenance.getDescriptionMaintenance());
			ps.setFloat(4,maintenance.getCoutMaintenance());
			ps.executeUpdate();
			PreparedStatement preparedStatement = connexion.prepareStatement("SELECT IDENT_CURRENT('maintenance');");
			ResultSet resultat = preparedStatement.executeQuery();
			resultat.next();
			idMaintenance=resultat.getInt(1);
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
		return idMaintenance;
	}
	
	/**
	 * Recupere toutes les maintenance
	 * @return la liste contenant toutes les maintenances de la BD
	 * @throws ConnexionBDException si la bd n'est pas accessible
	 */
	public List<Maintenance> recupererAllMaintenance() throws ConnexionBDException
	{
		List<Maintenance> listeARetourner=new ArrayList<Maintenance>();
		try {
			connexion = MaConnexion.getInstance().getConnexion();
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
		PreparedStatement ps;
		Maintenance maintenance=null;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
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
		try {
			connexion = MaConnexion.getInstance().getConnexion();
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
		try {
			connexion = MaConnexion.getInstance().getConnexion();
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
		PreparedStatement ps;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
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
	public ObservableList<String> recupererDateMaintenanceParObjet(String objetMaintenance) throws ConnexionBDException{
		ObservableList<String> listARetourner=FXCollections.observableArrayList();
		String dateMaintenanceARenvoyer;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement ps=connexion.prepareStatement("SELECT idMaintenance,dateMaintenance FROM MAINTENANCE where objetMaintenance=?");
			ps.setString(1,objetMaintenance);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				dateMaintenanceARenvoyer=rs.getString("idMaintenance")+"- "+DateConverter.getFrenchDate(rs.getString("dateMaintenance"));
				listARetourner.add(dateMaintenanceARenvoyer);
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
		return listARetourner;
	}
	
	public ObservableList<String> recupererAllObjetMaintenanceString() throws ConnexionBDException{
		ObservableList<String> listARetourner=FXCollections.observableArrayList();
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement ps=connexion.prepareStatement("SELECT DISTINCT objetMaintenance FROM MAINTENANCE");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				listARetourner.add(rs.getString("objetMaintenance"));
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
		return listARetourner;
	}
	
	/**
	 * Récupère les objets de maintenance contenues dans la table estIntervenu
	 * @return
	 * @throws ConnexionBDException
	 */
	public ObservableList<String> recupererAllObjetMaintenanceParEstIntervenuString() throws ConnexionBDException{
		ObservableList<String> listARetourner=FXCollections.observableArrayList();
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement ps=connexion.prepareStatement("SELECT DISTINCT objetMaintenance FROM MAINTENANCE M JOIN ESTINTERVENU EI ON "
					+ "M.idMaintenance=EI.idMaintenance");
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				listARetourner.add(rs.getString("objetMaintenance"));
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
		return listARetourner;
	}
	
	/**
	 * Récupère la date maintenance correspondant à l'objet maintenance passé en paramètre.
	 * La date maintenance qui correspond à une maintenance qui est dans la table estIntervenu
	 * @param objetMaintenance
	 * @return
	 * @throws ConnexionBDException
	 */
	public ObservableList<String> recupererDateMaintenanceParObjetParEstIntervenu(String objetMaintenance) throws ConnexionBDException{
		ObservableList<String> listARetourner=FXCollections.observableArrayList();
		String dateMaintenanceARenvoyer;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement ps=connexion.prepareStatement("SELECT M.idMaintenance,M.dateMaintenance FROM MAINTENANCE M "
					+ "JOIN ESTINTERVENU EI ON M.idMaintenance=EI.idMaintenance WHERE M.objetMaintenance=?");
			ps.setString(1,objetMaintenance);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				dateMaintenanceARenvoyer=rs.getString("idMaintenance")+"- "+DateConverter.getFrenchDate(rs.getString("dateMaintenance"));
				listARetourner.add(dateMaintenanceARenvoyer);
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
		return listARetourner;
	}
	
	public String recupererObjetMaintenanceParId(int idMaintenance) throws ConnexionBDException {
		ResultSet resultat;
		String objetMaintenance="";
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement Preparedstatement = connexion.prepareStatement("SELECT objetMaintenance FROM MAINTENANCE WHERE idMaintenance=?");
			Preparedstatement.setInt(1, idMaintenance);
			resultat = Preparedstatement.executeQuery();
			resultat.next();
			objetMaintenance=resultat.getString("objetMaintenance");
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
		return objetMaintenance;
	}
}
