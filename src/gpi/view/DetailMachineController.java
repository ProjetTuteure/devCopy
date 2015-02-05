package gpi.view;

import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.metier.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.net.URL;
import java.util.ResourceBundle;

import ping.ChangerCouleurPastille;
import ping.Ping;
import ping.PingWindows;
import utils.Popup;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * @author Victor
 *
 */
public class DetailMachineController{

	@FXML
	private Text textSiteNomMachine;
	@FXML
	private Text textCheminDossierDrivers;
	@FXML
	private Circle colorCircle;
	@FXML
	private TableView<Materiel> tableViewMateriel;
	@FXML
	private TableColumn<Materiel,String> numImmoMateriel;
	@FXML
	private TableColumn<Materiel,String> nomMateriel;
	@FXML
	private TableColumn<Materiel,String> typeMateriel;
	@FXML
	private TableColumn<Materiel,String> seMateriel;
	@FXML
	private TableColumn<Materiel,String> etatMateriel;
	@FXML
	private TableColumn<Materiel,String> finGarantieMateriel;
	@FXML
	private TableColumn<Materiel,String> driversMateriel;
	@FXML
	private TableColumn<Materiel,String> siteMateriel;
	@FXML
	private TableView<Facture> tableViewFacture;
	@FXML
	private TableColumn<Facture,String> numFacture;
	@FXML
	private TableColumn<Facture,String> montantFacture;
	@FXML
	private TableColumn<Facture,String> dateFacture;
	@FXML
	private TableColumn<Facture,String> fournisseurFacture;
	@FXML
	private TableView<Materiel> tableViewFabricant;
	@FXML
	private TableColumn<Materiel,String> numSerieFabricant;
	@FXML
	private TableColumn<Materiel,String> nomFabricant;
	@FXML
	private TableColumn<Materiel,String> telFabricant;
	@FXML
	private TableColumn<Materiel,String> adresseFabricant;
	@FXML
	private TableView<Facture> tableViewRevendeur;
	@FXML
	private TableColumn<Facture,String> nomRevendeur;
	@FXML
	private TableColumn<Facture,String> telRevendeur;
	@FXML
	private TableColumn<Facture,String> adresseRevendeur;
	@FXML
	private TableColumn<Facture,String> numFactureRevendeur;
	@FXML
	private TableView<Maintenance> tableViewMaintenances;
	@FXML
	private TableColumn<Maintenance,String> numMaintenance;
	@FXML
	private TableColumn<Maintenance,String> dateMaintenance;
	@FXML
	private TableColumn<Maintenance,String> objetMaintenance;
	@FXML
	private TableColumn<Maintenance,String> coutMaintenance;
	@FXML
	private TableColumn<Maintenance,String> descriptionMaintenance;
	@FXML
	private TableView<Materiel> tableViewUtilisateurs;
	@FXML
	private TableColumn<Materiel,String> nomUtilisateur;
	@FXML
	private TableColumn<Materiel,String> prenomUtilisateur;
	@FXML
	private TableColumn<Materiel,String> telUtilisateur;
	@FXML
	private TableColumn<Materiel,String> debutUtilisateur;
	@FXML
	private TableColumn<Materiel,String> finUtilisateur;
	@FXML
	private TableView<Logiciel> tableViewLogiciels;
	@FXML
	private TableColumn<Logiciel,String> nomLogiciel;
	@FXML
	private TableColumn<Logiciel,String> versionLogiciel;
	@FXML
	private TableColumn<Logiciel,String> finGarantieLogiciel;
	@FXML
	private TableColumn<Logiciel,String> numFactureLogiciel;
	@FXML
	private TableView<Composant> tableViewComposants;
	@FXML
	private TableColumn<Composant,String> nomComposant;
	@FXML
	private TableColumn<Composant,String> caracteristiqueComposant;
	@FXML
	private TableColumn<Composant,String> fabricantComposant;
	@FXML
	private ImageView imageType;
	
	private Materiel materiel;
	private int index=0;
	
	ObservableList<Materiel> listMateriel;
	ObservableList<Facture> listFacture;
	ObservableList<Materiel> listFabricant;
	ObservableList<Facture> listRevendeur;
	ObservableList<Maintenance> listMaintenance;
	
	ObservableList<Logiciel> listLogiciel;
	ObservableList<Composant> listComposant;
	SimpleStringProperty champNull;
	
	public DetailMachineController() {
    }
	
	/**
	 * Initialise les donnï¿½es et affecte l'index en fonction de la page
	 * courante
	 */
	@FXML
	public void initialize() {
		switch(MainApp.getActiveTab()){
			case 0:
				index=2;
				break;
			case 1:
				index=0;
				break;
			case 2:
				index=0;
				break;
			case 3:
                //index=0;
				index=11;
				break;
		}
		champNull=new SimpleStringProperty(" ");
		materiel=(Materiel)MainApp.getCritere(index);
		EstMaintenuDAO estMaintenuDAO = new EstMaintenuDAO();
		ComposeDAO composeDAO = new ComposeDAO();
		EstInstalleDAO estInstalleDAO = new EstInstalleDAO();
		
		textSiteNomMachine.setText(materiel.getSiteMateriel().getNomSiteProperty().getValue()+" --> "+materiel.getNomMateriel().getValueSafe());
		textCheminDossierDrivers.setText(materiel.getRepertoireDriverMateriel().getValueSafe());
		imageType.setImage(new Image(materiel.getTypeMateriel().getCheminImageType().getValue()));
		
		listMateriel = FXCollections.observableArrayList();
		listMateriel.add(materiel);
		tableViewMateriel.setItems(listMateriel);
		numImmoMateriel.setCellValueFactory(cellData -> cellData.getValue().getNumImmobMateriel());
		nomMateriel.setCellValueFactory(cellData -> cellData.getValue().getNomMateriel());
		typeMateriel.setCellValueFactory(cellData -> cellData.getValue().getTypeMateriel().getNomType());
		seMateriel.setCellValueFactory(cellData -> cellData.getValue().getSystemeExploitationMateriel());
		etatMateriel.setCellValueFactory(cellData -> cellData.getValue().getEtatMaterielStringProperty());
		finGarantieMateriel.setCellValueFactory(cellData -> cellData.getValue().getDateExpirationGarantieMaterielStringProperty());
		driversMateriel.setCellValueFactory(cellData -> cellData.getValue().getRepertoireDriverMateriel());
		siteMateriel.setCellValueFactory(cellData -> cellData.getValue().getSiteMateriel().getNomSiteProperty());
		
		listFacture = FXCollections.observableArrayList();
		listFacture.add(materiel.getFactureMateriel());
		tableViewFacture.setItems(listFacture);
		numFacture.setCellValueFactory(cellData -> cellData.getValue().getNumFactureProperty());
		montantFacture.setCellValueFactory(cellData -> cellData.getValue().getMontantFactureStringProperty());
		dateFacture.setCellValueFactory(cellData -> cellData.getValue().getDateFacStringProperty());
		fournisseurFacture.setCellValueFactory(cellData -> cellData.getValue().getRevendeurFacture().getNomRevendeur());
		
		listFabricant = FXCollections.observableArrayList();
		listFabricant.add(materiel);
		tableViewFabricant.setItems(listFabricant);
		numSerieFabricant.setCellValueFactory(cellData -> cellData.getValue().getNumeroSerieMateriel());
		nomFabricant.setCellValueFactory(cellData -> cellData.getValue().getFabricantMateriel().getNomFabricant());
		telFabricant.setCellValueFactory(cellData -> cellData.getValue().getFabricantMateriel().getTelFabricant());
		adresseFabricant.setCellValueFactory(cellData -> cellData.getValue().getFabricantMateriel().getAdresseFabricant());
		
		listRevendeur = FXCollections.observableArrayList();
		listRevendeur.add(materiel.getFactureMateriel());
		tableViewRevendeur.setItems(listRevendeur);
		nomRevendeur.setCellValueFactory(cellData -> cellData.getValue().getRevendeurFacture().getNomRevendeur());
		telRevendeur.setCellValueFactory(cellData -> cellData.getValue().getRevendeurFacture().getTelRevendeur());
		adresseRevendeur.setCellValueFactory(cellData -> cellData.getValue().getRevendeurFacture().getAdresseRevendeur());
		numFactureRevendeur.setCellValueFactory(cellData -> cellData.getValue().getNumFactureProperty());
		
		listMaintenance = FXCollections.observableArrayList();
		try {
			for(Maintenance maintenance : estMaintenuDAO.recupererMaintenanceParMateriel(materiel.getIdMateriel().getValue())){
				listMaintenance.add(maintenance);
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		tableViewMaintenances.setItems(listMaintenance);
		numMaintenance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdMaintenance().getValue()+""));
		dateMaintenance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getdateMaintenanceString()));
		objetMaintenance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getObjetMaintenance()));
		coutMaintenance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoutMaintenanceString()));
		descriptionMaintenance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescriptionMaintenance()));
		
		//utilisateurs onglet
		
		listLogiciel = FXCollections.observableArrayList();
		try {
			for(Logiciel logiciel : estInstalleDAO.recupererLogicielsParMateriel(materiel.getIdMateriel().getValue())){
				listLogiciel.add(logiciel);
			}
		} catch (ConnexionBDException e1) {
			new Popup(e1.getMessage());
		}
		tableViewLogiciels.setItems(listLogiciel);
		nomLogiciel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomLogiciel()));
		versionLogiciel.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVersionLogiciel()));
		finGarantieLogiciel.setCellValueFactory(cellData -> cellData.getValue().getDateExpirationLogicielStringProperty());
		numFactureLogiciel.setCellValueFactory(cellData -> cellData.getValue().getFactureLogiciel().getNumFactureProperty());
		
		listComposant = FXCollections.observableArrayList();
		try {
			for(Composant composant : composeDAO.recupererComposantsParMateriel(materiel.getIdMateriel().getValue())){
				listComposant.add(composant);
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		tableViewComposants.setItems(listComposant);
		nomComposant.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomComposant()));
		caracteristiqueComposant.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getcaracteristiqueComposant()));
		fabricantComposant.setCellValueFactory(cellData -> cellData.getValue().getFabricantComposant().getNomFabricant());

		
		colorCircle.setVisible(false);
		//Condition si ordinateur ou non à rajouter
		PingWindows pingWindows=new PingWindows(materiel);
		ChangerCouleurPastille pastille=new ChangerCouleurPastille(colorCircle,pingWindows);
		Thread threadPing=new Thread(pingWindows);
		threadPing.start();
		new Thread(pastille).start();
	}
	
	/**
	 * Permet le retour sur les pages prï¿½cï¿½dentes
	 * @param event 
	 */
	@FXML
    private void goBack(ActionEvent event) {
		switch(MainApp.getActiveTab()){
		case 0:
			MainApp.removeCritere();
			MainApp.changerTab("Materiel");
			break;
		case 1:
			MainApp.removeCritere();
			MainApp.changerTab("Anciennete");
			break;
		case 2:
			MainApp.removeCritere();
			MainApp.changerTab("Etat");
			break;
		case 3:
			MainApp.removeCritere();
			MainApp.changerTab("ResultatAvance");
			break;
		}
    }
}
