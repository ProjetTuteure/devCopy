package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Type;
import gpi.metier.TypeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.*;

import java.io.File;

public class AjouterType {
	private String cheminImageType;

	@FXML
	private Stage dialogStage;

	@FXML
	private TextField nomTypeField;

	@FXML
	private boolean okClicked = false;

	TypeDAO typeDAO = new TypeDAO();

	/**
	 * Initialise les donn�es
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
		if (controlerSaisies()) {
			Type typeAAjoute = new Type(0, nomTypeField.getText(),
					this.getCheminImageType());
			try {
				typeDAO.ajouterType(typeAAjoute);
				Popup.getInstance().afficherPopup("Type " + typeAAjoute.getNomTypeString()
						+ " ajouté");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}

	}

	private boolean controlerSaisies() {
		if (nomTypeField.getText().isEmpty()) {
			Popup.getInstance().afficherPopup("Le champ \"Nom du type\" doit être saisi");
			return false;
		}
		if (nomTypeField.getText().length() > Constante.LONGUEUR_NOM_TYPE) {
			Popup.getInstance().afficherPopup("La longueur du nom du type saisi doit être infêrieur à "
					+ Constante.LONGUEUR_NOM_TYPE + " caractères");
			return false;
		}
		if (this.getCheminImageType() == null) {
			Popup.getInstance().afficherPopup("Vous devez choisir une image");
			return false;
		}else{
			if (this.getCheminImageType().length() > Constante.LONGUEUR_CHEMIN_IMAGE) {
				Popup.getInstance().afficherPopup("La longueur du chemin saisi doit être infêrieur à "
						+ Constante.LONGUEUR_CHEMIN_IMAGE + " caractères");
				return false;
			}
		}
		return true;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton ANNULER
	 * est clique
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handleChoose(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une image");
		File selectedDirectory = fileChooser.showOpenDialog(null);
		if (selectedDirectory != null) {
			String adresse=selectedDirectory.getAbsolutePath();
			adresse=adresse.replace("\\", "/");
			adresse="file:///"+adresse;
			this.setCheminImageType(adresse);
        }else{
        	this.setCheminImageType("");
        }
	}

	public String getCheminImageType() {
		return cheminImageType;
	}

	public void setCheminImageType(String cheminImageType) {
		this.cheminImageType = cheminImageType;
	}
}