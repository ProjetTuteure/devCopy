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
import javafx.stage.Stage;
import utils.Constante;
import utils.Popup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

/**
 * Created by Kevin
 */

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
			new Popup(e.getMessage());
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

	// a voir pour la suite
	// public void setPerson(Site site) {
	// this.site = site;
	//
	// NameSiteField.setText(site.getNomSte());
	//
	// }

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
				new Popup("Site " + getNomSite() + " modifié !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}

	}

	private boolean controlerSaisies() {
		if (comboboxSiteMod.getValue() == null) {
			new Popup("Vous devez selectionner le site � modifier");
			return false;
		}
		if (NameSiteField.getText().isEmpty()) {
			new Popup("Le champ \"Nom du site\" doit �tre saisi");
			return false;
		}
		if (NameSiteField.getText().length() > Constante.LONGUEUR_NOM_SITE) {
			new Popup("La longueur du nom du site saisi doit �tre inf�rieur � "
					+ Constante.LONGUEUR_NOM_SITE + " caract�res");
			return false;
		}
		if (this.getCheminImageSite() != null) {
			if (getCheminImageSite().length() > Constante.LONGUEUR_CHEMIN_IMAGE) {
				new Popup("La longueur du chemin saisi doit �tre inf�rieur � "
						+ Constante.LONGUEUR_CHEMIN_IMAGE + " caract�res");
				return false;
			}
		} else {
			new Popup("Une image doit être sélectionnée");
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
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open File");
		fileChooser.showOpenDialog(null); // you could pass a stage
		File file = fileChooser.getSelectedFile(); // reference here if you

		if (file != null) {
			String adresse = file.getAbsolutePath();
			adresse = adresse.replace("\\", "/");
			adresse = "file:///" + adresse;
			this.setCheminImageSite(adresse);
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