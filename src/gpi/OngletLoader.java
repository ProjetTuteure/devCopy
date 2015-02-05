package gpi;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class OngletLoader implements Runnable{
	private TabPane rootLayout;
	private Tab tab;
	private String nom;
	
	public OngletLoader(TabPane rootLayout,Tab tab, String nom){
		this.tab = tab;
		this.nom = nom;
		this.rootLayout = rootLayout;
	}
	@Override
	public void run() {
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
			this.rootLayout.getTabs().add(tab);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
