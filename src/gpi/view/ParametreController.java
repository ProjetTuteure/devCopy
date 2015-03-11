package gpi.view;

import gpi.MainApp;
import gpi.metier.*;

import java.net.URL;
import java.util.ResourceBundle;

import utils.Constante;
import utils.Popup;
import utils.Rapports;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class ParametreController implements Initializable {

	/**
	 * Action lorsqu'un le bouton ajouter fabricant est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter fabricant
	 */
	@FXML
	private void handleaddFabr(ActionEvent event) {
		Fabricant fabricant = new Fabricant(0, null, null, null,null,null,null);
		MainApp.showAddFabrDialog(fabricant);
	}

	/**
	 * Action lorsqu'un le bouton modifier fabricant est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier fabricant
	 */
	@FXML
	private void handlemodFabr(ActionEvent event) {
		boolean okClicked = MainApp.showModFabrDialog();
	}
	
	@FXML
	public void onClickButtonRapport(){
		Rapports rapport=new Rapports();
		rapport.GenerateLogiciels();
		Popup.getInstance().afficherPopup("Rapport des logiciels de la société généré \n ("+Constante.CHEMIN_RAPPORTS+"Logiciels.pdf)");
	}

	/**
	 * Action lorsqu'un le bouton supprimer fabricant est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer fabricant
	 */
	@FXML
	private void handlesuppFabr(ActionEvent event) {
		boolean okClicked = MainApp.showSuppFabrDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter facture est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter facture
	 */
	@FXML
	private void handleaddFact(ActionEvent event) {
		Facture facture = new Facture(0, null, null, 0, null);
		boolean okClicked = MainApp.showAddFactDialog(facture);
	}

	/**
	 * Action lorsqu'un le bouton modifier facture est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier facture
	 */
	@FXML
	private void handlemodFact(ActionEvent event) {
		boolean okClicked = MainApp.showModFactDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer facture est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer facture
	 */
	@FXML
	private void handlesuppFact(ActionEvent event) {
		boolean okClicked = MainApp.showSuppFactDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter logiciel est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter logiciel
	 */
	@FXML
	private void handleaddLog(ActionEvent event) {
		Logiciel logiciel = new Logiciel(0, null, null, null, null);
		boolean okClicked = MainApp.showAddLogDialog(logiciel);
	}

	/**
	 * Action lorsqu'un le bouton modifier logiciel est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier logiciel
	 */
	@FXML
	private void handlemodLog(ActionEvent event) {
		boolean okClicked = MainApp.showModLogDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer logiciel est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer logiciel
	 */
	@FXML
	private void handlesuppLog(ActionEvent event) {
		boolean okClicked = MainApp.showSuppLogDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter maintenance est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter maintenance
	 */
	@FXML
	private void handleaddMaint(ActionEvent event) {
		Maintenance maintenance = new Maintenance(1, null, null, null, 0);
		boolean okClicked = MainApp.showAddMaintDialog(maintenance);
	}

	/**
	 * Action lorsqu'un le bouton modifier maintenance est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier maintenance
	 */
	@FXML
	private void handlemodMaint(ActionEvent event) {
		boolean okClicked = MainApp.showModMaintDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer maintenance est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer maintenance
	 */
	@FXML
	private void handlesuppMaint(ActionEvent event) {
		boolean okClicked = MainApp.showSuppMaintDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter materiel est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter materiel
	 */
	@FXML
	private void handleaddMat(ActionEvent event) {
		Materiel materiel = new Materiel(0, null,null,null, null, null, null, null, null,
				null, null, null, null);
		boolean okClicked = MainApp.showAddMatDialog(materiel);
	}

	/**
	 * Action lorsqu'un le bouton modifier materiel est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier materiel
	 */
	@FXML
	private void handlemodMat(ActionEvent event) {
		boolean okClicked = MainApp.showModMatDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer materiel est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer materiel
	 */
	@FXML
	private void handlesuppMat(ActionEvent event) {
		boolean okClicked = MainApp.showSuppMatDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter prestataire est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter prestataire
	 */
	@FXML
	private void handleaddPrest(ActionEvent event) {
		Prestataire prest = new Prestataire(0, null, null, null, null,null,null,null);
		boolean okClicked = MainApp.showAddPrestDialog(prest);
	}

	/**
	 * Action lorsqu'un le bouton modifier prestataire est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier prestataire
	 */
	@FXML
	private void handlemodPrest(ActionEvent event) {
		boolean okClicked = MainApp.showModPrestDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer prestataire est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer prestataire
	 */
	@FXML
	private void handlesuppPrest(ActionEvent event) {
		boolean okClicked = MainApp.showSuppPrestDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter revendeur est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter revendeur
	 */
	@FXML
	private void handleaddRev(ActionEvent event) {
		Revendeur rev = new Revendeur(null, null, null, null,null,null,null);
		boolean okClicked = MainApp.showAddRevDialog(rev);
	}

	/**
	 * Action lorsqu'un le bouton modifier revendeur est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier revendeur
	 */
	@FXML
	private void handlemodRev(ActionEvent event) {
		boolean okClicked = MainApp.showModRevDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer revendeur est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer revendeur
	 */
	@FXML
	private void handlesuppRev(ActionEvent event) {
		boolean okClicked = MainApp.showSuppRevDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter site est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter site
	 */
	@FXML
	private void handleaddSite(ActionEvent event) {
		Site site = new Site(0, null, null);
		boolean okClicked = MainApp.showAddSiteDialog(site);
	}

	/**
	 * Action lorsqu'un le bouton modifier site est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier site
	 */
	@FXML
	private void handlemodSite(ActionEvent event) {
		boolean okClicked = MainApp.showModSiteDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer site est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer site
	 */
	@FXML
	private void handlesuppSite(ActionEvent event) {
		boolean okClicked = MainApp.showSuppSiteDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter type
	 */
	@FXML
	private void handleaddType(ActionEvent event) {
		Type type = new Type(0, null, null);
		boolean okClicked = MainApp.showAddTypeDialog(type);
	}

	/**
	 * Action lorsqu'un le bouton modifier type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier type
	 */
	@FXML
	private void handlemodType(ActionEvent event) {
		boolean okClicked = MainApp.showModTypeDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer type
	 */
	@FXML
	private void handlesuppType(ActionEvent event) {
		boolean okClicked = MainApp.showSuppTypeDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter Utilisateur
	 */
	@FXML
	private void handleaddUtilisateur(ActionEvent event) {
		Utilisateur user = new Utilisateur(null, null, null, null);
		boolean okClicked = MainApp.showAddUtilisateurDialog(user);
	}

	/**
	 * Action lorsqu'un le bouton modifier type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier Utilisateur
	 */
	@FXML
	private void handlemodUtilisateur(ActionEvent event) {
		boolean okClicked = MainApp.showModUtilisateurDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer Utilisateur
	 */
	@FXML
	private void handlesuppUtilisateur(ActionEvent event) {
		boolean okClicked = MainApp.showSuppUtilisateurDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter composant
	 */
	@FXML
	private void handleaddComposant(ActionEvent event) {
		// Pas de type Type, mais de type compose => pas encore cr��
		Type type = new Type(0, null, null);
		boolean okClicked = MainApp.showAddComposantDialog(type);
	}

	/**
	 * Action lorsqu'un le bouton modifier type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier composant
	 */
	@FXML
	private void handlemodComposant(ActionEvent event) {
		boolean okClicked = MainApp.showModComposantDialog();
	}

	/**
	 * Action lorsqu'un le bouton supprimer type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer composant
	 */
	@FXML
	private void handlesuppComposant(ActionEvent event) {
		boolean okClicked = MainApp.showSuppComposantDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter Compose
	 */
	@FXML
	private void handleaddCompose(ActionEvent event) {
		// Pas de type Type, mais de type compose => pas encore cr��
		Composant comp = new Composant(null, null, null, null);
		boolean okClicked = MainApp.showAddComposeDialog(comp);
	}

	/**
	 * Action lorsqu'un le bouton supprimer type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer Compose
	 */
	@FXML
	private void handlesuppCompose(ActionEvent event) {
		boolean okClicked = MainApp.showSuppComposeDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter intervention
	 */
	@FXML
	private void handleaddIntervention(ActionEvent event) {
		EstIntervenu inter = new EstIntervenu(null, null, null);
		boolean okClicked = MainApp.showAddInterventionDialog(inter);
	}

	/**
	 * Action lorsqu'un le bouton supprimer type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer intervention
	 */
	@FXML
	private void handlesuppIntervention(ActionEvent event) {
		boolean okClicked = MainApp.showSuppInterventionDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter uitilisation
	 */
	@FXML
	private void handleaddUtilisation(ActionEvent event) {
		Utilise util = new Utilise(null, null,null);
		boolean okClicked = MainApp.showAddUtilisationDialog(util);
	}

	/**
	 * Action lorsqu'un le bouton supprimer type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer uitilisation
	 */
	@FXML
	private void handlesuppUtilisation(ActionEvent event) {
		boolean okClicked = MainApp.showSuppUtilisationDialog();
	}

	/**
	 * Action lorsqu'un le bouton ajouter type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter uitilisation
	 */
	@FXML
	private void handleaddEstMaintenu(ActionEvent event) {
		EstMaintenu mt = new EstMaintenu(null, null);
		boolean okClicked = MainApp.showAddEstMaintenuDialog(mt);
	}

	/**
	 * Action lorsqu'un le bouton supprimer type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer uitilisation
	 */
	@FXML
	private void handlesuppEstMaintenu(ActionEvent event) {
		boolean okClicked = MainApp.showSuppEstMaintenuDialog();
	}
	
	/**
	 * Action lorsqu'un le bouton ajouter type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton ajouter uitilisation
	 */
	@FXML
	private void handleaddInstallation(ActionEvent event) {
		EstMaintenu mt = new EstMaintenu(null, null);
		boolean okClicked = MainApp.showAddInstallationDialog(mt);
	}

	/**
	 * Action lorsqu'un le bouton supprimer type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton supprimer uitilisation
	 */
	@FXML
	private void handlesuppInstallation(ActionEvent event) {
		boolean okClicked = MainApp.showSuppInstallationDialog();
	}

	/**
	 * Action lorsqu'un le bouton modifier type est clique
	 * 
	 * @param event
	 *            un evenement sur le bouton modifier uitilisation
	 */
	@FXML
	private void handleBD(ActionEvent event) {
		boolean okClicked = MainApp.showModBDDialog();
	}

	/**
	 * Initialise les donn�es
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Cette methode permet de faire apparaitre un DirectoryChooser lorsqu'on
	 * clique sur "Changer le chemin d'acces au repertoire driver"
	 * 
	 * @param event
	 *            un evenement sur le bouton
	 *            "Changer le chemin d'acces au repertoire driver"
	 */
	@FXML
	private void handleDriver(ActionEvent event) {
		boolean okClicked = MainApp.showModDriverDialog();
		// DirectoryChooser directoryChooser = new DirectoryChooser();
		// directoryChooser.setTitle("Open directory");
		// File selectedDirectory = directoryChooser.showDialog(null);
		//
		// if (selectedDirectory != null) {
		// }

	}
}
