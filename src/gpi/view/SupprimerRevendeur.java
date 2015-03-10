package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Revendeur;
import gpi.metier.RevendeurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class SupprimerRevendeur {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxrev;

	private ObservableList<String> listrev;

	private List<Revendeur> listeRevendeur;

	private RevendeurDAO revendeurDAO = new RevendeurDAO();

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		listrev = FXCollections.observableArrayList();
		listeRevendeur = new ArrayList<Revendeur>();
		try {
			listeRevendeur = revendeurDAO.recupererAllRevendeur();
		} catch (ConnexionBDException ce) {
			Popup.getInstance().afficherPopup(ce.getMessage());
			this.dialogStage.close();
		}
		for (Revendeur revendeur : listeRevendeur) {
			listrev.add(revendeur.getNomRevendeur().getValue() + "- "
					+ revendeur.getIdRevendeur().getValue());
		}
		comboboxrev.setItems(listrev);
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
		if (comboboxrev.getValue() == null) {
			Popup.getInstance().afficherPopup("Veuillez selectionner un revendeur à supprimer");
		} else {
			Revendeur revendeur = listeRevendeur.get(comboboxrev
					.getSelectionModel().getSelectedIndex());
			try {
				revendeurDAO.supprimerRevendeur(revendeur);
				dialogStage.close();
				Popup.getInstance().afficherPopup("Revendeur " + revendeur.getNomRevendeur().getValue()
						+ " supprimé");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
				this.dialogStage.close();
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

}