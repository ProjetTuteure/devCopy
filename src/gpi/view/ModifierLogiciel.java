package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Facture;
import gpi.metier.FactureDAO;
import gpi.metier.Logiciel;
import gpi.metier.LogicielDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModifierLogiciel {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private TextField nomLogicielField;
	@FXML
	private TextField versionLogicielField;
	@FXML
	private DatePicker dateExpirationLogicielPicker;
	@FXML
	private ComboBox<String> comboboxFacture;
	@FXML
	private boolean choix1 = false;

	@FXML
	private ComboBox<String> comboboxLogiciel;
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
		
		comboboxLogiciel.setItems(listLogiciel);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if(controlerSaisies()){
			LogicielDAO logicielDAO = new LogicielDAO();
			FactureDAO factureDAO = new FactureDAO();
			Facture facture=null;
			int indexFacture=comboboxFacture.getSelectionModel().getSelectedIndex();
			if(indexFacture==-1){
				facture=listObjetsLogiciel.get(comboboxLogiciel.getSelectionModel().getSelectedIndex()).getFactureLogiciel();
			}else{
				try {
					facture=factureDAO.recupererFactureParId(listFactureId.get(indexFacture));
				} catch (ConnexionBDException e) {
					new Popup(e.getMessage());
				}
			}
			int indexLogiciel = comboboxLogiciel.getSelectionModel().getSelectedIndex();
			int idLogiciel=listObjetsLogiciel.get(indexLogiciel).getIdLogiciel();
			try {
				logicielDAO.modifierLogiciel(new Logiciel(idLogiciel,nomLogicielField.getText(),versionLogicielField.getText(),dateExpirationLogicielPicker.getValue(),facture));
				new Popup("Logiciel "+nomLogicielField.getText()+" modifié !");
			}  catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}
	}
	
	private boolean controlerSaisies() {
		if(comboboxLogiciel.getValue()==null){
			new Popup("Vous devez selectionner le logiciel à modifier");
			return false;
		}
		if(nomLogicielField.getText().isEmpty()){
			new Popup("Le champ \"Nom du logiciel\" doit être saisi");
			return false;
		}
		if(comboboxFacture.getPromptText()==null){
			new Popup("Le champ \"Facture du logiciel\" doit être saisi");
			return false;
		}
		if(nomLogicielField.getText().length()>Constante.LONGUEUR_NOM_LOGICIEL){
			new Popup("La longueur du nom du logiciel saisi doit être inférieur à "+Constante.LONGUEUR_NOM_LOGICIEL+" caractères");
			return false;
		}
		if(versionLogicielField.getText().length()>Constante.LONGUEUR_VERSION_LOGICIEL){
			new Popup("La longueur de la version du logiciel saisi doit être inférieur à "+Constante.LONGUEUR_VERSION_LOGICIEL+" caractères");
			return false;
		}	
		return true;
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handlechange() {
		FactureDAO factureDAO=new FactureDAO();
		int index = comboboxLogiciel.getSelectionModel().getSelectedIndex();
		nomLogicielField.setText(listObjetsLogiciel.get(index).getNomLogiciel());
		versionLogicielField.setText(listObjetsLogiciel.get(index).getVersionLogiciel());
		dateExpirationLogicielPicker.setValue(listObjetsLogiciel.get(index).getDateExpirationLogiciel());
		String numFacture=listObjetsLogiciel.get(index).getFactureLogiciel().getNumFacture();
		listFactureId=new ArrayList<Integer>();
		listFacture = FXCollections.observableArrayList();
		try {
			for (Facture facture : factureDAO.recupererAllFacture()){
				listFacture.add(facture.getNumFacture());
				listFactureId.add(facture.getIdFacture().getValue());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxFacture.setItems(listFacture);
		comboboxFacture.setPromptText(numFacture);
	}
}