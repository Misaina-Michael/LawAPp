/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.evenement.EvtTarif;
import modeles.intervenants.Intervenant;
import modeles.intervenants.TarifIntervLibelle;
import modeles.intervenants.TarifNormaux;
import services.BaseService;
import services.IntervenantService;
import statiques.StageStatique;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class TarifsNormauxController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Intervenant intervenant;
    @FXML
    private TableView listetauxnormaux;
    @FXML
    private TableColumn type;
    @FXML
    private TableColumn libelle;
    @FXML
    private TableColumn duree;
    @FXML
    private TableColumn base;
    @FXML
    private TableColumn taux;
    @FXML
    private TableColumn montant;
    @FXML
    private TableColumn idTarifIntervenant;
    private TarifsNormauxController tnc = this;

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        listetauxnormaux.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        TarifNormaux tb = (TarifNormaux) listetauxnormaux.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/TarifsNormauxdetails.fxml"));
                        Parent root = (Pane) loader.load();
                        TarifsNormauxdetailsController detailstarif = loader.<TarifsNormauxdetailsController>getController();
                        detailstarif.setTarifnormaux(tb);
                        detailstarif.setTemp(Integer.SIZE);
                        detailstarif.setTnController(tnc);
                        detailstarif.InitializeChamps();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Détail");
                        detailstarif.setStagedetail(stage);
                        stage.setScene(scene);
                        StageStatique.setStage2(stage);
                        stage.initOwner(StageStatique.getStage1());
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.showAndWait();
                        //stage.show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void intializeTableaulisteContact() {
        try {

            type.setCellValueFactory(new PropertyValueFactory<>("libelleTypeTarif"));
            libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
            base.setCellValueFactory(new PropertyValueFactory<>("mtevt"));
            taux.setCellValueFactory(new PropertyValueFactory<>("taux"));
            montant.setCellValueFactory(new PropertyValueFactory<>("mt"));           
            IntervenantService intervenantService = new IntervenantService();
            TarifNormaux t = new TarifNormaux();
            t.setIdintervenant(getIntervenant().getId());
            
            List<TarifNormaux> dataTarifIntervenantLibelle = intervenantService.findTarifNormaux(t);
            ObservableList<TarifNormaux> data = FXCollections.observableArrayList();
            for (int compteur1 = 0; compteur1 < dataTarifIntervenantLibelle.size(); compteur1++) {
                data.add(dataTarifIntervenantLibelle.get(compteur1));
            }
            listetauxnormaux.getColumns().clear();
            listetauxnormaux.setItems(data);
            listetauxnormaux.getColumns().addAll(type, libelle, duree, base, taux, montant);
            DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.FRANCE);
            dfs.setGroupingSeparator(' ');
            base.setCellFactory(column -> {
                return new TableCell<TarifNormaux, Float>() {
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(new DecimalFormat("#,##0.00", dfs).format(item));
                        }
                    }
                };
            });
            montant.setCellFactory(column -> {
                return new TableCell<TarifNormaux, Float>() {
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {

                            setText(new DecimalFormat("#,##0.00", dfs).format(item));
                        }
                    }
                };
            });

        } catch (Exception ex) {
            Logger.getLogger(TarifsNormauxController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
