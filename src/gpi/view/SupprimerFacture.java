package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Facture;
import gpi.metier.FactureDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class SupprimerFacture {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxFacture;


	private ObservableList<String> listFacture;
	List<Integer> listFactureId;

	/**
	 * Initialise les donnï¿½es Ajoute les donnï¿½es aux combobox
	 */
	@FXML
	private void initialize() {
		FactureDAO factureDAO = new FactureDAO();
		listFacture = FXCollections.observableArrayList();
		listFactureId=new ArrayList<Integer>();
		try {
			for (Facture facture : factureDAO.recupererAllFacture()) {
				listFacture.add(facture.getNumFacture());
				listFactureId.add(facture.getIdFacture().getValue());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxFacture.setItems(listFacture);
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
		FactureDAO factureDAO=new FactureDAO();
		int selected=comboboxFacture.getSelectionModel().getSelectedIndex();
		int id=listFactureId.get(selected);
		try {
			factureDAO.supprimerFacture(new Facture(id,null,null,0,null));
			new Popup("Facture "+comboboxFacture.getValue()+" supprimée !");
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
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

}