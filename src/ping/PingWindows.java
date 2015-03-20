package ping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import gpi.metier.Materiel;

public class PingWindows implements Ping,Runnable {

	private Materiel materiel;
	private boolean resultatPing=false;
	
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
	 public void ping(Materiel materiel) {
        Runtime runtime = Runtime.getRuntime();
        String cmds = "ping -n 1 " + materiel.getNomMateriel().getValue();
        Process proc;
        int compteurLigne=0;

        try {
            proc = runtime.exec(cmds);
            proc.getOutputStream().close();
            InputStream inputstream = proc.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line;

            while ((line = bufferedreader.readLine()) != null) {
               	compteurLigne++;
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
        if(compteurLigne==8){
        	this.resultatPing=true;
        }else{
        	this.resultatPing=false;
        }
        this.notify();
    }
	
	/**
	 * Réalise un ping sur un matériel
	 * @param materiel le matériel dont on veut tester le ping
	 */
	/*@Override
	public synchronized void ping(Materiel materiel) {
		boolean status=false;
		int timeOut=3000;
		try {
			status = InetAddress.getByName(materiel.getNomMateriel().getValue()).isReachable(timeOut);
			} catch (UnknownHostException e) {}
			catch (IOException e) {} 
		if(status==true){
			this.resultatPing=true;
			this.notify();
		}else{
			this.resultatPing=false;
			this.notify();
		}
	}*/
	
	public synchronized boolean getResultatPing()
	{
		return this.resultatPing;
	}
}
