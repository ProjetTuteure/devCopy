package vnc;

import java.io.IOException;

import utils.Constante;
import utils.Popup;

public class VNCWindows implements VNC{
	public VNCWindows(String IPDestination,String password){
		try {
			this.lancerVNC(IPDestination, password);
		} catch (IOException e) {
			new Popup(e.getMessage());
		}
	}

	@Override
	public void lancerVNC(String IPDestination, String password) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		String[] args = {Constante.CHEMIN_VNC_VIEWER,"192.168.137.123"};
		final Process process = runtime.exec(args);
	}	
}
