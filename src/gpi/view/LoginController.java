package gpi.view;

import gpi.MainApp;
import gpi.exception.LoginException;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import utils.Popup;
import utils.Propriete;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable{

	@FXML
	private Button ok;
	
	@FXML
	private TextField tf_identifiant;
	
	@FXML
	private PasswordField pf_motDePasse;
	
	private MainApp mainApp;
	
	private DonneeLoginController donneeLoginController;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.donneeLoginController=new DonneeLoginController();
	}
	
	public void eventOk(ActionEvent event){
		String identifiant=this.tf_identifiant.getText();
		String motDePasse=this.pf_motDePasse.getText();
		try {
			this.donneeLoginController.controllerLoginEtMotDePasse(identifiant,motDePasse);
			MainApp.launch();
		} catch (LoginException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
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
