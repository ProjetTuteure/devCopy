package gpi.view;

import java.util.ArrayList;
import java.util.List;

import gpi.exception.ConnexionBDException;
import gpi.metier.Type;
import gpi.metier.TypeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import utils.Popup;

public class SupprimerType {
	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;
	@FXML
	private ComboBox<String> comboboxtype;

	private ObservableList<String> listNomType;

	TypeDAO typeDAO = new TypeDAO();

	private List<Integer> listIdType;

	/**
	 * Initialise les donnees Ajoute les donnees aux combobox
	 */
	@FXML
	private void initialize() {
		listNomType = FXCollections.observableArrayList();
		listIdType = new ArrayList<Integer>();
		try {
			for (Type type : typeDAO.recupererAllType()) {
				listNomType.add(type.getNomTypeString());
				listIdType.add(type.getIdType());
			}
		} catch (ConnexionBDException e) {
			new Popup(e.getMessage());
		}
		comboboxtype.setItems(listNomType);

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
	 * Cette methode permet de savoir si le bouton SUPPRIMER est clique ou pas
	 * 
	 * @return vrai si le bouton SUPPRIMER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	private boolean controlerSaisies() {
		if (comboboxtype.getValue() == null) {
			new Popup("Vous devez selectionner le type à supprimer");
			return false;
		}
		return true;
	}

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton SUPPRIMER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		if (controlerSaisies()) {
			try {
				typeDAO.supprimerType(new Type(listIdType.get(comboboxtype
						.getSelectionModel().getSelectedIndex()), "", ""));
				new Popup("Type " + comboboxtype.getValue() + " supprimé !");
			} catch (ConnexionBDException e) {
				new Popup(e.getMessage());
			}
			okClicked = true;
			dialogStage.close();
		}
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