package gpi.view;


import gpi.MainApp;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Facture;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import gpi.metier.Revendeur;
import gpi.metier.Site;
import gpi.metier.SiteDAO;
import gpi.metier.Type;
import gpi.metier.Materiel;
import gpi.metier.TypeDAO;
import gpi.metier.UtiliseDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import javafx.scene.input.MouseEvent;

import java.beans.EventHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;





import utils.Popup;

/**
 * Created by Victor on 24/11/2014.
 */
public class AncienneteController implements Initializable {

	@FXML
	private ComboBox<String> comboboxSiteAncienneteOverview;
	@FXML
	private ComboBox<String> comboboxTypeAncienneteOverview;
	@FXML
	private TableView<Materiel> materielTable;
	@FXML
	private TableColumn<Materiel, String> nomMateriel;
	@FXML
	private TableColumn<Materiel, String> dateAchatMateriel;
	@FXML
	private TableColumn<Materiel, String> etatMateriel;
	@FXML
	private TableColumn<Materiel, String> finGarantieMateriel;
	@FXML
	private TableColumn<Materiel, String> revendeurMateriel;
	@FXML
	private TableColumn<Materiel, String> fabricantMateriel;
	@FXML
	private TableColumn<Materiel, String> siteMateriel;
	@FXML
	private TableColumn<Materiel, String> numSerieMateriel;
	@FXML
	private TableColumn<Materiel, String> dernierUtilisateurMateriel;
	
	ObservableList<Materiel> listMateriel;
	ObservableList<String> listSite;
	List<Integer> listSiteId;
	ObservableList<String> listType;
	List<Integer> listTypeId;

	private MainApp mainApp;
	
	public AncienneteController() {
    }
	
	/**
	 * Initialise les donnees
	 * ajoute les donnees dans la tableView
	 * ajoute les actions aux combobox
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		MaterielDAO materielDAO = new MaterielDAO();
		SiteDAO siteDAO = new SiteDAO();
		TypeDAO typeDAO = new TypeDAO();
		listMateriel=FXCollections.observableArrayList();
		listSite=FXCollections.observableArrayList();
		listSiteId=new ArrayList<Integer>();
		listType=FXCollections.observableArrayList();
		listTypeId=new ArrayList<Integer>();
		
		try {
			List<Materiel> AllMateriel = materielDAO.recupererAllMateriel();
			if (AllMateriel != null){
				for(Materiel materiel : AllMateriel){
					listMateriel.add(materiel);
				}
			}
			for(Site site : siteDAO.recupererAllSite()){
				listSite.add(site.getNomSiteString());
				listSiteId.add(site.getIdSite());
			}
			listSite.add("Tous");
			for(Type type : typeDAO.recupererAllType()){
				listType.add(type.getNomTypeString());
				listTypeId.add(type.getIdType());
			}
			listType.add("Tous");
		}catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		setItemsTableMateriel(listMateriel);
		comboboxSiteAncienneteOverview.setItems(listSite);
		comboboxTypeAncienneteOverview.setItems(listType);
		
		comboboxSiteAncienneteOverview.setOnAction((event) -> {
			actionOnCombo(listMateriel);
		});
		
		comboboxTypeAncienneteOverview.setOnAction((event) -> {
			actionOnCombo(listMateriel);
		});
		
		materielTable.setOnMouseClicked((event) -> {
//		materielTable.addEventFilter(MouseEvent.MOUSE_CLICKED, 
//                new Event(arg0)
       
			Materiel mat = materielTable.getSelectionModel().getSelectedItem();  
			
			if (mat != null){
				MainApp.setCritere(mat);
				MainApp.changerTab("DetailMachine");
			}
		});
	}
	
	/**
	 * Permet de gerer la restriction en fonction du combo box.
	 * @param materiel la liste de materiel qui sera affichee dans la tableView en fonction
	 * des restrictions
	 */
	public void actionOnCombo(ObservableList<Materiel> listMateriel){
		String selectedSite="";
		String selectedType="";
		selectedSite = comboboxSiteAncienneteOverview.getSelectionModel().getSelectedItem();
	    selectedType = comboboxTypeAncienneteOverview.getSelectionModel().getSelectedItem();
		if(selectedSite==null){
			selectedSite="Tous";
		}
		if(selectedType==null){
			selectedType="Tous";
		}
	    if(selectedSite!=null || selectedType!=null){
	    	addDonneeRestrictTableView(listMateriel,selectedSite,selectedType);
	    }
	}
	
	/**
	 * Set le MainApp
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	
	/**
	 * ajoute les materiels dans les colonnes de la tableView
	 * @param materiel la liste des materiels que l'on ajoute.
	 */
	public void setItemsTableMateriel(ObservableList<Materiel> materiel){
		UtiliseDAO utiliseDAO = new UtiliseDAO();
		materielTable.setItems(materiel);
		nomMateriel.setCellValueFactory(cellData -> cellData.getValue().getNomMateriel());
		numSerieMateriel.setCellValueFactory(cellData -> cellData.getValue().getNumeroSerieMateriel());
		dateAchatMateriel.setCellValueFactory(cellData -> cellData.getValue().getFactureMateriel().getDateFacStringProperty());
		etatMateriel.setCellValueFactory(cellData -> cellData.getValue().getEtatMaterielStringProperty());
		finGarantieMateriel.setCellValueFactory(cellData -> cellData.getValue().getDateExpirationGarantieMaterielStringProperty());
		revendeurMateriel.setCellValueFactory(cellData -> cellData.getValue().getFactureMateriel().getRevendeurFacture().getNomRevendeur());
		fabricantMateriel.setCellValueFactory(cellData -> cellData.getValue().getFabricantMateriel().getNomFabricant());
		dernierUtilisateurMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(utiliseDAO.recupererNomDernierUtilisateurMachine(cellData.getValue().getIdMateriel().getValue())));
		siteMateriel.setCellValueFactory(cellData -> cellData.getValue().getSiteMateriel().getNomSiteProperty());
	}
	
	
//	private StringProperty getDernierUtilisateur(Integer idMateriel){
//		UtiliseDAO utiliseDAO = new UtiliseDAO();
//		StringProperty nomDernierUtilisateur;
//		nomDernierUtilisateur=new SimpleStringProperty(utiliseDAO.recupererNomDernierUtilisateurMachine(idMateriel));
//		if(nomDernierUtilisateur.getValue()==""){
//			nomDernierUtilisateur=new SimpleStringProperty("Aucun utilisateur");
//		}
//		return nomDernierUtilisateur;
//	}

	/**
	 * Permet de restreindre l'affichage des donn�es dans la TableView en fonction des crit�res
	 * s�lectionn�s dans les combobox
	 * @param materiel la liste de mat�riel � afficher dans la tableView
	 * @param selectedSite le site dans lequel sont les mat�riels
	 * @param selectedType le type de mat�riel � afficher
	 */
	public void addDonneeRestrictTableView(ObservableList<Materiel> listMateriel,String selectedSite, String selectedType){
		ObservableList<Materiel> restrictedMateriel = FXCollections.observableArrayList();
		boolean isOk;
		System.out.println(selectedSite+" "+selectedType);
		for(Materiel materiel : listMateriel){
			isOk=true;
			if(selectedSite!="Tous"){
				if(!materiel.getSiteMateriel().getNomSiteString().equals(selectedSite)){
					isOk=false;
				}
			}
			if(selectedType!="Tous"){
				if(!materiel.getTypeMateriel().getNomTypeString().equals(selectedType)){
					isOk=false;
				}
			}
			if(isOk){
				restrictedMateriel.add(materiel);
			}
			setItemsTableMateriel(restrictedMateriel);
		}
	}
	
}
