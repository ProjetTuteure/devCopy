package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Prestataire;
import gpi.metier.PrestataireDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import utils.Popup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin
 */

public class SupprimerPrestataire {
	private int idPrestataire;

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxNomPrestataire;
	@FXML
	private ComboBox<String> comboboxPrenomPrestataire;

	private PrestataireDAO prestataireDAO=new PrestataireDAO();

	private ObservableList<String> listNomPrestataire;
	private ObservableList<String> listPrenomPrestataire;

	private List<Integer> listIdPrestataire;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		listNomPrestataire = FXCollections.observableArrayList();
		listIdPrestataire=new ArrayList<Integer>();
		try {
			for (Prestataire prestataire : prestataireDAO.recupererAllPrestataire()) {
                listNomPrestataire.add(prestataire.getNomPrestataire().getValue());
				listIdPrestataire.add(prestataire.getIdPrestataire().getValue());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxNomPrestataire.setItems(listNomPrestataire);
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
		try {
			prestataireDAO.supprimerPrestataire(new Prestataire(listIdPrestataire.get(comboboxPrenomPrestataire.getSelectionModel().getSelectedIndex()), null, null,null,null,null,null,null));
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		okClicked = true;
		dialogStage.close();

	}

	/**
	 * Cette methode permet de pre remplir le combobox du prenom du prestataire
	 * lorsqu'un nom du prestataire est selectionne
	 */
	@FXML
	private void handlechange() {
		this.setIdPrestataire(listIdPrestataire.get(comboboxNomPrestataire.getSelectionModel().getSelectedIndex()));
		Prestataire selected = null;
		try {
			selected = prestataireDAO.recupererPrestataireParId(this.getIdPrestataire());
		} catch (ConnexionBDException e) {
			e.printStackTrace();
		}
		listIdPrestataire=new ArrayList<Integer>();
		listPrenomPrestataire = FXCollections.observableArrayList();
		try {
			for (Prestataire prestataire : prestataireDAO.recupererAllPrestataire()) {
                if (prestataire.getNomPrestataire().getValue().equals(selected.getNomPrestataire().getValue())) {
                    listPrenomPrestataire.add(selected.getPrenomPrestataire().getValue());
					listIdPrestataire.add(selected.getIdPrestataire().getValue());
                }
            }
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxPrenomPrestataire.setItems(listPrenomPrestataire);
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	public int getIdPrestataire() {
		return idPrestataire;
	}

	public void setIdPrestataire(int idPrestataire) {
		this.idPrestataire = idPrestataire;
	}

}