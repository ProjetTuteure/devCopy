package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MaConnexion;

public class RapportsDAO {

private Connection connexion;
	
	public RapportsDAO(){}
	
	public String[][] getRapportMaterielParc() throws ConnexionBDException{
		connexion = MaConnexion.getInstance().getConnexion();
		String rapport[][] = null;
		try{
			PreparedStatement ps=connexion.prepareStatement("SELECT COUNT(*) as nbLigne FROM MATERIEL;");
			ResultSet resultat = ps.executeQuery();
			resultat.next();
			int nbLignes=resultat.getInt("nbLigne");
			String requete="SELECT nomMateriel,numeroSerieMateriel,numImmobMateriel,nomType,etat,dateExpirationGarantieMateriel,nomSite FROM MATERIEL m"
				+ " JOIN TYPE t ON m.idType=t.idType"
				+ " JOIN SITE s ON m.idSite=s.idSite"
				+ " ORDER BY s.nomSite ASC";
			ps=connexion.prepareStatement(requete);
			resultat = ps.executeQuery();
			rapport=new String[nbLignes][7];
			int i=0;
			while(resultat.next()){
				rapport[i][0]=resultat.getString("nomMateriel");
				rapport[i][1]=resultat.getString("numeroSerieMateriel");
				rapport[i][2]=resultat.getString("numImmobMateriel");
				rapport[i][3]=resultat.getString("nomType");
				rapport[i][4]=resultat.getString("etat");
				rapport[i][5]=resultat.getString("dateExpirationGarantieMateriel");
				rapport[i][6]=resultat.getString("nomSite");
				i++;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (connexion != null){
					connexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rapport;
	}
}
