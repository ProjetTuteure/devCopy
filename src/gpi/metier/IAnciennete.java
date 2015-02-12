package gpi.metier;

public class IAnciennete {

	private String idMateriel;
	private String nomMateriel;
	private String numSerieMateriel;
	private String dateAchatMateriel;
	private String etatMateriel;
	private String finGarantieMateriel;
	private String nomRevendeur;
	private String nomFabricant;
	private String nomUtilisateur;
	private String nomSite;
	
	public IAnciennete(String idMateriel,String nomMateriel,String numSerieMateriel, String dateAchatMateriel, String etatMateriel, String finGarantieMateriel, String nomRevendeur, String nomFabricant, String nomUtilisateur, String nomSite){
		this.idMateriel=idMateriel;
		this.setNomMateriel(nomMateriel);
		this.numSerieMateriel=numSerieMateriel;
		this.dateAchatMateriel=dateAchatMateriel;
		this.etatMateriel=etatMateriel;
		this.finGarantieMateriel=finGarantieMateriel;
		this.nomRevendeur=nomRevendeur;
		this.nomFabricant=nomFabricant;
		this.nomUtilisateur=nomUtilisateur;
		this.nomSite=nomSite;
	}

	public String getIdMateriel() {
		return idMateriel;
	}

	public void setIdMateriel(String idMateriel) {
		this.idMateriel = idMateriel;
	}

	public String getNumSerieMateriel() {
		return numSerieMateriel;
	}

	public void setNumSerieMateriel(String numSerieMateriel) {
		this.numSerieMateriel = numSerieMateriel;
	}

	public String getDateAchatMateriel() {
		return dateAchatMateriel;
	}

	public void setDateAchatMateriel(String dateAchatMateriel) {
		this.dateAchatMateriel = dateAchatMateriel;
	}

	public String getEtatMateriel() {
		return etatMateriel;
	}

	public void setEtatMateriel(String etatMateriel) {
		this.etatMateriel = etatMateriel;
	}

	public String getFinGarantieMateriel() {
		return finGarantieMateriel;
	}

	public void setFinGarantieMateriel(String finGarantieMateriel) {
		this.finGarantieMateriel = finGarantieMateriel;
	}

	public String getNomRevendeur() {
		return nomRevendeur;
	}

	public void setNomRevendeur(String nomRevendeur) {
		this.nomRevendeur = nomRevendeur;
	}

	public String getNomFabricant() {
		return nomFabricant;
	}

	public void setNomFabricant(String nomFabricant) {
		this.nomFabricant = nomFabricant;
	}

	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public String getNomSite() {
		return nomSite;
	}

	public void setNomSite(String nomSite) {
		this.nomSite = nomSite;
	}

	public String getNomMateriel() {
		return nomMateriel;
	}

	public void setNomMateriel(String nomMateriel) {
		this.nomMateriel = nomMateriel;
	}
}
