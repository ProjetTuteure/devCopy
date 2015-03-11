package gpi.metier;

import gpi.exception.ConnexionBDException;
import gpi.exception.PrimaryKeyException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.MaConnexion;

public class EstInstalleDAO {
	private Connection connection;
	
	public int ajouterEstInstalle(EstInstalle estInstalle) throws ConnexionBDException, PrimaryKeyException {
		int nombreLigneAffectee = 0;
		try {
			connection = MaConnexion.getInstance().getConnexion();
			PreparedStatement prepareStatement = connection
					.prepareStatement("INSERT INTO ESTINSTALLE (idLogiciel,idMateriel) VALUES(?,?)");
			prepareStatement.setString(1, estInstalle.getIdLogiciel());
			prepareStatement.setString(2, estInstalle.getIdMateriel());
			nombreLigneAffectee = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			if(e.getMessage().contains("PRIMARY KEY")){
        		throw new PrimaryKeyException("Ce logiciel a déjà été ajoutée sur ce matériel");
        	}else{
        		e.printStackTrace();
        	}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return nombreLigneAffectee;
	}

	
	/**
	 * Permet de supprimer une installation de logiciel
	 * 	 * 
	 * @param estInstalle
	 *            l'installation a supprimer
	 */
	public void supprimerEstInstalle(EstInstalle estInstalle)
			throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement preparedStatement = connexion
					.prepareStatement("DELETE FROM ESTINSTALLE WHERE idMateriel=? AND idLogiciel=?");
			preparedStatement.setString(1, estInstalle.getIdMateriel());
			preparedStatement.setString(2, estInstalle.getIdLogiciel());
			preparedStatement.executeUpdate();
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Logiciel> recupererLogicielsParMateriel(int idMateriel) throws ConnexionBDException{
        ResultSet resultat;
        List<Logiciel> logicielList=new ArrayList<Logiciel>();
        try{
            connection=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT idLogiciel FROM ESTINSTALLE WHERE idMateriel=?;");
            preparedStatement.setInt(1, idMateriel);
            resultat=preparedStatement.executeQuery();
            LogicielDAO logicielDAO = new LogicielDAO();
            Logiciel logiciel;
            while(resultat.next()){
            	logiciel=logicielDAO.recupererLogicielParId(resultat.getInt("idLogiciel"));
                logicielList.add(logiciel);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return logicielList;
    }
}

