package gpi.view;

import java.util.ArrayList;
import java.util.List;

import gpi.exception.ConnexionBDException;
import gpi.exception.PrimaryKeyException;
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
	private ComboBox<String> comboboxMaintenanceObjet;
	@FXML
	private ComboBox<String> comboboxMaintenanceDate;

	private ObservableList<String> listNomMateriel;
	private ObservableList<String> listDateMaintenance;
	private ObservableList<String> listObjetMaintenance;
	private List<Integer> listIdMateriel;
	
	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		listObjetMaintenance = FXCollections.observableArrayList();
		PageMaterielDAO pageMaterielDAO=new PageMaterielDAO();
		listNomMateriel = FXCollections.observableArrayList();
		listIdMateriel = new ArrayList<Integer>();
		try {
			for (Maintenance maintenance : maintenanceDAO.recupererAllMaintenance()){
				listObjetMaintenance.add(maintenance.getObjetMaintenance());
			}
			for (PageMateriel pageMateriel : pageMaterielDAO.getAllMateriel()){
				listNomMateriel.add(String.valueOf( pageMateriel.getNomMateriel()));
				listIdMateriel.add(Integer.parseInt(pageMateriel.getIdMateriel()));
			}
			
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxMaintenanceObjet.setItems(listObjetMaintenance);
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
		if(controlerSaisies()){
			EstMaintenuDAO estMaintenuDAO=new EstMaintenuDAO();
			MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
			MaterielDAO materielDAO=new MaterielDAO();
			int idMateriel = listIdMateriel.get(listNomMateriel.indexOf(comboboxMateriel.getValue()));
			int idMaintenance = Integer.parseInt(listDateMaintenance.get(comboboxMaintenanceDate.getSelectionModel().getSelectedIndex()).split("-")[0]);
			try {
				Maintenance maintenance=maintenanceDAO.recupererMaintenanceParId(idMaintenance);
				Materiel materiel=materielDAO.recupererMaterielParId(idMateriel);
				EstMaintenu estMaintenuAAjoute=new EstMaintenu(maintenance,materiel);
				estMaintenuDAO.ajouterEstMaintenu(estMaintenuAAjoute);
				Popup.getInstance().afficherPopup("L'opération de maintenance "+maintenance.getObjetMaintenance()
						+" du "+maintenance.getdateMaintenanceString()+" sur le matériel "+materiel.getNomMateriel().getValue()+" ajoutée !");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			} catch (PrimaryKeyException pke){
				Popup.getInstance().afficherPopup(pke.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}	

	}
	private boolean controlerSaisies() {
		if(comboboxMateriel.getSelectionModel().getSelectedIndex()==-1){
			Popup.getInstance().afficherPopup("Vous devez selectionner le matériel de l'opération à ajouter");
			return false;
		}
		if(comboboxMaintenanceObjet.getSelectionModel().getSelectedIndex()==-1){
			Popup.getInstance().afficherPopup("Vous devez selectionner l'objet de la maintenance de l'opération à ajouter");
			return false;
		}
		if(comboboxMaintenanceDate.getSelectionModel().getSelectedIndex()==-1){
			Popup.getInstance().afficherPopup("Vous devez selectionner la date de la maintenance de l'opération à ajouter");
			return false;
		}
		return true;
		
	}
	
	@FXML
	private void handleChange() {
		MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
		String objetSelected=this.listObjetMaintenance.get(comboboxMaintenanceObjet.getSelectionModel().getSelectedIndex());
		try {
			this.listDateMaintenance=maintenanceDAO.recupererDateMaintenanceParObjet(objetSelected);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxMaintenanceDate.setItems(this.listDateMaintenance);
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