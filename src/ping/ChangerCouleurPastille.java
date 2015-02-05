package ping;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ChangerCouleurPastille extends Task<Circle>{
	private PingWindows pingWindows;
	private Circle pastille;
	
	public ChangerCouleurPastille(Circle pastille,PingWindows pingWindows)
	{
		this.pastille=pastille;
		this.pingWindows=pingWindows;
	}
	@Override
	protected Circle call() throws Exception {
		synchronized(pingWindows){
			pingWindows.wait();
			if(pingWindows.getResultatPing()==true)
			{
				pastille.setFill(Color.GREEN);
				System.out.println("coucouVert");
			}
			else
			{
				pastille.setFill(Color.ORANGE);
				System.out.println("coucouOrange");
			}
			Platform.runLater(new Runnable(){

				@Override
				public void run() {
					pastille.setVisible(true);
				}
			});
			return this.pastille;
		}
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
