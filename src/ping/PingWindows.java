package ping;

import javafx.scene.paint.Color;
import gpi.metier.Materiel;
import gpi.view.DetailMachineController;

public class PingWindows implements Ping,Runnable {
	
	private Materiel materiel;
	private boolean resultatPing;
	
	public PingWindows(Materiel materiel)
	{
		this.materiel=materiel;
	}
	
	@Override
	public void run()
	{
		ping(materiel);
	}
	
	@Override
	public synchronized void ping(Materiel materiel) {
		try {  
			ProcessBuilder pb=new ProcessBuilder("cmd.exe","/c","ping","-n","1",materiel.getNomMateriel().getValue());
			Process p=pb.start();
	        int exitValue = p.waitFor();
	        if(exitValue==0)
	        {
	        	this.resultatPing=true;
	        	this.notifyAll();
	        }
	        else
	        {
	        	this.resultatPing=false;
	        	this.notifyAll();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public synchronized boolean getResultatPing()
	{
		return this.resultatPing;
	}

}
