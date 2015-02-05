package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Facture;
import gpi.metier.FactureDAO;
import gpi.metier.Logiciel;
import gpi.metier.LogicielDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class SupprimerLogiciel {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> ComboboxLogiciel;


	private ObservableList<String> listLogiciel;
	List<Integer> listLogicielId;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		LogicielDAO logicielDAO = new LogicielDAO();
		listLogiciel = FXCollections.observableArrayList();
		listLogicielId=new ArrayList<Integer>();
		try {
			for (Logiciel logiciel : logicielDAO.recupererAllLogiciel()) {
				listLogiciel.add(logiciel.getNomLogiciel()+" "+logiciel.getVersionLogiciel());
				listLogicielId.add(logiciel.getIdLogiciel());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		ComboboxLogiciel.setItems(listLogiciel);
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

		LogicielDAO logicielDAO=new LogicielDAO();
		int selected=ComboboxLogiciel.getSelectionModel().getSelectedIndex();
		int id=listLogicielId.get(selected);
		try {
			logicielDAO.supprimerLogiciel(new Logiciel(id,null,null,null,null));
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