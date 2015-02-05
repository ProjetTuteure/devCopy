package gpi.view;

import gpi.MainApp;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Site;
import gpi.metier.SiteDAO;
import gpi.metier.Type;
import gpi.metier.TypeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;






import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import utils.Popup;

/**
 * Created by Julien on 13/12/2014.
 */
public class AvanceController implements Initializable {
    @FXML
    private ComboBox<String> comboboxSiteAvanceOverview;
    @FXML
    private ComboBox<String> comboboxAncienneteAvanceOverview;
    @FXML
    private ComboBox<String> comboboxTypeAvanceOverview;
    @FXML
    private TextField noImmobilisation;
    @FXML
    private TextField nomMateriel;
    @FXML
    private TextField utilisateur;
    @FXML
    private TextField dateAchat;
    @FXML
    private TextField noFacture;
    @FXML
    private TextField revendeur;
    @FXML
    private TextField fabriquant;
    @FXML
    private TextField modele;

    private SiteDAO siteDAO = new SiteDAO();
    private TypeDAO typeDAO = new TypeDAO();

    ObservableList<String> listNomSite;
    List<Integer> listIdSite;
    ObservableList<String> listAnciennete;
    int listNumAnciennete[] = {1,2,3,4,5,6,7,8};
    ObservableList<String> listNomType;
    List<Integer> listIdType;

    /**
     * Initialise les combobox
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
    	listAnciennete = FXCollections.observableArrayList(
                "moins d'un ans", "moins de deux ans", "moins de trois ans",
                "moins de quattre ans", "moins de cinq ans", "moins de six ans",
                "moins de sept ans", "plus de sept ans");
    	listIdSite=new ArrayList<Integer>();
    	listNomSite=FXCollections.observableArrayList();
    	try {
			for (Site site : siteDAO.recupererAllSite()) {
				listNomSite.add(site.getNomSiteString());
				listIdSite.add(site.getIdSite());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
    	listIdType=new ArrayList<Integer>();
    	listNomType=FXCollections.observableArrayList();
    	try {
			for (Type type : typeDAO.recupererAllType()) {
				listNomType.add(type.getNomTypeString());
				listIdType.add(type.getIdType());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
        comboboxSiteAvanceOverview.setItems(listNomSite);
        comboboxAncienneteAvanceOverview.setItems(listAnciennete);
        comboboxTypeAvanceOverview.setItems(listNomType);
    }

    /**
     * Permet d'afficher les resultats de la recherche avanc�e.
     * @param event
     */
    @FXML
    private void goToScreen2(ActionEvent event) {
        MainApp.setCritere(noImmobilisation.getText());
        MainApp.setCritere(nomMateriel.getText());
        if(comboboxSiteAvanceOverview.getSelectionModel().getSelectedIndex()==-1){
        	MainApp.setCritere("");
        }else{
        	MainApp.setCritere(listIdSite.get(comboboxSiteAvanceOverview.getSelectionModel().getSelectedIndex()));
        }        
        if (comboboxAncienneteAvanceOverview.getSelectionModel().getSelectedIndex()==-1){
            MainApp.setCritere("");
        }else{
            MainApp.setCritere(listNumAnciennete[comboboxAncienneteAvanceOverview.getSelectionModel().getSelectedIndex()]);
        }
        if(comboboxTypeAvanceOverview.getSelectionModel().getSelectedIndex()==-1){
        	MainApp.setCritere("");
        }else{
            MainApp.setCritere(listIdType.get(comboboxTypeAvanceOverview.getSelectionModel().getSelectedIndex()));
        }
        MainApp.setCritere(utilisateur.getText());
        MainApp.setCritere(dateAchat.getText());
        MainApp.setCritere(noFacture.getText());
        MainApp.setCritere(revendeur.getText());
        MainApp.setCritere(fabriquant.getText());
        MainApp.setCritere(modele.getText());
        MainApp.changerTab("ResultatAvance");
    }
}
