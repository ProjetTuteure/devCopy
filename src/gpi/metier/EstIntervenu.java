package gpi.metier;


public class EstIntervenu {
    private Facture factureEstIntervenu;
    private Maintenance maintenanceEstIntervenu;
    private Prestataire prestataireEstIntervenu;

    public EstIntervenu(Facture factureEstIntervenu, Maintenance maintenanceEstIntervenu, Prestataire prestataireEstIntervenu) {
        this.factureEstIntervenu = factureEstIntervenu;
        this.maintenanceEstIntervenu = maintenanceEstIntervenu;
        this.prestataireEstIntervenu = prestataireEstIntervenu;
    }

    public Facture getFactureEstIntervenu() {
        return factureEstIntervenu;
    }

    public void setFactureEstIntervenu(Facture factureEstIntervenu) {
        this.factureEstIntervenu = factureEstIntervenu;
    }

    public Maintenance getMaintenanceEstIntervenu() {
        return maintenanceEstIntervenu;
    }

    public void setMaintenanceEstIntervenu(Maintenance maintenanceEstIntervenu) {
        this.maintenanceEstIntervenu = maintenanceEstIntervenu;
    }

    public Prestataire getPrestataireEstIntervenu() {
        return prestataireEstIntervenu;
    }

    public void setPrestataireEstIntervenu(Prestataire prestataireEstIntervenu) {
        this.prestataireEstIntervenu = prestataireEstIntervenu;
    }
}
