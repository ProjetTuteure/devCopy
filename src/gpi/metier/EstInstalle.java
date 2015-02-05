package gpi.metier;

public class EstInstalle {
	private int idMateriel;
	private int idLogiciel;
	
	public EstInstalle(int idMateriel, int idLogiciel){
		this.setIdLogiciel(idLogiciel);
		this.setIdMateriel(idMateriel);
	}

	public String getIdMateriel() {
		return ""+idMateriel;
	}

	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}

	public String getIdLogiciel() {
		return ""+idLogiciel;
	}

	public void setIdLogiciel(int idLogiciel) {
		this.idLogiciel = idLogiciel;
	}
	
	
}
