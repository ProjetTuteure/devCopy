package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Site;
import gpi.metier.SiteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.Constante;
import utils.Popup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ModifierSite {
	int idSite;
	String nomSite, cheminImageSite;
	@FXML
	private TextField NameSiteField;

	@FXML
	private Stage dialogStage;
	// @FXML
	// private Site site;
	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxSiteMod;

	private ObservableList<String> listSiteObservable;
	List<Site> listSite;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		SiteDAO siteDAO = new SiteDAO();

		listSite = new ArrayList<Site>();
		listSiteObservable = FXCollections.observableArrayList();
		try {
			listSite = siteDAO.recupererAllSite();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		for (Site site : listSite) {
			listSiteObservable.add(site.getNomSiteString());
		}
		comboboxSiteMod.setItems(listSiteObservable);
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
	 * Cette methode permet de savoir si le bouton MODIFIER est clique ou pas
	 * 
	 * @return vrai si le bouton MODIFIER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton MODIFIER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if (controlerSaisies()) {
			SiteDAO siteDAO = new SiteDAO();
			setNomSite(NameSiteField.getText());
			try {
				siteDAO.modifierSite(new Site(getIdSite(), getNomSite(),
						getCheminImageSite()));
				Popup.getInstance().afficherPopup("Site " + getNomSite() + " modifié !");
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}

	}

	private boolean controlerSaisies() {
		if (comboboxSiteMod.getValue() == null) {
			Popup.getInstance().afficherPopup("Vous devez sélectionner le site à modifier");
			return false;
		}
		if (NameSiteField.getText().isEmpty()) {
			Popup.getInstance().afficherPopup("Le champ \"Nom du site\" doit être saisi");
			return false;
		}
		if (NameSiteField.getText().length() > Constante.LONGUEUR_NOM_SITE) {
			Popup.getInstance().afficherPopup("La longueur du nom du site saisi doit être inférieur à "
					+ Constante.LONGUEUR_NOM_SITE + " caractères");
			return false;
		}
		if (this.getCheminImageSite() != null) {
			if (getCheminImageSite().length() > Constante.LONGUEUR_CHEMIN_IMAGE) {
				Popup.getInstance().afficherPopup("La longueur du chemin saisi doit être inférieur à "
						+ Constante.LONGUEUR_CHEMIN_IMAGE + " caractères");
				return false;
			}
		} else {
			Popup.getInstance().afficherPopup("Une image doit être sélectionnée");
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

	/**
	 * Cette methode permet de faire apparaitre un Filechooser lorsqu'on clique
	 * sur "choisir l'image"
	 * 
	 * @param event
	 *            un evenement sur le bouton "choisir l'image"
	 */
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

	/**
	 * Cette methode permet de pre remplir les champs lorsqu'un site est
	 * selectionne
	 */
	@FXML
	private void handlechange(ActionEvent event) {
		int selected = comboboxSiteMod.getSelectionModel().getSelectedIndex();
		String nom = comboboxSiteMod.getValue();
		int id = listSite.get(selected).getIdSite();
		NameSiteField.setText(nom);
		setIdSite(id);
		setNomSite(nom);

		for (Site site : listSite) {
			if (site.getIdSite() == id) {
				setCheminImageSite(site.getCheminImageSite());
			}
		}
	}

	public int getIdSite() {
		return idSite;
	}

	public void setIdSite(int idSite) {
		this.idSite = idSite;
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