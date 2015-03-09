package gpi.view;

import java.util.ArrayList;
import java.util.List;

import gpi.exception.ConnexionBDException;
import gpi.metier.Site;
import gpi.metier.SiteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import utils.Popup;


public class SupprimerSite {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxSiteSupp;

	private ObservableList<String> listSiteObservable;
	List<Integer> listSiteId;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		SiteDAO siteDAO=new SiteDAO();
		
		listSiteId=new ArrayList<Integer>();
		listSiteObservable = FXCollections.observableArrayList();
	
		try {
			for (Site site : siteDAO.recupererAllSite()){
				listSiteObservable.add(site.getNomSiteString());
				listSiteId.add(site.getIdSite());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxSiteSupp.setItems(listSiteObservable);
	}

	/**
	 * Cette methode permet de mettre en fenetre active le popup
	 * 
	 * @param dialogStage
	 *            la fenetre active
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * Cette methode permet de savoir si le bouton SUPPRIMER est clique ou pas
	 * 
	 * @return vrai si le bouton SUPPRIMER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton SUPPRIMER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if(controlerSaisies()){
			SiteDAO siteDAO=new SiteDAO();
			int selected=comboboxSiteSupp.getSelectionModel().getSelectedIndex();
			int id=listSiteId.get(selected);
			try {
				siteDAO.supprimerSite(new Site(id,null,null));
				Popup.getInstance().afficherPopup("Site "+comboboxSiteSupp.getValue()+" supprimé !");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}
	}
	
	private boolean controlerSaisies() {
		if(comboboxSiteSupp.getValue()==null){
			Popup.getInstance().afficherPopup("Vous devez sélectionner le site à supprimer");
			return false;
		}
		return true;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton ANNULER
	 * est clique
	 */
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

}