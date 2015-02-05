package gpi.metier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by thibault on 22/11/14.
 */
public class Utilise {
	private LocalDate dateUtilise;
    private Utilisateur utilisateurUtilise;
    private Materiel materielUtilise;
    /**
     * Constructeur de la classe
     * @param utilisateurUtilise
     * @param materielUtilise
     */
    public Utilise(LocalDate dateUtilise, Utilisateur utilisateurUtilise, Materiel materielUtilise) {
    	this.dateUtilise = dateUtilise;
        this.utilisateurUtilise = utilisateurUtilise;	
        this.materielUtilise = materielUtilise;
    }
    
    public LocalDate getDateUtilise() {
		return dateUtilise;
	}

	public void setDateUtilise(LocalDate dateUtilise) {
		this.dateUtilise = dateUtilise;
	}

	/**
     * Getter de UtilisateurUtilise
     * @return
     */
    public Utilisateur getUtilisateurUtilise() {
        return utilisateurUtilise;
    }

    /**
     * Setter de UtilisateurUtilise
     * @param utilisateurUtilise
     */
    public void setUtilisateurUtilise(Utilisateur utilisateurUtilise) {
        this.utilisateurUtilise = utilisateurUtilise;
    }
    
    /**
     * Getter de Materielutilise
     * @return
     */
    public Materiel getMaterielUtilise() {
        return materielUtilise;
    }

    /**
     * Setter de MaterielUtilise
     * @param materielUtilise
     */
    public void setMaterielUtilise(Materiel materielUtilise) {
        this.materielUtilise = materielUtilise;
    }
    
    public StringProperty getDateUtiliseStringProperty() {
        String chaine1="";
        String chaine2="";
        if(dateUtilise.getDayOfMonth()<10){
            chaine1="0";
        }
        if(dateUtilise.getMonthValue()<10){
            chaine2="0";
        }
        String dateUtilise=chaine1+this.dateUtilise.getDayOfMonth()+"/"+chaine2+this.dateUtilise.getMonthValue()+"/"+this.dateUtilise.getYear();
        return new SimpleStringProperty(dateUtilise);
    }
}
