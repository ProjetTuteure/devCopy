package gpi.bd;

import gpi.metier.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

/**
 * Created by admin on 24/11/14.
 */
public class Donnee {
	private ObservableList<EstIntervenu> estIntervenuData = FXCollections
			.observableArrayList();
	private ObservableList<EstMaintenu> estMaintenuData = FXCollections
			.observableArrayList();
	private ObservableList<Etat> etatData = FXCollections.observableArrayList();
	private ObservableList<Fabricant> fabricantData = FXCollections
			.observableArrayList();
	private ObservableList<Facture> factureData = FXCollections
			.observableArrayList();
	private ObservableList<Logiciel> logicielData = FXCollections
			.observableArrayList();
	private ObservableList<Maintenance> maintenanceData = FXCollections
			.observableArrayList();
	private ObservableList<Materiel> materielData = FXCollections
			.observableArrayList();
	private ObservableList<Prestataire> prestataireData = FXCollections
			.observableArrayList();
	private ObservableList<Revendeur> revendeurData = FXCollections
			.observableArrayList();
	private ObservableList<Site> siteData = FXCollections.observableArrayList();
	private ObservableList<Type> typeData = FXCollections.observableArrayList();
	private ObservableList<Utilisateur> utilisateurData = FXCollections
			.observableArrayList();
	private ObservableList<Utilise> utiliseData = FXCollections
			.observableArrayList();
	private ObservableList<Composant> composantData = FXCollections
			.observableArrayList();

	public Donnee() {
		
	}
}
