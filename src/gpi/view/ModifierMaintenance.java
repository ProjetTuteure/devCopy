package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Maintenance;
import gpi.metier.MaintenanceDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModifierMaintenance {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private TextField tf_objetMaintenance;
	@FXML
	private TextField tf_coutMaintenance;
	@FXML
	private DatePicker dp_dateMaintenance;
	@FXML
	private TextArea ta_descriptionMaintenance;
	@FXML
	private boolean choix1 = false;

	@FXML
	private ComboBox<String> cb_objetMaintenance;

	private ObservableList<String> listeObjet;
	
	private List<Maintenance> listeMaintenance;
	private MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
	private Maintenance maintenanceAModifier;

	@FXML
	private void initialize() {
		listeMaintenance=new ArrayList<Maintenance>();
		listeObjet=FXCollections.observableArrayList();
		try {
			listeMaintenance=maintenanceDAO.recupererAllMaintenance();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
			dialogStage.close();
		}
		for(Maintenance maintenance:listeMaintenance)
		{
			listeObjet.add(maintenance.getIdMaintenance().getValue()+"- "+maintenance.getObjetMaintenance());
		}
		cb_objetMaintenance.setItems(listeObjet);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		float coutMaintenance=0;
		try {
			if(controlerSaisies()==true)
			{
				if(!tf_coutMaintenance.getText().isEmpty()){
					coutMaintenance=Float.parseFloat(tf_coutMaintenance.getText());
				}
				Maintenance maintenance=new Maintenance(maintenanceAModifier.getIdMaintenance().getValue(),
						dp_dateMaintenance.getValue(),
						tf_objetMaintenance.getText(),
						ta_descriptionMaintenance.getText(),
						coutMaintenance);
						maintenanceDAO.modifierMaintenance(maintenance);
				Popup.getInstance().afficherPopup("Maintenance du "+maintenanceAModifier.getdateMaintenanceStringProperty().getValue()+ " modifiée");
				okClicked = true;
				dialogStage.close();
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
	}

	/**
	 * Permet de controler la saisie de l'utilisateur
	 * @return vrai si toutes les saisies sont coh�rentes, faux sinon
	 */
	public boolean controlerSaisies()
	{
		if(cb_objetMaintenance.getValue()==null)
		{
			Popup.getInstance().afficherPopup("Une maintenance doit �tre s�lectionn�e");
			return false;
		}
		if(dp_dateMaintenance.getValue()==null)
		{
			Popup.getInstance().afficherPopup("Une date doit �tre saisie");
			return false;
		}
		if(tf_objetMaintenance.getText().length()>Constante.LONGUEUR_OBJET_MAINTENANCE)
		{
			Popup.getInstance().afficherPopup("La longueur de l'objet saisi doit �tre inf�rieur � "+Constante.LONGUEUR_OBJET_MAINTENANCE+" caract�res");
			return false;
		}
		if(ta_descriptionMaintenance.getText().length()>Constante.LONGUEUR_DESCRIPTION_MAINTENANCE)
		{
			Popup.getInstance().afficherPopup("La longueur de la description saisie doit �tre inf�rieur � "+Constante.LONGUEUR_OBJET_MAINTENANCE+" caract�res");
			return false;
		}		
		if(!tf_coutMaintenance.getText().isEmpty())
		{
			if(tf_coutMaintenance.getText().contains(","))
			{
				tf_coutMaintenance.setText(tf_coutMaintenance.getText().replace(',','.'));
			}
			try
			{
				if(Float.parseFloat(tf_coutMaintenance.getText())<0)
				{
					Popup.getInstance().afficherPopup("La valeur du co�t de la maintenance ne doit pas �tre n�gative");
					return false;
				}
			}
			catch(NumberFormatException nfe)
			{
				Popup.getInstance().afficherPopup("La valeur du co�t saisie doit �tre un nombre");
				return false;
			}
		}
		return true;
	}
	
	
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handlechange1() {
		maintenanceAModifier = listeMaintenance.get(cb_objetMaintenance.getSelectionModel().getSelectedIndex());
		tf_coutMaintenance.setText(maintenanceAModifier.getCoutMaintenanceString());
		tf_objetMaintenance.setText(maintenanceAModifier.getObjetMaintenance());
		ta_descriptionMaintenance.setText(maintenanceAModifier.getDescriptionMaintenance());
		dp_dateMaintenance.setValue(maintenanceAModifier.getdateMaintenance());
	}
}