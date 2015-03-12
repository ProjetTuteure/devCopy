package gpi.metier;

public class IAvance {
	private String idMateriel;
	private String numImmobMateriel;
	private String nomMateriel;
	private String nomSite;
	private String anciennete;
	private String typeMateriel;
	private String nomUtilisateur;
	private String dateAchatMateriel;
	private String numFactureMateriel;
	private String nomRevendeur;
	private String nomFabricant;
	private String modeleMateriel;
	
	public IAvance(String idMateriel, String numImmobMateriel,
			String nomMateriel, String nomSite, String anciennete,
			String typeMateriel, String nomUtilisateur,
			String dateAchatMateriel, String numFactureMateriel,
			String nomRevendeur, String nomFabricant, String modeleMateriel) {
		this.idMateriel = idMateriel;
		this.numImmobMateriel = numImmobMateriel;
		this.nomMateriel = nomMateriel;
		this.nomSite = nomSite;
		this.anciennete = anciennete;
		this.typeMateriel = typeMateriel;
		this.nomUtilisateur = nomUtilisateur;
		this.dateAchatMateriel = dateAchatMateriel;
		this.numFactureMateriel = numFactureMateriel;
		this.nomRevendeur = nomRevendeur;
		this.nomFabricant = nomFabricant;
		this.modeleMateriel = modeleMateriel;
	}

	public String getIdMateriel() {
		return idMateriel;
	}

	public void setIdMateriel(String idMateriel) {
		this.idMateriel = idMateriel;
	}

	public String getNumImmobMateriel() {
		return numImmobMateriel;
	}

	public void setNumImmobMateriel(String numImmobMateriel) {
		this.numImmobMateriel = numImmobMateriel;
	}

	public String getNomMateriel() {
		return nomMateriel;
	}

	public void setNomMateriel(String nomMateriel) {
		this.nomMateriel = nomMateriel;
	}

	public String getNomSite() {
		return nomSite;
	}

	public void setNomSite(String nomSite) {
		this.nomSite = nomSite;
	}

	public String getAnciennete() {
		return anciennete;
	}

	public void setAnciennete(String anciennete) {
		this.anciennete = anciennete;
	}

	public String getTypeMateriel() {
		return typeMateriel;
	}

	public void setTypeMateriel(String typeMateriel) {
		this.typeMateriel = typeMateriel;
	}

	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public String getDateAchatMateriel() {
		return dateAchatMateriel;
	}

	public void setDateAchatMateriel(String dateAchatMateriel) {
		this.dateAchatMateriel = dateAchatMateriel;
	}

	public String getNumFactureMateriel() {
		return numFactureMateriel;
	}

	public void setNumFactureMateriel(String numFactureMateriel) {
		this.numFactureMateriel = numFactureMateriel;
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

	public String getModeleMateriel() {
		return modeleMateriel;
	}

	public void setModeleMateriel(String modeleMateriel) {
		this.modeleMateriel = modeleMateriel;
	}

	@Override
	public String toString() {
		return "numImmob="
				+ numImmobMateriel + ", nom=" + nomMateriel
				+ ", nom=" + nomSite + ", anciennete=" + anciennete
				+ ", type=" + typeMateriel + ", nom Utilisateur="
				+ nomUtilisateur + ", dateAchat=" + dateAchatMateriel
				+ ", numFacture=" + numFactureMateriel
				+ ", nomRevendeur=" + nomRevendeur + ", nomFabricant="
				+ nomFabricant + ", modele=" + modeleMateriel;
	}
	
	
}
