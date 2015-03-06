package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Julien on 12/01/2015.
 */
public class Composant {
    private IntegerProperty idComposant;
    private StringProperty nomComposant;
    private StringProperty caracteristiqueComposant;
    private Fabricant fabricantComposant;

    public Composant(IntegerProperty idComposant, String nomComposant, String caracteristiqueComposant, Fabricant fabricantComposant) {
        this.idComposant = idComposant;
        this.nomComposant = new SimpleStringProperty(nomComposant);
        this.caracteristiqueComposant = new SimpleStringProperty(caracteristiqueComposant);
        this.fabricantComposant = fabricantComposant;
    }

    public int getIdComposant() {
        return idComposant.get();
    }

    public IntegerProperty idComposantProperty() {
        return idComposant;
    }

    public void setIdComposant(int idComposant) {
        this.idComposant.set(idComposant);
    }

    public String getNomComposant() {
        return nomComposant.get();
    }

    public StringProperty nomComposantProperty() {
        return nomComposant;
    }

    public void setNomComposant(String nomComposant) {
        this.nomComposant.set(nomComposant);
    }

    public String getCaracteristiqueComposant() {
        return caracteristiqueComposant.get();
    }

    public StringProperty caracteristiqueComposantProperty() {
        return caracteristiqueComposant;
    }

    public void setcaracteristiqueComposant(String caracteristiqueComposant) {
        this.caracteristiqueComposant.set(caracteristiqueComposant);
    }

    public Fabricant getFabricantComposant() {
        return fabricantComposant;
    }

    public void setFabricantComposant(Fabricant fabricantComposant) {
        this.fabricantComposant = fabricantComposant;
    }
}
