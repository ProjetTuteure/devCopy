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

/**
 * Created by Kevin
 */

public class AjouterType {
	private String cheminImageType;

	@FXML
	private Stage dialogStage;

	@FXML
	private TextField nomTypeField;

	@FXML
	private boolean okClicked = false;

	TypeDAO typeDAO=new TypeDAO();

	/**
	 * Initialise les donnï¿½es
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
		if(controlerSaisies())
		{
			Type typeAAjoute=new Type(0,nomTypeField.getText(),this.getCheminImageType());
			try {
				typeDAO.ajouterType(typeAAjoute);
				new Popup("Type "+typeAAjoute.getNomTypeString()+" ajouté !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}

	}

	private boolean controlerSaisies() {
		if(nomTypeField.getText().isEmpty()){
			new Popup("Le champ \"Nom du type\" doit être saisi");
			return false;
		}
		if(nomTypeField.getText().length()>Constante.LONGUEUR_NOM_TYPE){
			new Popup("La longueur du nom du type saisi doit être inférieur à "+Constante.LONGUEUR_NOM_TYPE+" caractères");
			return false;
		}
		if(this.getCheminImageType().length()>Constante.LONGUEUR_CHEMIN_IMAGE){
			new Popup("La longueur du chemin saisi doit être inférieur à "+Constante.LONGUEUR_CHEMIN_IMAGE+" caractères");
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
			String adresse=file.getAbsolutePath();
			adresse=adresse.replace("\\", "/");
			adresse="file:///"+adresse;
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