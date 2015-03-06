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

	private ObservableList<String> listNomPrestataire;
	private ObservableList<String> listPrenomPrestataire;
	
	private List<Prestataire> listPrestataireParNom;
	private Prestataire prestataireAModifier;
	private int indicePrestataireAModifier;

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		listNomPrestataire = FXCollections.observableArrayList();
		try {
				listNomPrestataire=prestataireDAO.recupererAllNomPrestataire();
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		comboboxnom.setItems(listNomPrestataire);
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
			Popup.getInstance().afficherPopup("Le champ \" Nom du prestataire doit être rempli\"");
			return false;
		} 
		if (nomPrestataireField.getText().length()>Constante.LONGUEUR_NOM_PRESTATAIRE){
			Popup.getInstance().afficherPopup("Le nom du prestataire doit etre inférieur à "
					+ Constante.LONGUEUR_NOM_PRESTATAIRE + " caractères");
			return false;
		} 
		if (prenomPrestataireField.getText().equals("")){
			Popup.getInstance().afficherPopup("Le champ \" Nom du prestataire doit être rempli\"");
			return false;
		} 
		if (prenomPrestataireField.getText().length()>Constante.LONGUEUR_NOM_PRESTATAIRE){
			Popup.getInstance().afficherPopup("Le nom du composant doit etre inférieur à "
					+ Constante.LONGUEUR_NOM_PRESTATAIRE + " caractères");
			return false;
		} 
		if(telPrestataireField.getText().length()>Constante.LONGUEUR_NUM_TELEPHONE){
			Popup.getInstance().afficherPopup("Le numéro de téléphone doit être inférieur à " + Constante.LONGUEUR_NUM_TELEPHONE+" caractères");
			return false;
		} 
		if(faxPrestataireField.getText().length()>Constante.LONGUEUR_NUM_FAX){
			Popup.getInstance().afficherPopup("Le numéro de fax doit être inférieur à " + Constante.LONGUEUR_NUM_FAX+" caractères");
			return false;
		} 
		if(mobilePrestataireField.getText().length()>Constante.LONGUEUR_NUM_MOBILE){
			Popup.getInstance().afficherPopup("Le numéro de mobile doit être inférieur à " + Constante.LONGUEUR_NUM_MOBILE+" caractères");
			return false;
		} 
		if(emailPrestataireField.getText().length()>Constante.LONGUEUR_MAIL){
			Popup.getInstance().afficherPopup("L'adresse email doit être inférieur à " + Constante.LONGUEUR_MAIL+" caractères");
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
				/*int indexPrestataireSelectionne=comboboxprenom.getSelectionModel().getSelectedIndex();
				Prestataire pr = listePrenom.get(indexPrestataireSelectionne);*/
				
				prestataireAModifier.setNomPrestataire(nomPrestataireField.getText());
				prestataireAModifier.setPrenomPrestataire(prenomPrestataireField.getText());
				prestataireAModifier.setTelPrestataire(telPrestataireField.getText());
				prestataireAModifier.setMobilePrestataire(mobilePrestataireField.getText());
				prestataireAModifier.setFaxPrestataire(faxPrestataireField.getText());
				prestataireAModifier.setEmailPrestataire(emailPrestataireField.getText());
				prestataireAModifier.setSocietePrestataire(societePrestataireField.getText());
				
				prestataireDAO.modifierPrestataire(prestataireAModifier);
				Popup.getInstance().afficherPopup("Prestataire "+prestataireAModifier.getNomPrestataire().getValue()+" "+prestataireAModifier.getPrenomPrestataire().getValue()+" modifié !");
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
		listPrestataireParNom=new ArrayList<Prestataire>();
		comboboxprenom.setItems(null);
		String nomPrestataireSelected=comboboxnom.getValue();
		try {
			listPrestataireParNom = prestataireDAO.recupererPrestataireParNom(nomPrestataireSelected);
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
		listPrenomPrestataire = FXCollections.observableArrayList();
		for(Prestataire prestataire:listPrestataireParNom){
			listPrenomPrestataire.add(prestataire.getPrenomPrestataire().getValue());
		}
		comboboxprenom.setItems(listPrenomPrestataire);
		desinitTextArea();
	}

	private void desinitTextArea(){
		nomPrestataireField.setText("");
		prenomPrestataireField.setText("");
		telPrestataireField.setText("");
		mobilePrestataireField.setText("");
		faxPrestataireField.setText("");
		emailPrestataireField
				.setText("");
		societePrestataireField.setText("");
	}
	/**
	 * Cette methode permet de pre remplir les champs lorsqu'un prestataire est
	 * selectionne avec son pr�nom et son nom
	 */
	@FXML
	private void handlechange2() {
		this.indicePrestataireAModifier=comboboxprenom.getSelectionModel().getSelectedIndex();
		if(this.indicePrestataireAModifier!=-1){
			this.prestataireAModifier=this.listPrestataireParNom.get(indicePrestataireAModifier);

			nomPrestataireField.setText(prestataireAModifier.getNomPrestataire().get());
			prenomPrestataireField.setText(prestataireAModifier.getPrenomPrestataire()
					.get());
			telPrestataireField.setText(prestataireAModifier.getTelPrestataire().get());
			mobilePrestataireField.setText(prestataireAModifier.getMobilePrestataire()
					.get());
			faxPrestataireField.setText(prestataireAModifier.getFaxPrestataire().get());
			emailPrestataireField
					.setText(prestataireAModifier.getEmailPrestataire().get());
			societePrestataireField.setText(prestataireAModifier.getSocieteePrestataire()
					.get());
		}
	}

	public int getIdPrestataire() {
		return idPrestataire;
	}

	public void setIdPrestataire(int idPrestataire) {
		this.idPrestataire = idPrestataire;
	}
}