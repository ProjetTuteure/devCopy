package gpi.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Maintenance;
import gpi.metier.MaintenanceDAO;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class AjouterMaintenance {
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
	
	private MaintenanceDAO maintenanceDAO=new MaintenanceDAO();
	
	private List<Maintenance> listMaintenance;
	/**
	 * Initialise les donn�es
	 */
	@FXML
	private void initialize() {
		listMaintenance=new ArrayList<Maintenance>();
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
				new Popup("Maintenance du "+maintenance.getdateMaintenanceStringProperty().getValue()+" ajout�e !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
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
			new Popup("La date doit obligatoirement �tre remplie");
			return false;
		}
		else if(tf_objetMaintenance.getText().length()>Constante.LONGUEUR_OBJET_MAINTENANCE)
		{
			new Popup("L'objet de la maintenance doit faire au maximum "+Constante.LONGUEUR_OBJET_MAINTENANCE+" caract�res");
			return false;
		}
		else if(ta_description.getText().length()>Constante.LONGUEUR_DESCRIPTION_MAINTENANCE)
		{
			new Popup("La description doit contenir au maximum "+Constante.LONGUEUR_DESCRIPTION_MAINTENANCE+" caract�res");
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
				new Popup("La valeur du co�t de la maintenance ne doit pas �tre n�gative");
				return false;
			}
			}
			catch(NumberFormatException nfe)
			{
				new Popup("La valeur du co�t saisie doit �tre un nombre");
				return false;
			}
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
