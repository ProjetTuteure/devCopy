package ping;

import gpi.metier.Materiel;

public interface Ping{
	/**
	 * Permet de ping un mat�riel
	 * @param materiel le mat�riel � ping
	 * @return true si ping effectu�, false sinon
	 */
	public void ping(Materiel materiel);
}
