package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import utils.Popup;

/**
 * Created by Kevin
 */

public class SupprimerEstMaintenu {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxMaintenance;
	@FXML
	private ComboBox<String> comboboxMateriel;

	private EstMaintenuDAO estMaintenuDAO =new EstMaintenuDAO();

	private ObservableList<String> listIdMateriel;
	private ObservableList<String> listIdMaintenance;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		MaterielDAO materielDAO=new MaterielDAO();
		listIdMateriel = FXCollections.observableArrayList();
		PageMaterielDAO pageMaterielDAO = new PageMaterielDAO();
		try {
			
			for (PageMateriel pageMateriel : pageMaterielDAO.getAllMateriel()){
				listIdMateriel.add(String.valueOf( pageMateriel.getNomMateriel()));
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
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
			EstMaintenu estMaintenuASupprime=new EstMaintenu(maintenance,materiel);
			estMaintenuDAO.supprimerEstMaintenu(estMaintenuASupprime);
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

	@FXML
	private void handleChange() {
		listIdMaintenance = FXCollections.observableArrayList();
		try {
			for (Maintenance maintenance : estMaintenuDAO.recupererMaintenanceParMateriel(Integer.parseInt(comboboxMateriel.getValue()))){
				listIdMaintenance.add(String.valueOf(maintenance.getIdMaintenance().getValue()));
			}
		} catch (ConnexionBDException e) {
			e.printStackTrace();
		}
		comboboxMaintenance.setItems(listIdMaintenance);
	}
}