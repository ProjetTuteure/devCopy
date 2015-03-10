package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Maintenance;
import gpi.metier.MaintenanceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class SupprimerMaintenance {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> cb_objetMaintenance;
	@FXML
	private ComboBox<String> cb_dateMaintenance;

	private ObservableList<String> listeObjet;

	
	private List<Maintenance> listeMaintenance;
	
	private Maintenance maintenanceASupprimer;
	
	private MaintenanceDAO maintenanceDAO=new MaintenanceDAO();

	@FXML
	private void initialize() {
		listeObjet = FXCollections.observableArrayList();
		listeMaintenance=new ArrayList<Maintenance>();
		try {
			listeMaintenance=maintenanceDAO.recupererAllMaintenance();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		for (Maintenance m : listeMaintenance) {
			listeObjet.add(m.getIdMaintenance().getValue()+"- "+m.getObjetMaintenance());
		}
		cb_objetMaintenance.setItems(listeObjet);
	}

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
		if(controlerCombobox()==true)
		{
			maintenanceASupprimer=listeMaintenance.get(cb_objetMaintenance.getSelectionModel().getSelectedIndex());
			try {
				maintenanceDAO.supprimerMaintenance(maintenanceASupprimer);
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			Popup.getInstance().afficherPopup("Maintenance du "+maintenanceASupprimer.getdateMaintenanceStringProperty().getValue()+" supprimée");
			okClicked = true;
			dialogStage.close();
		}
	}

	private boolean controlerCombobox()
	{
		if(cb_objetMaintenance.getSelectionModel().getSelectedItem()==null)
		{
			Popup.getInstance().afficherPopup("Un objet de maintenance doit être selectionné");
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
}