package gpi.view;

import gpi.MainApp;
import gpi.bd.Donnee;
import gpi.exception.ConnexionBDException;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import gpi.metier.Utilise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import utils.Popup;

/**
 * Created by Julien on 13/12/2014.
 */
public class ResultatAvanceController implements Initializable {
    private boolean test=false;
    private Donnee donnee=new Donnee();
    private List<Materiel> materielObservableList;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");

    @FXML
    private ListView<Materiel> listMateriel;

    /**
     * Initialise les donnéees
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	MaterielDAO materielDAO=new MaterielDAO();
    	List<Materiel> resultatMateriels = null;
		try {
			resultatMateriels = materielDAO.recupererRechercheAvanceeMateriel();
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
    	listMateriel.getItems().addAll(resultatMateriels);

        listMateriel.setOnMouseClicked((event)->{
            MainApp.setCritere(listMateriel.getFocusModel().getFocusedItem());
            MainApp.changerTab("DetailMachine");
        });
    }

    @FXML
    private void goToScreen1(ActionEvent event) {
        MainApp.removeCriteres();
        MainApp.changerTab("Avance");
    }
}
