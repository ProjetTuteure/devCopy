package utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe permettant de g�rer les popups d'erreurs
 * @author Cedric
 *
 */
public final class Popup {
	private Stage dialogStage;
	private Button buttonOk;
	private static volatile Popup instance = null;
	/**
	 * Construit une popup avec un message d'erreur et un bouton OK
	 * @param nomChamp le champ qui n'a pas �t� rempli
	 */
	public Popup()
	{
		super();
		if(instance==null){
			this.dialogStage=new Stage();
			this.dialogStage.setResizable(false);
			this.buttonOk=new Button("Ok");
			this.dialogStage.initModality(Modality.WINDOW_MODAL);	
		}
	}
	
	public final static Popup getInstance() {
        //Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet 
        //d'éviter un appel coûteux à synchronized, 
        //une fois que l'instanciation est faite.
        if (Popup.instance == null) {
           // Le mot-clé synchronized sur ce bloc empêche toute instanciation
           // multiple même par différents "threads".
           // Il est TRES important.
           synchronized(Popup.class) {
             if (Popup.instance == null) {
               Popup.instance = new Popup();
             }
           }
        }
        return Popup.instance;
    }
	
	public void afficherPopup(String texteAAfficher){
		if(Popup.instance!=null){
			buttonOk.setOnAction(new EventHandler<ActionEvent>() {
			    @Override
			    public void handle(ActionEvent e) {
			    	Popup.instance=null;
			        dialogStage.close();
			    }
			});
		}
		Text text=new Text();
		text.setTextAlignment(TextAlignment.CENTER);
		text.setText(texteAAfficher);
		dialogStage.setScene(new Scene(VBoxBuilder.create().
			    children(text,buttonOk).
			    alignment(Pos.CENTER).padding(new Insets(5)).build()));
			dialogStage.setAlwaysOnTop(true);
			dialogStage.setTitle(" ");
			dialogStage.show();
	}
	
}
