package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Composant;
import gpi.metier.ComposantDAO;
import gpi.metier.Compose;
import gpi.metier.ComposeDAO;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;



public class SupprimerCompose {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	private ComposeDAO composeDAO;
	private ComposantDAO composantDAO;
	
	@FXML
	private ComboBox<String> comboboxNomComposant;
	@FXML
	private ComboBox<String> comboboxCaracteristiqueComposant;
	@FXML
	private ComboBox<String> comboboxMateriel;
	
	private ObservableList<String> listeNomComposant;
	private List<Integer> listeIdComposantPourNom;
	private List<Integer> listeIdComposant;
	private ObservableList<String> listeCaracterisiqueComposant;
	private ObservableList<String> listeNomMateriel;
	private List<String> listeIdMateriel;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		this.listeNomComposant = FXCollections.observableArrayList();
		this.listeIdComposantPourNom = new ArrayList<Integer>();
		this.composeDAO = new ComposeDAO();
		this.listeIdMateriel = new ArrayList<String>();

		try {
			for (Compose c : composeDAO.recupererAllCompose()) {
				if(!listeNomComposant.contains(c.getComposant().getNomComposant())){
					listeNomComposant.add(c.getComposant().getNomComposant());
					listeIdComposantPourNom.add(c.getComposant().getIdComposant());
				}
			}
		} catch (ConnexionBDException e) {
			e.printStackTrace();
		}
		comboboxNomComposant.setItems(listeNomComposant);
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
			Composant composantSelected = null;
			Materiel materielSelected = null;
			MaterielDAO materielDAO = new MaterielDAO();
			
			okClicked = true;
			try {
				composantSelected = composantDAO.recupererComposantParId(listeIdComposant.get(comboboxCaracteristiqueComposant.getSelectionModel().getSelectedIndex()));
				materielSelected = materielDAO.recupererMaterielParId(Integer.parseInt(listeIdMateriel.get(comboboxMateriel.getSelectionModel().getSelectedIndex())));
				composeDAO.supprimerCompose(new Compose(composantSelected,materielSelected));
				Popup.getInstance().afficherPopup("Composant "+composantSelected.getNomComposant()+" supprimé de "+materielSelected.getNomMateriel().getValue());
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}
			dialogStage.close();
		}
	}

	/**
	 * Permet de controler les saises
	 * @return true si les saisies sont bonnes, false si les saisies sont incorrects
	 */
	private boolean controlerSaisies(){
		if(comboboxNomComposant.getSelectionModel().getSelectedItem()==null){
			Popup.getInstance().afficherPopup("Le champ \"Nom du composant\" doit être rempli");
			return false;
		}
		if(comboboxCaracteristiqueComposant.getSelectionModel().getSelectedItem()==null){
			Popup.getInstance().afficherPopup("Le champ \"Caractéristique du composant\" doit être rempli");
			return false;
		}
		if(comboboxMateriel.getSelectionModel().getSelectedItem()==null){
			Popup.getInstance().afficherPopup("Le champ \"Materiel ayant le composant\" doit être rempli");
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
		List<Composant> listComposant = null;
		composantDAO = new ComposantDAO();
		this.listeIdComposant = new ArrayList<Integer>();
		try {
			listComposant = composantDAO.recupererComposantParNom(comboboxNomComposant.getValue());
		} catch (ConnexionBDException e2) {
			Popup.getInstance().afficherPopup(e2.getMessage());
		}
		listeCaracterisiqueComposant = FXCollections.observableArrayList();
		
		for (Composant composant :listComposant) {
			if(listeIdComposantPourNom.contains(composant.getIdComposant())){
				listeCaracterisiqueComposant.add(composant.getFabricantComposant().getNomFabricantString()+"- "+composant.getCaracteristiqueComposant());
				listeIdComposant.add(composant.getIdComposant());
			}			
		}
		
		comboboxCaracteristiqueComposant.setItems(listeCaracterisiqueComposant);
	}
	
	@FXML
	private void handleChange1() {
		listeNomMateriel = FXCollections.observableArrayList();
		try {
			for (Materiel materiel :  composeDAO.recupererMaterielParComposant(listeIdComposant.get(comboboxCaracteristiqueComposant.getSelectionModel().getSelectedIndex()))) {
				listeNomMateriel.add(materiel.getNomMateriel().getValue());
				listeIdMateriel.add(materiel.getIdMateriel().getValue().toString());
			}
		} catch (ConnexionBDException e) {
			e.printStackTrace();
		}
		comboboxMateriel.setItems(listeNomMateriel);
	}
}