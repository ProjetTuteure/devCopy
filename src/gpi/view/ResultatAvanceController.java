package gpi.view;

import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.metier.IAvance;
import gpi.metier.IAvanceDAO;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import utils.Popup;

public class ResultatAvanceController implements Initializable {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yy");

    @FXML
    private ListView<IAvance> listMateriel;
    
    @FXML
    private Label labelNbMaterielTrouve;

    /**
     * Initialise les données
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	IAvanceDAO iAvanceDAO=new IAvanceDAO();
    	List<IAvance> resultatMateriels = null;
		try {
			resultatMateriels = iAvanceDAO.recupererRechercheAvanceeMateriel();
			labelNbMaterielTrouve.setText("Resultats de la recherche: "+resultatMateriels.size()+" résultat trouvé");
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
    	listMateriel.getItems().addAll(resultatMateriels);

        listMateriel.setOnMouseClicked((event)->{
        	MaterielDAO materielDAO=new MaterielDAO();
        	
            try {
				MainApp.setCritere(materielDAO.recupererMaterielParId(Integer.parseInt(listMateriel.getFocusModel().getFocusedItem().getIdMateriel())));
			} catch (Exception e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
            MainApp.changerTab("DetailMachine");
        });
    }

    @FXML
    private void goToScreen1(ActionEvent event) {
        MainApp.removeCriteres();
        MainApp.changerTab("Avance");
    }
}
