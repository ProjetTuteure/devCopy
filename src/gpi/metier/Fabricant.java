package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by thibault on 22/11/14.
 */
public class Fabricant {
    private IntegerProperty idFabricant;
    private StringProperty nomFabricant;
    private StringProperty telFabricant;
    private StringProperty mobileFabricant;
    private StringProperty faxFabricant;
    private StringProperty emailFabricant;
    private StringProperty adresseFabricant;


    public Fabricant(int idFabricant, String nomFabricant, String telFabricant, String mobileFabricant, String faxFabricant, String emailFabricant, String adresseFabricant){

        this.idFabricant = new SimpleIntegerProperty(idFabricant);
        this.nomFabricant = new SimpleStringProperty(nomFabricant);
        this.telFabricant = new SimpleStringProperty(telFabricant);
        this.mobileFabricant = new SimpleStringProperty(mobileFabricant);
        this.faxFabricant = new SimpleStringProperty(faxFabricant);
        this.emailFabricant = new SimpleStringProperty(emailFabricant);
        this.adresseFabricant = new SimpleStringProperty(adresseFabricant);
    }

    public IntegerProperty getIdFabricant() {
        return idFabricant;
    }

    public void setIdFabricant(IntegerProperty idFabricant) {
        this.idFabricant = idFabricant;
    }

    public StringProperty getTelFabricant() {
        return telFabricant;
    }

    public void setTelFabricant(String telFabricant) {
        this.telFabricant.setValue(telFabricant);
    }

    public StringProperty getAdresseFabricant() {
        return adresseFabricant;
    }

    public void setAdresseFabricant(String adresseFabricant) {
        this.adresseFabricant.setValue(adresseFabricant);
    }

    public StringProperty getNomFabricant() {
        return nomFabricant;
    }

    public String getNomFabricantString(){
    	return this.getNomFabricant().getValue();
    }

    public void setNomFabricant(String nomFabricant) {
        this.nomFabricant.setValue(nomFabricant);
    }

    public StringProperty getMobileFabricant() {
		return mobileFabricant;
	}

	public void setMobileFabricant(String mobileFabricant) {
		this.mobileFabricant.set(mobileFabricant);
	}

	public StringProperty getFaxFabricant() {
		return faxFabricant;
	}

	public void setFaxFabricant(String faxFabricant) {
		this.faxFabricant.set(faxFabricant);
	}

	public StringProperty getEmailFabricant() {
		return emailFabricant;
	}

	public void setEmailFabricant(String emailFabricant) {
		this.emailFabricant.set(emailFabricant);
	}

	@Override
    public String toString() {
        return "Fabricant{" +
                "idFabricant=" + idFabricant +
                ", nomFabricant=" + nomFabricant +
                ", telFabricant=" + telFabricant +
                ", adresseFabricant=" + adresseFabricant +
                '}';
    }
}
