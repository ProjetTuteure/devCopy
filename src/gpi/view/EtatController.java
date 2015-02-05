package gpi.view;

import gpi.MainApp;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Etat;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import gpi.metier.SiteDAO;
import gpi.metier.TypeDAO;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import utils.Popup;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * @author Victor
 *
 */
public class EtatController implements Initializable{
	
	@FXML
	private CheckBox checkBoxEnService;
	@FXML
	private CheckBox checkBoxEnReparation;
	@FXML
	private CheckBox checkBoxHorsService;
	@FXML
	private TableView<Materiel> materielTable;
	@FXML
	private TableColumn<Materiel, String> nomMateriel;
	@FXML
	private TableColumn<Materiel, String> etatMateriel;
	@FXML
	private TableColumn<Materiel, String> siteMateriel;
	@FXML
	private TableColumn<Materiel, String> etatDepuisDateMateriel;


	
	private MaterielDAO materielDAO;
	private List<Materiel> listMateriel;
	private ObservableList<Object> materiel;
	public EtatController() {
    }
	
	/**
	 * Initialise les donnees et ajoute les evenements aux differents composants
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		materielDAO = new MaterielDAO();
		listMateriel = null;
		/*try {
			listMateriel = this.materielDAO.recupererAllMateriel();
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}*/
		//if (listMateriel != null){
			final ObservableList<Materiel> materiel = null;//FXCollections.observableArrayList(listMateriel);
			this.addDonneeTableView(materiel);
			
			checkBoxEnService.setOnAction((event) -> {
				actionOnCheckBox(materiel);
			});
		
		
			checkBoxEnReparation.setOnAction((event) -> {
				actionOnCheckBox(materiel);
			});
			
			checkBoxHorsService.setOnAction((event) -> {
				actionOnCheckBox(materiel);
			});
		//}
		materielTable.setOnMouseClicked((event) -> {
			Materiel materiel_clicked = materielTable.getSelectionModel().getSelectedItem();
			if( materiel_clicked != null){
				MainApp.setCritere(materiel_clicked);
				MainApp.changerTab("DetailMachine");
			}
		});
	}

	/**
	 * Ajoute les donnees a la TableView suite aux checkBox cochees
	 * @param materiel la liste de materiel a ajouter a la TableView
	 */
	public void actionOnCheckBox(ObservableList<Materiel> materiel){
		boolean checkEnService=checkBoxEnService.selectedProperty().getValue();
		boolean checkEnReparation=checkBoxEnReparation.selectedProperty().getValue();
		boolean checkHorsService=checkBoxHorsService.selectedProperty().getValue();
		addDonneeRestrictTableView(checkEnService,checkEnReparation,checkHorsService);
	}
	
	/**
	 * Ajoute les donn�es dans la tableView en fonction des checkBox coch�es
	 * @param materiel la liste des materiels � ajouter dans les tableView
	 * @param checkEnService est vrai si la checkBox enService est coch�e faux sinon
	 * @param checkEnReparation est vrai si la checkBox enReparation est coch�e faux sinon
	 * @param checkHorsService est vrai si la checkBox horsService est coch�e faux sinon
	 */
	private void addDonneeRestrictTableView(boolean checkEnService, boolean checkEnReparation,boolean checkHorsService) {
		ObservableList<Materiel> restrictedMateriel = FXCollections.observableArrayList();
		MaterielDAO materielDAO = new MaterielDAO();
		List<Etat> etats=new ArrayList<Etat>();
		if(!checkEnService && !checkEnReparation && !checkHorsService){
			restrictedMateriel=null;
		}else{
			if(checkEnService){
				etats.add(Etat.EN_MARCHE);
			}
			if(checkEnReparation){
				etats.add(Etat.EN_PANNE);
			}
			if(checkHorsService){
				etats.add(Etat.HS);
			}
			try {
				for(Materiel materiel : materielDAO.recupererMaterielParEtat(etats)){
					restrictedMateriel.add(materiel);
				}
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
		}
		addDonneeTableView(restrictedMateriel);
	}

	/**
	 * Ajoute les donnees relatives a la liste des materiels dans les cases de la tableView
	 * @param materiel la liste des materiels a ajouter dans les cases de la tableView
	 */
	private void addDonneeTableView(ObservableList<Materiel> materiel) {
		materielTable.setItems(materiel);
		nomMateriel.setCellValueFactory(cellData -> cellData.getValue().getNomMateriel());
		etatMateriel.setCellValueFactory(cellData -> cellData.getValue().getEtatMaterielStringProperty());
		siteMateriel.setCellValueFactory(cellData -> cellData.getValue().getSiteMateriel().getNomSiteProperty());
		
		ObservableList<String> listMaintenanceMateriel = FXCollections.observableArrayList();
		etatDepuisDateMateriel.setCellValueFactory(new Callback<CellDataFeatures<Materiel, String>, ObservableValue<String>>() {
		     public ObservableValue<String> call(CellDataFeatures<Materiel, String> p) {
		         return new ReadOnlyObjectWrapper("[TODO] Recherche derniere maintenance");
		     }
		  });
	}

}

