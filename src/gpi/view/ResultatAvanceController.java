package gpi.view;

import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import utils.Popup;

public class ResultatAvanceController implements Initializable {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");

    @FXML
    private ListView<Materiel> listMateriel;

    /**
     * Initialise les donn�ees
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	MaterielDAO materielDAO=new MaterielDAO();
    	List<Materiel> resultatMateriels = null;
		try {
			resultatMateriels = materielDAO.recupererRechercheAvanceeMateriel();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
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
