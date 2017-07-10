    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modeles.affichage.EvtDossierLibGroupBy;
import modeles.evenement.EvtDossierLibelle;
import modeles.intervenants.Intervenant;
import services.EvenementService;
import services.IntervenantService;
import static ui.controllers.intervenant.IntervenantPlanningController.debutIntervalle;
import static ui.controllers.intervenant.IntervenantPlanningController.finIntervalle;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class DossierIntervenantController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Intervenant intervenant;
    @FXML
    private TableView dossierIntervTableView;
    @FXML
    private TableColumn numDossier;
    @FXML
    private TableColumn nomDossier;
     @FXML
    private TableView demandeurDossierTableView;
    @FXML
    private TableColumn numdossdem;
    @FXML
    private TableColumn nomDossDem;


    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }
    IntervenantService intervServ = new IntervenantService();
    EvenementService evtService = new EvenementService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void InitialiseTableauIntervenant() throws Exception {
        try {
            nomDossier.setCellValueFactory(new PropertyValueFactory<>("nomDossier"));
            numDossier.setCellValueFactory(new PropertyValueFactory<>("numDossier"));
            List<EvtDossierLibGroupBy> evtGroupby = evtService.FindEventByIntervGroupByDossier(this.getIntervenant().getId(), "intervenant");
            ObservableList<EvtDossierLibGroupBy> data = FXCollections.observableArrayList();
            for (int compteur1 = 0; compteur1 < evtGroupby.size(); compteur1++) {
                data.add(evtGroupby.get(compteur1));
            }
            dossierIntervTableView.getColumns().clear();
            dossierIntervTableView.setItems(data);
            dossierIntervTableView.getColumns().addAll(numDossier, nomDossier);
            /*------*/
             nomDossDem.setCellValueFactory(new PropertyValueFactory<>("nomDossier"));
            numdossdem.setCellValueFactory(new PropertyValueFactory<>("numDossier"));
            List<EvtDossierLibGroupBy> evtGroupbyDem = evtService.FindEventByIntervGroupByDossier(this.getIntervenant().getId(), "demandeur");
            ObservableList<EvtDossierLibGroupBy> data2 = FXCollections.observableArrayList();
            for (int compteur1 = 0; compteur1 < evtGroupbyDem.size(); compteur1++) {
                data2.add(evtGroupbyDem.get(compteur1));
            }
            demandeurDossierTableView.getColumns().clear();
            demandeurDossierTableView.setItems(data2);
            demandeurDossierTableView.getColumns().addAll(numdossdem, nomDossDem);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
