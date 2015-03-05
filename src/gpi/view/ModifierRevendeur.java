package gpi.view;

import java.util.ArrayList;
import java.util.List;

import utils.Constante;
import utils.Popup;
import gpi.exception.ConnexionBDException;
import gpi.metier.Revendeur;
import gpi.metier.RevendeurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModifierRevendeur {
	@FXML
	private Stage dialogStage;

	@FXML
	private boolean okClicked = false;

	@FXML
	private TextField tf_nomRevendeur;
	@FXML
	private TextField tf_telRevendeur;
	@FXML
	private TextField tf_adresseRevendeur;
	@FXML
	private TextField tf_mobileRevendeur;
	@FXML
	private TextField tf_faxRevendeur;
	@FXML
	private TextField tf_emailRevendeur;

	@FXML
	private ComboBox<String> comboboxrev;

	private ObservableList<String> listrev;
	
	private List<Revendeur> listeRevendeur;
	
	private RevendeurDAO revendeurDAO=new RevendeurDAO();

	/**
	 * Initialise les donn�es Ajoute les donn�es aux combobox
	 */
	@FXML
	private void initialize() {
		listrev = FXCollections.observableArrayList();
		listeRevendeur=new ArrayList<Revendeur>();
		try
		{
			listeRevendeur=revendeurDAO.recupererAllRevendeur();
		}
		catch(ConnexionBDException ce)
		{
			Popup.getInstance().afficherPopup(ce.getMessage());
			this.dialogStage.close();
		}
		for(Revendeur revendeur : listeRevendeur)
		{
			listrev.add(revendeur.getIdRevendeur().getValue()+"- "+revendeur.getNomRevendeur().getValue());
		}
		comboboxrev.setItems(listrev);
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

	/**
	 * Cette procedure permet de fermer la fenetre, lorsque le bouton MODIFIER
	 * est clique
	 */
	@FXML
	private void handleOk() {
		okClicked = true;
		
		if(controlerSaisies()==true)
		{
			int indexRevendeurSelectionne=comboboxrev.getSelectionModel().getSelectedIndex();
			Revendeur revendeur = listeRevendeur.get(indexRevendeurSelectionne);
			revendeur.setNomRevendeur(tf_nomRevendeur.getText());
			revendeur.setAdresseRevendeur(tf_adresseRevendeur.getText());
			revendeur.setTelRevendeur(tf_telRevendeur.getText());
			revendeur.setEmailRevendeur(tf_emailRevendeur.getText());
			revendeur.setMobileRevendeur(tf_mobileRevendeur.getText());
			revendeur.setFaxRevendeur(tf_faxRevendeur.getText());
			try
			{
				revendeurDAO.modifierRevendeur(revendeur);
				Popup.getInstance().afficherPopup("Revendeur "+revendeur.getNomRevendeur().getValue()+" modifié !");
			}
			catch(ConnexionBDException ce)
			{
				Popup.getInstance().afficherPopup(ce.getMessage());
			}
			dialogStage.close();
		}
	}

	private boolean controlerSaisies()
	{
		if(tf_nomRevendeur.getText().isEmpty())
		{
			Popup.getInstance().afficherPopup("Le champ \"Nom du revendeur\" doit être rempli");
			return false;
		}
		if(tf_nomRevendeur.getText().length()>Constante.LONGUEUR_NOM_REVENDEUR){
			Popup.getInstance().afficherPopup("La longueur du nom du revendeur doit être inférieur à "+Constante.LONGUEUR_NOM_REVENDEUR+" caractères");
			return false;
		}
		if(!tf_telRevendeur.getText().isEmpty() && tf_telRevendeur.getText().length()>Constante.LONGUEUR_NUM_TELEPHONE)
		{
			Popup.getInstance().afficherPopup("Le num�ro de t�l�phone saisi doit être inférieur à "+Constante.LONGUEUR_NUM_TELEPHONE+" caractères");
			return false;
		}
		if(!tf_mobileRevendeur.getText().isEmpty() && tf_mobileRevendeur.getText().length()>Constante.LONGUEUR_NUM_MOBILE)
		{
			Popup.getInstance().afficherPopup("Le num�ro de mobile saisi doit être inférieur à "+Constante.LONGUEUR_NUM_TELEPHONE+" caractères");
			return false;
		}
		if(!tf_faxRevendeur.getText().isEmpty() && tf_faxRevendeur.getText().length()>Constante.LONGUEUR_NUM_FAX)
		{
			Popup.getInstance().afficherPopup("Le num�ro de fax saisi doit être inférieur à "+Constante.LONGUEUR_NUM_TELEPHONE+" caractères");
			return false;
		}
		if(!tf_emailRevendeur.getText().isEmpty() && tf_emailRevendeur.getText().length()>Constante.LONGUEUR_MAIL)
		{
			Popup.getInstance().afficherPopup("L'adresse email saisie doit être inférieur à "+Constante.LONGUEUR_NUM_TELEPHONE+" caractères");
			return false;
		}
		if(!tf_adresseRevendeur.getText().isEmpty() && tf_adresseRevendeur.getText().length()>Constante.LONGUEUR_ADRESSE)
		{
			Popup.getInstance().afficherPopup("L'adresse saisie doit être inférieur à "+Constante.LONGUEUR_ADRESSE+" caractères");
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

	/**
	 * Cette methode permet de pre remplir les champs lorsqu'un revendeur est
	 * selectionne
	 */
	@FXML
	private void handlechange() {
		
		int indexRevendeurSelectionne=comboboxrev.getSelectionModel().getSelectedIndex();
		Revendeur selected = listeRevendeur.get(indexRevendeurSelectionne);
		tf_nomRevendeur.setText(selected.getNomRevendeur().getValue());
		tf_telRevendeur.setText(selected.getTelRevendeur().getValue());
		tf_adresseRevendeur.setText(selected.getAdresseRevendeur().getValue());
		tf_mobileRevendeur.setText(selected.getMobileRevendeur().getValue());
		tf_faxRevendeur.setText(selected.getFaxRevendeur().getValue());
		tf_emailRevendeur.setText(selected.getEmailRevendeur().getValue());
	}

}