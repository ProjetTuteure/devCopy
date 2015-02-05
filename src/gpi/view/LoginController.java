package gpi.view;

import gpi.MainApp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class LoginController implements Initializable{

	@FXML
	private Button ok;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void eventOk(ActionEvent event){
		MainApp.launch();
	}

}
