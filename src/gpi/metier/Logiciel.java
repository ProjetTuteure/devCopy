package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by thibault on 22/11/14.
 */
public class Logiciel {
    private Integer idLogiciel;
    private String nomLogiciel;
    private String versionLogiciel;
    private LocalDate dateExpirationLogiciel;
    private Facture factureLogiciel;

    public Logiciel(int idLogiciel, String nomLogiciel, String versionLogiciel, LocalDate dateExpirationLogiciel, Facture factureLogiciel) {
        this.idLogiciel = idLogiciel;
        this.nomLogiciel = nomLogiciel;
        this.versionLogiciel = versionLogiciel;
        this.dateExpirationLogiciel = dateExpirationLogiciel;
        this.factureLogiciel = factureLogiciel;
    }

    public Integer getIdLogiciel() {
        return idLogiciel;
    }

    public void setIdLogiciel(Integer idLogiciel) {
        this.idLogiciel = idLogiciel;
    }
    
    public String getNomLogiciel() {
    	System.out.println(nomLogiciel);
        return nomLogiciel;
    }

    public void setNomLogiciel(String nomLogiciel) {
        this.nomLogiciel=nomLogiciel;
    }

    public String getVersionLogiciel() {
        return versionLogiciel;
    }

    public void setVersionLogiciel(String versionLogiciel) {
        this.versionLogiciel=versionLogiciel;
    }

    public LocalDate getDateExpirationLogiciel() {
        return dateExpirationLogiciel;
    }

    public StringProperty getDateExpirationLogicielStringProperty() {
        String chaine1="";
        String chaine2="";
        if(dateExpirationLogiciel.getDayOfMonth()<10){
            chaine1="0";
        }
        if(dateExpirationLogiciel.getMonthValue()<10){
            chaine2="0";
        }
        String dateFacture=chaine1+this.dateExpirationLogiciel.getDayOfMonth()+"/"+chaine2+this.dateExpirationLogiciel.getMonthValue()+"/"+this.dateExpirationLogiciel.getYear();
        return new SimpleStringProperty(dateFacture);
    }

    public void setDateExpirationLogiciel(LocalDate dateExpiration) {
        this.dateExpirationLogiciel=dateExpiration;
    }

    public Facture getFactureLogiciel() {
        return factureLogiciel;
    }

    public void setFactureLogiciel(Facture factureLogiciel) {
        this.factureLogiciel = factureLogiciel;
    }
}
