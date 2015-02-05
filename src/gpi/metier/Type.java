package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by thibault on 22/11/14.
 */
public class Type {
    private IntegerProperty idType;
    private StringProperty nomType;
    private StringProperty cheminImageType;

     /**
     * Constructeur de la classe Type
     * @param nomType
     * @param cheminImageType
     */
    public Type(int idType,String nomType, String cheminImageType) {
        this.idType =new SimpleIntegerProperty(idType);

        this.nomType = new SimpleStringProperty(nomType);
        this.cheminImageType = new SimpleStringProperty(cheminImageType);
    }
    
    /**
     * Getter du nom du type
     * @return le nom du type
     */
    public StringProperty getNomType() {
        return nomType;
    }
    
    /**
     * Set le nom du type
     * @param nomType
     */
    public void setNomType(String nomType) {
        this.nomType.setValue(nomType);
    }
    
    /**
     * Get le nom du type sous forme de string
     * @return le nom du type sous forme de string
     */
    public String getNomTypeString(){
    	return this.nomType.get();
    }

    /**
     * Get le chemin de l'image correspondant au type
     * @return le chemin de l'image
     */
    public StringProperty getCheminImageType() {
        return cheminImageType;
    }
    
    /**
     * Set le chemin de l'image correspondant au type
     * @param cheminImageType
     */
    public void setCheminImageType(String cheminImageType) {
        this.cheminImageType.setValue(cheminImageType);
    }

    public int getIdType() {
        return idType.get();
    }

    public IntegerProperty idTypeProperty() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType.set(idType);
    }

    @Override
    public String toString() {
        return "Type{" +
                "nomType=" + nomType +
                ", cheminImageType=" + cheminImageType +
                '}';
    }
}
