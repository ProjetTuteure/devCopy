package gpi.view;

import gpi.bd.Donnee;
import gpi.metier.Facture;
import gpi.metier.Maintenance;
import gpi.metier.Prestataire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class ModifierIntervention {

	@FXML
	private Stage dialogStage;

	@FXML
	private boolean okClicked = false;
	
	@FXML
	private ComboBox<String> comboboxfact;
	@FXML
	private ComboBox<String> comboboxnom;
	@FXML
	private ComboBox<String> comboboxprenom;
	@FXML
	private ComboBox<String> comboboxnum;
	@FXML
	private ComboBox<String> comboboxobj;
	@FXML
	private ComboBox<String> comboboxdate;

	private Donnee donnee = new Donnee();

	private ObservableList<String> listfact;
	private ObservableList<String> listnom;
	private ObservableList<String> listprenom;
	private ObservableList<String> listnum;
	private ObservableList<String> listobj;
	private ObservableList<String> listdate;

	/**
	 * Initialise les données
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
	 * Cette methode permet de savoir si le bouton AJOUTER est clique
	 * 
	 * @return vrai si le bouton AJOUTER est clique
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

		okClicked = true;
		dialogStage.close();

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
	private void handleChange() {
		
	}
	@FXML
	private void handleChange1() {
		
	}

	@FXML
	private void handleChange2() {
		
	}
	

}
