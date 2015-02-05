package gpi.metier;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

/**
 * Created by thibault on 22/11/14.
 */
public class Facture {
	private IntegerProperty idFacture;
    private StringProperty numFacture;
    private LocalDate dateFacture;
    private FloatProperty montantFacture;
    private Revendeur revendeurFacture;


    public Facture(int idFacture,String numFacture, LocalDate dateFacture, float montantFacture, Revendeur revendeurFacture) {
    	this.idFacture= new SimpleIntegerProperty(idFacture);
        this.numFacture = new SimpleStringProperty(numFacture);
        this.dateFacture= dateFacture;
        this.montantFacture= new SimpleFloatProperty(montantFacture);
        this.revendeurFacture = revendeurFacture;
    }

    public StringProperty getDateFacStringProperty() {
        String chaine1="";
        String chaine2="";
        if(dateFacture.getDayOfMonth()<10){
            chaine1="0";
        }
        if(dateFacture.getMonthValue()<10){
            chaine2="0";
        }
        String dateFacture=chaine1+this.dateFacture.getDayOfMonth()+"/"+chaine2+this.dateFacture.getMonthValue()+"/"+this.dateFacture.getYear();
        return new SimpleStringProperty(dateFacture);
    }

    public String getNumFacture() {
        return numFacture.get();
    }

    public void setNumFacture(String numFacture) {
        this.numFacture.set(numFacture);
    }

    public FloatProperty montantFactureProperty() {
        return montantFacture;
    }

    public LocalDate getDateFacture() {
        return dateFacture;
    }

    public StringProperty numFactureProperty(){
        return numFacture;
    }
    
    public void setDateFacture(LocalDate dateFacture) {
        this.dateFacture=(dateFacture);
    }

    public FloatProperty getMontantFacture() {
        return montantFacture;
    }
    
    public StringProperty getMontantFactureStringProperty() {
        return new SimpleStringProperty(""+this.getMontantFacture().floatValue());
    }

    public void setMontantFacture(float montantFacture) {
        this.montantFacture.set(montantFacture);
    }

    public Revendeur getRevendeurFacture() {
        return revendeurFacture;
    }

    public void setRevendeurFacture(Revendeur revendeurFacture) {
        this.revendeurFacture = revendeurFacture;
    }

	public IntegerProperty getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(int idFacture) {
		this.idFacture = new SimpleIntegerProperty(idFacture);
	}

	public StringProperty getNumFactureProperty() {
		return numFacture;
	}
}
