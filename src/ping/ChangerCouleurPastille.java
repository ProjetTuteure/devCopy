package ping;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ChangerCouleurPastille extends Task<Void>{
	private PingWindows pingWindows;
	private Circle pastille;
	private boolean resultatPing;
	
	public ChangerCouleurPastille(Circle pastille,PingWindows pingWindows)
	{
		this.pastille=pastille;
		this.pingWindows=pingWindows;
	}
	@Override
	protected Void call() throws Exception {
		synchronized(pingWindows){
			pingWindows.notify();
			pingWindows.wait();
		}
		resultatPing=pingWindows.getResultatPing();
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				if(resultatPing==true)
				{
					pastille.setFill(Color.GREEN);
				}
				else
				{
					pastille.setFill(Color.ORANGE);
				}
				pastille.setVisible(true);
			}
		});
		return null;
	}
}
/*public class ChangerCouleurPastille  implements Runnable{
	private PingWindows pingWindows;
	private Circle pastille;
	
	public ChangerCouleurPastille(Circle pastille,PingWindows pingWindows)
	{
		this.pastille=pastille;
		this.pingWindows=pingWindows;
	}
	
	@Override
	public void run() {
		synchronized(this)
		{
			if(pingWindows.getResultatPing()==true)
			{
				pastille.setFill(Color.GREEN);
				this.pastille.setVisible(true);
			}
			else
			{
				pastille.setFill(Color.ORANGE);
				this.pastille.setVisible(true);
			}
		}
	}
}*/
