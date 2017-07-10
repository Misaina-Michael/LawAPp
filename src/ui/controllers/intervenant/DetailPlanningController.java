/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import modeles.affichage.ColonneDate;
import modeles.affichage.EspaceString;
import modeles.affichage.EvtDossierLibGroupBy;
import modeles.evenement.EvtDossierLibelle;
import services.EvenementService;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class DetailPlanningController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView DetailEvenements;
    @FXML
    private TableColumn notes;
    @FXML
    private TableColumn duree;
    @FXML
    private TableColumn demandeur;
    @FXML
    private TableColumn intervenant;
    @FXML
    private TableColumn evenement;
    @FXML
    private TableColumn date;
    private Integer idDossier;
    Util util = new Util();
    public Integer getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void LoadDetailPlanningTableView() {
        try {
            EvenementService evtService = new EvenementService();
           
            List<EvtDossierLibelle> evtdlb = evtService.FindEventBetween2DatesByDossier(IntervenantPlanningController.debutIntervalle, IntervenantPlanningController.finIntervalle, this.getIdDossier());
            intervenant.setCellValueFactory(new PropertyValueFactory<>("nomInterv"));
            demandeur.setCellValueFactory(new PropertyValueFactory<>("nomDemandeur"));
            duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
            evenement.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            notes.setCellValueFactory(new PropertyValueFactory<>("note"));
            date.setCellValueFactory(new PropertyValueFactory<>("daty"));
             ObservableList<EvtDossierLibelle> data = FXCollections.observableArrayList();
            for (int compteur1 = 0 ; compteur1 < evtdlb.size() ; compteur1 ++ ){
                 data.add(evtdlb.get(compteur1));
            }
            DetailEvenements.getColumns().clear();
            DetailEvenements.setItems(data);
            DetailEvenements.getColumns().addAll(intervenant, demandeur, duree,evenement,notes,date);
            date.setCellFactory(column -> {
                return new TableCell<EvtDossierLibelle,Date>() {
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            System.out.println("valeur nulle");
                        } 
                        else {
                             String date = util.dateToString(item);
                             setText(date);
                            
                        }
                    }
                };
            });
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
