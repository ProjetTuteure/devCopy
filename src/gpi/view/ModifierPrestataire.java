package gpi.view;

import gpi.exception.ConnexionBDException;
import gpi.metier.Prestataire;
import gpi.metier.PrestataireDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Constante;
import utils.Popup;

import java.util.ArrayList;
import java.util.List;

public class ModifierPrestataire {
	private int idPrestataire;

	@FXML
	private Stage dialogStage;
	@FXML
	private boolean okClicked = false;

	@FXML
	private TextField nomPrestataireField;

	@FXML
	private TextField prenomPrestataireField;

	@FXML
	private TextField telPrestataireField;

	@FXML
	private TextField societePrestataireField;

	@FXML
	private TextField mobilePrestataireField;

	@FXML
	private TextField faxPrestataireField;

	@FXML
	private TextField emailPrestataireField;

	@FXML
	private boolean choix1 = false;

	@FXML
	private ComboBox<String> comboboxnom;
	@FXML
	private ComboBox<String> comboboxprenom;

	private PrestataireDAO prestataireDAO = new PrestataireDAO();

	private ObservableList<String> listNom;
	private ObservableList<String> listPrenom;

	private List<Prestataire> listeNom;
	private List<Prestataire> listePrenom;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		listNom = FXCollections.observableArrayList();
		listeNom = new ArrayList<Prestataire>();

		try {
			listeNom = prestataireDAO.recupererAllPrestataire();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		for (Prestataire prestataire : listeNom) {
			listNom.add(prestataire.getNomPrestataire().get());
		}
		comboboxnom.setItems(listNom);
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
	 * Cette methode permet de savoir si le bouton MODIFIER est clique ou pas
	 * 
	 * @return vrai si le bouton MODIFIER est clique, faux sinon
	 */
	public boolean isOkClicked() {
		return okClicked;
	}

	private boolean controlerSaisies()
	{
		if(nomPrestataireField.getText().equals("")){
			Popup.getInstance().afficherPopup("Le champ \" Nom du prestataire doit �tre rempli");
			return false;
		} 
		if (nomPrestataireField.getText().length()>Constante.LONGUEUR_NOM_PRESTATAIRE){
			Popup.getInstance().afficherPopup("Le nom du prestataire doit etre inf�rieur � "
					+ Constante.LONGUEUR_NOM_PRESTATAIRE + " caract�res");
			return false;
		} 
		if (prenomPrestataireField.getText().equals("")){
			Popup.getInstance().afficherPopup("Le champ \" Nom du prestataire doit �tre rempli");
			return false;
		} 
		if (prenomPrestataireField.getText().length()>Constante.LONGUEUR_NOM_PRESTATAIRE){
			Popup.getInstance().afficherPopup("Le nom du composant doit etre inf�rieur � "
					+ Constante.LONGUEUR_NOM_PRESTATAIRE + " caract�res");
			return false;
		} 
		if(telPrestataireField.getText().length()>Constante.LONGUEUR_NUM_TELEPHONE){
			Popup.getInstance().afficherPopup("Le num�ro de t�l�phone doit �tre inf�rieur �" + Constante.LONGUEUR_NUM_TELEPHONE+" caract�res");
			return false;
		} 
		if(faxPrestataireField.getText().length()>Constante.LONGUEUR_NUM_FAX){
			Popup.getInstance().afficherPopup("Le num�ro de fax doit �tre inf�rieur �" + Constante.LONGUEUR_NUM_FAX+" caract�res");
			return false;
		} 
		if(mobilePrestataireField.getText().length()>Constante.LONGUEUR_NUM_MOBILE){
			Popup.getInstance().afficherPopup("Le num�ro de mobile doit �tre inf�rieur �" + Constante.LONGUEUR_NUM_MOBILE+" caract�res");
			return false;
		} 
		if(emailPrestataireField.getText().length()>Constante.LONGUEUR_MAIL){
			Popup.getInstance().afficherPopup("L'adresse email doit �tre inf�rieur �" + Constante.LONGUEUR_MAIL+" caract�res");
			return false;
		}
		return true;
		
	}
	
	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton MODIFIER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		okClicked = true;
		
		if(controlerSaisies()==true)
		{
			try {
				int indexPrestataireSelectionne=comboboxprenom.getSelectionModel().getSelectedIndex();
				Prestataire pr = listePrenom.get(indexPrestataireSelectionne);
				
				pr.setNomPrestataire(nomPrestataireField.getText());
				pr.setPrenomPrestataire(prenomPrestataireField.getText());
				pr.setTelPrestataire(telPrestataireField.getText());
				pr.setMobilePrestataire(mobilePrestataireField.getText());
				pr.setFaxPrestataire(faxPrestataireField.getText());
				pr.setEmailPrestataire(emailPrestataireField.getText());
				pr.setSocietePrestataire(societePrestataireField.getText());
				
				prestataireDAO.modifierPrestataire(pr);
				Popup.getInstance().afficherPopup("Prestataire "+pr.getNomPrestataire().get()+" "+pr.getSocieteePrestataire().get()+" modifi� !");
			} catch (ConnexionBDException e) {
				e.printStackTrace();
			}
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

	/**
	 * Cette methode permet de pre remplir le combobox avec les prenoms afin
	 * d'avoir un seul et unique prestataire cibl�
	 */
	@FXML
	private void handlechange1() {
		listPrenom = FXCollections.observableArrayList();
		listePrenom = new ArrayList<Prestataire>();

		int indexPrestataireSelectionne = comboboxnom.getSelectionModel()
				.getSelectedIndex();

		try {
			listePrenom = prestataireDAO.recupererPrestataireParNom((listNom
					.get(comboboxnom.getSelectionModel().getSelectedIndex())));
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		Prestataire selected = listeNom.get(indexPrestataireSelectionne);
		for (Prestataire pr : listePrenom) {
			if (pr.getNomPrestataire().getValue().equals(selected.getNomPrestataire().getValue())) {
				
				listPrenom.add(pr.getPrenomPrestataire().get());
			}
		}
		comboboxprenom.setItems(listPrenom);
		
	}

	/**
	 * Cette methode permet de pre remplir les champs lorsqu'un prestataire est
	 * selectionne avec son pr�nom et son nom
	 */
	@FXML
	private void handlechange2() {
		try {
			int indexPrestataireSelectionne = comboboxprenom
					.getSelectionModel().getSelectedIndex();
			Prestataire selected2 = listePrenom
					.get(indexPrestataireSelectionne);

			nomPrestataireField.setText(selected2.getNomPrestataire().get());
			prenomPrestataireField.setText(selected2.getPrenomPrestataire()
					.get());
			telPrestataireField.setText(selected2.getTelPrestataire().get());
			mobilePrestataireField.setText(selected2.getMobilePrestataire()
					.get());
			faxPrestataireField.setText(selected2.getFaxPrestataire().get());
			emailPrestataireField
					.setText(selected2.getEmailPrestataire().get());
			societePrestataireField.setText(selected2.getSocieteePrestataire()
					.get());

		} catch (NullPointerException e) {
			// tripotasse
		}
	}

	public int getIdPrestataire() {
		return idPrestataire;
	}

	public void setIdPrestataire(int idPrestataire) {
		this.idPrestataire = idPrestataire;
	}
}