package gpi.view;

import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.metier.IAvance;
import gpi.metier.IAvanceDAO;
import gpi.metier.IEtat;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import utils.DateConverter;
import utils.Popup;

public class ResultatAvanceController implements Initializable {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");
    
    @FXML
    private TableView<IAvance> tv_tableauRechercheAvancee;
    
    @FXML
    private TableColumn<IAvance,String> tc_numImmobilisation;
 
    @FXML
    private TableColumn<IAvance,String> tc_nom;
    
    @FXML
    private TableColumn<IAvance,String> tc_site;
    
    @FXML
    private TableColumn<IAvance,String> tc_type;
    
    @FXML
    private TableColumn<IAvance,String> tc_utilisateur;
    
    @FXML
    private TableColumn<IAvance,String> tc_numFacture;
    
    @FXML
    private TableColumn<IAvance,String> tc_revendeur;
    
    @FXML
    private TableColumn<IAvance,String> tc_fabricant;
    
    @FXML
    private Label labelNbMaterielTrouve;

    /**
     * Initialise les données
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	tv_tableauRechercheAvancee.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	IAvanceDAO iAvanceDAO=new IAvanceDAO();
    	ObservableList resultatMateriels = null;
		try {
			resultatMateriels = FXCollections.observableArrayList(iAvanceDAO.recupererRechercheAvanceeMateriel());
			labelNbMaterielTrouve.setText("Resultats de la recherche: "+resultatMateriels.size()+" résultat(s) trouvé(s)");
			this.addDonneeTableView(resultatMateriels);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		
		tv_tableauRechercheAvancee.setOnMouseClicked((event)->{
			MaterielDAO materielDAO=new MaterielDAO();
       	
			try {
				MainApp.setCritere(materielDAO.recupererMaterielParId(Integer.parseInt(tv_tableauRechercheAvancee.getFocusModel().getFocusedItem().getIdMateriel())));
			} catch (Exception e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
				MainApp.changerTab("DetailMachine");
			});
    }
    
    
    private void addDonneeTableView(ObservableList<IAvance> materiel) {
		tv_tableauRechercheAvancee.setItems(materiel);
		tc_numImmobilisation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumImmobMateriel()));
		tc_nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomMateriel()));
		tc_site.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomSite()));
		tc_type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTypeMateriel()));
		tc_utilisateur.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomUtilisateur()));
		tc_numFacture.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumFactureMateriel()));
		tc_revendeur.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomRevendeur()));
		tc_fabricant.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNomFabricant()));
	}

    @FXML
    private void goToScreen1(ActionEvent event) {
        MainApp.removeCriteres();
        MainApp.changerTab("Avance");
    }
}
