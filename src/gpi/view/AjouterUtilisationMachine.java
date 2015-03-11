package gpi.view;

import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.exception.PrimaryKeyException;
import gpi.metier.Materiel;
import gpi.metier.Utilisateur;
import gpi.metier.UtilisateurDAO;
import gpi.metier.Utilise;
import gpi.metier.UtiliseDAO;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class AjouterUtilisationMachine {
		private Stage dialogStage;

		@FXML
		private boolean okClicked = false;

		@FXML
		private ComboBox<String> ComboboxNomUtilisateur;
		@FXML
		private DatePicker dateDebutUtilisation;
		
		private ObservableList<String> listNomUtilisateur;
		private List<Integer> listIdUtilisateur;
		
		/**
		 * Initialise les données
		 */
		@FXML
		private void initialize() {
			UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
			listNomUtilisateur = FXCollections.observableArrayList();
			listIdUtilisateur = new ArrayList<Integer>();
			
			try{
				for(Utilisateur utilisateur : utilisateurDAO.recupererAllUtilisateur()){
					listNomUtilisateur.add(utilisateur.getNomUtilisateur().getValue()+" "+utilisateur.getPrenomUtilisateur().getValue());
					listIdUtilisateur.add(utilisateur.getIdUtilisateur().intValue());
				}
			}catch(ConnexionBDException e){
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			ComboboxNomUtilisateur.setItems(listNomUtilisateur);
		}
		
		public Materiel getActiveMateriel(){
			int index=0;
			switch(MainApp.getActiveTab()){
			case 0:
				index=2;
				break;
			case 1:
				index=0;
				break;
			case 2:
				index=0;
				break;
			case 3:
	            //index=0;
				index=11;
				break;
			}
		return (Materiel)MainApp.getCritere(index);
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
			if(controlerSaisies()){
				UtiliseDAO utiliseDAO = new UtiliseDAO();
				UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
				int indexUtilisateur = ComboboxNomUtilisateur.getSelectionModel().getSelectedIndex();
				try{
					Utilisateur utilisateur=utilisateurDAO.recupererUtilisateurParId(listIdUtilisateur.get(indexUtilisateur));
					Materiel materiel=getActiveMateriel();
					utiliseDAO.ajouterUtilise(new Utilise(dateDebutUtilisation.getValue(),utilisateur, materiel));
					Popup.getInstance().afficherPopup("Utilisateur "+utilisateur.getNomUtilisateur().getValue()+" ajouté sur "+materiel.getNomMateriel().getValue());
				}catch (ConnexionBDException e) {
					Popup.getInstance().afficherPopup(e.getMessage());
				}
				catch (PrimaryKeyException e1) {
					Popup.getInstance().afficherPopup(e1.getMessage());
				}
				okClicked = true;
				dialogStage.close();

			}
		}
		
		private boolean controlerSaisies(){
			if(this.ComboboxNomUtilisateur.getSelectionModel().getSelectedItem()==null){
				Popup.getInstance().afficherPopup("Le champ \"Utilisateur\" doit être rempli");
				return false;
			}
			if(this.dateDebutUtilisation.getValue()==null){
				Popup.getInstance().afficherPopup("Le champ \"Début d'utilisation\" doit être rempli");
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

		@FXML
		private void handleChange() {
		}
	}
