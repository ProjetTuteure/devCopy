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

	private ObservableList<String> listIdMateriel;
	private ObservableList<String> listIdMaintenance;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		listIdMaintenance = FXCollections.observableArrayList();
		PageMaterielDAO pageMaterielDAO=new PageMaterielDAO();
		listIdMateriel = FXCollections.observableArrayList();
		
			
		try {
			for (Maintenance maintenance : maintenanceDAO.recupererAllMaintenance()){
				listIdMaintenance.add(String.valueOf(maintenance.getIdMaintenance().getValue()));
			}
			for (PageMateriel pageMateriel : pageMaterielDAO.getAllMateriel()){
				listIdMateriel.add(String.valueOf( pageMateriel.getNomMateriel()));
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxMaintenance.setItems(listIdMaintenance);
		comboboxMateriel.setItems(listIdMateriel);
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
		try {
			Maintenance maintenance=maintenanceDAO.recupererMaintenanceParId(Integer.parseInt(comboboxMaintenance.getValue()));
			Materiel materiel=materielDAO.recupererMaterielParId(Integer.parseInt(comboboxMateriel.getValue()));
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