package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.PageMateriel;
import gpi.metier.PageMaterielDAO;
import gpi.metier.Utilisateur;
import gpi.metier.UtilisateurDAO;
import gpi.metier.Utilise;
import gpi.metier.UtiliseDAO;

import java.util.ArrayList;
import java.util.List;

import utils.DateConverter;
import utils.Popup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class SupprimerUtilisation {
	@FXML
	private Stage dialogStage;

	@FXML
	private boolean okClicked = false;

	@FXML
	private ComboBox<String> comboboxNomUtilisateur;
	@FXML
	private ComboBox<String> comboboxMateriel;
	@FXML
	private ComboBox<String> comboboxDateUtilisation;
	
	private ObservableList<String> listNomUtilisateur;
	private List<Integer> listIdUtilisateur;
	private ObservableList<String> listNomMateriel;
	private List<Integer> listIdMateriel;
	private ObservableList<String> listDateUtilisation;
	private List<String> listDateUtilisationDB;

	private int idUtilisateur;

	private int idMateriel;
	
	private String dateUtilisation;
	
	/**
	 * Initialise les données
	 */
	@FXML
	private void initialize() {
		UtiliseDAO utiliseDAO = new UtiliseDAO();
		listNomUtilisateur = FXCollections.observableArrayList();
		listIdUtilisateur = new ArrayList<Integer>();
		listNomMateriel = FXCollections.observableArrayList();
		listIdMateriel = new ArrayList<Integer>();
		
		try{
			for(Utilise utilise : utiliseDAO.recupererMaterielUtiliseAll()){
				listNomUtilisateur.add(utilise.getUtilisateurUtilise().getNomUtilisateur().getValue()+" "+utilise.getUtilisateurUtilise().getPrenomUtilisateur().getValue());
				listIdUtilisateur.add(utilise.getUtilisateurUtilise().getIdUtilisateur().intValue());
			}
		}catch(ConnexionBDException e){
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxNomUtilisateur.setItems(listNomUtilisateur);
		comboboxMateriel.setItems(listNomMateriel);
	}

	@FXML
	private void handleChange() {
		this.idUtilisateur = (listIdUtilisateur.get(comboboxNomUtilisateur.getSelectionModel().getSelectedIndex()));
		PageMaterielDAO pageMaterielDAO = new PageMaterielDAO();
		this.listNomMateriel = FXCollections.observableArrayList();
		this.listIdMateriel = new ArrayList<Integer>();
		this.listDateUtilisation = FXCollections.observableArrayList();
		listDateUtilisationDB = new ArrayList<String>();
		try {
			for(PageMateriel pageMateriel : pageMaterielDAO.getMaterielByIdUtilisateur(idUtilisateur)){
				listNomMateriel.add(pageMateriel.getNomMateriel());
				listIdMateriel.add(Integer.parseInt(pageMateriel.getIdMateriel()));	
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxMateriel.setItems(listNomMateriel);
	}
	
	@FXML
	private void handleChange1() {
		if(comboboxMateriel.getSelectionModel().getSelectedIndex()!=-1){
			this.idUtilisateur = listIdUtilisateur.get(comboboxNomUtilisateur.getSelectionModel().getSelectedIndex());
			this.idMateriel = listIdMateriel.get(comboboxMateriel.getSelectionModel().getSelectedIndex());
			System.out.println(idUtilisateur);
			System.out.println(idMateriel);
			UtiliseDAO utiliseDAO = new UtiliseDAO();
			this.listDateUtilisation = FXCollections.observableArrayList();
			listDateUtilisationDB = new ArrayList<String>();
			try {
				for(String date : utiliseDAO.recupererDateUtilisation(idMateriel, idUtilisateur)){
					listDateUtilisation.add(DateConverter.getFrenchDate(date));
					listDateUtilisationDB.add(date);
				}
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			comboboxDateUtilisation.setItems(listDateUtilisation);
		}
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
		UtiliseDAO utiliseDAO=new UtiliseDAO();
		this.idUtilisateur = (listIdUtilisateur.get(comboboxNomUtilisateur.getSelectionModel().getSelectedIndex()));
		this.idMateriel = (listIdMateriel.get(comboboxMateriel.getSelectionModel().getSelectedIndex()));
		this.dateUtilisation = (listDateUtilisationDB.get(comboboxDateUtilisation.getSelectionModel().getSelectedIndex()));
		System.out.println(dateUtilisation);
		
		try {
			utiliseDAO.supprimerUtilise(this.idUtilisateur, this.idMateriel,this.dateUtilisation);
			Popup.getInstance().afficherPopup("Utilisateur "+comboboxNomUtilisateur.getValue()+" supprimé du matériel "+comboboxMateriel.getValue());
		} catch (NumberFormatException | ConnexionBDException e) {
			/*Todo*/
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
}
