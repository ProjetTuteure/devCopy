package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Site;
import gpi.metier.SiteDAO;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.Constante;
import utils.Popup;

public class AjouterSite {
	String nomSite,cheminImageSite;
	@FXML
	private TextField NameSiteField;
	@FXML
	private Stage dialogStage;
	// @FXML
	// private Site site;
	@FXML
	private boolean okClicked = false;

	/**
	 * Initialise les donn�es
	 */
	@FXML
	private void initialize() {

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

	// public void setPerson(Site site) {
	// this.site = site;
	//
	// NameSiteField.setText(site.getNomSte());
	//
	// }

	/**
	 * Cette methode permet de savoir si le bouton AJOUTER est clique ou pas
	 * 
	 * @return vrai si le bouton AJOUTER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton AJOUTER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if(controlerSaisies())
		{
			SiteDAO siteDAO = new SiteDAO();
			setNomSite(NameSiteField.getText());
			try {
				siteDAO.ajouterSite(new Site(0,getNomSite(),getCheminImageSite()));
				Popup.getInstance().afficherPopup("Site "+getNomSite()+" ajouté");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			dialogStage.close();
		}
	}

	private boolean controlerSaisies() {
		if(NameSiteField.getText().isEmpty()){
			Popup.getInstance().afficherPopup("Le champ \"Nom du site\" doit être saisi");
			return false;
		}
		if(NameSiteField.getText().length()>Constante.LONGUEUR_NOM_SITE){
			Popup.getInstance().afficherPopup("La longueur du nom du site saisi doit être inférieur à "+Constante.LONGUEUR_NOM_SITE+" caractéres");
			return false;
		}	
		if(getCheminImageSite()!=null){
			if(getCheminImageSite().length()>Constante.LONGUEUR_CHEMIN_IMAGE){
				Popup.getInstance().afficherPopup("La longueur du chemin saisi doit être inférieur à "+Constante.LONGUEUR_CHEMIN_IMAGE+" caractéres");
				return false;
			}
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


	@FXML
	private void handleChoose(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une image");
		File selectedDirectory = fileChooser.showOpenDialog(null);
		if (selectedDirectory != null) {
			String adresse=selectedDirectory.getAbsolutePath();
			adresse=adresse.replace("\\", "/");
			adresse="file:///"+adresse;
			this.setCheminImageSite(adresse);
        }else{
        	this.setCheminImageSite("");
        }

	}

	public String getNomSite() {
		return nomSite;
	}

	public void setNomSite(String nomSite) {
		this.nomSite = nomSite;
	}

	public String getCheminImageSite() {
		return cheminImageSite;
	}

	public void setCheminImageSite(String cheminImageSite) {
		this.cheminImageSite = cheminImageSite;
	}


}