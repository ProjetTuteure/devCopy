package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.EstIntervenuDAO;
import gpi.metier.Facture;
import gpi.metier.FactureDAO;
import gpi.metier.Maintenance;
import gpi.metier.MaintenanceDAO;
import gpi.metier.Prestataire;
import gpi.metier.PrestataireDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AjouterIntervention {

	@FXML
	private Stage dialogStage;

	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxNomPrestataireIntervention;
	@FXML
	private ComboBox<String> comboboxPrenomPrestataireIntervention;
	@FXML
	private ComboBox<String> comboboxNumFactureIntervention;
	@FXML
	private ComboBox<String> comboboxObjetMaintenanceIntervention;
	@FXML
	private ComboBox<String> comboboxDateMaintenanceIntervention;

	private ObservableList<String> listNomPrestataireIntervention;
	private ObservableList<String> listPrenomPrestataireIntervention;
	private ObservableList<String> listNumFactureIntervention;
	private ObservableList<String> listObjetMaintenanceIntervention;
	private ObservableList<String> listDateMaintenanceIntervention;

	private List<Integer> listIdPrestataireIntervention;
	private List<Integer> listIdMaintenanceIntervention;

	/**
	 * Initialise les donn�es
	 */
	@FXML
	private void initialize() {

		listObjetMaintenanceIntervention = FXCollections.observableArrayList();
		listIdMaintenanceIntervention = new ArrayList<Integer>();

		MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
		try {
			for (Maintenance maintenance : maintenanceDAO.recupererAllMaintenance()) {
				listObjetMaintenanceIntervention.add(maintenance.getObjetMaintenance());
				listIdMaintenanceIntervention.add(maintenance.getIdMaintenance().getValue());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxObjetMaintenanceIntervention.setItems(listObjetMaintenanceIntervention);

		listNomPrestataireIntervention = FXCollections.observableArrayList();
		listIdPrestataireIntervention = new ArrayList<Integer>();
		PrestataireDAO prestataireDAO = new PrestataireDAO();
		try {
			for (Prestataire prestataire : prestataireDAO.recupererAllPrestataire()) {
				listNomPrestataireIntervention.add(prestataire.getNomPrestataire().getValue());
				listIdPrestataireIntervention.add(prestataire.getIdPrestataire().getValue());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxNomPrestataireIntervention.setItems(listNomPrestataireIntervention);

		listNumFactureIntervention = FXCollections.observableArrayList();
		FactureDAO factureDAO = new FactureDAO();
		try {
			for (Facture fa : factureDAO.recupererAllFacture()) {
				listNumFactureIntervention.add(fa.getNumFacture());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxNumFactureIntervention.setItems(listNumFactureIntervention);

	}

	private boolean controlerSaisies() {
		if (comboboxObjetMaintenanceIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"objet maintenance de l'intervention\" doit �tre saisi");
			return false;
		}
		if (comboboxDateMaintenanceIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"date maintenance de l'intervention\" doit �tre saisi");
			return false;
		}
		if (comboboxNomPrestataireIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"nom du prestataire de l'intervention\" doit �tre saisi");
			return false;
		}
		if (comboboxPrenomPrestataireIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"prenom du prestataire de l'intervention\" doit �tre saisi");
			return false;
		}
		if (comboboxNumFactureIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"numero facture de l'intervention\" doit �tre saisi");
			return false;
		}
		return false;
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
	 * Cette methode permet de savoir si le bouton AJOUTER est clique
	 * 
	 * @return vrai si le bouton AJOUTER est clique
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
		if(controlerSaisies()){
			MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
			PrestataireDAO prestataireDAO =new PrestataireDAO();
			FactureDAO factureDAO = new FactureDAO();			
			EstIntervenuDAO estIntervenuDAO=new EstIntervenuDAO();
			Popup.getInstance().afficherPopup("Pas ajout� !");
			okClicked = true;
			dialogStage.close();
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
	private void handleChange1() {
		MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
		Maintenance selected = null;
		try {
			selected = maintenanceDAO.recupererMaintenanceParId(listIdMaintenanceIntervention.get(comboboxObjetMaintenanceIntervention.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		listDateMaintenanceIntervention = FXCollections.observableArrayList();

		try {
			for (Maintenance pr : maintenanceDAO.recupererAllMaintenance()) {
				if (pr.getObjetMaintenance().equals(selected.getObjetMaintenance())) {
					listDateMaintenanceIntervention.add(pr.getdateMaintenanceStringProperty().getValue());
				}
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxDateMaintenanceIntervention.setItems(listDateMaintenanceIntervention);
	}

	@FXML
	private void handleChange2() {
		PrestataireDAO prestataireDAO = new PrestataireDAO();
		Prestataire selected = null;
		try {
			selected = prestataireDAO.recupererPrestataireParId(listIdPrestataireIntervention.get(comboboxNomPrestataireIntervention.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		listPrenomPrestataireIntervention = FXCollections.observableArrayList();

		try {
			for (Prestataire pr : prestataireDAO.recupererAllPrestataire()) {
				if (pr.getNomPrestataire().getValue().equals(selected.getNomPrestataire().getValue())) {
					listPrenomPrestataireIntervention.add(pr.getPrenomPrestataire().getValue());
				}
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxPrenomPrestataireIntervention.setItems(listPrenomPrestataireIntervention);
	}

}
