package ping;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
	
	/**
	 * Réalise un ping sur un matériel
	 * @param materiel le matériel dont on veut tester le ping
	 */
	@Override
	public synchronized void ping(Materiel materiel) {
		boolean status=false;
		int timeOut=3000;
		try {
			status = InetAddress.getByName (materiel.getNomMateriel().getValue()).isReachable(timeOut);
			} catch (UnknownHostException e) {}
			catch (IOException e) {} 
		if(status==true){
			this.resultatPing=true;
			this.notify();
		}else{
			this.resultatPing=false;
			this.notify();
		}
	}
	
	public synchronized boolean getResultatPing()
	{
		return this.resultatPing;
	}
}
