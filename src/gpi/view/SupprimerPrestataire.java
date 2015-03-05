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

	private PrestataireDAO prestataireDAO = new PrestataireDAO();

	private ObservableList<String> listNomPrestataire;
	private ObservableList<String> listPrenomPrestataire;
	
	private List<Prestataire> listPrestataireParNom;

	private int indicePrestataireASupprimer;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		listNomPrestataire = FXCollections.observableArrayList();
		try {
			/*for (Prestataire prestataire : prestataireDAO
					.recupererAllPrestataire()) {
				listNomPrestataire.add(prestataire.getNomPrestataire()
						.getValue()+"- "+prestataire.getIdPrestataire()
						.getValue());
				listIdPrestataire
						.add(prestataire.getIdPrestataire().getValue());
			 	}*/
				listNomPrestataire=prestataireDAO.recupererAllNomPrestataire();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
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
		if (controlerSaisies()) {
			try {
				prestataireDAO.supprimerPrestataire(this.listPrestataireParNom.get(indicePrestataireASupprimer));
				Popup.getInstance().afficherPopup("Prestataire "+this.listPrestataireParNom.get(indicePrestataireASupprimer).
						getNomPrestataire().getValue()+" "+
						this.listPrestataireParNom.get(indicePrestataireASupprimer).getPrenomPrestataire().getValue()
						+" supprimé !");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}

	}

	private boolean controlerSaisies() {
		if (comboboxNomPrestataire.getValue() == null) {
			Popup.getInstance().afficherPopup("Vous devez selectionner le nom du prestataire à supprimer");
			return false;
		}
		if (comboboxPrenomPrestataire.getValue() == null) {
			Popup.getInstance().afficherPopup("Vous devez selectionner le prenom du prestataire à supprimer");
			return false;
		}
		return true;
	}

	/**
	 * Cette methode permet de pre remplir le combobox du prenom du prestataire
	 * lorsqu'un nom du prestataire est selectionne
	 */
	@FXML
	private void handlechange() {
		listPrestataireParNom=new ArrayList<Prestataire>();
		String nomPrestataireSelected=comboboxNomPrestataire.getValue();
		try {
			listPrestataireParNom = prestataireDAO.recupererPrestataireParNom(nomPrestataireSelected);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		listPrenomPrestataire = FXCollections.observableArrayList();
		for(Prestataire prestataire:listPrestataireParNom){
			listPrenomPrestataire.add(prestataire.getPrenomPrestataire().getValue());
		}
		/*try {
			for (Prestataire prestataire : prestataireDAO.recupererAllPrestataire()) {
				if (prestataire.getNomPrestataire().getValue().equals(selected.getNomPrestataire().getValue())) {
					listPrenomPrestataire.add(selected.getPrenomPrestataire().getValue());
					listeIdPrestataire.add(selected.getIdPrestataire().getValue());
					System.out.println(listPrenomPrestataire);
				}
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}*/
		comboboxPrenomPrestataire.setItems(listPrenomPrestataire);
	}

	@FXML
	private void handleChange2(){
		this.indicePrestataireASupprimer=comboboxPrenomPrestataire.getSelectionModel().getSelectedIndex();
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