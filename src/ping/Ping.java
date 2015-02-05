package ping;

import gpi.metier.Materiel;

public interface Ping{
	/**
	 * Permet de ping un matériel
	 * @param materiel le matériel à ping
	 * @return true si ping effectué, false sinon
	 */
	public void ping(Materiel materiel);
}
