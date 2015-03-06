package ping;

import gpi.metier.Materiel;

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
		synchronized(this){
		try {
			this.wait();
			ping(materiel);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
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
	        	this.notify();
	        }
	        else
	        {
	        	this.resultatPing=false;
	        	this.notify();
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
