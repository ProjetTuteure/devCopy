package gpi.metier;

public class PageMateriel {
	private String idMateriel;
	private String nomMateriel;
	private String cheminImageMateriel;
	
	public PageMateriel(String idMateriel,String nomMateriel,String cheminImageMateriel){
		this.idMateriel=idMateriel;
		this.nomMateriel=nomMateriel;
		this.cheminImageMateriel=cheminImageMateriel;
	}
	
	public PageMateriel(String idMateriel,String nomMateriel){
		this.idMateriel=idMateriel;
		this.nomMateriel=nomMateriel;
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
	public String getCheminImageMateriel() {
		return cheminImageMateriel;
	}
	public void setCheminImageMateriel(String cheminImageMateriel) {
		this.cheminImageMateriel = cheminImageMateriel;
	}
	
	
}
