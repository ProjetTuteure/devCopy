package vnc;

import java.io.IOException;

import utils.Constante;
import utils.Popup;


public class VNCWindows implements VNC{
	
	/**
	 * Constructeur de VNC Windows qui lance le VNC
	 * @param IPDestination l'adresse IP ou le nom DNS de la machine dont on veut prendre le contrôle
	 */
	public VNCWindows(String IPDestination){
		try {
			this.lancerVNC(IPDestination);
		} catch (IOException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
	}

	/**
	 * Permet de lancer vnc
	 * @param IPDestination, l'adresse IP ou le nom DNS de la machine dont on veut prendre le contrôle
	 */
	@Override
	public void lancerVNC(String IPDestination) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String[] args = {Constante.CHEMIN_VNC_VIEWER,IPDestination};
		final Process process = runtime.exec(args);
	}	
}
