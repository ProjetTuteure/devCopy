package gpi.view;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Utilisateur;
import gpi.metier.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class AjouterUtilisateur {

	String nomUtilisateur, prenomUtilisateur, telUtilisateur;

	@FXML
	private TextField nomField;
	@FXML
	private TextField prenomField;
	@FXML
	private TextField telField;
	@FXML
	private Stage dialogStage;

	UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

	@FXML
	private boolean okClicked = false;

	/**
	 * Initialise les données
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
	 * Cette methode permet de savoir si le bouton AJOUTER est clique
	 * 
	 * @return vrai si le bouton AJOUTER est clique
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
		if (nomField.getText().equals("")) {
			new Popup("Le champ \"Nom de l'Utilisateur\" doit être saisi");
			return false;
		}
		if (prenomField.getText().equals("")) {
			new Popup("Le champ \"Prenom de l'Utilisateur\" doit être saisi");
			return false;
		}
		if (telField.getText().length() > Constante.LONGUEUR_NUM_TELEPHONE) {
			new Popup("Le numéro de téléphone saisi doit être inférieur à "
					+ Constante.LONGUEUR_NUM_TELEPHONE + " caractères");
			return false;
		}
		if (nomField.getText().length() > Constante.LONGUEUR_NOM_UTILISATEUR) {
			new Popup(
					"La longueur du nom de l'utilisateur doit être inférieur à "
							+ Constante.LONGUEUR_NOM_UTILISATEUR
							+ " caractères");
			return false;
		}
		if (prenomField.getText().length() > Constante.LONGUEUR_NOM_UTILISATEUR) {
			new Popup(
					"La longueur du prénom de l'utilisateur doit être inférieur à "
							+ Constante.LONGUEUR_NOM_UTILISATEUR
							+ " caractères");
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

			Utilisateur utilisateurAAjouter = new Utilisateur(null,
					nomField.getText(),prenomField.getText(),telField.getText());
			try {
				utilisateurDAO.ajouterUtilisateur(utilisateurAAjouter);
				new Popup("Utilisateur "
						+ utilisateurAAjouter.getNomUtilisateur().getValue()
						+ " ajouté !");
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

	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public String getPrenomUtilisateur() {
		return prenomUtilisateur;
	}

	public void setPrenomUtilisateur(String prenomUtilisateur) {
		this.prenomUtilisateur = prenomUtilisateur;
	}

	public String getTelUtilisateur() {
		return telUtilisateur;
	}

	public void setTelUtilisateur(String telUtilisateur) {
		this.telUtilisateur = telUtilisateur;
	}

}