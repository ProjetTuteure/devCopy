package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Composant;
import gpi.metier.ComposantDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class SupprimerComposant {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxnom;
	@FXML
	private ComboBox<String> comboboxcarac;

	private ObservableList<String> listnom;
	private ObservableList<String> listcarac;

	private List<Composant> listeNom;
	private List<Composant> listeCarac;

	private ComposantDAO composantDAO = new ComposantDAO();

	@FXML
	private void initialize() {
		listnom = FXCollections.observableArrayList();
		listeNom = new ArrayList<Composant>();
		try {
			listnom.addAll(composantDAO.recupererAllNomComposant());
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxnom.setItems(listnom);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Cette methode permet de savoir si le bouton SUPPRIMER est clique ou pas
	 * 
	 * @return vrai si le bouton SUPPRIMER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton SUPPRIMER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		okClicked = true;

		if (comboboxnom.getValue() == null) {
			Popup.getInstance().afficherPopup("Veuillez choisir un nom de composant");
		} else if (comboboxcarac.getValue() == null) {
			Popup.getInstance().afficherPopup("Veuillez choisir une caractéristique de composant");
		} else {
			int indexComposantSelectionne = comboboxcarac.getSelectionModel()
					.getSelectedIndex();
			Composant composant = listeCarac.get(indexComposantSelectionne);
			try {
				if (composantDAO.supprimerComposant(composant) == true) {
					dialogStage.close();
					Popup.getInstance().afficherPopup("Composant supprimé !");
				} else {
					Popup.getInstance().afficherPopup("Echec lors de la suppression");
				}
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
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

	@FXML
	private void handlechange() {
		listcarac = FXCollections.observableArrayList();
		try {
			listeCarac = composantDAO.recupererComposantParNom(listnom
					.get(comboboxnom.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}

		for (Composant composant : listeCarac) {
			listcarac.add(composant.getCaracteristiqueComposant());
		}
		comboboxcarac.setItems(listcarac);
	}

}