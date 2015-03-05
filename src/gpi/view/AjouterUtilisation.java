package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.MaterielDAO;
import gpi.metier.PageMateriel;
import gpi.metier.PageMaterielDAO;
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


public class AjouterUtilisation {
	@FXML
	private Stage dialogStage;

	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> ComboboxNomUtilisateur;
	@FXML
	private DatePicker dateDebutUtilisation;
	@FXML
	private ComboBox<String> ComboboxMateriel;
	
	private ObservableList<String> listNomUtilisateur;
	private List<Integer> listIdUtilisateur;
	private ObservableList<String> listNomMateriel;
	private List<String> listIdMateriel;
	/**
	 * Initialise les donn�es
	 */
	@FXML
	private void initialize() {
		UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
		PageMaterielDAO pageMaterielDAO = new PageMaterielDAO();
		listNomUtilisateur = FXCollections.observableArrayList();
		listIdUtilisateur = new ArrayList<Integer>();
		listNomMateriel = FXCollections.observableArrayList();
		listIdMateriel = new ArrayList<String>();
		
		try{
			for(Utilisateur utilisateur : utilisateurDAO.recupererAllUtilisateur()){
				listNomUtilisateur.add(utilisateur.getNomUtilisateur().getValue()+" "+utilisateur.getPrenomUtilisateur().getValue());
				listIdUtilisateur.add(utilisateur.getIdUtilisateur().intValue());
			}
			for(PageMateriel pageMateriel : pageMaterielDAO.getAllMateriel()){
				listNomMateriel.add(pageMateriel.getNomMateriel());
				listIdMateriel.add(pageMateriel.getIdMateriel());	
			}
		}catch(ConnexionBDException e){
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		ComboboxNomUtilisateur.setItems(listNomUtilisateur);
		ComboboxMateriel.setItems(listNomMateriel);
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
		UtiliseDAO utiliseDAO = new UtiliseDAO();
		MaterielDAO materielDAO = new MaterielDAO();
		UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
		int indexUtilisateur = ComboboxNomUtilisateur.getSelectionModel().getSelectedIndex();
		int indexMateriel = ComboboxMateriel.getSelectionModel().getSelectedIndex();
		try{
			utiliseDAO.ajouterUtilise(new Utilise(dateDebutUtilisation.getValue(),utilisateurDAO.recupererUtilisateurParId(listIdUtilisateur.get(indexUtilisateur)), materielDAO.recupererMaterielParId(Integer.parseInt(listIdMateriel.get(indexMateriel)))));
		}catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		okClicked = true;
		dialogStage.close();

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
