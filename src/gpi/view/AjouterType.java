package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Type;
import gpi.metier.TypeDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.*;
import utils.Popup;

import javax.swing.*;

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
						+ " ajout� !");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}

	}

	private boolean controlerSaisies() {
		if (nomTypeField.getText().isEmpty()) {
			Popup.getInstance().afficherPopup("Le champ \"Nom du type\" doit �tre saisi");
			return false;
		}
		if (nomTypeField.getText().length() > Constante.LONGUEUR_NOM_TYPE) {
			Popup.getInstance().afficherPopup("La longueur du nom du type saisi doit �tre inf�rieur � "
					+ Constante.LONGUEUR_NOM_TYPE + " caract�res");
			return false;
		}
		if (this.getCheminImageType() != null) {
			if (this.getCheminImageType().length() > Constante.LONGUEUR_CHEMIN_IMAGE) {
				Popup.getInstance().afficherPopup("La longueur du chemin saisi doit �tre inf�rieur � "
						+ Constante.LONGUEUR_CHEMIN_IMAGE + " caract�res");
				return false;
			}
		}
		else{
			Popup.getInstance().afficherPopup("Une image doit être s�lectionn�e");
			return false;
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
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open File");
		fileChooser.showOpenDialog(null);
		File file = fileChooser.getSelectedFile();

		if (file != null) {
			String adresse = file.getAbsolutePath();
			adresse = adresse.replace("\\", "/");
			adresse = "file:///" + adresse;
			this.setCheminImageType(adresse);
		}

	}

	public String getCheminImageType() {
		return cheminImageType;
	}

	public void setCheminImageType(String cheminImageType) {
		this.cheminImageType = cheminImageType;
	}
}