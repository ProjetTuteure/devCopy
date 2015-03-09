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
					.prepareStatement("INSERT INTO Compose (idComposant,idMateriel) VALUES(?,?)");
			prepareStatement.setInt(1, compose.getComposant().getIdComposant());
			prepareStatement.setInt(2, compose.getMateriel().getIdMateriel().getValue());
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
			PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM COMPOSE WHERE idMateriel=? AND idComposant=?");
			preparedStatement.setInt(1, compose.getMateriel().getIdMateriel().getValue());
			preparedStatement.setInt(2, compose.getComposant().getIdComposant());			
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
	
	public List<Materiel> recupererMaterielParComposant(int idComposant) throws ConnexionBDException{
        ResultSet resultat;
        List<Materiel> materielList=new ArrayList<Materiel>();
        try{
            connection=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT idMateriel FROM COMPOSE WHERE idComposant=?;");
            preparedStatement.setInt(1, idComposant);
            resultat=preparedStatement.executeQuery();
            MaterielDAO materielDAO = new MaterielDAO();
            Materiel materiel;
            while(resultat.next()){
            	materiel=materielDAO.recupererMaterielParId(resultat.getInt("idMateriel"));
            	materielList.add(materiel);
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
        return materielList;
    }
	
	
	public List<Compose> recupererAllCompose() throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<Compose> listCompose = new ArrayList<Compose>();
		MaterielDAO materielDAO=new MaterielDAO();
		ComposantDAO composantDAO=new ComposantDAO();
		try {
			PreparedStatement prep = connexion.prepareStatement("SELECT * from COMPOSE");
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listCompose.add(new Compose(composantDAO.recupererComposantParId(resultat.getInt("idComposant")),materielDAO.recupererMaterielParId(resultat.getInt("idMateriel"))));
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
		return listCompose;
	}
}
