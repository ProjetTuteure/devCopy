package gpi.view;


import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import gpi.metier.PageMateriel;
import gpi.metier.PageMaterielDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SupprimerMateriel {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxMateriel;

	private ObservableList<String> listNomMateriel;
	private List<String> listIdMateriel;
	

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		listNomMateriel = FXCollections.observableArrayList();
		listIdMateriel = new ArrayList<String>();
		PageMaterielDAO pageMaterielDAO=new PageMaterielDAO();
		
		try {
			for (PageMateriel materiel : pageMaterielDAO.getAllMateriel()) {
				listNomMateriel.add(materiel.getNomMateriel());
				listIdMateriel.add(materiel.getIdMateriel());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxMateriel.setItems(listNomMateriel);
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
		MaterielDAO materielDAO=new MaterielDAO();
		try {
			materielDAO.supprimerMateriel(new Materiel(Integer.parseInt(listIdMateriel.get(comboboxMateriel.getSelectionModel().getSelectedIndex())), null, null, null, null, null, null, null, null, null, null,null,null));
			Popup.getInstance().afficherPopup("Materiel "+comboboxMateriel.getValue()+" supprimé");
		} catch (NumberFormatException e) {
			Popup.getInstance().afficherPopup("L'id du materiel doit être un entier");
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		okClicked = true;
		dialogStage.close();

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