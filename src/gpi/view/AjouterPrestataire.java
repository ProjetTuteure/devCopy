package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Prestataire;
import gpi.metier.PrestataireDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Constante;
import utils.Popup;

/**
 * Created by Kevin
 */

public class AjouterPrestataire {

	@FXML
	private Stage dialogStage;

	@FXML
	private boolean okClicked = false;

	@FXML
	private TextField nomPrestataireField;

	@FXML
	private TextField prenomPrestataireField;

	@FXML
	private TextField telPrestataireField;

	@FXML
	private TextField mobilePrestataireField;

	@FXML
	private TextField faxPrestataireField;

	@FXML
	private TextField emailPrestataireField;

	@FXML
	private TextField societePrestataireField;

	private PrestataireDAO prestataireDAO = new PrestataireDAO();

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
	 * Controle les saisies
	 * 
	 * @return vrai si les données saisies sont cohérentes, faux sinon
	 */
	public boolean controlerSaisies() {
		if (nomPrestataireField.getText().equals("")) {
			new Popup("Le champ \"Nom du prestataire\" doit être saisi");
			return false;
		}
		if (nomPrestataireField.getText().length() > Constante.LONGUEUR_NOM_PRESTATAIRE) {
			new Popup("Le nom du prestataire saisi doit être inférieur à "
					+ Constante.LONGUEUR_NOM_PRESTATAIRE + " caractères");
			return false;
		}
		if (prenomPrestataireField.getText().equals("")) {
			new Popup("Le champ \"Prenom du prestataire\" doit être saisi");
			return false;
		}
		if (prenomPrestataireField.getText().length() > Constante.LONGUEUR_NOM_PRESTATAIRE) {
			new Popup("Le prenom du prestataire saisi doit être inférieur à "
					+ Constante.LONGUEUR_NOM_PRESTATAIRE + " caractères");
			return false;
		}
		if (telPrestataireField.getText().length() > Constante.LONGUEUR_NUM_TELEPHONE) {
			new Popup(
					"Le téléphone du prestataire saisi doit être inférieur à "
							+ Constante.LONGUEUR_NUM_TELEPHONE + " caractères");
			return false;
		}
		if (mobilePrestataireField.getText().length() > Constante.LONGUEUR_NUM_MOBILE) {
			new Popup("Le mobile du prestataire saisi doit être inférieur à "
					+ Constante.LONGUEUR_NUM_MOBILE + " caractères");
			return false;
		}
		if (faxPrestataireField.getText().length() > Constante.LONGUEUR_NUM_FAX) {
			new Popup("Le fax du prestataire saisi doit être inférieur à "
					+ Constante.LONGUEUR_NUM_FAX + " caractères");
			return false;
		}
		if (emailPrestataireField.getText().length() > Constante.LONGUEUR_MAIL) {
			new Popup("L'email du prestataire saisi doit être inférieur à "
					+ Constante.LONGUEUR_MAIL + " caractères");
			return false;
		}
		if (societePrestataireField.getText().length() > Constante.LONGUEUR_SOCIETE) {
			new Popup("La societe du prestataire saisi doit être inférieur à "
					+ Constante.LONGUEUR_SOCIETE + " caractères");
			return false;
		}
		return true;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton AJOUTER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if (controlerSaisies() == true) {
			Prestataire prestataireAAjouter = new Prestataire(0,
					nomPrestataireField.getText(),
					prenomPrestataireField.getText(),
					telPrestataireField.getText(),
					mobilePrestataireField.getText(),
					faxPrestataireField.getText(),
					emailPrestataireField.getText(),
					societePrestataireField.getText());
			try {
				prestataireDAO.ajouterPrestataire(prestataireAAjouter);
				new Popup("Prestataire " + nomPrestataireField.getText() + " "
						+ prenomPrestataireField.getText() + " ajouté !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
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

}