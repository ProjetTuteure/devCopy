package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by thibault on 22/11/14.
 */
public class Maintenance {
    private IntegerProperty idMaintenance;
    private LocalDate dateMaintenance;
    private String objetMaintenance;
    private String descriptionMaintenance;
    private float coutMaintenance;

    public Maintenance(int idMaintenance, LocalDate dateMaintenance, String objetMaintenance, String descriptionMaintenance, float coutMaintenance) {
        this.idMaintenance = new SimpleIntegerProperty(idMaintenance);
        this.dateMaintenance = dateMaintenance;
        this.objetMaintenance = objetMaintenance;
        this.descriptionMaintenance = descriptionMaintenance;
        this.coutMaintenance = coutMaintenance;
    }

	public IntegerProperty getIdMaintenance() {
        return idMaintenance;
    }

    public void setIdMaintenance(IntegerProperty idMaintenance) {
        this.idMaintenance = idMaintenance;
    }

    public float getCoutMaintenance() {
        return coutMaintenance;
    }
    
    public String getCoutMaintenanceString() {
        return ""+this.getCoutMaintenance();
    }

    public void setCoutMaintenance(float coutMaintenance) {
        this.coutMaintenance = coutMaintenance;
    }

    public String getDescriptionMaintenance() {
        return descriptionMaintenance;
    }

    public void setDescriptionMaintenance(String descriptionMaintenance) {
        this.descriptionMaintenance = descriptionMaintenance;
    }

    public String getObjetMaintenance() {
        return objetMaintenance;
    }

    public void setObjetMaintenance(String objetMaintenance) {
        this.objetMaintenance = objetMaintenance;
    }

    public LocalDate getdateMaintenance() {
        return dateMaintenance;
    }

    public String getdateMaintenanceString() {
    	if(dateMaintenance==null)return null;
        String chaine1="";
        String chaine2="";
        if(dateMaintenance.getDayOfMonth()<10){
            chaine1="0";
        }
        if(dateMaintenance.getMonthValue()<10){
            chaine2="0";
        }
        String date=chaine1+this.dateMaintenance.getDayOfMonth()+"/"+chaine2+this.dateMaintenance.getMonthValue()+"/"+this.dateMaintenance.getYear();
        return date;
    }
    
    public StringProperty getdateMaintenanceStringProperty() {
    	if(dateMaintenance==null)return null;
        String chaine1="";
        String chaine2="";
        if(dateMaintenance.getDayOfMonth()<10){
            chaine1="0";
        }
        if(dateMaintenance.getMonthValue()<10){
            chaine2="0";
        }
        String date=chaine1+this.dateMaintenance.getDayOfMonth()+"/"+chaine2+this.dateMaintenance.getMonthValue()+"/"+this.dateMaintenance.getYear();
        return new SimpleStringProperty(date);
    }

    public void setdateMaintenance(LocalDate dateMaintenance) {
        this.dateMaintenance = dateMaintenance;
    }
    
    @Override
   	public String toString() {
   		return "Maintenance [idMaintenance=" + idMaintenance
   				+ ", dateMaintenance=" + dateMaintenance
   				+ ", objetMaintenance=" + objetMaintenance
   				+ ", descriptionMaintenance=" + descriptionMaintenance
   				+ ", coutMaintenance=" + coutMaintenance + "]";
   	}
}
