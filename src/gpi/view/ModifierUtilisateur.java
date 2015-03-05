package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Utilisateur;
import gpi.metier.UtilisateurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class ModifierUtilisateur {
	int idUtilisateur;
	String nomUtilisateur, prenomUtilisateur, telUtilisateur;

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private TextField nomfield;
	@FXML
	private TextField prenomfield;
	@FXML
	private TextField telfield;
	@FXML
	private boolean choix1 = false;

	@FXML
	private ComboBox<String> comboboxnom;
	@FXML
	private ComboBox<String> comboboxprenom;

	private ObservableList<String> listnom;
	private ObservableList<String> listprenom;

	private List<Utilisateur> listeNom;
	private List<Utilisateur> listePrenom;

	private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {

		listnom = FXCollections.observableArrayList();
		listeNom = new ArrayList<Utilisateur>();
		try {
			listeNom = utilisateurDAO.recupererAllUtilisateur();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		for (Utilisateur utilisateur : listeNom) {
			listnom.add(utilisateur.getNomUtilisateur().getValue());
		}
		comboboxnom.setItems(listnom);
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

	private boolean controlerSaisies() {
		if (nomfield.getText().equals("")) {
			Popup.getInstance().afficherPopup("Le champ \"Nom du utilisateur\" doit �tre rempli");
			return false;
		}
		if (prenomfield.getText().equals("")) {
			Popup.getInstance().afficherPopup("Le champ \"Prenom du utilisateur\" doit �tre rempli");
			return false;
		}
		if (telfield.getText().length() > Constante.LONGUEUR_NUM_TELEPHONE) {
			Popup.getInstance().afficherPopup("Le num�ro de t�l�phone saisi doit �tre inf�rieur � "
					+ Constante.LONGUEUR_NUM_TELEPHONE + " caract�res");
			return false;
		}

		return true;
	}

	/**
	 * Cette methode permet de savoir si le bouton MODIFIER est clique ou pas
	 * 
	 * @return vrai si le bouton MODIFIER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton MODIFIER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if (controlerSaisies() == true) {

			int indexRevendeurSelectionne = comboboxprenom.getSelectionModel()
					.getSelectedIndex();
			Utilisateur utilisateur = listePrenom
					.get(indexRevendeurSelectionne);
			utilisateur.setNomUtilisateur(nomfield.getText());
			utilisateur.setPrenomUtilisateur(prenomfield.getText());
			utilisateur.setTelUtilisateur(telfield.getText());
			try {
				utilisateurDAO.modifierUtilisateur(utilisateur);
				Popup.getInstance().afficherPopup("Utilisateur "
						+ utilisateur.getNomUtilisateur().getValue()
						+ " modifi� !");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
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

	/**
	 * Cette methode permet de pre remplir le combobox avec les prenoms afin
	 * d'avoir un seul et unique prestataire cibl�
	 */
	@FXML
	private void handlechange1() {
		listprenom = FXCollections.observableArrayList();
		int indexUtilisateurSelectionne = comboboxnom.getSelectionModel()
				.getSelectedIndex();
		try {
			listePrenom = utilisateurDAO.recupererUtilisateurParNom(listnom
					.get(comboboxnom.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		Utilisateur selected = listeNom.get(indexUtilisateurSelectionne);

		for (Utilisateur ut : listePrenom) {
			if (ut.getNomUtilisateur().getValue()
					.equals(selected.getNomUtilisateur().getValue())) {
				listprenom.add(ut.getPrenomUtilisateur().getValue());
			}
		}
		comboboxprenom.setItems(listprenom);
	}

	/**
	 * Cette methode permet de pre remplir les champs lorsqu'un prestataire est
	 * selectionne avec son pr�nom et son nom
	 */
	@FXML
	private void handlechange2() {
		try {
			int indexUtilisateurSelectionne = comboboxprenom
					.getSelectionModel().getSelectedIndex();
			Utilisateur selected2 = listePrenom
					.get(indexUtilisateurSelectionne);

			nomfield.setText(selected2.getNomUtilisateur().getValue());
			prenomfield.setText(selected2.getPrenomUtilisateur().getValue());
			telfield.setText(selected2.getTelUtilisateur().getValue());
		} catch (NullPointerException e) {

		}
	}
}
