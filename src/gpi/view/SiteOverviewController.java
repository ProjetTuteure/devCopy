package gpi.view;

import utils.Popup;
import gpi.MainApp;
import gpi.exception.ConnexionBDException;
import gpi.metier.Site;
import gpi.metier.SiteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class SiteOverviewController {
	@FXML
	private GridPane gp_site;

	int onglet;

	@FXML
	private ScrollPane sp_site;
	
	@FXML
	private ImageView image_site;

	@FXML
	private Label nomSite;

	@FXML
	private ObservableList<Site> sites;

	@FXML
	private MainApp mainApp;
	
	/**
	 * Constructeur
	 */
	public SiteOverviewController() {
		SiteDAO siteDAO = new SiteDAO();
		this.sites = FXCollections.observableArrayList();
		try {
			this.sites = FXCollections.observableArrayList(siteDAO
					.recupererAllSite());
		} catch (ConnexionBDException e) {
			Popup.getInstance().afficherPopup(e.getMessage());
		}
	}

	/**
	 * Retourne le nombre de site
	 * 
	 * @return
	 */
	public int getNbSite() {
		return this.sites.size();
	}

	/**
	 * Initialise les donn�es Ajoute les villes dans le GridPane
	 */
	@FXML
	private void initialize() {
		this.gp_site.setGridLinesVisible(false);
		this.ajouterVilleGridPane(this.sites);
	}

	/**
	 * Ajoute les villes dans le gridPane
	 * 
	 * @param sites
	 * la liste de sites a ajouter dans le gridPane
	 */
	@FXML
	public void ajouterVilleGridPane(ObservableList<Site> sites) {
		int nbType,largeurCellule;
		nbType=sites.size();
		largeurCellule=getLargeurCellule(sites);
		for(int i=0;i<getNbLigne();i++)
		{
			for(int j=0;j<4;j++)
			{
				if(i*4+j<nbType)
				{
					Site site=sites.get(i*4+j);
					BorderPane bp=new BorderPane();
					Label label=new Label(sites.get(i*4+j).getNomSiteProperty().getValue());
					label.setFont(new Font("Arial",20));
					ImageView image=new ImageView(sites.get(i*4+j).getCheminImageSiteProperty().getValue());
					image.setFitHeight(100);
					image.setFitWidth(100);
					BorderPane.setAlignment(label,Pos.CENTER);
					BorderPane.setAlignment(image,Pos.CENTER);
					bp.setCenter(image);
					bp.setBottom(label);
					Button button=new Button();
					button.setGraphic(bp);
					button.setMinWidth(largeurCellule);
					button.setMaxWidth(largeurCellule);
					button.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							MainApp.setCritere(site);
							MainApp.changerTab("Type");
						}
					});
					this.gp_site.add(button,j,i);
				}
			}
		}
	}
	
	public int getLargeurCellule(ObservableList<Site> sites)
	{
		if(sites.size()<9)
		{
			return 200;
		}
		else
		{
			 return 196;
		}
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Retourne le nombre de lignes en fonction du nombre de site
	 * 
	 * @return le nombre de lignes n�cessaires � l'ajout des sites dans le
	 *         gridPane
	 */
	public int getNbLigne() {
		if (this.getNbSite() % 4 == 0) {
			return this.getNbSite() / 4;
		} else {
			return this.getNbSite() / 4 + 1;
		}
	}

	/**
	 * Retourne le num�ro d'onglet courant
	 * 
	 * @return
	 */
	public int getOnglet() {
		return 0;
	}
}
