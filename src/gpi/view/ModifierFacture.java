package gpi.view;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Facture;
import gpi.metier.FactureDAO;
import gpi.metier.Revendeur;
import gpi.metier.RevendeurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierFacture {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> factureCombobox;

	private ObservableList<String> listFacture;

	@FXML
	private TextField numFactureField;
	@FXML
	private DatePicker dateFacturePicker;
	@FXML
	private TextField montantFactureField;
	@FXML
	private ComboBox<String> numRevendeurCombobox;
	List<Integer> listIdFacture;
	private ObservableList<String> listRevendeurObservable;
	List<Integer> listRevendeurId;

	@FXML
	private void initialize() {
		listFacture = FXCollections.observableArrayList();
		listIdFacture=new ArrayList<Integer>();
		FactureDAO factureDAO = new FactureDAO();

		try {
			for (Facture facture : factureDAO.recupererAllFacture()) {
				listIdFacture.add(facture.getIdFacture().getValue());
				listFacture.add(facture.getNumFacture() +" - "+ facture.getDateFacStringProperty().get());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		
		factureCombobox.setItems(listFacture);
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
			FactureDAO factureDAO = new FactureDAO();
			RevendeurDAO revendeurDAO = new RevendeurDAO();
			Revendeur revendeur=null;
			int indexRevendeur=numRevendeurCombobox.getSelectionModel().getSelectedIndex();
			int idFacture=listIdFacture.get(factureCombobox.getSelectionModel().getSelectedIndex());
			try {
				if(indexRevendeur==-1){
					if(numRevendeurCombobox.getPromptText()==null){
						revendeur=null;
					}else{
						revendeur=factureDAO.recupererFactureParId(idFacture).getRevendeurFacture();
					}					
				}else{
					revendeur=revendeurDAO.recupererRevendeurParId(listRevendeurId.get(indexRevendeur));
				}
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			try {
				factureDAO.modifierFacture(new Facture(idFacture,numFactureField.getText(),dateFacturePicker.getValue(),Float.parseFloat(montantFactureField.getText()),revendeur));
				Popup.getInstance().afficherPopup("Facture "+numFactureField.getText()+" modifiée");
			}  catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}

	}

	private boolean controlerSaisies() {
		if(factureCombobox.getValue()==null){
			Popup.getInstance().afficherPopup("Le champ \"date de facture\" doit être saisi");
			return false;
		}
		if(numFactureField.getText().isEmpty()){
			Popup.getInstance().afficherPopup("Le champ \"Numéro de facture\" doit être saisi");
			return false;
		}
		if(dateFacturePicker.getValue()==null){
			Popup.getInstance().afficherPopup("Le champ \"date de facture\" doit être saisi");
			return false;
		}
		if(numFactureField.getText().length()>Constante.LONGUEUR_NUM_FACTURE){
			Popup.getInstance().afficherPopup("La longueur du numero de facture saisi doit être inférieur à "+Constante.LONGUEUR_NUM_FACTURE+" caract�res");
			return false;
		}
		if(montantFactureField.getText().length()>Constante.LONGUEUR_MONTANT_FACTURE){
			Popup.getInstance().afficherPopup("La longueur du montant de la facture saisi doit être inférieur à "+Constante.LONGUEUR_MONTANT_FACTURE+" caract�res");
			return false;
		}
		Pattern p = Pattern.compile("[0-9]{1,8}[.]{1}[0-9]{1,2}");
		Matcher m = p.matcher(montantFactureField.getText());
		if(!m.matches()){
			Popup.getInstance().afficherPopup("Le format du montant de la facture est erroné. Format : 123.45");
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
		RevendeurDAO revendeurDAO=new RevendeurDAO();
		Facture factureAModifie=null;
		try {
			factureAModifie = factureDAO.recupererFactureParId(listIdFacture.get(factureCombobox.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e1) {
			e1.printStackTrace();
		}
		numFactureField.setText(factureAModifie.getNumFacture());
		dateFacturePicker.setValue(factureAModifie.getDateFacture());
		montantFactureField.setText(factureAModifie.getMontantFactureStringProperty().getValue());
		String nomRevendeur=null;
		if(factureAModifie.getRevendeurFacture()!=null){
			nomRevendeur=factureAModifie.getRevendeurFacture().getNomRevendeur().getValue();
		}
		 
		listRevendeurId=new ArrayList<Integer>();
		listRevendeurObservable = FXCollections.observableArrayList();
		try {
			for (Revendeur revendeur : revendeurDAO.recupererAllRevendeur()){
				listRevendeurObservable.add(revendeur.getNomRevendeur().getValue());
				listRevendeurId.add(revendeur.getIdRevendeur().getValue());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		numRevendeurCombobox.setItems(listRevendeurObservable);
		numRevendeurCombobox.setPromptText(nomRevendeur);
	}
}