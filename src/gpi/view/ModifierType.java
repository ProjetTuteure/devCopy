package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Type;
import gpi.metier.TypeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.*;
import utils.Popup;

import javax.swing.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin
 */

public class ModifierType {
	private int idType;
	private String cheminImageType;

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxTypeMod;

	TypeDAO typeDAO=new TypeDAO();

	private ObservableList<String> listNomType;
	private List<Integer> listIdType;

	@FXML
	private TextField nomTypeField;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		listNomType = FXCollections.observableArrayList();
		listIdType=new ArrayList<Integer>();
		try {
			for (Type type : typeDAO.recupererAllType()) {
                listNomType.add(type.getNomTypeString());
				listIdType.add(type.getIdType());
				
            }
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxTypeMod.setItems(listNomType);
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
	 * Cette methode permet de savoir si le bouton MODIFIER est clique ou pas
	 * 
	 * @return vrai si le bouton MODIFIER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton MODIFIER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if(controlerSaisies())
		{
			okClicked = true;
			Type typeAModifie=new Type(this.getIdType(),nomTypeField.getText(),this.getCheminImageType());
			try {
				typeDAO.modifierType(typeAModifie);
				new Popup("Type "+typeAModifie.getNomTypeString()+" modifié !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			dialogStage.close();
		}

	}

	private boolean controlerSaisies() {
		if(comboboxTypeMod==null)
		{
			new Popup("Vous devez selectionner le type à modifier");
			return false;
		}
		if(nomTypeField.getText().isEmpty())
		{
			new Popup("Le champ \"Nom du type\" doit �tre saisi");
			return false;
		}
		if(nomTypeField.getText().length()>Constante.LONGUEUR_NOM_TYPE){
			new Popup("La longueur du nom du type doit être inférieur à "+Constante.LONGUEUR_NOM_TYPE+" caractères");
			return false;
		}
		if(this.getCheminImageType()!=null){
			if(this.getCheminImageType().length()>Constante.LONGUEUR_CHEMIN_IMAGE){
				new Popup("La longueur du chemin doit être inférieur à "+Constante.LONGUEUR_CHEMIN_IMAGE+" caractères");
				return false;
			}
		}
		else{
			new Popup("Une image doit être sélectionnée");
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
	 * Cette methode permet de faire apparaitre un Filechooser lorsqu'on clique
	 * sur "choisir l'image"
	 * 
	 * @param event
	 *            un evenement sur le bouton "choisir l'image"
	 */
	@FXML
	private void handleChoose(ActionEvent event) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open File");
		fileChooser.showOpenDialog(null); // you could pass a stage
		File file = fileChooser.getSelectedFile();												// reference here if you


		if (file != null) {
			String adresse=file.getAbsolutePath();
			adresse=adresse.replace("\\", "/");
			adresse="file:///"+adresse;
			this.setCheminImageType(adresse);
		}

	}

	/**
	 * Cette methode permet de pre remplir les champs lorsqu'un type est
	 * selectionne
	 */
	@FXML
	private void handlechange() {
		this.setIdType(listIdType.get(comboboxTypeMod.getSelectionModel().getSelectedIndex()));
		Type selected= null;
		try {
			selected = typeDAO.recupererTypeParId(this.getIdType());
			this.cheminImageType=selected.getCheminImageType().get();
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		nomTypeField.setText(selected.getNomTypeString());

	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getCheminImageType() {
		return cheminImageType;
	}

	public void setCheminImageType(String cheminImageType) {
		this.cheminImageType = cheminImageType;
	}

}