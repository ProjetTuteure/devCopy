package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by thibault on 22/11/14.
 */
public class Prestataire {
	private IntegerProperty idPrestataire;
	private StringProperty nomPrestataire;
	private StringProperty prenomPrestataire;
	private StringProperty telPrestataire;
	private StringProperty faxPrestataire;
	private StringProperty mobilePrestataire;
	private StringProperty emailPrestataire;
	private StringProperty societePrestataire;

	public Prestataire(int idPrestataire, String nomPrestataire,
			String prenomPrestataire, String telPrestataire,
			String mobilePrestataire, String faxPrestataire,
			String emailPrestataire, String societePrestataire) {

		this.idPrestataire = new SimpleIntegerProperty(idPrestataire);
		this.nomPrestataire = new SimpleStringProperty(nomPrestataire);
		this.prenomPrestataire = new SimpleStringProperty(prenomPrestataire);
		this.telPrestataire = new SimpleStringProperty(telPrestataire);
		this.faxPrestataire = new SimpleStringProperty(faxPrestataire);
		this.mobilePrestataire = new SimpleStringProperty(mobilePrestataire);
		this.emailPrestataire = new SimpleStringProperty(emailPrestataire);
		this.societePrestataire = new SimpleStringProperty(societePrestataire);
	}

	public IntegerProperty getIdPrestataire() {
		return idPrestataire;
	}

	public void setIdPrestataire(IntegerProperty idPrestataire) {
		this.idPrestataire = idPrestataire;
	}

	public StringProperty getPrenomPrestataire() {
		return prenomPrestataire;
	}

	public void setPrenomPrestataire(String prenomPrestataire) {
		this.prenomPrestataire.setValue(prenomPrestataire);
	}

	public StringProperty getNomPrestataire() {
		return nomPrestataire;
	}

	public void setNomPrestataire(String nomPrestataire) {
		this.nomPrestataire.setValue(nomPrestataire);
	}

	public StringProperty getFaxPrestataire() {
		return faxPrestataire;
	}

	public void setFaxPrestataire(String faxPrestataire) {
		this.faxPrestataire.setValue(faxPrestataire);
	}

	public StringProperty getMobilePrestataire() {
		return mobilePrestataire;
	}

	public void setMobilePrestataire(String mobilePrestataire) {
		this.mobilePrestataire.setValue(mobilePrestataire);
	}

	public StringProperty getTelPrestataire() {
		return telPrestataire;
	}

	public void setTelPrestataire(String telPrestataire) {
		this.telPrestataire.setValue(telPrestataire);
	}

	public StringProperty getEmailPrestataire() {
		return emailPrestataire;
	}

	public void setEmailPrestataire(String emailPrestataire) {
		this.emailPrestataire.setValue(emailPrestataire);
	}

	public StringProperty getSocieteePrestataire() {
		return societePrestataire;
	}

	public void setSocietePrestataire(String societePrestataire) {
		this.societePrestataire.setValue(societePrestataire);
	}

	@Override
	public String toString() {
		return "Prestataire{" + "idPrestataire=" + idPrestataire
				+ ", nomPrestataire=" + nomPrestataire + ", prenomPrestataire="
				+ prenomPrestataire + ", telPrestataire=" + telPrestataire
				+ ", mobilePrestataire=" + mobilePrestataire
				+ ", faxPrestataire=" + faxPrestataire + ", emailPrestataire="
				+ emailPrestataire + ", societePrestataire="
				+ societePrestataire + '}';
	}
}
