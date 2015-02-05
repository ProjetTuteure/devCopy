package gpi.view;

import gpi.bd.Donnee;
import gpi.metier.Composant;
import gpi.metier.Facture;
import gpi.metier.Materiel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class ModifierCompose {

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
	@FXML
	private ComboBox<String> comboboxnom1;
	@FXML
	private ComboBox<String> comboboxcarac1;
	@FXML
	private ComboBox<String> comboboxmat1;

	private Donnee donnee = new Donnee();
	
	private boolean test;

	private ObservableList<String> list1;
	private ObservableList<String> list2;
	private ObservableList<String> list3;
	private ObservableList<String> list11;
	private ObservableList<String> list21;
	private ObservableList<String> list31;

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
		test = true;
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
		
		list11 = FXCollections.observableArrayList();
		list21 = FXCollections.observableArrayList();
		list31 = FXCollections.observableArrayList();
		
		for (Composant cm : donnee.getComposantData()) {
			list11.add(cm.getNomComposant());
		}
		comboboxnom1.setItems(list11);
		//comboboxnom1.setPromptText(selected.getNomComposant());
		
		for (Composant cp : donnee.getComposantData()) {
			list21.add(cp.getcaracteristiqueComposant());
		}
		comboboxcarac1.setItems(list21);
		//comboboxcarac1.setPromptText(selected.getcaracteristiqueComposant());
		
		for (Materiel ma : donnee.getMaterielData()) {
			list31.add(ma.getNumImmobMateriel().getValue());
		}
		comboboxmat1.setItems(list31);
	}
	
	
}