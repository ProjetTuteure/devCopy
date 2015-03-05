package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Fabricant;
import gpi.metier.FabricantDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import utils.Popup;

import java.util.ArrayList;
import java.util.List;

public class SupprimerFabricant {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxfabr;

	private FabricantDAO fabricantDAO=new FabricantDAO();

	private ObservableList<String> listNomFabricant;

	private List<Integer> listIdFabricant;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		listNomFabricant = FXCollections.observableArrayList();
		listIdFabricant=new ArrayList<Integer>();
		try {
			for (Fabricant fabricant : fabricantDAO.recupererAllFabricant()) {
                listNomFabricant.add(fabricant.getNomFabricantString());
				listIdFabricant.add(fabricant.getIdFabricant().getValue());
            }
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxfabr.setItems(listNomFabricant);
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
	
	private boolean controlerSaisies() {
		if (comboboxfabr.getValue() == null) {
			Popup.getInstance().afficherPopup("Vous devez selectionner le fabricant à supprimer");
			return false;
		}
		return true;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton SUPPRIMER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if (controlerSaisies()) {
		try {
			fabricantDAO.supprimerFabricant(new Fabricant(listIdFabricant.get(comboboxfabr.getSelectionModel().getSelectedIndex()),null, null, null, null, null,null));
			Popup.getInstance().afficherPopup("Fabricant "+comboboxfabr.getValue()+" supprimé !");
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		okClicked = true;
		dialogStage.close();
	}}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton ANNULER
	 * est clique
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}
}