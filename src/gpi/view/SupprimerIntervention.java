package gpi.view;


import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.EstIntervenuDAO;
import gpi.metier.FactureDAO;
import gpi.metier.MaintenanceDAO;
import gpi.metier.PrestataireDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SupprimerIntervention {

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
			this.listObjetIntervention=this.maintenanceDAO.recupererAllObjetMaintenanceParEstIntervenuString();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxObjetMaintenanceIntervention.setItems(listObjetIntervention);
		
		try{
			this.listNomPrestataire=this.prestataireDAO.recupererAllNomPrestataireParEstIntervenu();
		}catch(ConnexionBDException e){
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxNomPrestataireIntervention.setItems(this.listNomPrestataire);
		
		try{
			this.listFacture=this.factureDAO.recupererAllNumeroFactureParEstIntervenu();
		}catch(ConnexionBDException e){
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxNumFactureIntervention.setItems(this.listFacture);
	}

	private boolean controlerSaisies() {
		if (comboboxObjetMaintenanceIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"Objet\" de maintenance doit être saisi");
			return false;
		}
		if (comboboxDateMaintenanceIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"Date\" de maintenance doit être saisi");
			return false;
		}
		if (comboboxNomPrestataireIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"Nom\" de prestataire doit être saisi");
			return false;
		}
		if (comboboxPrenomPrestataireIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"Prenom\" de prestataire de doit être saisi");
			return false;
		}
		if (comboboxNumFactureIntervention.getValue() == null) {
			Popup.getInstance().afficherPopup(
					"Le champ \"Numéro\" de facture doit être saisi");
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
		int idPrestataireASupprimer;
		int idMaintenanceASupprimer;
		int idFactureASupprimer;
		if(controlerSaisies()){
			idMaintenanceASupprimer=Integer.parseInt(comboboxDateMaintenanceIntervention.getSelectionModel().getSelectedItem().split("-")[0]);
			idPrestataireASupprimer=Integer.parseInt(comboboxPrenomPrestataireIntervention.getSelectionModel().getSelectedItem().split("-")[0]);
			idFactureASupprimer=Integer.parseInt(comboboxNumFactureIntervention.getSelectionModel().getSelectedItem().split("-")[0]);
			try {
				estIntervenuDAO.supprimerUtilise(idMaintenanceASupprimer, idPrestataireASupprimer, idFactureASupprimer);
				Popup.getInstance().afficherPopup("Intervention de "+comboboxPrenomPrestataireIntervention.getSelectionModel().getSelectedItem().split("-")[1]+" de la maintenance du "+comboboxDateMaintenanceIntervention.getSelectionModel().getSelectedItem().split("-")[1]+" supprimée");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
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
		String objetSelected=this.listObjetIntervention.get(comboboxObjetMaintenanceIntervention.getSelectionModel().getSelectedIndex());
		try {
			this.listDateMaintenanceParObjet=maintenanceDAO.recupererDateMaintenanceParObjetParEstIntervenu(objetSelected);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxDateMaintenanceIntervention.setItems(this.listDateMaintenanceParObjet);
	}

	@FXML
	private void handleChange2() {
		String nomPrestataireMaintenanceSelected;
		nomPrestataireMaintenanceSelected=this.listNomPrestataire.get(this.comboboxNomPrestataireIntervention.getSelectionModel().getSelectedIndex());
		try {
			this.listPrenomPrestataire=this.prestataireDAO.recupererPrenomPrestataireParNomParEstIntervenu(nomPrestataireMaintenanceSelected);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
			e.printStackTrace();
		}
		comboboxPrenomPrestataireIntervention.setItems(this.listPrenomPrestataire);
	}
}
