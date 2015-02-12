package vnc;

import java.io.IOException;

public interface VNC {
	public void lancerVNC(String IPDestination,String password) throws IOException;
}
