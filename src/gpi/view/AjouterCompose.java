package gpi.view;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Composant;
import gpi.metier.ComposantDAO;
import gpi.metier.Compose;
import gpi.metier.ComposeDAO;
import gpi.metier.Materiel;
import gpi.metier.MaterielDAO;
import gpi.metier.PageMateriel;
import gpi.metier.PageMaterielDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class AjouterCompose {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked=false;
	@FXML
	private ComboBox<String> comboboxNomComposant;
	@FXML
	private ComboBox<String> comboboxCaracteristiqueComposant;
	@FXML
	private ComboBox<String> comboboxMateriel;

	private ComposantDAO composantDAO;
	
	private ObservableList<String> listeNomComposant;
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
		this.listeIdComposant = new ArrayList<Integer>();
		this.composantDAO = new ComposantDAO();
		this.listeIdMateriel = new ArrayList<String>();
		try {
			for (Composant c : composantDAO.recupererAllComposant()) {
				if(!listeNomComposant.contains(c.getNomComposant())){
					listeNomComposant.add(c.getNomComposant());
				}				
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
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
	 * 
	 */
	@FXML
	private void handleOk() {
		if(controlerSaisies()){
			Composant composantSelected = null;
			Materiel materielSelected = null;
			MaterielDAO materielDAO = new MaterielDAO();
			ComposeDAO composeDAO = new ComposeDAO();
			okClicked = true;
			try {
				composantSelected = composantDAO.recupererComposantParId(listeIdComposant.get(comboboxCaracteristiqueComposant.getSelectionModel().getSelectedIndex()));
				materielSelected = materielDAO.recupererMaterielParId(Integer.parseInt(listeIdMateriel.get(listeNomMateriel.indexOf(comboboxMateriel.getValue()))));
				composeDAO.ajouterCompose(new Compose(composantSelected,materielSelected));
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
		try {
			listComposant = composantDAO.recupererComposantParNom(comboboxNomComposant.getValue());
		} catch (ConnexionBDException e2) {
			Popup.getInstance().afficherPopup(e2.getMessage());
		}
		PageMaterielDAO pageMaterielDAO = new PageMaterielDAO();
		listeCaracterisiqueComposant = FXCollections.observableArrayList();
		
		for (Composant composant :listComposant) {
			listeCaracterisiqueComposant.add(composant.getFabricantComposant().getNomFabricantString()+"- "+composant.getCaracteristiqueComposant());
			listeIdComposant.add(composant.getIdComposant());
		}
		
		comboboxCaracteristiqueComposant.setItems(listeCaracterisiqueComposant);

		listeNomMateriel = FXCollections.observableArrayList();
		try {
			for (PageMateriel p :  pageMaterielDAO.getAllMateriel()) {
				listeNomMateriel.add(p.getNomMateriel());
				listeIdMateriel.add(p.getIdMateriel());
			}
		} catch (ConnexionBDException e) {
			e.printStackTrace();
		}
		comboboxMateriel.setItems(listeNomMateriel);
	}
}