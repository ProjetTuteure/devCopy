package gpi.metier;

public class Compose {
	private int idComposant;
	private int idMateriel;
	
	public Compose(int idComposant,int idMateriel){
		this.setIdComposant(idComposant);
		this.setIdMateriel(idMateriel);
	}

	public String getIdMateriel() {
		return ""+idMateriel;
	}

	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}

	public String getIdComposant() {
		return ""+idComposant;
	}

	public void setIdComposant(int idComposant) {
		this.idComposant = idComposant;
	}
	
	
}
