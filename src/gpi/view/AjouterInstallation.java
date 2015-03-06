package gpi.view;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.EstInstalle;
import gpi.metier.EstInstalleDAO;
import gpi.metier.EstMaintenu;
import gpi.metier.EstMaintenuDAO;
import gpi.metier.Logiciel;
import gpi.metier.LogicielDAO;
import gpi.metier.Maintenance;
import gpi.metier.MaintenanceDAO;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
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
	private ComboBox<String> comboboxNomMateriel;
	
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
		listIdMateriel = FXCollections.observableArrayList();
		LogicielDAO  logicielDAO = new LogicielDAO();
		listIdLogiciel = FXCollections.observableArrayList();
		listNomLogiciel = FXCollections.observableArrayList();
		try {
			for(PageMateriel pageMateriel : pageMaterielDAO.getAllMateriel()){
				listIdMateriel.add(Integer.parseInt(pageMateriel.getIdMateriel()));
				listNomMateriel.add(pageMateriel.getNomMateriel());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxNomMateriel.setItems(listNomMateriel);
		try {
			for(Logiciel logiciel : logicielDAO.recupererAllLogiciel()){
				listIdLogiciel.add(logiciel.getIdLogiciel());
				listNomLogiciel.add(logiciel.getNomLogiciel() + " " + logiciel.getVersionLogiciel());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxLogiciel.setItems(listNomLogiciel);
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
		EstInstalleDAO estInstalleDAO=new EstInstalleDAO();
		LogicielDAO logicielDAO=new LogicielDAO();
		MaterielDAO materielDAO=new MaterielDAO();
		int idMateriel = listIdMateriel.get(listNomMateriel.indexOf(comboboxNomMateriel.getValue()));
		int idLogiciel = listIdLogiciel.get(listNomLogiciel.indexOf(comboboxLogiciel.getValue()));
		try {
			EstInstalle estInstalle=new EstInstalle(idLogiciel,idMateriel);
			estInstalleDAO.ajouterEstInstalle(estInstalle);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		okClicked = true;
		dialogStage.close();
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