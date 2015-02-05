package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.MaConnexion;

public class ComposeDAO {
	private Connection connection;
	
	public int ajouterCompose(Compose compose) throws ConnexionBDException {
		int nombreLigneAffectee = 0;
		try {
			connection = MaConnexion.getInstance().getConnexion();
			PreparedStatement prepareStatement = connection
					.prepareStatement("INSERT INTO Compose (idComposant,idMateriel VALUES(?,?)");
			prepareStatement.setString(1, compose.getIdComposant());
			prepareStatement.setString(2, compose.getIdMateriel());
			nombreLigneAffectee = prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
	 * Permet de supprimer une composition
	 * 	 * 
	 * @param compose
	 *            la composition a supprimer
	 */
	public void supprimerCompose(Compose compose)
			throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		try {
			PreparedStatement preparedStatement = connexion
					.prepareStatement("DELETE FROM COMPOSE WHERE idMateriel=? AND idComposant=?");
			preparedStatement.setString(1, compose.getIdMateriel());
			preparedStatement.setString(2, compose.getIdComposant());
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
	
	public List<Composant> recupererComposantsParMateriel(int idMateriel) throws ConnexionBDException{
        ResultSet resultat;
        List<Composant> composantList=new ArrayList<Composant>();
        try{
            connection=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT idComposant FROM COMPOSE WHERE idMateriel=?;");
            preparedStatement.setInt(1, idMateriel);
            resultat=preparedStatement.executeQuery();
            ComposantDAO composantDAO = new ComposantDAO();
            Composant composant;
            while(resultat.next()){
            	composant=composantDAO.recupererComposantParId(resultat.getInt("idComposant"));
                composantList.add(composant);
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
        return composantList;
    }
}
