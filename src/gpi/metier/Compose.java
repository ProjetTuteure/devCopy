package gpi.metier;

public class Compose {
	private Composant composant;
	private Materiel Materiel;
	
	

	public Compose(Composant composant, Materiel materiel) {
		this.composant = composant;
		Materiel = materiel;
	}

	public Composant getComposant() {
		return composant;
	}

	public void setComposant(Composant composant) {
		this.composant = composant;
	}

	public Materiel getMateriel() {
		return Materiel;
	}

	public void setMateriel(Materiel materiel) {
		Materiel = materiel;
	}

	
	
}
