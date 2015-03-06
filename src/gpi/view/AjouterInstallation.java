package gpi.view;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.PageMateriel;
import gpi.metier.PageMaterielDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;


public class AjouterInstallation {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	
	@FXML
	private ComboBox<String> comboboxLogiciel;

	@FXML
	private ComboBox<String> comboboxNomMatariel;
	
	private ObservableList<String> listNomMateriel;
	private ObservableList<Integer> listIdMateriel;
	private ObservableList<String> listNomLogiciel;
	private ObservableList<Integer> listIdLogiciel;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		PageMaterielDAO pageMaterielDAO = new PageMaterielDAO();
		listNomMateriel = FXCollections.observableArrayList();
		try {
			for(PageMateriel pageMateriel : pageMaterielDAO.getAllMateriel()){
				
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
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

	@FXML
	private void handleChange() {

	}
}