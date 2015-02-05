package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Fabricant;
import gpi.metier.FabricantDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Constante;
import utils.Popup;


public class AjouterFabricant {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private TextField nomFabricantField;
	@FXML
	private TextField telFabricantField;
	@FXML
	private TextField mobileFabricantField;
	@FXML
	private TextField faxFabricantField;
	@FXML
	private TextField emailFabricantField;
	@FXML
	private TextField adresseFabricantField;

	private FabricantDAO fabricantDAO=new FabricantDAO();
	/**
	 * Initialise les donnees
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
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton AJOUTER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if(controlerSaisies())
		{
			Fabricant fabricantAAjouter=new Fabricant(0,nomFabricantField.getText(),telFabricantField.getText(),mobileFabricantField.getText(),faxFabricantField.getText(),emailFabricantField.getText(),adresseFabricantField.getText());
			try {
				fabricantDAO.ajouterFabricant(fabricantAAjouter);
				new Popup("Fabricant "+fabricantAAjouter.getNomFabricantString()+" ajout� !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}
	}

	private boolean controlerSaisies() {
		if(nomFabricantField.getText().isEmpty()){
			new Popup("Le champ \"Nom du fabricant\" doit �tre saisi");
			return false;
		}
		if(nomFabricantField.getText().length()>Constante.LONGUEUR_NOM_FABRICANT){
			new Popup("La longueur du nom du fabricant saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NOM_FABRICANT+" caract�res");
			return false;
		}		
		if(telFabricantField.getText().length()>Constante.LONGUEUR_NUM_TELEPHONE){
			new Popup("Le num�ro de t�l�phone saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NUM_TELEPHONE+" caract�res");
			return false;
		}
		if(mobileFabricantField.getText().length()>Constante.LONGUEUR_NUM_MOBILE){
			new Popup("Le num�ro de mobile saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NUM_MOBILE+" caract�res");
			return false;
		}
		if(faxFabricantField.getText().length()>Constante.LONGUEUR_NUM_FAX){
			new Popup("Le num�ro de fax saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NUM_FAX+" caract�res");
			return false;
		}
		if(emailFabricantField.getText().length()>Constante.LONGUEUR_MAIL){
			new Popup("Le num�ro de fax saisi doit �tre inf�rieur � "+Constante.LONGUEUR_MAIL+" caract�res");
			return false;
		}
		if(adresseFabricantField.getText().length()>Constante.LONGUEUR_ADRESSE){
			new Popup("L'adresse saisie doit �tre inf�rieur � "+Constante.LONGUEUR_ADRESSE+" caract�res");
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