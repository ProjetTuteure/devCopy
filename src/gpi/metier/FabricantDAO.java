package gpi.metier;

import gpi.exception.ConnexionBDException;
import javafx.beans.property.SimpleIntegerProperty;
import utils.MaConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julien on 14/01/2015.
 */
public class FabricantDAO {
    private Connection connexion;

    public int ajouterFabricant(Fabricant fabricant) throws ConnexionBDException{
        int nombreLigneAffectee=0;
        try {
            connexion= MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO FABRICANT (nomFabricant,telFabricant,mobileFabricant,faxFabricant,emailFabricant,adresseFabricant) VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1,fabricant.getNomFabricantString());
            preparedStatement.setString(2,fabricant.getTelFabricant().getValue());
            preparedStatement.setString(3,fabricant.getMobileFabricant().getValue());
            preparedStatement.setString(4,fabricant.getFaxFabricant().getValue());
            preparedStatement.setString(5,fabricant.getEmailFabricant().getValue());
            preparedStatement.setString(6,fabricant.getAdresseFabricant().getValue());

            nombreLigneAffectee=preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return nombreLigneAffectee;
    }

    public int modifierFabricant(Fabricant fabricant) throws ConnexionBDException{
        int nombreLigneAffectee=0;
        try {
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement =connexion.prepareStatement("UPDATE FABRICANT SET nomFabricant=?,telFabricant=?,mobileFabricant=?,faxFabricant=?,emailFabricant=?,adresseFabricant=? WHERE idFabricant=?");
            preparedStatement.setString(1,fabricant.getNomFabricantString());
            preparedStatement.setString(2,fabricant.getTelFabricant().getValue());
            preparedStatement.setString(3,fabricant.getMobileFabricant().getValue());
            preparedStatement.setString(4,fabricant.getFaxFabricant().getValue());
            preparedStatement.setString(5,fabricant.getEmailFabricant().getValue());
            preparedStatement.setString(6,fabricant.getAdresseFabricant().getValue());
            preparedStatement.setInt(7, fabricant.getIdFabricant().getValue());
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

    public int supprimerFabricant(Fabricant fabricant) throws ConnexionBDException{
        int nombreLigneAffectee=0;
        try{
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM FABRICANT WHERE idFabricant=?;");

            preparedStatement.setInt(1, fabricant.getIdFabricant().getValue());

            nombreLigneAffectee=preparedStatement.executeUpdate();
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
        return nombreLigneAffectee;
    }

    public List<Fabricant> recupererAllFabricant() throws ConnexionBDException{
        List<Fabricant> fabricantList= new ArrayList<Fabricant>();
        ResultSet resultat;
        try{
            connexion= MaConnexion.getInstance().getConnexion();
            Statement statement = connexion.createStatement();
            resultat=statement.executeQuery("SELECT * FROM FABRICANT");
            while(resultat.next()){
                fabricantList.add(new Fabricant(resultat.getInt("idFabricant"),resultat.getString("nomFabricant"),resultat.getString("telFabricant"),resultat.getString("mobileFabricant"),resultat.getString("faxFabricant"),resultat.getString("emailFabricant"),resultat.getString("adresseFabricant")));
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
        return fabricantList;
    }

    public Fabricant recupererFabricantParId(int idFabricant) throws ConnexionBDException{
        ResultSet resultat;
        Fabricant fabricant=null;
        try{
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM FABRICANT WHERE idFabricant=?;");
            preparedStatement.setInt(1, idFabricant);
            resultat=preparedStatement.executeQuery();
            resultat.next();
            fabricant=new Fabricant(idFabricant,resultat.getString("nomFabricant"),resultat.getString("telFabricant"),resultat.getString("mobileFabricant"),resultat.getString("faxFabricant"),resultat.getString("emailFabricant"),resultat.getString("adresseFabricant"));
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
        return fabricant;
    }

}
