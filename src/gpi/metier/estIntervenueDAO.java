package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.MaConnexion;

public class estIntervenueDAO {
	public estIntervenueDAO(){}

	public int ajouterEstIntervenu(estIntervenu estIntervenu) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		int resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion
					.prepareStatement("INSERT INTO ESTINTERVENU(idFacture, idPrestataire, idMaintenance)  VALUES (?,?,?);");
				prep.setInt(1,estIntervenu.getFactureEstIntervenu().getIdFacture().get());
			prep.setInt(2, estIntervenu.getPrestataireEstIntervenu().getIdPrestataire().get());
			prep.setInt(2, estIntervenu.getMaintenanceEstIntervenu().getIdMaintenance().get());
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

	public int supprimerUtlise(estIntervenu estIntervenu) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		int resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion
					.prepareStatement("DELETE FROM ESTINTERVENU WHERE idFacture=? And idMaintenance=? AND idPrestataire;");
			prep.setInt(1,estIntervenu.getFactureEstIntervenu().getIdFacture().getValue());
			prep.setInt(2, estIntervenu.getMaintenanceEstIntervenu().getIdMaintenance().getValue());
			prep.setInt(3, estIntervenu.getPrestataireEstIntervenu().getIdPrestataire().getValue());
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
