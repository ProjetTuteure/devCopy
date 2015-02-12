package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Composant;
import gpi.metier.ComposantDAO;
import gpi.metier.Materiel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class AjouterCompose {

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

	private ComposantDAO composantDAO;

	
	private ObservableList<String> listeNomComposant;
	private List<Integer> listeIdComposant;
	private ObservableList<String> list2;
	private ObservableList<String> list3;
	

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		this.listeNomComposant = FXCollections.observableArrayList();
		this.listeIdComposant = new ArrayList<Integer>();
		this.composantDAO = new ComposantDAO();
		try {
			for (Composant c : composantDAO.recupererAllComposant()) {
				listeNomComposant.add(c.getNomComposant());
				listeIdComposant.add(c.getIdComposant());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxnom.setItems(listeNomComposant);
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
		/*Composant selected = composantDAO.recupererComposantParId(listeIdComposant.get(listeNomComposant.indexOf(comboboxnom.getValue())));
		list2 = FXCollections.observableArrayList();

		for (Composant c :) {
			if (c.getNomComposant().equals(selected.getNomComposant())) {
				list2.add(selected.getcaracteristiqueComposant());
			}
		}
		comboboxcarac.setItems(list2);

		list3 = FXCollections.observableArrayList();
		for (Materiel m : donnee.getMaterielData()) {
			list3.add(m.getNumImmobMateriel().getValue());
		}
		comboboxmat.setItems(list3);*/
	}
}