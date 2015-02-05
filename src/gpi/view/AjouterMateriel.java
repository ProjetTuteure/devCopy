package gpi.view;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import utils.Popup;
import utils.Propriete;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Etat;
import gpi.metier.Fabricant;
import gpi.metier.FabricantDAO;
import gpi.metier.Facture;
import gpi.metier.FactureDAO;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
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
		System.out.println(listEtatMateriel);
		comboboxEtatMateriel.setItems(listEtatMateriel);

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
		
		TypeDAO typeDAO=new TypeDAO();
		try {
			for (Type type : typeDAO.recupererAllType()) {
				listTypeMateriel.add(type.getNomTypeString());
				listIdTypeMateriel.add(type.getIdType());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
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
		MaterielDAO materielDAO=new MaterielDAO();
		TypeDAO typeDAO = new TypeDAO();
		Type typeMateriel=null;
		try {
			typeMateriel = typeDAO.recupererTypeParId(listIdTypeMateriel.get(comboboxTypeMateriel.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e1) {
			new Popup(e1.getMessage());
		}
		LocalDate dateExpirationGarantieMateriel = dateMaterielPicker.getValue();
		FactureDAO factureDAO = new FactureDAO();
		Facture factureMateriel=null;
		try {
			factureMateriel = factureDAO.recupererFactureParId(listIdFactureMateriel.get(comboboxFactureMateriel.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e1) {
			new Popup(e1.getMessage());
		}
		SiteDAO siteDAO = new SiteDAO();
		Site siteMateriel=null;
		try {
			siteMateriel = siteDAO.recupererSiteParId(listIdSiteMateriel.get(comboboxSiteMateriel.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e1) {
			new Popup(e1.getMessage());
		}
		FabricantDAO fabricantDAO = new FabricantDAO();
		Fabricant fabricantMateriel=null;
		try {
			fabricantMateriel = fabricantDAO.recupererFabricantParId(listIdFabricantMateriel.get(comboboxFabricantMateriel.getSelectionModel().getSelectedIndex()));
		} catch (ConnexionBDException e1) {
			new Popup(e1.getMessage());
		}
		try {
			materielDAO.ajouterMateriel(new Materiel(0,immobMaterielField.getText(),
					numeroSerieMaterielField.getText(),
					systemeExploitationMaterielField.getText(),
					nomMaterielField.getText(),
					typeMateriel, Etat.valueOf(comboboxEtatMateriel.getValue()),
					dateExpirationGarantieMateriel,
					"",
					factureMateriel,
					siteMateriel,
					fabricantMateriel,
					modeleMaterielField.getText()));
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		okClicked = true;
		dialogStage.close();

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
	 * Cette methode permet de pre remplir les champs lorsqu'un Materiel est
	 * selectionne
	 */
	@FXML
	private void handleChoose() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Choisir un dossier");
		File selectedDirectory = directoryChooser.showDialog(null);

		if (selectedDirectory != null) {
		}
	}

}