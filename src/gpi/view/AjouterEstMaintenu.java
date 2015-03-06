package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import utils.Popup;


public class AjouterEstMaintenu {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxMateriel;
	@FXML
	private ComboBox<String> comboboxMaintenance;

	private ObservableList<String> listNomMateriel;
	private ObservableList<String> listNomMaintenance;
	private ObservableList<Integer> listIdMateriel;
	private ObservableList<Integer> listIdMaintenance;
	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		listNomMaintenance = FXCollections.observableArrayList();
		PageMaterielDAO pageMaterielDAO=new PageMaterielDAO();
		listNomMateriel = FXCollections.observableArrayList();
		listIdMateriel = FXCollections.observableArrayList();
		listIdMaintenance = FXCollections.observableArrayList();	
		try {
			for (Maintenance maintenance : maintenanceDAO.recupererAllMaintenance()){
				listNomMaintenance.add(maintenance.getdateMaintenanceStringProperty().get());
				listIdMaintenance.add(maintenance.getIdMaintenance().intValue());
			}
			for (PageMateriel pageMateriel : pageMaterielDAO.getAllMateriel()){
				listNomMateriel.add(String.valueOf( pageMateriel.getNomMateriel()));
				listIdMateriel.add(Integer.parseInt(pageMateriel.getIdMateriel()));
			}
			
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxMaintenance.setItems(listNomMaintenance);
		comboboxMateriel.setItems(listNomMateriel);
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
		EstMaintenuDAO estMaintenuDAO=new EstMaintenuDAO();
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		MaterielDAO materielDAO=new MaterielDAO();
		int idMateriel = listIdMateriel.get(listNomMateriel.indexOf(comboboxMateriel.getValue()));
		int idMaintenance = listIdMaintenance.get(listNomMaintenance.indexOf(comboboxMaintenance.getValue()));
		try {
			Maintenance maintenance=maintenanceDAO.recupererMaintenanceParId(idMaintenance);
			Materiel materiel=materielDAO.recupererMaterielParId(idMateriel);
			EstMaintenu estMaintenuAAjoute=new EstMaintenu(maintenance,materiel);
			estMaintenuDAO.ajouterEstMaintenu(estMaintenuAAjoute);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
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