package gpi.view;

import java.util.ArrayList;
import java.util.List;

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
	private ComboBox<String> comboboxMateriel;
	@FXML
	private ComboBox<String> comboboxMaintenanceObjet;
	@FXML
	private ComboBox<String> comboboxMaintenanceDate;

	private EstMaintenuDAO estMaintenuDAO =new EstMaintenuDAO();

	private ObservableList<String> listNomMateriel;
	private ObservableList<String> listDateMaintenance;
	private ObservableList<String> listObjetMaintenance;
	private List<Integer> listIdMateriel;
	private List<Integer> listIdMaintenance;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		listNomMateriel = FXCollections.observableArrayList();
		listIdMateriel = FXCollections.observableArrayList();
		PageMaterielDAO pageMaterielDAO = new PageMaterielDAO();
		try {
			
			for (PageMateriel pageMateriel : pageMaterielDAO.getMaterielMaintenu()){
				listNomMateriel.add(String.valueOf( pageMateriel.getNomMateriel()));
				listIdMateriel.add(Integer.parseInt(pageMateriel.getIdMateriel()));
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
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
		if (controlerSaisies()) {
			EstMaintenuDAO estMaintenuDAO=new EstMaintenuDAO();
			int idMateriel = listIdMateriel.get(comboboxMateriel.getSelectionModel().getSelectedIndex());
			int idMaintenance = listIdMaintenance.get(comboboxMaintenanceDate.getSelectionModel().getSelectedIndex());
			MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
			MaterielDAO materielDAO=new MaterielDAO();
			try {
				Maintenance maintenance=maintenanceDAO.recupererMaintenanceParId(idMaintenance);
				Materiel materiel=materielDAO.recupererMaterielParId(idMateriel);
				EstMaintenu estMaintenuASupprime=new EstMaintenu(maintenance,materiel);
				estMaintenuDAO.supprimerEstMaintenu(estMaintenuASupprime);
				Popup.getInstance().afficherPopup("L'opération de maintenance "+maintenance.getObjetMaintenance()
						+" du "+maintenance.getdateMaintenanceString()+" sur le matériel "+materiel.getNomMateriel().getValue()+" supprimée");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}
	}

	private boolean controlerSaisies() {
		if(comboboxMateriel.getSelectionModel().getSelectedIndex()==-1){
			Popup.getInstance().afficherPopup("Vous devez selectionner le matériel de l'opération à supprimer");
			return false;
		}
		if(comboboxMaintenanceObjet.getSelectionModel().getSelectedIndex()==-1){
			Popup.getInstance().afficherPopup("Vous devez selectionner l'objet de la maintenance de l'opération à supprimer");
			return false;
		}
		if(comboboxMaintenanceDate.getSelectionModel().getSelectedIndex()==-1){
			Popup.getInstance().afficherPopup("Vous devez selectionner la date de la maintenance de l'opération à supprimer");
			return false;
		}
		return true;
		
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
		comboboxMaintenanceObjet.setItems(null);
		comboboxMaintenanceDate.setItems(null);
		int idMateriel = listIdMateriel.get(comboboxMateriel.getSelectionModel().getSelectedIndex());
		listNomMateriel = FXCollections.observableArrayList();
		listIdMaintenance = new ArrayList<Integer>();
		listObjetMaintenance =FXCollections.observableArrayList();
		try {
			for (Maintenance maintenance : estMaintenuDAO.recupererMaintenanceParMateriel(idMateriel)){
				listObjetMaintenance.add(maintenance.getObjetMaintenance());
				listIdMaintenance.add(maintenance.getIdMaintenance().getValue());
			}
		} catch (ConnexionBDException e) {
			e.printStackTrace();
		}
		comboboxMaintenanceObjet.setItems(listObjetMaintenance);
	}
	
	@FXML
	private void handleChange1() {
		if(comboboxMaintenanceObjet.getSelectionModel().getSelectedIndex()!=-1){
			MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
			String objetSelected=this.listObjetMaintenance.get(comboboxMaintenanceObjet.getSelectionModel().getSelectedIndex());
			try {
				this.listDateMaintenance=maintenanceDAO.recupererDateMaintenanceParObjet(objetSelected);
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			comboboxMaintenanceDate.setItems(this.listDateMaintenance);
		}
	}
}