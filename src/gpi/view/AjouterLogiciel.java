package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Constante;
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

public class AjouterLogiciel {
	@FXML
	private Stage dialogStage;
	@FXML
	private TextField nomLogicielField;
	@FXML
	private TextField versionLogicielField;
	@FXML
	private DatePicker dateExpirationGarantieField;

	@FXML
	private ComboBox<String> ComboboxFacture;

	private ObservableList<String> listFacture;
	private List<Integer> listFactureId;
	boolean isOkClicked=false;

	/**
	 * Initialise les donnees Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		FactureDAO factureDAO=new FactureDAO();
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
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton AJOUTER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if(controlerSaisies()){
			LogicielDAO logicielDAO = new LogicielDAO();
			FactureDAO factureDAO = new FactureDAO();
			int index=ComboboxFacture.getSelectionModel().getSelectedIndex();
			try {
				logicielDAO.ajouterLogiciel(new Logiciel(0,nomLogicielField.getText(),versionLogicielField.getText(),dateExpirationGarantieField.getValue(),factureDAO.recupererFactureParId(listFactureId.get(index))));
				new Popup("Logiciel "+nomLogicielField.getText()+" ajout� !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			dialogStage.close();
			isOkClicked=true;
		}		
	}
	
	private boolean controlerSaisies() {
		if(nomLogicielField.getText().isEmpty()){
			new Popup("Le champ \"Nom du logiciel\" doit �tre saisi");
			return false;
		}
		if(ComboboxFacture.getValue()==null){
			new Popup("Le champ \"Facture du logiciel\" doit �tre saisi");
			return false;
		}
		if(nomLogicielField.getText().length()>Constante.LONGUEUR_NOM_LOGICIEL){
			new Popup("La longueur du nom du logiciel saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NOM_LOGICIEL+" caract�res");
			return false;
		}
		if(versionLogicielField.getText().length()>Constante.LONGUEUR_VERSION_LOGICIEL){
			new Popup("La longueur de la version du logiciel saisi doit �tre inf�rieur � "+Constante.LONGUEUR_VERSION_LOGICIEL+" caract�res");
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

	public boolean isOkClicked() {
		return isOkClicked;
	}

}