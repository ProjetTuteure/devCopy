package gpi.view;

import gpi.bd.Donnee;
import gpi.metier.Facture;
import gpi.metier.Maintenance;
import gpi.metier.Prestataire;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class AjouterIntervention {

	@FXML
	private Stage dialogStage;

	@FXML
	private boolean okClicked = false;
	
	@FXML
	private ComboBox<String> comboboxnom;
	@FXML
	private ComboBox<String> comboboxprenom;
	@FXML
	private ComboBox<String> comboboxnum;
	@FXML
	private ComboBox<String> comboboxobj;
	@FXML
	private ComboBox<String> comboboxdate;

	private Donnee donnee = new Donnee();

	private ObservableList<String> listnom;
	private ObservableList<String> listprenom;
	private ObservableList<String> listnum;
	private ObservableList<String> listobj;
	private ObservableList<String> listdate;

	/**
	 * Initialise les données
	 */
	@FXML
	private void initialize() {
		listobj= FXCollections.observableArrayList();

		for (Maintenance m : donnee.getMaintenanceData()) {
			listobj.add(m.getObjetMaintenance());
		}
		comboboxobj.setItems(listobj);
		
		listnom = FXCollections.observableArrayList();

		for (Prestataire pr : donnee.getPrestataireData()) {
			listnom.add(pr.getNomPrestataire().getValue());
		}
		comboboxnom.setItems(listnom);
		
		listnum = FXCollections.observableArrayList();

		for (Facture fa : donnee.getFactureData()) {
			listnum.add(fa.getNumFacture());
		}
		comboboxnum.setItems(listnum);
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
	private void handleChange1() {
		Maintenance selected = donnee.getMaintenance(comboboxobj.getValue());
		listdate = FXCollections.observableArrayList();

		for (Maintenance pr : donnee.getMaintenanceData()) {
			if (pr.getObjetMaintenance()
					.equals(selected.getObjetMaintenance())) {
				listdate.add(pr.getdateMaintenance().toString());
			}
		}
		comboboxdate.setItems(listdate);
	}

	@FXML
	private void handleChange2() {
		Prestataire selected = donnee.getPrestaire(comboboxnom.getValue());
		listprenom = FXCollections.observableArrayList();

		for (Prestataire pr : donnee.getPrestataireData()) {
			if (pr.getNomPrestataire().getValue()
					.equals(selected.getNomPrestataire().getValue())) {
				listprenom.add(pr.getPrenomPrestataire().getValue());
			}
		}
		comboboxprenom.setItems(listprenom);
	}
	

}
