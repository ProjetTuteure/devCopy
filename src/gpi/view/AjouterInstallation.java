package gpi.view;

import gpi.bd.Donnee;
import gpi.metier.Composant;
import gpi.metier.Materiel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class AjouterInstallation {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxnom;
	@FXML
	private ComboBox<String> comboboxcarac;
	@FXML
	private ComboBox<String> comboboxmat;

	private Donnee donnee = new Donnee();

	private ObservableList<String> list1;
	private ObservableList<String> list2;
	private ObservableList<String> list3;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		list1 = FXCollections.observableArrayList();

		for (Composant c : donnee.getComposantData()) {
			list1.add(c.getNomComposant());
		}
		comboboxnom.setItems(list1);
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
		Composant selected = donnee.getComposant(comboboxnom.getValue());
		list2 = FXCollections.observableArrayList();

		for (Composant c : donnee.getComposantData()) {
			if (c.getNomComposant().equals(selected.getNomComposant())) {
				list2.add(selected.getcaracteristiqueComposant());
			}
		}
		comboboxcarac.setItems(list2);

		list3 = FXCollections.observableArrayList();
		for (Materiel m : donnee.getMaterielData()) {
			list3.add(m.getNumImmobMateriel().getValue());
		}
		comboboxmat.setItems(list3);
	}
}