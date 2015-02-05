package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Utilisateur;
import gpi.metier.UtilisateurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class SupprimerUtilisateur {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

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
	 * Initialise les donnï¿½es Ajoute les donnï¿½es aux combobox
	 */
	@FXML
	private void initialize() {
		listnom = FXCollections.observableArrayList();
		listeNom = new ArrayList<Utilisateur>();
		try {
			listeNom = utilisateurDAO.recupererAllUtilisateur();
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
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
		okClicked = true;
		
		if (comboboxnom.getValue() == null) {
			new Popup("Veuillez choisir un nom d'utilisateur");
		} else if (comboboxprenom.getValue() == null) {
			new Popup("Veuillez choisir un prenom d'utilisateur");
		} else {
			int indexRevendeurSelectionne = comboboxprenom.getSelectionModel()
					.getSelectedIndex();
			Utilisateur utilisateur = listePrenom
					.get(indexRevendeurSelectionne);
			try {
				if (utilisateurDAO.supprimerUtilisateur(utilisateur) == true) {
					dialogStage.close();
					new Popup("Utilisateur supprimé !");
				} else {
					new Popup("Echec lors de la suppression");
				}
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
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
	 * Cette methode permet de pre remplir le combobox du prenom du prestataire
	 * lorsqu'un nom du prestataire est selectionne
	 */
	@FXML
	private void handlechange() {
		listprenom = FXCollections.observableArrayList();
		int indexUtilisateurSelectionne = comboboxnom.getSelectionModel()
				.getSelectedIndex();
		try {
			listePrenom = utilisateurDAO.recupererUtilisateurParNom(listnom
					.get(comboboxnom.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
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
}
