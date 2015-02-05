package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by thibault on 22/11/14.
 */
public class Revendeur {
	private IntegerProperty idRevendeur;
    private StringProperty nomRevendeur;
    private StringProperty telRevendeur;
    private StringProperty adresseRevendeur;
    private StringProperty mobileRevendeur;
    private StringProperty faxRevendeur;
    private StringProperty emailRevendeur;

    /**
     * Construit un revendeur
     * @param idRevendeur l'id du revendeur
     * @param nomRevendeur le nom du revendeur
     * @param telRevendeur le telephone du revendeur
     * @param adresseRevendeur l'adresse du revendeur
     * @param mobileRevendeur le mobile du revendeur
     * @param faxRevendeur le fax du revendeur
     * @param emailRevendeur l'email du revendeur
     */
    public Revendeur(IntegerProperty idRevendeur, String nomRevendeur, String telRevendeur,String mobileRevendeur,String faxRevendeur,String emailRevendeur,String adresseRevendeur) 
    {
        this.idRevendeur = idRevendeur;
        this.nomRevendeur= new SimpleStringProperty(nomRevendeur);
        this.telRevendeur = new SimpleStringProperty(telRevendeur);
        this.adresseRevendeur = new SimpleStringProperty(adresseRevendeur);
        this.mobileRevendeur=new SimpleStringProperty(mobileRevendeur);
        this.faxRevendeur=new SimpleStringProperty(faxRevendeur);
        this.emailRevendeur=new SimpleStringProperty(emailRevendeur);
    }

    /**
     * Retourne l'idRevendeur du revendeur
     * @return idRevendeur l'id du revendeur
     */
    public IntegerProperty getIdRevendeur() {
        return idRevendeur;
    }

    /**
     * setter de l'idRevendeur
     * @param idRevendeur l'idRevendeur à setter
     */
    public void setIdRevendeur(IntegerProperty idRevendeur) {
        this.idRevendeur = idRevendeur;
    }

    /**
     * Retourne le nom du revendeur
     * @return le nom du revendeur
     */
    public StringProperty getNomRevendeur() {
        return this.nomRevendeur;
    }

    /**
     * setter du nom du revendeur
     * @param nomRevendeur le nom du revendeur à setter
     */
    public void setNomRevendeur(String nomRevendeur) {
        this.nomRevendeur.set(nomRevendeur);
    }

    /**
     * Retourne le téléphone du revendeur
     * @return telRevendeur le téléphone du revendeur
     */
    public StringProperty getTelRevendeur() {
        return telRevendeur;
    }

    /**
     * Setter du téléphone du revendeur
     * @param telRevendeur le téléphone du revendeur à setter
     */
    public void setTelRevendeur(String telRevendeur) {
        this.telRevendeur.setValue(telRevendeur);
    }

    /**
     * Retourne l'adresse du revendeur
     * @return adresseRevendeur l'adresse du revendeur
     */
    public StringProperty getAdresseRevendeur() {
        return adresseRevendeur;
    }

    /**
     * Setter de l'adresse du revendeur
     * @param adresseRevendeur l'adresse du revendeur à setter
     */
    public void setAdresseRevendeur(String adresseRevendeur) {
        this.adresseRevendeur.setValue(adresseRevendeur);
    }
    
    /**
     * Retourne le numéro de mobile du revendeur
     * @return le numéro de mobile du revendeur
     */
    public StringProperty getMobileRevendeur()
    {
    	return this.mobileRevendeur;
    }
    
    /**
     * Setter du mobile du revendeur
     * @param mobileRevendeur le mobile du revendeur à setter
     */
    public void setMobileRevendeur(String mobileRevendeur)
    {
    	this.mobileRevendeur.setValue(mobileRevendeur);
    }
    
    /**
     * retourne le fax du revendeur
     * @return le fax du revendeur
     */
    public StringProperty getFaxRevendeur()
    {
    	return this.faxRevendeur;
    }
    
    /**
     * Setter du fax du revendeur
     * @param faxRevendeur le numéro de fax du revendeur à setter
     */
    public void setFaxRevendeur(String faxRevendeur)
    {
    	this.faxRevendeur.setValue(faxRevendeur);
    }
    /**
     * Setter du mail revendeur
     * @param emailRevendeur l'email du revendeur à setter
     */
    public void setEmailRevendeur(String emailRevendeur)
    {
    	this.emailRevendeur.setValue(emailRevendeur);
    }
    
    /**
     * Retourne le mail du revendeur
     * @return le mail du revendeur
     */
    public StringProperty getEmailRevendeur()
    {
    	return this.emailRevendeur;
    }
    
    @Override
   	public String toString() {
   		return "Revendeur ,"+ "nomRevendeur="
   				+ nomRevendeur.getValue() + ", telRevendeur=" + telRevendeur.getValue()
   				+ ", adresseRevendeur=" + adresseRevendeur.getValue()
   				+ ", mobileRevendeur=" + mobileRevendeur.getValue() + ", faxRevendeur="
   				+ faxRevendeur.getValue() + ", emailRevendeur=" + emailRevendeur.getValue() + "]";
   	}
}
