package Test;

import javafx.application.Application;
import javafx.stage.Stage;
import utils.PopupChargement;

public class TestPopup extends Application{
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		PopupChargement popup=new PopupChargement();
	}
}
