package ping;

import gpi.metier.Materiel;

public class PingLinux implements Ping,Runnable {
		
		private Materiel materiel;
		private boolean resultatPing;
		
		public PingLinux(Materiel materiel)
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
				ProcessBuilder pb=new ProcessBuilder("/sbin/ping","-c","1",materiel.getNomMateriel().getValue());
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
