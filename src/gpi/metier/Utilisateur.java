package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by thibault on 22/11/14.
 */
public class Utilisateur {
    private IntegerProperty idUtilisateur;
    private StringProperty nomUtilisateur;
    private StringProperty prenomUtilisateur;
    private StringProperty telUtilisateur;

    /**
     * Constructeur de utilisateur
     * @param idUtilisateur
     * @param nomUtilisateur
     * @param prenomUtilisateur
     * @param telUtilisateur
     */
    public Utilisateur(IntegerProperty idUtilisateur, String nomUtilisateur, String prenomUtilisateur, String telUtilisateur) {
        this.idUtilisateur = idUtilisateur;
        this.nomUtilisateur = new SimpleStringProperty(nomUtilisateur);
        this.prenomUtilisateur = new SimpleStringProperty(prenomUtilisateur);
        this.telUtilisateur = new SimpleStringProperty(telUtilisateur);
    }
    /**
     * Getter de l'idUtilisateur
     * @return
     */
    public IntegerProperty getIdUtilisateur() {
        return idUtilisateur;
    }
    
    /**
     * Setter de l'idUtilisateur
     * @param idUtilisateur
     */
    public void setIdUtilisateur(IntegerProperty idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    /**
     * Getter de NomUtilisateur
     * @return le Nom de l'utilisateur
     */
    public StringProperty getNomUtilisateur() {
        return nomUtilisateur;
    }
    
    /**
     * Setter de NomUtilisateur
     * @param nomUtilisateur
     */
    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur.setValue(nomUtilisateur);
    }
    
    /**
     * Getter de PrenomUtilisateur
     * @return le prenom de l'utilisateur
     */
    public StringProperty getPrenomUtilisateur() {
        return prenomUtilisateur;
    }
    
    /**
     * Setter de PrenomUtilisateur
     * @param prenomUtilisateur
     */
    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur.setValue(prenomUtilisateur);
    }

    /**
     * Getter de telUtilisateur
     * @return le numéro de telephone de l'utilisateur
     */
    public StringProperty getTelUtilisateur() {
        return telUtilisateur;
    }

    /**
     * Setter de telUtilisateur
     * @param telUtilisateur
     */
    public void setTelUtilisateur(String telUtilisateur) {
        this.telUtilisateur.setValue(telUtilisateur);
    }
}
