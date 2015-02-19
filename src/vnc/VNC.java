package vnc;

import java.io.IOException;

public interface VNC {
	/**
	 * Permet de lancer VNC à l'adresse IP ou au nom DNS de la machine dont on veut prendre le contrôle
	 * @param IPDestination l'adresse IP ou au nom DNS de la machine dont on veut prendre le contrôle
	 * @throws IOException Si une erreur survient lors de la lecture du chemin
	 */
	public void lancerVNC(String IPDestination) throws IOException;
}
