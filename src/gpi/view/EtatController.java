package gpi.view;

import gpi.MainApp;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Etat;
import gpi.metier.IEtat;
import gpi.metier.IEtatDAO;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import gpi.metier.SiteDAO;
import gpi.metier.TypeDAO;

import javafx.beans.property.SimpleStringProperty;
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
	private TableView<IEtat> materielTable;
	@FXML
	private TableColumn<IEtat, String> nomMateriel;
	@FXML
	private TableColumn<IEtat, String> etatMateriel;
	@FXML
	private TableColumn<IEtat, String> siteMateriel;
	@FXML
	private TableColumn<IEtat, String> etatDepuisDateMateriel;


	
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
		listMateriel = null;
		/*try {
			listMateriel = this.materielDAO.recupererAllMateriel();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}*/
		//if (listMateriel != null){
			final ObservableList<IEtat> materiel = null;//FXCollections.observableArrayList(listMateriel);
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
			IEtat materiel_clicked = materielTable.getSelectionModel().getSelectedItem();
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
	public void actionOnCheckBox(ObservableList<IEtat> materiel){
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
		ObservableList<IEtat> restrictedMateriel = FXCollections.observableArrayList();
		IEtatDAO iEtatDAO = new IEtatDAO();
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
				for(IEtat data : iEtatDAO.recupererIEtat(etats)){
					restrictedMateriel.add(data);
				}
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
		}
		addDonneeTableView(restrictedMateriel);
	}

	/**
	 * Ajoute les donnees relatives a la liste des materiels dans les cases de la tableView
	 * @param materiel la liste des materiels a ajouter dans les cases de la tableView
	 */
	private void addDonneeTableView(ObservableList<IEtat> materiel) {
		materielTable.setItems(materiel);
		nomMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomMateriel()));
		etatMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtatMateriel()));
		siteMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomMateriel()));
		etatDepuisDateMateriel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateModifEtatMateriel()));
	}

}

