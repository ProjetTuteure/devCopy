package gpi.metier;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Site {
	private IntegerProperty idSite;
	private StringProperty nomSite;
	private StringProperty cheminImageSite;

	/**
	 * Constructeur du site
	 * @param idSite
	 * @param nomSite
	 * @param cheminImageSite
	 */
	public Site(int idSite, String nomSite, String cheminImageSite) {
		this.idSite = new SimpleIntegerProperty(idSite);
		this.nomSite = new SimpleStringProperty(nomSite);
		this.cheminImageSite = new SimpleStringProperty(cheminImageSite);
	}
	/**
	 * String get le nom du site
	 * @return le nom du site
	 */
	public String getNomSiteString() {
		return this.nomSite.get();
	}
	
	/**
	 * 
	 * @return
	 */
	public StringProperty getNomSiteProperty() {
		return this.nomSite;
	}

	public void setNomSite(String nomSite) {
		this.nomSite.set(nomSite);
	}

	public IntegerProperty getIdSiteProperty() {
		return idSite;
	}

	public int getIdSite() {
		return this.idSite.get();
	}

	public void setIdSite(int idSite) {
		this.idSite.set(idSite);
	}

	public String getCheminImageSite() {
		return cheminImageSite.get();
	}

	public StringProperty getCheminImageSiteProperty() {
		return this.cheminImageSite;
	}

	public void setCheminImageSite(String cheminImageSite) {
		this.cheminImageSite.set(cheminImageSite);
	}
	@Override
	public String toString() {
		return "Site [idSite=" + idSite.getValue() + ", nomSite=" + nomSite.getValue()
				+ ", cheminImageSite=" + cheminImageSite.getValue() + "]";
	}

}
