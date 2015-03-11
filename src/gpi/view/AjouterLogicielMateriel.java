package gpi.view;

import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.exception.PrimaryKeyException;
import gpi.metier.EstInstalle;
import gpi.metier.EstInstalleDAO;
import gpi.metier.Logiciel;
import gpi.metier.LogicielDAO;
import gpi.metier.Materiel;
import utils.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AjouterLogicielMateriel{

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	
	@FXML
	private ComboBox<String> comboboxLogiciel;

	private ObservableList<String> listNomLogiciel;
	private ObservableList<Integer> listIdLogiciel;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		LogicielDAO  logicielDAO = new LogicielDAO();
		listIdLogiciel = FXCollections.observableArrayList();
		listNomLogiciel = FXCollections.observableArrayList();
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
		return okClicked;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton AJOUTER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if(controlerSaisies()){
			EstInstalleDAO estInstalleDAO=new EstInstalleDAO();
			LogicielDAO logicielDAO = new LogicielDAO();
			int idLogiciel = listIdLogiciel.get(listNomLogiciel.indexOf(comboboxLogiciel.getValue()));
			try {
				Materiel materiel=getActiveMateriel();
				String nomLogiciel=logicielDAO.recupererNomLogicielParId(idLogiciel);
				EstInstalle estInstalle=new EstInstalle(materiel.getIdMateriel().getValue(),idLogiciel);
				estInstalleDAO.ajouterEstInstalle(estInstalle);
				Popup.getInstance().afficherPopup("Installation de '"+nomLogiciel+"' sur "+materiel.getNomMateriel().getValue()+" ajoutée");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			} catch (PrimaryKeyException pke){
				Popup.getInstance().afficherPopup(pke.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}

	}
	
	public Materiel getActiveMateriel(){
		int index=0;
		switch(MainApp.getActiveTab()){
		case 0:
			index=2;
			break;
		case 1:
			index=0;
			break;
		case 2:
			index=0;
			break;
		case 3:
            //index=0;
			index=11;
			break;
		}
	return (Materiel)MainApp.getCritere(index);
	}

	private boolean controlerSaisies(){
		if(comboboxLogiciel.getSelectionModel().getSelectedItem()==null){
			Popup.getInstance().afficherPopup("Le champ \"Logiciel à ajouter\" doit être rempli");
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

	@FXML
	private void handleChange() {
	}
}