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
import gpi.metier.PageMateriel;
import gpi.metier.PageMaterielDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AjouterCompose {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxnom;
	@FXML
	private Label labelCarac;
	@FXML
	private ComboBox<String> comboboxmat;

	private ComposantDAO composantDAO;

	
	private ObservableList<String> listeNomComposant;
	private List<Integer> listeIdComposant;
	private ObservableList<String> listeCaracterisiqueComposant;
	private List<Integer> listeIdCaracteristique;
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
		this.listeIdCaracteristique = new ArrayList<Integer>();
		this.listeIdMateriel = new ArrayList<String>();
		try {
			for (Composant c : composantDAO.recupererAllComposant()) {
				listeNomComposant.add(c.getNomComposant());
				listeIdComposant.add(c.getIdComposant());
			}
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxnom.setItems(listeNomComposant);
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
		Composant composantSelected = null;
		Materiel materielSelected = null;
		MaterielDAO materielDAO = new MaterielDAO();
		ComposeDAO composeDAO = new ComposeDAO();
		okClicked = true;
		try {
			composantSelected = composantDAO.recupererComposantParId(listeIdComposant.get(listeNomComposant.indexOf(comboboxnom.getValue())));
			materielSelected = materielDAO.recupererMaterielParId(Integer.parseInt(listeIdMateriel.get(listeNomMateriel.indexOf(comboboxmat.getValue()))));
			composeDAO.ajouterCompose(new Compose(composantSelected.getIdComposant(),materielSelected.getIdMateriel().getValue()));
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
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
		Composant selected = null;
		try {
			selected = composantDAO.recupererComposantParId(listeIdComposant.get(listeNomComposant.indexOf(comboboxnom.getValue())));
		} catch (ConnexionBDException e2) {
			Popup.getInstance().afficherPopup(e2.getMessage());
		}
		PageMaterielDAO pageMaterielDAO = new PageMaterielDAO();
		listeCaracterisiqueComposant = FXCollections.observableArrayList();

		
		labelCarac.setText(selected.getcaracteristiqueComposant());

		listeNomMateriel = FXCollections.observableArrayList();
		try {
			for (PageMateriel p :  pageMaterielDAO.getAllMateriel()) {
				listeNomMateriel.add(p.getNomMateriel());
				listeIdMateriel.add(p.getIdMateriel());
			}
		} catch (ConnexionBDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comboboxmat.setItems(listeNomMateriel);
	}
}