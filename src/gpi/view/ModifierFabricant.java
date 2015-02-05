package gpi.view;

import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Fabricant;
import gpi.metier.FabricantDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Constante;
import utils.Popup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kevin
 */

public class ModifierFabricant {
	private int idFabriquant;

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxfabr;

	private ObservableList<String> listNomFabricant;
	private List<Integer> listIdFabricant;

	private FabricantDAO fabricantDAO=new FabricantDAO();

	@FXML
	private TextField nomFabricantField;
	@FXML
	private TextField telFabricantField;
	@FXML
	private TextField mobileFabricantField;
	@FXML
	private TextField faxFabricantField;
	@FXML
	private TextField emailFabricantField;
	@FXML
	private TextField adresseFabricantField;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		listNomFabricant = FXCollections.observableArrayList();
		listIdFabricant = new ArrayList<Integer>();
		try {
			for (Fabricant fabricant : fabricantDAO.recupererAllFabricant()) {
                listNomFabricant.add(fabricant.getNomFabricant().getValue());
                listIdFabricant.add(fabricant.getIdFabricant().getValue());
            }
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxfabr.setItems(listNomFabricant);
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
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton MODIFIER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if(controlerSaisies()==true)
		{
			Fabricant fabricantAModifier=new Fabricant(this.getIdFabriquant(),nomFabricantField.getText(),telFabricantField.getText(),mobileFabricantField.getText(),faxFabricantField.getText(),emailFabricantField.getText(),adresseFabricantField.getText());
			try {
				fabricantDAO.modifierFabricant(fabricantAModifier);
				new Popup("Fabricant "+fabricantAModifier.getNomFabricantString()+" modifi� !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}
	}

	private boolean controlerSaisies() {
		if(nomFabricantField.getText().isEmpty()){
			new Popup("Le champ \"Nom du fabricant\" doit �tre saisi");
			return false;
		}
		if(nomFabricantField.getText().length()>Constante.LONGUEUR_NOM_FABRICANT){
			new Popup("La longueur du nom du fabricant saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NOM_FABRICANT+" caract�res");
			return false;
		}		
		if(telFabricantField.getText().length()>Constante.LONGUEUR_NUM_TELEPHONE){
			new Popup("Le num�ro de t�l�phone saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NUM_TELEPHONE+" caract�res");
			return false;
		}
		if(mobileFabricantField.getText().length()>Constante.LONGUEUR_NUM_MOBILE){
			new Popup("Le num�ro de mobile saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NUM_MOBILE+" caract�res");
			return false;
		}
		if(faxFabricantField.getText().length()>Constante.LONGUEUR_NUM_FAX){
			new Popup("Le num�ro de fax saisi doit �tre inf�rieur � "+Constante.LONGUEUR_NUM_FAX+" caract�res");
			return false;
		}
		if(emailFabricantField.getText().length()>Constante.LONGUEUR_MAIL){
			new Popup("Le num�ro de fax saisi doit �tre inf�rieur � "+Constante.LONGUEUR_MAIL+" caract�res");
			return false;
		}
		if(adresseFabricantField.getText().length()>Constante.LONGUEUR_ADRESSE){
			new Popup("L'adresse saisie doit �tre inf�rieur � "+Constante.LONGUEUR_ADRESSE+" caract�res");
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

	/**
	 * Cette methode permet de savoir si le bouton MODIFIER est clique ou pas
	 * 
	 * @return vrai si le bouton MODIFIER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Cette methode permet de pre remplir les champs lorsqu'un Fabricant est
	 * selectionne
	 */
	@FXML
	private void handlechange() {
		this.setIdFabriquant(listIdFabricant.get(comboboxfabr.getSelectionModel().getSelectedIndex()));
		Fabricant selected= null;
		try {
			selected = fabricantDAO.recupererFabricantParId(this.getIdFabriquant());
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		nomFabricantField.setText(selected.getNomFabricant().getValue());
		telFabricantField.setText(selected.getTelFabricant().getValue());
		mobileFabricantField.setText(selected.getMobileFabricant().getValue());
		faxFabricantField.setText(selected.getFaxFabricant().getValue());
		emailFabricantField.setText(selected.getEmailFabricant().getValue());
		adresseFabricantField.setText(selected.getAdresseFabricant().getValue());
	}

	public int getIdFabriquant() {
		return idFabriquant;
	}

	public void setIdFabriquant(int idFabriquant) {
		this.idFabriquant = idFabriquant;
	}

}