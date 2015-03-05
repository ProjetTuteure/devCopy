package gpi.view;


import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.metier.IAnciennete;
import gpi.metier.IAncienneteDAO;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import gpi.metier.Site;
import gpi.metier.SiteDAO;
import gpi.metier.Type;
import gpi.metier.TypeDAO;
import gpi.metier.UtiliseDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import javafx.scene.input.MouseEvent;

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
	private TableView<IAnciennete> materielTable;
	@FXML
	private TableColumn<IAnciennete, String> nomMateriel;
	@FXML
	private TableColumn<IAnciennete, String> dateAchatMateriel;
	@FXML
	private TableColumn<IAnciennete, String> etatMateriel;
	@FXML
	private TableColumn<IAnciennete, String> finGarantieMateriel;
	@FXML
	private TableColumn<IAnciennete, String> revendeurMateriel;
	@FXML
	private TableColumn<IAnciennete, String> fabricantMateriel;
	@FXML
	private TableColumn<IAnciennete, String> siteMateriel;
	@FXML
	private TableColumn<IAnciennete, String> numSerieMateriel;
	@FXML
	private TableColumn<IAnciennete, String> dernierUtilisateurMateriel;
	
	
	
	ObservableList<IAnciennete> listMateriel;
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

		SiteDAO siteDAO = new SiteDAO();
		TypeDAO typeDAO = new TypeDAO();
		listMateriel=FXCollections.observableArrayList();
		listSite=FXCollections.observableArrayList();
		listSiteId=new ArrayList<Integer>();
		listType=FXCollections.observableArrayList();
		listTypeId=new ArrayList<Integer>();
		
		try {
			List<Materiel> AllMateriel = null;
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
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		setItemsTableMateriel(listMateriel);
		comboboxSiteAncienneteOverview.setItems(listSite);
		comboboxTypeAncienneteOverview.setItems(listType);
		
		comboboxSiteAncienneteOverview.setOnAction((event) -> {
			actionOnCombo(listTypeId,listSiteId);
		});
		
		comboboxTypeAncienneteOverview.setOnAction((event) -> {
			actionOnCombo(listTypeId,listSiteId);
		});
		
		
	}

     
	@FXML 
	public void handleMouseClick(MouseEvent arg0) {
		MaterielDAO materielDAO = new MaterielDAO();
		int idMachine = Integer.parseInt(materielTable.getSelectionModel().getSelectedItem().getIdMateriel());  
		Materiel mat=null;
		try {
			mat = materielDAO.recupererMaterielParId(idMachine);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		if (mat != null){
			MainApp.setCritere(mat);
			MainApp.changerTab("DetailMachine");
		}
 	}
	/**
	 * Permet de gerer la restriction en fonction du combo box.
	 * @param materiel la liste de materiel qui sera affichee dans la tableView en fonction
	 * des restrictions
	 */
	public void actionOnCombo(List<Integer> listTypeId,List<Integer> listSiteId){
		String selectedSite="";
		String selectedType="";
		selectedSite = comboboxSiteAncienneteOverview.getSelectionModel().getSelectedItem();
	    selectedType = comboboxTypeAncienneteOverview.getSelectionModel().getSelectedItem();
		if(selectedSite==null || selectedSite.equals("Tous")){
			selectedSite="Tous";
		}else{
			selectedSite=listSiteId.get(comboboxSiteAncienneteOverview.getSelectionModel().getSelectedIndex())+"";
		}
		if(selectedType==null || selectedType.equals("Tous")){
			selectedType="Tous";
		}else{		
			selectedType=listTypeId.get(comboboxTypeAncienneteOverview.getSelectionModel().getSelectedIndex())+"";
		}
	    if(selectedSite!=null || selectedType!=null){
	    	addDonneeRestrictTableView(selectedSite,selectedType);
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
	public void setItemsTableMateriel(ObservableList<IAnciennete> materiel){
		UtiliseDAO utiliseDAO = new UtiliseDAO();
		materielTable.setItems(materiel);
		nomMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomMateriel()));
		numSerieMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumSerieMateriel()));
		dateAchatMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateAchatMateriel()));
		etatMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtatMateriel()));
		finGarantieMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateAchatMateriel()));
		revendeurMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomRevendeur()));
		fabricantMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomFabricant()));
		dernierUtilisateurMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(utiliseDAO.recupererNomDernierUtilisateurMachine(Integer.parseInt(cellData.getValue().getIdMateriel()))));
		siteMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomSite()));
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
	public void addDonneeRestrictTableView(String selectedSite, String selectedType){
		ObservableList<IAnciennete> restrictedMateriel = FXCollections.observableArrayList();
		IAncienneteDAO ancienneteDAO = new IAncienneteDAO();
		try{
			if(!selectedSite.equals("Tous") && !selectedType.equals("Tous")){
				for(IAnciennete  anciennete : ancienneteDAO.recupererAncienneteParSiteEtType(new Site(Integer.parseInt(selectedSite), null, null), new Type(Integer.parseInt(selectedType),null,null)))
					restrictedMateriel.add(anciennete);
			}else if(!selectedSite.equals("Tous")){
				for(IAnciennete anciennete : ancienneteDAO.recupererAncienneteParSite(new Site(Integer.parseInt(selectedSite), null, null)))
					restrictedMateriel.add(anciennete);
			}else if(!selectedType.equals("Tous")){
				for(IAnciennete anciennete : ancienneteDAO.recupererAncienneteParType(new Type(Integer.parseInt(selectedType),null,null)))
					restrictedMateriel.add(anciennete);
			}else{
				for(IAnciennete anciennete  : ancienneteDAO.recupererAnciennete())
					restrictedMateriel.add(anciennete);
			}
		}catch(ConnexionBDException e){
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		setItemsTableMateriel(restrictedMateriel);
	}

	
}
