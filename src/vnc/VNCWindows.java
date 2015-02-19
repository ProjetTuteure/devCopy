package vnc;

import java.io.IOException;

import utils.Constante;
import utils.Popup;

public class VNCWindows implements VNC{
	public VNCWindows(String IPDestination){
		try {
			this.lancerVNC(IPDestination);
		} catch (IOException e) {
			new Popup(e.getMessage());
		}
	}

	@Override
	public void lancerVNC(String IPDestination) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String[] args = {Constante.CHEMIN_VNC_VIEWER,IPDestination};
		final Process process = runtime.exec(args);
	}	
}
