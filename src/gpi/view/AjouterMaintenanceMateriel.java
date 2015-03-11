package gpi.view;

import utils.Constante;
import utils.Popup;
import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.exception.PrimaryKeyException;
import gpi.metier.EstMaintenu;
import gpi.metier.EstMaintenuDAO;
import gpi.metier.Maintenance;
import gpi.metier.MaintenanceDAO;
import gpi.metier.Materiel;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterMaintenanceMateriel {
	@FXML
	private Stage dialogStage;

	@FXML
	private boolean okClicked = false;

	@FXML
	private DatePicker dp_dateMaintenance;
	
	@FXML 
	private TextField tf_coutMaintenance;
	
	@FXML
	private TextField tf_objetMaintenance;
	
	@FXML
	private TextArea ta_description;
	
	private MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
	private EstMaintenuDAO estMaintenuDAO = new EstMaintenuDAO();
	/**
	 * Initialise les donn�es
	 */
	@FXML
	private void initialize() {
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
	 * Permet d'ajouter la maintenance � la base de donn�es
	 * est clique
	 */
	@FXML
	private void handleOk() {
		okClicked = true;
		float coutMaintenance=0;
		Materiel materiel=getActiveMateriel();
		if(controlerSaisies()==true)
		{
			if(!tf_coutMaintenance.getText().isEmpty()){
			coutMaintenance=Float.parseFloat(tf_coutMaintenance.getText());
			}
			Maintenance maintenance=new Maintenance(0,dp_dateMaintenance.getValue(),
					tf_objetMaintenance.getText(),
					ta_description.getText(),
					coutMaintenance);
			try {
				maintenanceDAO.ajouterMaintenance(maintenance);
				estMaintenuDAO.ajouterEstMaintenu(new EstMaintenu(maintenance, materiel));
				Popup.getInstance().afficherPopup("Maintenance du "+maintenance.getdateMaintenanceStringProperty().getValue()+" ajoutée pour le matériel "+materiel.getNomMateriel().getValue());
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			} catch (PrimaryKeyException pke) {
				Popup.getInstance().afficherPopup(pke.getMessage());
			}
			dialogStage.close();
		}
	}

	/**
	 * M�thode qui permet de contr�ler les valeurs saisies
	 * par l'utilisateur dans les champs
	 * @return
	 */
	private boolean controlerSaisies()
	{
		if(dp_dateMaintenance.getValue()==null)
		{
			Popup.getInstance().afficherPopup("La date doit obligatoirement être remplie");
			return false;
		}
		else if(tf_objetMaintenance.getText().length()>Constante.LONGUEUR_OBJET_MAINTENANCE)
		{
			Popup.getInstance().afficherPopup("L'objet de la maintenance doit faire au maximum "+Constante.LONGUEUR_OBJET_MAINTENANCE+" caractères");
			return false;
		}
		else if(ta_description.getText().length()>Constante.LONGUEUR_DESCRIPTION_MAINTENANCE)
		{
			Popup.getInstance().afficherPopup("La description doit contenir au maximum "+Constante.LONGUEUR_DESCRIPTION_MAINTENANCE+" caractères");
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
				Popup.getInstance().afficherPopup("La valeur du coût de la maintenance ne doit pas être négative");
				return false;
			}
			}
			catch(NumberFormatException nfe)
			{
				Popup.getInstance().afficherPopup("La valeur du coût saisie doit être un nombre");
				return false;
			}
		}
		return true;
	}
	
	public Materiel getActiveMateriel(){
		int index=0;
		switch(MainApp.getActiveTab()){
		case 0:
			index=2;
			break;
		case 1:
			index=0;
			break;
		case 2:
			index=0;
			break;
		case 3:
            //index=0;
			index=11;
			break;
		}
	return (Materiel)MainApp.getCritere(index);
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
