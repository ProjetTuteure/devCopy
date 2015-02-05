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

public class PopupChargement {
	private Stage dialogStage;
	private Button buttonOk;
	
	public PopupChargement(){
		this.dialogStage=new Stage();
		this.buttonOk=new Button("Ok");
		buttonOk.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        dialogStage.close();
		    }
		});
		dialogStage.initModality(Modality.NONE);
		dialogStage.setScene(new Scene(VBoxBuilder.create().
		    children(new Text("coucou"),buttonOk).
		    alignment(Pos.CENTER).padding(new Insets(5)).build()));
		dialogStage.setAlwaysOnTop(true);
		dialogStage.show();
	}
}
