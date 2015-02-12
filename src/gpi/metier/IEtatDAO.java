package gpi.metier;

import gpi.exception.ConnexionBDException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.MaConnexion;

public class IEtatDAO {
	public List<IEtat> recupererIEtat(List<Etat> etats) throws ConnexionBDException {
		Connection connexion = MaConnexion.getInstance().getConnexion();
		List<IEtat> listIEtat = new ArrayList<IEtat>();
		String sql;
		try {
			sql="SELECT idMateriel, nomMateriel, etat, nomSite,dateModifierEtat FROM MATERIEL m JOIN SITE s on m.idSite=s.idSite WHERE ";
			for(Etat etat : etats){
				sql+="etat='"+etat.name()+"' OR ";
			}
			sql+="1=2";
			PreparedStatement prep = connexion.prepareStatement(sql);	
			ResultSet resultat = prep.executeQuery();
			while (resultat.next()) {
				listIEtat.add(new IEtat(resultat.getString("idMateriel"),
						resultat.getString("nomMateriel"),resultat.getString("etat"),
						resultat.getString("nomSite"),resultat.getString("dateModifierEtat")));
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
		return listIEtat;
	}
}
