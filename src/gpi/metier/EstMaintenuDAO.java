package gpi.metier;

import gpi.exception.ConnexionBDException;
import utils.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julien on 15/01/2015.
 */
public class EstMaintenuDAO {
    private Connection connexion;
    public int ajouterEstMaintenu(EstMaintenu estMaintenu) throws ConnexionBDException {
        int nombreLigneAffectee=0;
        try {
            connexion= MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO ESTMAINTENU (idMaintenance,idMateriel) VALUES(?,?)");
            preparedStatement.setInt(1,estMaintenu.getIdMaintenanceEstMaintenu());
            preparedStatement.setInt(2,estMaintenu.getIdMaterielEstMaintenu());

            nombreLigneAffectee=preparedStatement.executeUpdate();
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

    public int supprimerEstMaintenu(EstMaintenu estMaintenu) throws ConnexionBDException{
        int nombreLigneAffectee=0;
        try{
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM ESTMAINTENU WHERE idMaintenance=? AND idMateriel=?;");

            preparedStatement.setInt(1, estMaintenu.getIdMaintenanceEstMaintenu());
            preparedStatement.setInt(2, estMaintenu.getIdMaterielEstMaintenu());

            nombreLigneAffectee=preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
            	if (connexion != null){
					connexion.close();
				};
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nombreLigneAffectee;
    }

    public List<EstMaintenu> recupererAllEstMaintenu() throws ConnexionBDException{
        List<EstMaintenu> estMaintenuList= new ArrayList<EstMaintenu>();
        ResultSet resultat;
        try{
            connexion= MaConnexion.getInstance().getConnexion();
            Statement statement = connexion.createStatement();
            resultat=statement.executeQuery("SELECT * FROM ESTMAINTENU");
            while(resultat.next()){
                MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
                MaterielDAO materielDAO=new MaterielDAO();
                Maintenance maintenance=maintenanceDAO.recupererMaintenanceParId(resultat.getInt("idMaintenance"));
                Materiel materiel=materielDAO.recupererMaterielParId(resultat.getInt("idMateriel"));
                estMaintenuList.add(new EstMaintenu(maintenance,materiel));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
            	if (connexion != null){
					connexion.close();
				}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return estMaintenuList;
    }

    public EstMaintenu recupererEstMaintenuParId(int idMaintenance,int idMateriel) throws ConnexionBDException{
        ResultSet resultat;
        EstMaintenu estMaintenu=null;
        try{
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM ESTMAINTENU WHERE idMaintenance=? AND idMateriel=?;");
            preparedStatement.setInt(1, idMaintenance);
            preparedStatement.setInt(2, idMateriel);
            resultat=preparedStatement.executeQuery();
            resultat.next();
            MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
            MaterielDAO materielDAO=new MaterielDAO();
            Maintenance maintenance=maintenanceDAO.recupererMaintenanceParId(resultat.getInt("idMaintenance"));
            Materiel materiel=materielDAO.recupererMaterielParId(resultat.getInt("idMateriel"));
            estMaintenu=new EstMaintenu(maintenance,materiel);
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
            	if (connexion != null){
					connexion.close();
				}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return estMaintenu;
    }
    
    public List<Maintenance> recupererMaintenanceParMateriel(int idMateriel) throws ConnexionBDException{
        ResultSet resultat;
        List<Maintenance> maintenanceList=new ArrayList<Maintenance>();
        try{
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM ESTMAINTENU WHERE idMateriel=?;");
            preparedStatement.setInt(1, idMateriel);
            resultat=preparedStatement.executeQuery();
            MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
            while(resultat.next()){  	
                maintenanceList.add(maintenanceDAO.recupererMaintenanceParId(resultat.getInt("idMaintenance")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try {
            	if (connexion != null){
					connexion.close();
				}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return maintenanceList;
    }
}
