package gpi.view;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ModifierMateriel {
	private int  idMateriel;
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private TextField nomMaterielField;
	@FXML
	private TextField typeMaterielField;
	@FXML
	private TextField immobMaterielField;
	@FXML
	private DatePicker dateMaterielPicker;
	@FXML
	private ComboBox<String> comboboxIdMateriel;
	@FXML
	private ComboBox<String> comboboxEtatMateriel;
	@FXML
	private ComboBox<String> comboboxFabricantMateriel;
	@FXML
	private ComboBox<String> comboboxFactureMateriel;
	@FXML
	private ComboBox<String> comboboxSiteMateriel;
	@FXML
	private ComboBox<String> comboboxTypeMateriel;
	@FXML
	private TextField numeroSerieMaterielField;
	@FXML
	private TextField systemeExploitationMaterielField;
	@FXML
	private TextField modeleMaterielField;

	private MaterielDAO materielDAO=new MaterielDAO();
	
	private ObservableList<String> listTypeMateriel;
	private List<Integer> listIdTypeMateriel;
	private ObservableList<String> listEtatMateriel;
	private ObservableList<String> listSiteMateriel;
	private List<Integer> listIdSiteMateriel;
	private ObservableList<String> listFabricantMateriel;
	private List<Integer> listIdFabricantMateriel;
	private ObservableList<String> listFactureMateriel;
	private List<Integer> listIdFactureMateriel;
	private ObservableList<String> listIdMateriel;

	@FXML
	private void initialize() {
		listEtatMateriel = FXCollections.observableArrayList();
		listSiteMateriel = FXCollections.observableArrayList();
		listIdSiteMateriel=new ArrayList<Integer>();
		listFabricantMateriel = FXCollections.observableArrayList();
		listIdFabricantMateriel=new ArrayList<Integer>();
		listFactureMateriel = FXCollections.observableArrayList();
		listIdFactureMateriel=new ArrayList<Integer>();
		listTypeMateriel=FXCollections.observableArrayList();
		listIdTypeMateriel=new ArrayList<Integer>();
		listIdMateriel = FXCollections.observableArrayList();
		MaterielDAO materielDAO=new MaterielDAO();
		
		try {
			for (Materiel materiel : materielDAO.recupererAllMateriel()) {
				listIdMateriel.add(materiel.getIdMateriel().getValue().intValue()+"- "+materiel.getNomMateriel().getValue());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxIdMateriel.setItems(listIdMateriel);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		try {
			TypeDAO typeDAO = new TypeDAO();
			Type typeMateriel=null;
			Materiel materielAChanger=materielDAO.recupererMaterielParId(this.getIdMateriel());
			try {
				if(comboboxTypeMateriel.getSelectionModel().getSelectedIndex()==-1){
					typeMateriel = materielAChanger.getTypeMateriel();
				}else{
					typeMateriel = typeDAO.recupererTypeParId(listIdTypeMateriel.get(comboboxTypeMateriel.getSelectionModel().getSelectedIndex()));
				}
			} catch (ConnexionBDException e1) {
				new Popup(e1.getMessage());
			}
			LocalDate dateExpirationGarantieMateriel=null;
			if(dateMaterielPicker.getValue()==null){
				dateExpirationGarantieMateriel = materielAChanger.getDateExpirationGarantieMateriel();
			}else{
				dateExpirationGarantieMateriel = dateMaterielPicker.getValue();
			}
			FactureDAO factureDAO = new FactureDAO();
			Facture factureMateriel=null;
			try {
				if(comboboxFactureMateriel.getSelectionModel().getSelectedIndex()==-1){
					factureMateriel = materielAChanger.getFactureMateriel();
				}else{
					factureMateriel = factureDAO.recupererFactureParId(listIdFactureMateriel.get(comboboxFactureMateriel.getSelectionModel().getSelectedIndex()));
				}
			} catch (ConnexionBDException e1) {
				new Popup(e1.getMessage());
			}
			SiteDAO siteDAO = new SiteDAO();
			Site siteMateriel=null;
			try {
				if(comboboxSiteMateriel.getSelectionModel().getSelectedIndex()==-1){
					siteMateriel = materielAChanger.getSiteMateriel();
				}else{
					siteMateriel = siteDAO.recupererSiteParId(listIdSiteMateriel.get(comboboxSiteMateriel.getSelectionModel().getSelectedIndex()));
				}
			} catch (ConnexionBDException e1) {
				new Popup(e1.getMessage());
			}
			FabricantDAO fabricantDAO = new FabricantDAO();
			Fabricant fabricantMateriel=null;
			try {
				if(comboboxFabricantMateriel.getSelectionModel().getSelectedIndex()==-1){
					fabricantMateriel = materielAChanger.getFabricantMateriel();
				}else{
					fabricantMateriel = fabricantDAO.recupererFabricantParId(listIdFabricantMateriel.get(comboboxFabricantMateriel.getSelectionModel().getSelectedIndex()));
				}
			} catch (ConnexionBDException e1) {
				new Popup(e1.getMessage());
			}
			Etat etatMateriel;
			if(comboboxEtatMateriel.getSelectionModel().getSelectedIndex()==-1){
				etatMateriel=materielAChanger.getEtatMateriel();
			}else{
				etatMateriel=Etat.valueOf(comboboxEtatMateriel.getSelectionModel().getSelectedItem());
			}
			
			comboboxTypeMateriel.getSelectionModel().getSelectedIndex();
			materielDAO.modifierMateriel(new Materiel(this.getIdMateriel(), immobMaterielField.getText(),
					numeroSerieMaterielField.getText(), systemeExploitationMaterielField.getText(),
					nomMaterielField.getText(),typeMateriel,etatMateriel,
					dateExpirationGarantieMateriel,"", factureMateriel, siteMateriel, fabricantMateriel, modeleMaterielField.getText()));
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
	private void handlechoose() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choisir un dossier");
		File selectedDirectory = directoryChooser.showDialog(null);

		if (selectedDirectory != null) {
		}
	}

	@FXML
	private void handlechange() {
		listEtatMateriel = FXCollections.observableArrayList();
		listSiteMateriel = FXCollections.observableArrayList();
		listFactureMateriel = FXCollections.observableArrayList();
		listFabricantMateriel = FXCollections.observableArrayList();
		
		MaterielDAO materielDAO=new MaterielDAO();
		Materiel selected=null;
		try {
			String[] listStrings =comboboxIdMateriel.getValue().split("-");
			this.setIdMateriel(Integer.parseInt(listStrings[0]));
			selected = materielDAO.recupererMaterielParId(this.getIdMateriel());
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}

		numeroSerieMaterielField.setText(selected.getNumeroSerieMateriel().getValue());
		systemeExploitationMaterielField.setText(selected.getSystemeExploitationMateriel().getValue());
		modeleMaterielField.setText(selected.getModeleMateriel());
		nomMaterielField.setText(selected.getNomMateriel().getValue());
		immobMaterielField.setText(selected.getNumImmobMateriel().getValue());
		dateMaterielPicker.setPromptText(selected.getDateExpirationGarantieMaterielStringProperty().getValue());

		for (Etat etat : Etat.values()) {
			listEtatMateriel.add(etat.name());
		}
		comboboxEtatMateriel.setItems(listEtatMateriel);
		comboboxEtatMateriel.setPromptText(selected.getEtatMateriel().name());

		FactureDAO factureDAO=new FactureDAO();
		
		try {
			for (Facture facture : factureDAO.recupererAllFacture()) {
				listFactureMateriel.add(facture.getNumFacture());
				listIdFactureMateriel.add(facture.getIdFacture().getValue());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxFactureMateriel.setItems(listFactureMateriel);
		comboboxFactureMateriel.setPromptText(selected.getFactureMateriel().getNumFacture());

		FabricantDAO fabricantDAO=new FabricantDAO();
				
		try {
			for (Fabricant fabricant : fabricantDAO.recupererAllFabricant()) {
				listFabricantMateriel.add(fabricant.getNomFabricant().getValue());
				listIdFabricantMateriel.add(fabricant.getIdFabricant().getValue());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxFabricantMateriel.setItems(listFabricantMateriel);
		comboboxFabricantMateriel.setPromptText(selected.getFabricantMaterielString());

		SiteDAO siteDAO=new SiteDAO();
		
		try {
			for (Site site : siteDAO.recupererAllSite()) {
				listSiteMateriel.add(site.getNomSiteString());
				listIdSiteMateriel.add(site.getIdSite());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxSiteMateriel.setItems(listSiteMateriel);
		comboboxSiteMateriel.setPromptText(selected.getSiteMateriel().getNomSiteString());

		TypeDAO typeDAO =new TypeDAO();
		
		try {
			for (Type type : typeDAO.recupererAllType()){
				listTypeMateriel.add(type.getNomTypeString());
				System.out.println(type.idTypeProperty().getValue());
				listIdTypeMateriel.add(type.idTypeProperty().getValue());
			}
		}catch (ConnexionBDException e){
			new Popup(e.getMessage());
		}
		comboboxTypeMateriel.setItems(listTypeMateriel);
		comboboxTypeMateriel.setPromptText(selected.getTypeMateriel().getNomTypeString());
	}

	public int getIdMateriel() {
		return idMateriel;
	}

	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}
	
	

}