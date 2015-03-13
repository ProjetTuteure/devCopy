package gpi.view;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Etat;
import gpi.metier.Fabricant;
import gpi.metier.FabricantDAO;
import gpi.metier.Facture;
import gpi.metier.FactureDAO;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import gpi.metier.PageMateriel;
import gpi.metier.PageMaterielDAO;
import gpi.metier.Site;
import gpi.metier.SiteDAO;
import gpi.metier.Type;
import gpi.metier.TypeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class AjouterMateriel {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxTypeMateriel;
	@FXML
	private ComboBox<String> comboboxEtatMateriel;
	@FXML
	private ComboBox<String> comboboxFabricantMateriel;
	@FXML
	private ComboBox<String> comboboxFactureMateriel;
	@FXML
	private ComboBox<String> comboboxSiteMateriel;

	private ObservableList<String> listTypeMateriel;
	private List<Integer> listIdTypeMateriel;
	private ObservableList<String> listEtatMateriel;
	private ObservableList<String> listSiteMateriel;
	private List<Integer> listIdSiteMateriel;
	private ObservableList<String> listFabricantMateriel;
	private List<Integer> listIdFabricantMateriel;
	private ObservableList<String> listFactureMateriel;
	private List<Integer> listIdFactureMateriel;
	
	@FXML
	private TextField immobMaterielField;
	@FXML
	private TextField nomMaterielField;
	@FXML
	private TextField numeroSerieMaterielField;
	@FXML
	private TextField systemeExploitationMaterielField;
	@FXML
	private TextField modeleMaterielField;
	@FXML
	private DatePicker dateMaterielPicker;
		
	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
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

		for (Etat etat : Etat.values()) {
			listEtatMateriel.add(etat.name());
		}
		comboboxEtatMateriel.setItems(listEtatMateriel);

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

		FactureDAO factureDAO=new FactureDAO();
		try {
			for (Facture facture : factureDAO.recupererAllFacture()) {
				if(!listFactureMateriel.contains(facture.getNumFacture())){
					listFactureMateriel.add(facture.getNumFacture());
				}else{
					listFactureMateriel.add(facture.getNumFacture()+" - "+facture.getRevendeurFacture().getNomRevendeur().getValue());
				}		
				listIdFactureMateriel.add(facture.getIdFacture().get());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxFactureMateriel.setItems(listFactureMateriel);

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
		
		TypeDAO typeDAO=new TypeDAO();
		try {
			for (Type type : typeDAO.recupererAllType()) {
				listTypeMateriel.add(type.getNomTypeString());
				listIdTypeMateriel.add(type.getIdType());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxTypeMateriel.setItems(listTypeMateriel);

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
		if (controlerSaisies()) {
			MaterielDAO materielDAO=new MaterielDAO();
			TypeDAO typeDAO = new TypeDAO();
			Type typeMateriel=null;
			try {
				if(comboboxTypeMateriel.getSelectionModel().getSelectedIndex()!=-1){
					typeMateriel = typeDAO.recupererTypeParId(listIdTypeMateriel.get(comboboxTypeMateriel.getSelectionModel().getSelectedIndex()));
				}
			} catch (ConnexionBDException e1) {
				Popup.getInstance().afficherPopup(e1.getMessage());
			}
			LocalDate dateExpirationGarantieMateriel = dateMaterielPicker.getValue();
			FactureDAO factureDAO = new FactureDAO();
			Facture factureMateriel=null;
			try {
				if(comboboxFactureMateriel.getSelectionModel().getSelectedIndex()!=-1){
					factureMateriel = factureDAO.recupererFactureParId(listIdFactureMateriel.get(comboboxFactureMateriel.getSelectionModel().getSelectedIndex()));
					
				}
			} catch (ConnexionBDException e1) {
				Popup.getInstance().afficherPopup(e1.getMessage());
			}
			SiteDAO siteDAO = new SiteDAO();
			Site siteMateriel=null;
			try {
				if(comboboxSiteMateriel.getSelectionModel().getSelectedIndex()!=-1){
					siteMateriel = siteDAO.recupererSiteParId(listIdSiteMateriel.get(comboboxSiteMateriel.getSelectionModel().getSelectedIndex()));
				}
			} catch (ConnexionBDException e1) {
				Popup.getInstance().afficherPopup(e1.getMessage());
			}
			FabricantDAO fabricantDAO = new FabricantDAO();
			Fabricant fabricantMateriel=null;
			try {
				if(comboboxFabricantMateriel.getSelectionModel().getSelectedIndex()!=-1){
					fabricantMateriel = fabricantDAO.recupererFabricantParId(listIdFabricantMateriel.get(comboboxFabricantMateriel.getSelectionModel().getSelectedIndex()));
				}
			} catch (ConnexionBDException e1) {
				Popup.getInstance().afficherPopup(e1.getMessage());
			}
			Etat etatMateriel=null;
			if(comboboxEtatMateriel.getValue()!=null){
				etatMateriel=Etat.valueOf(comboboxEtatMateriel.getValue());
			}
			try {
				materielDAO.ajouterMateriel(new Materiel(0,immobMaterielField.getText(),
						numeroSerieMaterielField.getText(),
						systemeExploitationMaterielField.getText(),
						nomMaterielField.getText(),
						typeMateriel, etatMateriel,
						dateExpirationGarantieMateriel,
						"",
						factureMateriel,
						siteMateriel,
						fabricantMateriel,
						modeleMaterielField.getText()));
				materielDAO.ajouterRepertoireDriverMateriel();
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
			Popup.getInstance().afficherPopup("Materiel "+nomMaterielField.getText()+" ajouté");
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
		if(nomMateriels.contains(nomMaterielField.getText())){
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
		if (comboboxTypeMateriel.getSelectionModel().getSelectedIndex()==-1) {
			Popup.getInstance().afficherPopup("Le matériel doit avoir un type");
			return false;
		}
		if (comboboxEtatMateriel.getSelectionModel().getSelectedIndex()==-1) {
			Popup.getInstance().afficherPopup("Le matériel doit avoir un état");
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