package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.MaterielDAO;

import java.io.File;

import utils.Popup;
import utils.Propriete;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ModifierDriver {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private TextField cheminfield;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Cette methode permet de mettre en fenetre active le popup
	 * 
	 * @param dialogStage
	 *            la fenetre active
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Cette methode permet de savoir si le bouton AJOUTER est clique ou pas
	 * 
	 * @return vrai si le bouton AJOUTER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton AJOUTER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if (!(cheminfield.getText().isEmpty())){
			MaterielDAO materielDAO=new MaterielDAO();
			try {
				materielDAO.updateCheminRepertoire(cheminfield.getText());
				Propriete.getInstance().setProperties("driver",cheminfield.getText());
				Popup.getInstance().afficherPopup("Chemin driver modifié \n ('"+cheminfield.getText()+"')");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}		
			okClicked = true;
			dialogStage.close();
		}
		else{
			Popup.getInstance().afficherPopup("le chemin spécifié est vide");
		}

	}

	@FXML
	private void handleBrowse() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Open directory");
		File selectedDirectory = directoryChooser.showDialog(null);
		if (selectedDirectory != null) {
			String adresse=selectedDirectory.getAbsolutePath();
			adresse=adresse.replace("\\", "/");
			adresse="file:///"+adresse;
			this.cheminfield.setText(adresse);
        }else{
        	this.cheminfield.setText("");
        }
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton ANNULER
	 * est clique
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
	
}