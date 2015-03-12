package gpi.view;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utils.Constante;
import utils.Popup;
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
	
	private String repertoireDriver;
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
	private ObservableList<String> listNomMateriel;
	private List<String> listIdMateriel;
	
	Materiel selected=null;

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
		listNomMateriel = FXCollections.observableArrayList();
		listIdMateriel = new ArrayList<String>();
		PageMaterielDAO pageMaterielDAO=new PageMaterielDAO();
		
		try {
			for (PageMateriel materiel : pageMaterielDAO.getAllMateriel()) {
				listNomMateriel.add(materiel.getNomMateriel());
				listIdMateriel.add(materiel.getIdMateriel());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxIdMateriel.setItems(listNomMateriel);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		if (controlerSaisies()) {
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
					Popup.getInstance().afficherPopup(e1.getMessage());
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
					Popup.getInstance().afficherPopup(e1.getMessage());
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
					Popup.getInstance().afficherPopup(e1.getMessage());
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
					Popup.getInstance().afficherPopup(e1.getMessage());
				}
				Etat etatMateriel;
				if(comboboxEtatMateriel.getSelectionModel().getSelectedIndex()==-1){
					etatMateriel=materielAChanger.getEtatMateriel();
				}else{
					etatMateriel=Etat.valueOf(comboboxEtatMateriel.getSelectionModel().getSelectedItem());
				}
				System.out.println(this.getRepertoireDriver());
				comboboxTypeMateriel.getSelectionModel().getSelectedIndex();
				materielDAO.modifierMateriel(new Materiel(this.getIdMateriel(), immobMaterielField.getText(),
						numeroSerieMaterielField.getText(), systemeExploitationMaterielField.getText(),
						nomMaterielField.getText(),typeMateriel,etatMateriel,
						dateExpirationGarantieMateriel,this.getRepertoireDriver(), factureMateriel, siteMateriel, fabricantMateriel, modeleMaterielField.getText()));
				Popup.getInstance().afficherPopup("Materiel "+nomMaterielField.getText()+" modifié");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}
	}
	
	private boolean controlerSaisies() {
		if (nomMaterielField.getText().equals("")) {
			Popup.getInstance().afficherPopup("Le champ \"Nom matériel\" doit être rempli");
			return false;
		}
		PageMaterielDAO pageMaterielDAO=new PageMaterielDAO();
		List<String> nomMateriels=null;
		try {
			nomMateriels=pageMaterielDAO.getAllNomMateriel();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		if(nomMateriels.contains(nomMaterielField.getText()) && !this.selected.getNomMateriel().getValue().equals(nomMaterielField.getText())){
			Popup.getInstance().afficherPopup("Un matériel de ce nom est déjà ajouté, un nom matériel doit être unique");
			return false;
		}
		if (nomMaterielField.getText().length() > Constante.LONGUEUR_NOM_MATERIEL) {
			Popup.getInstance().afficherPopup("Le nom du matériel doit être inférieur à " + Constante.LONGUEUR_NOM_MATERIEL + " caractères");
			return false;
		}
		if (immobMaterielField.getText().length() > Constante.LONGUEUR_NOM_MATERIEL) {
			Popup.getInstance().afficherPopup("Le nom du matériel doit être inférieur a " + Constante.LONGUEUR_NOM_MATERIEL + " caractères");
			return false;
		}
		if (numeroSerieMaterielField.getText().length() > Constante.LONGUEUR_NUM_SERIE_MAT) {
			Popup.getInstance().afficherPopup("Le numero de série du matériel doit être inférieur à " + Constante.LONGUEUR_NUM_SERIE_MAT + " caractères");
			return false;
		}
		if (modeleMaterielField.getText().length() > Constante.LONGUEUR_MODELE_MAT) {
			Popup.getInstance().afficherPopup("Le modèle du matériel doit être inférieur à "+ Constante.LONGUEUR_MODELE_MAT + " caractères");
			return false;
		}
		if (comboboxTypeMateriel.getSelectionModel().getSelectedIndex()==-1 && selected.getTypeMateriel().getNomTypeString()==null) {
			Popup.getInstance().afficherPopup("Le matériel doit avoir un type");
			return false;
		}
		if (comboboxTypeMateriel.getSelectionModel().getSelectedIndex()==-1 && selected.getTypeMateriel().getNomTypeString()!=null) {
			comboboxTypeMateriel.setValue(selected.getTypeMateriel().getNomTypeString());
		}
		if (comboboxEtatMateriel.getSelectionModel().getSelectedIndex()==-1 && selected.getEtatMaterielString()==null) {
			Popup.getInstance().afficherPopup("Le matériel doit avoir un état");
			return false;
		}
		if (comboboxEtatMateriel.getSelectionModel().getSelectedIndex()==-1 && selected.getEtatMaterielString()!=null) {
			comboboxEtatMateriel.setValue(selected.getEtatMaterielString());
		}
		return true;
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
			String adresse=selectedDirectory.getAbsolutePath();
			adresse=adresse.replace("\\", "/");
			adresse="file:///"+adresse;
			this.setRepertoireDriver(adresse);
        }else{
        	this.setRepertoireDriver(selected.getRepertoireDriverMateriel().getValue());
        }
	}

	@FXML
	private void handlechange() {
		listEtatMateriel = FXCollections.observableArrayList();
		listSiteMateriel = FXCollections.observableArrayList();
		listFactureMateriel = FXCollections.observableArrayList();
		listFabricantMateriel = FXCollections.observableArrayList();
		
		MaterielDAO materielDAO=new MaterielDAO();
		this.selected=null;
		try {
			this.setIdMateriel(Integer.parseInt(listIdMateriel.get(comboboxIdMateriel.getSelectionModel().getSelectedIndex())));
			this.selected = materielDAO.recupererMaterielParId(this.getIdMateriel());
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		numeroSerieMaterielField.setText(this.selected.getNumeroSerieMateriel().getValue());
		systemeExploitationMaterielField.setText(this.selected.getSystemeExploitationMateriel().getValue());
		modeleMaterielField.setText(this.selected.getModeleMateriel());
		nomMaterielField.setText(this.selected.getNomMateriel().getValue());
		immobMaterielField.setText(this.selected.getNumImmobMateriel().getValue());
		if(this.selected.getDateExpirationGarantieMateriel()==null){
			dateMaterielPicker.setPromptText("");
		}else{
			dateMaterielPicker.setPromptText(this.selected.getDateExpirationGarantieMaterielStringProperty().getValue());			
		}

		for (Etat etat : Etat.values()) {
			listEtatMateriel.add(etat.name());
		}
		comboboxEtatMateriel.setItems(listEtatMateriel);
		if(this.selected.getEtatMateriel()==null){
			comboboxEtatMateriel.setPromptText("");
		}else{
			comboboxEtatMateriel.setPromptText(this.selected.getEtatMateriel().name());		
		}

		FactureDAO factureDAO=new FactureDAO();
		
		try {
			for (Facture facture : factureDAO.recupererAllFacture()) {
				listFactureMateriel.add(facture.getNumFacture());
				listIdFactureMateriel.add(facture.getIdFacture().getValue());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxFactureMateriel.setItems(listFactureMateriel);
		if(this.selected.getFactureMateriel()==null){
			comboboxFactureMateriel.setPromptText("");
		}else{
			comboboxFactureMateriel.setPromptText(this.selected.getFactureMateriel().getNumFacture());
		}	

		FabricantDAO fabricantDAO=new FabricantDAO();
				
		try {
			for (Fabricant fabricant : fabricantDAO.recupererAllFabricant()) {
				listFabricantMateriel.add(fabricant.getNomFabricant().getValue());
				listIdFabricantMateriel.add(fabricant.getIdFabricant().getValue());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxFabricantMateriel.setItems(listFabricantMateriel);
		if(this.selected.getFabricantMateriel()==null){
			comboboxFabricantMateriel.setPromptText("");
		}else{
			comboboxFabricantMateriel.setPromptText(this.selected.getFabricantMaterielString());
		}

		SiteDAO siteDAO=new SiteDAO();
		
		try {
			for (Site site : siteDAO.recupererAllSite()) {
				listSiteMateriel.add(site.getNomSiteString());
				listIdSiteMateriel.add(site.getIdSite());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxSiteMateriel.setItems(listSiteMateriel);
		if(this.selected.getSiteMateriel()==null){
			comboboxSiteMateriel.setPromptText("");
		}else{
			comboboxSiteMateriel.setPromptText(this.selected.getSiteMateriel().getNomSiteString());
		}

		TypeDAO typeDAO =new TypeDAO();
		
		try {
			for (Type type : typeDAO.recupererAllType()){
				listTypeMateriel.add(type.getNomTypeString());
				listIdTypeMateriel.add(type.idTypeProperty().getValue());
			}
		}catch (ConnexionBDException e){
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxTypeMateriel.setItems(listTypeMateriel);
		if(this.selected.getTypeMateriel()==null){
			comboboxTypeMateriel.setPromptText("");
		}else{
			comboboxTypeMateriel.setPromptText(this.selected.getTypeMateriel().getNomTypeString());
		}
	}

	public int getIdMateriel() {
		return idMateriel;
	}

	public void setIdMateriel(int idMateriel) {
		this.idMateriel = idMateriel;
	}

	public String getRepertoireDriver() {
		return repertoireDriver;
	}

	public void setRepertoireDriver(String repertoireDriver) {
		this.repertoireDriver = repertoireDriver;
	}
	
	

}