package gpi.view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class ModifierEstMaintenu {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxmat1;
	@FXML
	private ComboBox<String> comboboxmaint;
	@FXML
	private ComboBox<String> comboboxmat;
	@FXML
	private ComboBox<String> comboboxmaint1;

	private ObservableList<String> list1;
	private ObservableList<String> list2;
	private ObservableList<String> list21;
	private ObservableList<String> list11;

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

}