package gpi.metier;

import gpi.exception.ConnexionBDException;
import utils.MaConnexion;

import java.sql.*;
import java.util.*;

/**
 * Created by Julien on 13/01/2015.
 */
public class TypeDAO {

    private Connection connexion;

    public int ajouterType(Type type) throws ConnexionBDException {
        int nombreLigneAffectee=0;
        try {
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO TYPE (nomType, cheminImageType) VALUES(?,?)");

            preparedStatement.setString(1,type.getNomTypeString());
            preparedStatement.setString(2,type.getCheminImageType().getValue());

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

    public int modifierType(Type type) throws ConnexionBDException {
        int nombreLigneAffectee=0;
        try {
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement =connexion.prepareStatement("UPDATE TYPE SET nomType=?,cheminImageType=? WHERE idType=?");
            preparedStatement.setString(1, type.getNomTypeString());
            preparedStatement.setString(2, type.getCheminImageType().getValue());
            preparedStatement.setInt(3,type.getIdType());
            
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

    public int supprimerType(Type type) throws ConnexionBDException {
        int nombreLigneAffectee=0;
        try{
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("DELETE FROM TYPE WHERE idType=?;");

            preparedStatement.setInt(1, type.getIdType());

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

    public List<Type> recupererAllType() throws ConnexionBDException {
        List<Type> typeList= new ArrayList<Type>();
        ResultSet resultat;
        try{
            connexion= MaConnexion.getInstance().getConnexion();
            Statement statement = connexion.createStatement();
            resultat=statement.executeQuery("SELECT * FROM TYPE");
            while(resultat.next()){
                typeList.add(new Type(resultat.getInt("idType"),resultat.getString("nomType"),resultat.getString("cheminImageType")));
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
        return typeList;
    }

    public Type recupererTypeParId(int idType) throws ConnexionBDException {
        ResultSet resultat;
        Type type=null;
        try{
            connexion=MaConnexion.getInstance().getConnexion();
            PreparedStatement preparedStatement = connexion.prepareStatement("SELECT * FROM TYPE WHERE idType=?;");
            preparedStatement.setInt(1, idType);
            resultat=preparedStatement.executeQuery();
            resultat.next();
            type=new Type(idType,resultat.getString("nomType"),resultat.getString("cheminImageType"));
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
        return type;
    }



}
