package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Composant;
import gpi.metier.ComposantDAO;
import gpi.metier.Fabricant;
import gpi.metier.FabricantDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierComposant {

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private TextField nomfield;
	@FXML
	private TextArea caracfield;
	@FXML
	private boolean choix1 = false;

	@FXML
	private ComboBox<String> comboboxnom;
	@FXML
	private ComboBox<String> comboboxcarac;
	@FXML
	private ComboBox<String> comboboxfabr;

	private ObservableList<String> listnom;
	private ObservableList<String> listcarac;
	private ObservableList<String> listfabr;

	private List<Composant> listeNom;
	private List<Composant> listeCarac;
	private List<Fabricant> listeFabricant;

	private ComposantDAO composantDAO = new ComposantDAO();
	private FabricantDAO fabricantDAO = new FabricantDAO();

	@FXML
	private void initialize() {
		listnom = FXCollections.observableArrayList();
		listeNom = new ArrayList<Composant>();
		try {
			listnom.addAll(composantDAO.recupererAllNomComposant());
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxnom.setItems(listnom);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked() {
		return okClicked;
	}

	@FXML
	private void handleOk() {
		Fabricant fabricant = null;
		if (nomfield.getText().equals("")) {
			Popup.getInstance().afficherPopup("Le champ \"Nom du composant\" doit être rempli");
		} else if (nomfield.getText().length() > Constante.LONGUEUR_NOM_COMPOSANT) {
			Popup.getInstance().afficherPopup("Le nom du composant doit etre inférieur à "
					+ Constante.LONGUEUR_NOM_COMPOSANT + " caractères");
		} else if (caracfield.getText().length() > Constante.LONGUEUR_CARACTERISTIQUE_COMPOSANT) {
			Popup.getInstance().afficherPopup("Les caractéristiques ne peuvent pas dépasser "
					+ Constante.LONGUEUR_CARACTERISTIQUE_COMPOSANT
					+ " caract�res");
		} else {
			try {

				int indexFabricant = comboboxfabr.getSelectionModel()
						.getSelectedIndex();
				int indexComposantSelectionne = comboboxcarac
						.getSelectionModel().getSelectedIndex();
				Composant composant = listeCarac.get(indexComposantSelectionne);

				composant.setNomComposant(nomfield.getText());
				composant.setcaracteristiqueComposant(caracfield.getText());
				if (indexFabricant == -1) {
					fabricant = listeCarac.get(
							comboboxcarac.getSelectionModel()
									.getSelectedIndex())
							.getFabricantComposant();
				} else {
					try {
						fabricant = fabricantDAO
								.recupererFabricantParId((listeFabricant.get(
										indexFabricant).getIdFabricant()
										.getValue()));
					} catch (ConnexionBDException e) {
						Popup.getInstance().afficherPopup(e.getMessage());
					}
				}
				composant.setFabricantComposant(fabricant);

				composantDAO.modifierComposant(composant);
				Popup.getInstance().afficherPopup("Composant "+composant.getNomComposant()+" modifié");
			} catch (ConnexionBDException e) {
				e.printStackTrace();
			}
			dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handlechange1() {
		listcarac = FXCollections.observableArrayList();
		listeCarac = new ArrayList<Composant>();
		try {
			listeCarac = composantDAO.recupererComposantParNom((listnom
					.get(comboboxnom.getSelectionModel().getSelectedIndex())));
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		for (Composant cp : listeCarac) {
			listcarac.add(cp.getCaracteristiqueComposant() +" - " +  cp.getFabricantComposant().getNomFabricant().get());
		}
		comboboxcarac.setItems(listcarac);
	}

	@FXML
	private void handlechange2() {
		try {
			listfabr = FXCollections.observableArrayList();
			listeFabricant = new ArrayList<Fabricant>();
			try {
				listeFabricant = fabricantDAO.recupererAllFabricant();
			} catch (ConnexionBDException e) {
				Popup.getInstance().afficherPopup(e.getMessage());
			}

			int indexComposantSelectionne = comboboxcarac.getSelectionModel()
					.getSelectedIndex();
			Composant selected2 = listeCarac.get(indexComposantSelectionne);

			nomfield.setText(selected2.getNomComposant());
			caracfield.setText(selected2.getCaracteristiqueComposant());

			for (Fabricant fab : listeFabricant) {
				listfabr.add(fab.getNomFabricant().getValue());
			}

			comboboxfabr.setItems(listfabr);
			comboboxfabr.setPromptText(selected2.getFabricantComposant()
					.getNomFabricant().getValue());
		} catch (NullPointerException e) {
			//tripotasse
		}
	}

}