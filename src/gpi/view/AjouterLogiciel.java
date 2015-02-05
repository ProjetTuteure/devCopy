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
import gpi.metier.Revendeur;
import gpi.metier.RevendeurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class AjouterLogiciel {
	@FXML
	private Stage dialogStage;
	@FXML
	private TextField nomLogicielField;
	@FXML
	private TextField versionLogicielField;
	@FXML
	private DatePicker dateExpirationGarantieField;

	@FXML
	private ComboBox<String> ComboboxFacture;

	private ObservableList<String> listFacture;
	private List<Integer> listFactureId;
	boolean isOkClicked=false;

	/**
	 * Initialise les donnees Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		FactureDAO factureDAO=new FactureDAO();
		listFactureId=new ArrayList<Integer>();
		listFacture = FXCollections.observableArrayList();
		try {
			for (Facture facture : factureDAO.recupererAllFacture()){
				listFacture.add(facture.getNumFacture());
				listFactureId.add(facture.getIdFacture().getValue());
			}
		} catch (ConnexionBDException e) {
			// TODO Auto-generated catch block
			new Popup(e.getMessage());
		}
		ComboboxFacture.setItems(listFacture);
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
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton AJOUTER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		LogicielDAO logicielDAO = new LogicielDAO();
		FactureDAO factureDAO = new FactureDAO();
		int index=ComboboxFacture.getSelectionModel().getSelectedIndex();
		try {
			logicielDAO.ajouterLogiciel(new Logiciel(0,nomLogicielField.getText(),versionLogicielField.getText(),dateExpirationGarantieField.getValue(),factureDAO.recupererFactureParId(listFactureId.get(index))));
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		dialogStage.close();
		isOkClicked=true;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton ANNULER
	 * est clique
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	public boolean isOkClicked() {
		return isOkClicked;
	}

}