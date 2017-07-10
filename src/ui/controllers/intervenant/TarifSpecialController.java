/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static jdk.nashorn.internal.objects.NativeJava.type;
import modeles.intervenants.Intervenant;
import modeles.intervenants.TarifNormaux;
import modeles.intervenants.TarifSpecial;
import modeles.intervenants.TarifSpeciaux;
import org.hibernate.exception.ConstraintViolationException;
import services.BaseService;
import services.IntervenantService;
import statiques.StageStatique;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class TarifSpecialController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    TableView tarifSpecialTableView;
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
    IntervenantService intervService = new IntervenantService();
    private Intervenant intervenant;
    BaseService bs = new BaseService();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initialiseTableau() throws Exception {
       try {

            type.setCellValueFactory(new PropertyValueFactory<>("libelleTypeTarif"));
            libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
            base.setCellValueFactory(new PropertyValueFactory<>("mtevt"));
            taux.setCellValueFactory(new PropertyValueFactory<>("taux"));
            montant.setCellValueFactory(new PropertyValueFactory<>("mt"));           
            IntervenantService intervenantService = new IntervenantService();
            TarifSpeciaux t = new TarifSpeciaux();
            t.setIdintervenant(getIntervenant().getId());
            t.setType("ts");
            List<TarifSpeciaux> dataTarifIntervenantLibelle = intervenantService.findTarifSpeciaux(t);
            ObservableList<TarifSpeciaux> data = FXCollections.observableArrayList();
            for (int compteur1 = 0; compteur1 < dataTarifIntervenantLibelle.size(); compteur1++) {
                data.add(dataTarifIntervenantLibelle.get(compteur1));
            }

            tarifSpecialTableView.getColumns().clear();
            tarifSpecialTableView.setItems(data);
            tarifSpecialTableView.getColumns().addAll(type, libelle, duree, base, taux, montant);
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

    @FXML
    public void addTarifSpecial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/AddTarifSpecial.fxml"));
            Parent root = (Pane) loader.load();
            AddTarifSpecialController addTarif = loader.<AddTarifSpecialController>getController();
            addTarif.setInterv(this.getIntervenant());
            addTarif.intializeCombobox();
            addTarif.setTarifSpecialController(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Ajouter une cotation spéciale pour cet individu");
            stage.setScene(scene);
            StageStatique.setStage2(stage);
            stage.initOwner(StageStatique.getStage1());
            stage.initModality(Modality.WINDOW_MODAL);
            addTarif.setStagePrec(stage);
            stage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void updatetarifbutton(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/UpdateTarifSpecial.fxml"));
            Parent root = (Pane) loader.load();
            UpdateTarifSpecialController upd = loader.<UpdateTarifSpecialController>getController();
            TarifSpeciaux ts = (TarifSpeciaux) tarifSpecialTableView.getSelectionModel().getSelectedItem();
            upd.setTarifSpecial(ts);
            upd.setInterv(this.getIntervenant());
            upd.setTarifSpecialController(this);
            upd.initialiseField();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Modifier");
            stage.setScene(scene);
            StageStatique.setStage2(stage);
            stage.initOwner(StageStatique.getStage1());
            stage.initModality(Modality.WINDOW_MODAL);
            upd.setStagePrec(stage);
            stage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void deleteTarif(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer intervenant");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Confirmez vous la suppression de ce type de tâche?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                TarifSpecial ts = (TarifSpecial) tarifSpecialTableView.getSelectionModel().getSelectedItem();
                TarifSpecial tarif = new TarifSpecial();
                tarif.setId(ts.getId());
                bs.delete(ts);
                this.initialiseTableau();
            } else {
                alert.close();
            }
        }
        catch(ConstraintViolationException ex)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de suppresion");
            alert.setContentText("Impossible de supprimer ce type de tâche car elle est déjà utilisée");
            alert.show();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
       

    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

}
