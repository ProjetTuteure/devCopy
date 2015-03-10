package gpi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.Popup;
import gpi.bd.Donnee;
import gpi.metier.*;
import gpi.view.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	public static Donnee donnee;
	private static Stage primaryStage;
	private static TabPane rootLayout;
	private static Tab SiteOverview;
	private static Tab AncienneteOverview;
	private static Tab EtatOverview;
	private static Tab AvanceOverview;
	private static Tab ParametreOverview;
	private AnchorPane login;
    //pour garder en memoire l'etat des onglet : utiliser ces variable :
	private static List<Object> tab0;
    private static List<Object> tab1;
    private static List<Object> tab2;
    private static List<Object> tab3;
    private static List<Object> tab4;
    ////////////////////////////////////////////

	@Override
	public void start(Stage primaryStage) {
		tab0 = new ArrayList<Object>();
        tab1 = new ArrayList<Object>();
        tab2 = new ArrayList<Object>();
        tab3 = new ArrayList<Object>();
        tab4 = new ArrayList<Object>();
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Adam SAS");
		this.primaryStage.setWidth(805);
		this.primaryStage.setHeight(685);
		this.primaryStage.setResizable(false);
		initLoginLayout();
	}
	
	public static void launch(){
		initRootLayout();
		Thread threadSite = new Thread(new OngletLoader(rootLayout,SiteOverview,"Site"));
		Thread threadAnciennete = new Thread(new OngletLoader(rootLayout,AncienneteOverview,"Anciennete"));
		Thread threadEtat = new Thread(new OngletLoader(rootLayout,EtatOverview,"Etat"));
		Thread threadAvance = new Thread(new OngletLoader(rootLayout,AvanceOverview,"Avance"));
		Thread threadParametre = new Thread(new OngletLoader(rootLayout,ParametreOverview,"Parametre"));
		Platform.runLater(threadSite);
		Platform.runLater(threadAnciennete);
		Platform.runLater(threadEtat);
		Platform.runLater(threadAvance);
		Platform.runLater(threadParametre);
	}
	
	public void initLoginLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/LoginLayout.fxml"));
			login= (AnchorPane) loader.load();
			Scene scene = new Scene(login);
			primaryStage.setScene(scene);
			primaryStage.show();
			LoginController controller = loader.getController();
	        controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initTabOverview(Tab tab, String nom) {
		try {
			tab = new Tab();
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/" + nom
					+ "Overview.fxml"));
			AnchorPane tabOverview = (AnchorPane) loader.load();
			tab.setText(nom);
			tab.setContent(tabOverview);
			// Set person overview into the center of root layout.
			rootLayout.getTabs().add(tab);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes the root layout.
	 */
	public static void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class
					.getResource("view/RootLayout.fxml"));
			rootLayout = (TabPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static void setCritere(Object o){
        switch (getActiveTab()) {
        	case 0:
        		tab0.add(o);
        		break;
            case 1:
                tab1.add(o);
                break;
            case 2:
                tab2.add(o);
                break;
            case 3:
                tab3.add(o);
                break;
            case 4:
                tab4.add(o);
                break;
        }
    }

    public static Object getCritere(int index){
        switch (getActiveTab()) {
        	case 0:
        		return tab0.get(index);
            case 1:
                return tab1.get(index);
            case 2:
                return tab2.get(index);
            case 3:
                return tab3.get(index);
            default:
                return tab4.get(index);
        }
    }

    public static void removeCritere(){
        switch (getActiveTab()) {
            case 0:
                tab0.remove(tab0.size()-1);
                break;
            case 1:
                tab1.remove(tab1.size()-1);
                break;
            case 2:
                tab2.remove(tab2.size()-1);
                break;
            case 3:
                tab3.remove(tab3.size()-1);
                break;
            case 4:
                tab4.remove(tab4.size()-1);
                break;
        }
    }
	public static void removeCriteres(){
		switch (getActiveTab()) {
			case 0:
				//tab0.remove(tab0.size()-1);
				break;
			case 1:
				//tab1.remove(tab1.size()-1);
				break;
			case 2:
				//tab2.remove(tab2.size()-1);
				break;
			case 3:
				tab3.removeAll(tab3);
				break;
			case 4:
				//tab4.remove(tab4.size()-1);
				break;
		}
	}

    public static int getActiveTab(){
        return rootLayout.getSelectionModel().getSelectedIndex();
    }

    /**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	//private static ObservableList<Site> siteData = FXCollections.observableArrayList();
	public static void main(String[] args) {
		donnee = new Donnee();
		launch(args);

	}

	public static void changerTab(String nom) {
		try {
			int onglet = getActiveTab();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/" + nom
					+ "Overview.fxml"));
			AnchorPane tabOverview = (AnchorPane) loader.load();
			rootLayout.getTabs().get(onglet).setContent(tabOverview);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//public static ObservableList<Site> getSiteData() {
		//return siteData;
	//}

	public static boolean showAddSiteDialog(Site site) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterSite.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un nouveau site");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterSite controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppSiteDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerSite.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un site");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerSite controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModSiteDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierSite.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un site");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierSite controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddTypeDialog(Type type) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterType.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un nouveau type");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterType controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModTypeDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierType.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un type");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierType controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppTypeDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerType.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un type");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerType controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddFactDialog(Facture facture) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterFacture.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter une nouvelle facture");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterFacture controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModFactDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierFacture.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier une facture");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierFacture controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppFactDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerFacture.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer une facture");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerFacture controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddFabrDialog(Fabricant fabricant) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterFabricant.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un nouveau fabricant");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterFabricant controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModFabrDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierFabricant.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un fabricant");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierFabricant controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppFabrDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerFabricant.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un fabricant");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerFabricant controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddLogDialog(Logiciel logiciel) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterLogiciel.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un nouveau logiciel");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterLogiciel controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModLogDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierLogiciel.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un logiciel");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierLogiciel controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppLogDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerLogiciel.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un logiciel");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerLogiciel controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddMaintDialog(Maintenance maintenance) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterMaintenance.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter une nouvelle maintenance");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterMaintenance controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModMaintDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierMaintenance.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier une maintenance");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierMaintenance controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppMaintDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerMaintenance.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer une maintenance");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerMaintenance controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddMatDialog(Materiel materiel) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterMateriel.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un nouveau matériel");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterMateriel controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModMatDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierMateriel.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un matériel");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierMateriel controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean showSuppMatDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerMateriel.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un matériel");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerMateriel controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddPrestDialog(Prestataire prest) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterPrestataire.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un prestataire");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterPrestataire controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModPrestDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierPrestataire.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un prestataire");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierPrestataire controller = loader.getController();
			controller.setDialogStage(dialogStage);


			dialogStage.showAndWait();
			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppPrestDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerPrestataire.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un prestataire");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerPrestataire controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddRevDialog(Revendeur rev) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterRevendeur.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un revendeur");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterRevendeur controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModRevDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierRevendeur.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un revendeur");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierRevendeur controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppRevDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerRevendeur.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un revendeur");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerRevendeur controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddUtilisateurDialog(Utilisateur user) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterUtilisateur.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un utilisateur");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterUtilisateur controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModUtilisateurDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierUtilisateur.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un utilisateur");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierUtilisateur controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppUtilisateurDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerUtilisateur.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un utilisateur");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerUtilisateur controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}


	public static boolean showSuppComposeDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerCompose.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer une composition");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerCompose controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddComposeDialog(Composant comp) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterCompose.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter une composition");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterCompose controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showSuppComposantDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerComposant.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer un composant");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerComposant controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModComposantDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierComposant.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier un composant");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierComposant controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddComposantDialog(Type type) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterComposant.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter un composant");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterComposant controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddInterventionDialog(EstIntervenu inter) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterIntervention.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter une intervention");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterIntervention controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}


	public static boolean showSuppInterventionDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerIntervention.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer une intervention");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerIntervention controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showAddUtilisationDialog(Utilise util) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterUtilisation.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter une utilisation");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterUtilisation controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}


	public static boolean showSuppUtilisationDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerUtilisation.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer une Utilisation");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerUtilisation controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean showAddEstMaintenuDialog(EstMaintenu mt) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterEstMaintenu.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Ajouter une opération de maintenance");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			AjouterEstMaintenu controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.showAndWait();
			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}


	public static boolean showSuppEstMaintenuDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerEstMaintenu.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Supprimer une opération de maintenance");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerEstMaintenu controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean showAddInstallationDialog(EstMaintenu mt) {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/ajouterInstallation.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Installation");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			AjouterInstallation controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}


	public static boolean showSuppInstallationDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/supprimerInstallation.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Désinstaller");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			SupprimerInstallation controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean showModBDDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierBD.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier l'URL de la BD");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierBD controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

	public static boolean showModDriverDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(
					MainApp.class.getResource("view/modifierDriver.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Modifier le dossier driver");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			dialogStage.setResizable(false);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			ModifierDriver controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

			return controller.isOkClicked();

		} catch (IOException e) {

			e.printStackTrace();
			return false;
		}
	}

}
