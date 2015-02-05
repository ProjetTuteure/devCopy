package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Facture;
import gpi.metier.FactureDAO;
import gpi.metier.Logiciel;
import gpi.metier.LogicielDAO;
import gpi.metier.Revendeur;
import gpi.metier.RevendeurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Kevin
 */

public class ModifierLogiciel {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private TextField NomLogicielField;
	@FXML
	private TextField VersionLogicielField;
	@FXML
	private DatePicker DateExpirationLogiciel;
	@FXML
	private ComboBox<String> ComboboxFacture;
	@FXML
	private boolean choix1 = false;

	@FXML
	private ComboBox<String> ComboboxLogiciel;
	private ObservableList<String> listLogiciel;
	private ObservableList<String> listFacture;
	List<Logiciel> listObjetsLogiciel;
	List<Integer> listFactureId;
	

	@FXML
	private void initialize() {
		listLogiciel = FXCollections.observableArrayList();

		listObjetsLogiciel=new ArrayList<Logiciel>();
		LogicielDAO logicielDAO = new LogicielDAO();
		try{
			listObjetsLogiciel=logicielDAO.recupererAllLogiciel();
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		for (Logiciel logiciel : listObjetsLogiciel) {
			listLogiciel.add(logiciel.getNomLogiciel()+" "+logiciel.getVersionLogiciel());
		}
		
		ComboboxLogiciel.setItems(listLogiciel);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		LogicielDAO logicielDAO = new LogicielDAO();
		FactureDAO factureDAO = new FactureDAO();
		Facture facture=null;
		int indexFacture=ComboboxFacture.getSelectionModel().getSelectedIndex();
		if(indexFacture==-1){
			facture=listObjetsLogiciel.get(ComboboxLogiciel.getSelectionModel().getSelectedIndex()).getFactureLogiciel();
		}else{
			try {
				facture=factureDAO.recupererFactureParId(listFactureId.get(indexFacture));
			} catch (ConnexionBDException e) {
				// TODO Auto-generated catch block
				new Popup(e.getMessage());
			}
		}
		int indexLogiciel = ComboboxLogiciel.getSelectionModel().getSelectedIndex();
		int idLogiciel=listObjetsLogiciel.get(indexLogiciel).getIdLogiciel();
		try {
			logicielDAO.modifierLogiciel(new Logiciel(idLogiciel,NomLogicielField.getText(),VersionLogicielField.getText(),DateExpirationLogiciel.getValue(),facture));
		} catch (NumberFormatException e) {
			new Popup("Erreur de format. Format : 123.45");
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		okClicked = true;
		dialogStage.close();

	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handlechange() {
		FactureDAO factureDAO=new FactureDAO();
		int index = ComboboxLogiciel.getSelectionModel().getSelectedIndex();
		NomLogicielField.setText(listObjetsLogiciel.get(index).getNomLogiciel());
		VersionLogicielField.setText(listObjetsLogiciel.get(index).getVersionLogiciel());
		DateExpirationLogiciel.setValue(listObjetsLogiciel.get(index).getDateExpirationLogiciel());
		String numFacture=listObjetsLogiciel.get(index).getFactureLogiciel().getNumFacture();
		listFactureId=new ArrayList<Integer>();
		listFacture = FXCollections.observableArrayList();
		try {
			for (Facture facture : factureDAO.recupererAllFacture()){
				listFacture.add(facture.getNumFacture());
				listFactureId.add(facture.getIdFacture().getValue());
			}
		} catch (ConnexionBDException e) {
			// TODO Auto-generated catch block
			new Popup(e.getMessage());
		}
		ComboboxFacture.setItems(listFacture);
		ComboboxFacture.setPromptText(numFacture);
	}
}