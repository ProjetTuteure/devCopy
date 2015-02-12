package gpi.view;

import gpi.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class LoginController implements Initializable{

	@FXML
	private Button ok;
	private MainApp mainApp;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void eventOk(ActionEvent event){
		MainApp.launch();
	}
	
	public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
	
	public void eventQuit(ActionEvent event){
		try {
			Platform.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
