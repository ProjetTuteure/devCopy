package gpi.metier;

public class IEtat {
	private String idMateriel;
	private String nomMateriel;
	private String etatMateriel;
	private String siteMateriel;
	private String dateModifEtatMateriel;
	
	public IEtat(String idMateriel, String nomMateriel, String etatMateriel, String siteMateriel, String dateModifEtatMateriel){
		this.idMateriel=idMateriel;
		this.nomMateriel=nomMateriel;
		this.etatMateriel=etatMateriel;
		this.siteMateriel=siteMateriel;
		this.setDateModifEtatMateriel(dateModifEtatMateriel);
	}

	public String getDateModifEtatMateriel() {
		return dateModifEtatMateriel;
	}

	public String getIdMateriel() {
		return idMateriel;
	}

	public void setIdMateriel(String idMateriel) {
		this.idMateriel = idMateriel;
	}

	public String getNomMateriel() {
		return nomMateriel;
	}

	public void setNomMateriel(String nomMateriel) {
		this.nomMateriel = nomMateriel;
	}

	public String getEtatMateriel() {
		return etatMateriel;
	}

	public void setEtatMateriel(String etatMateriel) {
		this.etatMateriel = etatMateriel;
	}

	public String getSiteMateriel() {
		return siteMateriel;
	}

	public void setSiteMateriel(String siteMateriel) {
		this.siteMateriel = siteMateriel;
	}

	public void setDateModifEtatMateriel(String dateModifEtatMateriel) {
		this.dateModifEtatMateriel = dateModifEtatMateriel;
	}
	
	
}
