/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.dossiers.DossierLibelle;
import modeles.facturation.FactureLibelle;
import modeles.planning.Planning;
import modeles.planning.PlanningLibelle;
import modeles.planning.ProcedureLibelle;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import services.BaseService;
import services.PlanningService;
import statiques.StageStatique;
import utilitaire.ReportUtil;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ListePlanningDossierController implements Initializable {

    @FXML private TableColumn colDate;
    @FXML private TableColumn colHeure;
    @FXML private TableColumn colNotes;
    @FXML private TableColumn colInterv;
    @FXML private TableView tabListe;
    
    
    private DossierLibelle dossierLib;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initPrim()
    {
        try {
       
            PlanningService ds=new PlanningService();
            PlanningLibelle pl=new PlanningLibelle();
            pl.setIdDossier(dossierLib.getId());
             
            List<PlanningLibelle> liste=ds.findPlanning(pl);
            initializeListe(liste);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void initializeListe(List<PlanningLibelle> li)
    {
        try
        {
            colDate.setCellValueFactory(
            new PropertyValueFactory<PlanningLibelle, String>("datePlanning"));
            colHeure.setCellValueFactory(
                new PropertyValueFactory<PlanningLibelle, Boolean>("heureDebut"));
            colNotes.setCellValueFactory(
                new PropertyValueFactory<PlanningLibelle, String>("notes"));
            colInterv.setCellValueFactory(
                new PropertyValueFactory<PlanningLibelle, Date>("nomCompletIntervenant"));


            ObservableList<PlanningLibelle> data;
            data =FXCollections.observableArrayList();
            data.setAll(li);

            tabListe.setItems(data);
            colDate.setCellFactory(column -> {
                return new TableCell<FactureLibelle,Date>() {
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if(item!=null){

                            setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                        }
                    }
                };
            });
            colHeure.setCellFactory(column -> {
                return new TableCell<FactureLibelle,Time>() {
                    protected void updateItem(Time item, boolean empty) {
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER);
                        if(item!=null)
                        {
                            setText(new SimpleDateFormat("HH:mm").format(item));
                        }
                    }
                };
            });
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void createPlanning(ActionEvent ae)
    {
        Stage stage=new Stage();
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/FormulairePlanning.fxml"));
            Parent root = (AnchorPane)loader.load();
            FormulairePlanningController formCtrl=loader.<FormulairePlanningController>getController();

            formCtrl.setDossierLib(dossierLib);
            
            formCtrl.setPredCtrl(this);
            PlanningLibelle pl=new PlanningLibelle();
            pl.initValues();
            formCtrl.setPlLib(pl);
            formCtrl.initializeChamps();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            formCtrl.setStage(stage);
            StageStatique.setStage3(stage);
            stage.initOwner(StageStatique.getStage2());
            stage.initModality(Modality.WINDOW_MODAL); 
            stage.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void delete(ActionEvent ae)
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            Planning planning=new Planning();
            planning.setId(((PlanningLibelle)tabListe.getSelectionModel().getSelectedItem()).getId());
            planning=(Planning)bs.findById(planning);
            bs.delete(planning);
            this.initPrim();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void print(ActionEvent ae)
    {
        ReportUtil reportUtil=null;
        Map<String, Object> map=null;
        PlanningService service=null;
        try
        {
            service=new PlanningService();
            map=new HashMap<String, Object>();
            PlanningLibelle pl=new PlanningLibelle();
            pl.setIdDossier(dossierLib.getId());
            
            JRBeanCollectionDataSource collections=new JRBeanCollectionDataSource(service.findPlanning(pl));
            map.put("PLANNING", collections);
            map.put("NUMERO_DOSSIER", dossierLib.getNumeroDossier());
            map.put("NOM_DOSSIER", dossierLib.getVnomDossier());
            reportUtil=new ReportUtil();
            String pathReport=ReportUtil.pathDocs+"/report/planning-dossier";
            reportUtil.showViewer(map, pathReport);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }
    
}
