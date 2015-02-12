package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.MaConnexion;

public class EstIntervenuDAO {
	public EstIntervenuDAO(){}

	public int ajouterEstIntervenu(EstIntervenu EstIntervenu) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		int resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion
					.prepareStatement("INSERT INTO ESTINTERVENU(idFacture, idPrestataire, idMaintenance)  VALUES (?,?,?);");
				prep.setInt(1,EstIntervenu.getFactureEstIntervenu().getIdFacture().get());
			prep.setInt(2, EstIntervenu.getPrestataireEstIntervenu().getIdPrestataire().get());
			prep.setInt(2, EstIntervenu.getMaintenanceEstIntervenu().getIdMaintenance().get());
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

	public int supprimerUtlise(EstIntervenu EstIntervenu) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		int resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion
					.prepareStatement("DELETE FROM ESTINTERVENU WHERE idFacture=? And idMaintenance=? AND idPrestataire;");
			prep.setInt(1,EstIntervenu.getFactureEstIntervenu().getIdFacture().getValue());
			prep.setInt(2, EstIntervenu.getMaintenanceEstIntervenu().getIdMaintenance().getValue());
			prep.setInt(3, EstIntervenu.getPrestataireEstIntervenu().getIdPrestataire().getValue());
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
}