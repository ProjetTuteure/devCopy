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
	
	ObservableList<String> listObjetIntervention;
	ObservableList<String> listDateMaintenanceParObjet;
	
	ObservableList<String> listNomPrestataire;
	ObservableList<String> listPrenomPrestataire;
	
	ObservableList<String> listFacture;
	
	private PrestataireDAO prestataireDAO;
	private MaintenanceDAO maintenanceDAO;
	private FactureDAO factureDAO;
	private EstIntervenuDAO estIntervenuDAO;
//	private ObservableList<String> listNomPrestataireIntervention;
//	private ObservableList<String> listPrenomPrestataireIntervention;
//	private ObservableList<String> listNumFactureIntervention;
//	private ObservableList<String> listObjetMaintenanceIntervention;
//	private ObservableList<String> listDateMaintenanceIntervention;
//
//	private List<Integer> listIdPrestataireIntervention;
//	private List<Integer> listIdMaintenanceIntervention;

	/**
	 * Initialise les donn�es
	 */
	@FXML
	private void initialize() {
		this.maintenanceDAO=new MaintenanceDAO();
		this.prestataireDAO=new PrestataireDAO();
		this.factureDAO=new FactureDAO();
		this.estIntervenuDAO=new EstIntervenuDAO();
		try {
			this.listObjetIntervention=this.maintenanceDAO.recupererAllObjetMaintenanceString();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxObjetMaintenanceIntervention.setItems(listObjetIntervention);
		
		try{
			this.listNomPrestataire=this.prestataireDAO.recupererAllNomPrestataire();
		}catch(ConnexionBDException e){
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxNomPrestataireIntervention.setItems(this.listNomPrestataire);
		
		try{
			this.listFacture=this.factureDAO.recupererAllNumeroFacture();
		}catch(ConnexionBDException e){
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxNumFactureIntervention.setItems(this.listFacture);
	}

	private boolean controlerSaisies() {
		if (comboboxObjetMaintenanceIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"objet maintenance de l'intervention\" doit être saisi");
			return false;
		}
		if (comboboxDateMaintenanceIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"date maintenance de l'intervention\" doit être saisi");
			return false;
		}
		if (comboboxNomPrestataireIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"nom du prestataire de l'intervention\" doit être saisi");
			return false;
		}
		if (comboboxPrenomPrestataireIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"prenom du prestataire de l'intervention\" doit être saisi");
			return false;
		}
		if (comboboxNumFactureIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"numero facture de l'intervention\" doit être saisi");
			return false;
		}
		return true;
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
		int idPrestataireAAjouter;
		int idMaintenanceAAjouter;
		int idFactureAAjouter;
		if(controlerSaisies()){
			System.out.println("Coucou2");
			idMaintenanceAAjouter=Integer.parseInt(comboboxDateMaintenanceIntervention.getSelectionModel().getSelectedItem().split("-")[0]);
			idPrestataireAAjouter=Integer.parseInt(comboboxPrenomPrestataireIntervention.getSelectionModel().getSelectedItem().split("-")[0]);
			idFactureAAjouter=Integer.parseInt(comboboxNumFactureIntervention.getSelectionModel().getSelectedItem().split("-")[0]);
			try {
				estIntervenuDAO.ajouterEstIntervenu(idMaintenanceAAjouter,idPrestataireAAjouter,idFactureAAjouter);
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			Popup.getInstance().afficherPopup("Maintenance ajoutée !");
			okClicked = true;
			dialogStage.close();
		}
//		if(controlerSaisies()){
//			MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
//			PrestataireDAO prestataireDAO =new PrestataireDAO();
//			FactureDAO factureDAO = new FactureDAO();			
//			EstIntervenuDAO estIntervenuDAO=new EstIntervenuDAO();
//			Popup.getInstance().afficherPopup("Pas ajouté !");
//			okClicked = true;
//			dialogStage.close();
//		}
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
		String objetSelected=this.listObjetIntervention.get(comboboxObjetMaintenanceIntervention.getSelectionModel().getSelectedIndex());
		try {
			this.listDateMaintenanceParObjet=maintenanceDAO.recupererDateMaintenanceParObjet(objetSelected);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxDateMaintenanceIntervention.setItems(this.listDateMaintenanceParObjet);
		
//		MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
//		Maintenance selected = null;
//		try {
//			selected = maintenanceDAO.recupererMaintenanceParId(listIdMaintenanceIntervention.get(comboboxObjetMaintenanceIntervention.getSelectionModel().getSelectedIndex()));
//		} catch (ConnexionBDException e) {
//			Popup.getInstance().afficherPopup(e.getMessage());
//		}
//		listDateMaintenanceIntervention = FXCollections.observableArrayList();
//
//		try {
//			for (Maintenance pr : maintenanceDAO.recupererAllMaintenance()) {
//				if (pr.getObjetMaintenance().equals(selected.getObjetMaintenance())) {
//					listDateMaintenanceIntervention.add(pr.getdateMaintenanceStringProperty().getValue());
//				}
//			}
//		} catch (ConnexionBDException e) {
//			Popup.getInstance().afficherPopup(e.getMessage());
//		}
//		comboboxDateMaintenanceIntervention.setItems(listDateMaintenanceIntervention);
	}

	@FXML
	private void handleChange2() {
		String nomPrestataireMaintenanceSelected;
		nomPrestataireMaintenanceSelected=this.listNomPrestataire.get(this.comboboxNomPrestataireIntervention.getSelectionModel().getSelectedIndex());
		try {
			this.listPrenomPrestataire=this.prestataireDAO.recupererPrenomPrestataireParNom(nomPrestataireMaintenanceSelected);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
			e.printStackTrace();
		}
		comboboxPrenomPrestataireIntervention.setItems(this.listPrenomPrestataire);
//		PrestataireDAO prestataireDAO = new PrestataireDAO();
//		Prestataire selected = null;
//		try {
//			selected = prestataireDAO.recupererPrestataireParId(listIdPrestataireIntervention.get(comboboxNomPrestataireIntervention.getSelectionModel().getSelectedIndex()));
//		} catch (ConnexionBDException e) {
//			Popup.getInstance().afficherPopup(e.getMessage());
//		}
//		listPrenomPrestataireIntervention = FXCollections.observableArrayList();
//
//		try {
//			for (Prestataire pr : prestataireDAO.recupererAllPrestataire()) {
//				if (pr.getNomPrestataire().getValue().equals(selected.getNomPrestataire().getValue())) {
//					listPrenomPrestataireIntervention.add(pr.getPrenomPrestataire().getValue());
//				}
//			}
//		} catch (ConnexionBDException e) {
//			Popup.getInstance().afficherPopup(e.getMessage());
//		}
//		comboboxPrenomPrestataireIntervention.setItems(listPrenomPrestataireIntervention);
	}

}
