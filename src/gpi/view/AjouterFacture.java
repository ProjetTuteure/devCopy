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


public class AjouterFacture {
	@FXML
	private Stage dialogStage;
	@FXML
	private TextField numFactureField;
	@FXML
	private DatePicker dateFacturePicker;
	@FXML
	private TextField montantFactureField;
	@FXML
	private ComboBox<String> numRevendeurCombobox;
	@FXML
	private boolean okClicked = false;
	private ObservableList<String> listRevendeurObservable;
	List<Integer> listRevendeurId;

	/**
	 * Initialise les données
	 */
	@FXML
	private void initialize() {
		RevendeurDAO revendeurDAO=new RevendeurDAO();
		listRevendeurId=new ArrayList<Integer>();
		listRevendeurObservable = FXCollections.observableArrayList();
		try {
			for (Revendeur revendeur : revendeurDAO.recupererAllRevendeur()){
				listRevendeurObservable.add(revendeur.getNomRevendeur().getValue());
				listRevendeurId.add(revendeur.getIdRevendeur().getValue());
			}
		} catch (ConnexionBDException e) {
			// TODO Auto-generated catch block
			new Popup(e.getMessage());
		}
		numRevendeurCombobox.setItems(listRevendeurObservable);
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
			FactureDAO factureDAO = new FactureDAO();
			RevendeurDAO revendeurDAO = new RevendeurDAO();
			int index=numRevendeurCombobox.getSelectionModel().getSelectedIndex();
			try {
				factureDAO.ajouterFacture(new Facture(0,numFactureField.getText(),dateFacturePicker.getValue(),Float.parseFloat(montantFactureField.getText()),revendeurDAO.recupererRevendeurParId(listRevendeurId.get(index))));
				new Popup("Facture "+numFactureField.getText()+" ajouté !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}
		
	}

	private boolean controlerSaisies() {
		if(numFactureField.getText().isEmpty()){
			new Popup("Le champ \"Numéro de facture\" doit être saisi");
			return false;
		}
		if(dateFacturePicker.getValue()==null){
			new Popup("Le champ \"date de facture\" doit être saisi");
			return false;
		}
		if(numFactureField.getText().length()>Constante.LONGUEUR_NUM_FACTURE){
			new Popup("La longueur du numero de facture saisi doit être inférieur à "+Constante.LONGUEUR_NUM_FACTURE+" caractères");
			return false;
		}
		if(montantFactureField.getText().length()>Constante.LONGUEUR_MONTANT_FACTURE){
			new Popup("La longueur du montant de la facture saisi doit être inférieur à "+Constante.LONGUEUR_MONTANT_FACTURE+" caractères");
			return false;
		}
		Pattern p = Pattern.compile("[0-9]{1,8}[.]{1}[0-9]{1,2}");
		Matcher m = p.matcher(montantFactureField.getText());
		if(!m.matches()){
			new Popup("Le format du montant de la facture est erroné. Format : 123.45");
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

}