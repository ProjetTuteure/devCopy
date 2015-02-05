package utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe permettant de gérer les popups d'erreurs
 * @author Cedric
 *
 */
public class Popup {
	private Stage dialogStage;
	private Button buttonOk;
	
	/**
	 * Construit une popup avec un message d'erreur et un bouton OK
	 * @param nomChamp le champ qui n'a pas été rempli
	 */
	public Popup(String texteAAfficher)
	{
		this.dialogStage=new Stage();
		this.buttonOk=new Button("Ok");
		buttonOk.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        dialogStage.close();
		    }
		});
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.setScene(new Scene(VBoxBuilder.create().
		    children(new Text(texteAAfficher),buttonOk).
		    alignment(Pos.CENTER).padding(new Insets(5)).build()));
		dialogStage.setAlwaysOnTop(true);
		dialogStage.show();
	}
	
}
