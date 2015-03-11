package gpi.metier;

import gpi.exception.ConnexionBDException;
import gpi.exception.PrimaryKeyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utils.MaConnexion;

public class EstIntervenuDAO {
	public EstIntervenuDAO(){}

	public int ajouterEstIntervenu(int idMaintenance,int idPrestataire, int idFacture) throws ConnexionBDException, PrimaryKeyException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		int resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion
					.prepareStatement("INSERT INTO ESTINTERVENU(idFacture, idPrestataire, idMaintenance)  VALUES (?,?,?);");
				prep.setInt(1,idFacture);
			prep.setInt(2, idPrestataire);
			prep.setInt(3, idMaintenance);
			resultat = prep.executeUpdate();
			return resultat;
		} catch (SQLException e) {
			if(e.getMessage().contains("PRIMARY KEY")){
        		throw new PrimaryKeyException("Cette facture, et ce prestataire ont déjà été ajoutée sur cette maintenance");
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

	public int supprimerUtilise(int idMaintenance, int idPrestataire, int idFacture) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		int resultat;
		try {
			connexion = MaConnexion.getInstance().getConnexion();
			PreparedStatement prep = connexion
					.prepareStatement("DELETE FROM ESTINTERVENU WHERE idFacture=? AND idMaintenance=? AND idPrestataire=?;");
			prep.setInt(1,idFacture);
			prep.setInt(2, idMaintenance);
			prep.setInt(3,idPrestataire);
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
