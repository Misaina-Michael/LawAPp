/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.net.URL;
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
public class ListeProcedureController implements Initializable {

    @FXML private TableView listeProc;
    @FXML private TableColumn colType;
    @FXML private TableColumn colPlanifiee;
    @FXML private TableColumn colNote;
    @FXML private TableColumn colDateProc;
    
    private DossierLibelle dossierLib;
    /**liste
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
            ProcedureLibelle pl=new ProcedureLibelle();
            pl.setIdDossier(dossierLib.getId());
            List<ProcedureLibelle> liste=ds.findProcedure(pl);
            initializeListe(liste);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void initializeListe(List<ProcedureLibelle> dataListe)
    {
        
        colType.setCellValueFactory(
            new PropertyValueFactory<ProcedureLibelle, String>("libProcedure"));
        colPlanifiee.setCellValueFactory(
            new PropertyValueFactory<ProcedureLibelle, Boolean>("planifiee"));
        colNote.setCellValueFactory(
            new PropertyValueFactory<ProcedureLibelle, String>("notes"));
        colDateProc.setCellValueFactory(
            new PropertyValueFactory<ProcedureLibelle, Date>("dateProcedure"));
        
        
        ObservableList<ProcedureLibelle> data;
        data =FXCollections.observableArrayList();
        data.setAll(dataListe);
        
        listeProc.setItems(data);
        colDateProc.setCellFactory(column -> {
            return new TableCell<FactureLibelle,Date>() {
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if(item!=null){

                        setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                    }
                }
            };
        });
        colPlanifiee.setCellFactory(column -> {
            return new TableCell<FactureLibelle,Boolean>() {
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    setAlignment(Pos.CENTER);
                    if(item!=null){
                        if(item)setText("Oui");
                        else setText("Non");
                    }
                }
            };
        });
        
    }
    
    public void createProcedure(ActionEvent ae)
    {
        Stage s=null;
        try
        {
            s=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/FormulaireProcedure.fxml"));
            Parent root = (AnchorPane)loader.load();
            FormulaireProcedureController formCtrl=loader.<FormulaireProcedureController>getController();

            formCtrl.setDossierLib(dossierLib);
            
            formCtrl.setPrecCtrl(this);
            formCtrl.initializeChamps();
            Scene scene = new Scene(root);
            s.setScene(scene);
            formCtrl.setStage(s);
            s.setHeight(330);
            StageStatique.setStage3(s);
            s.initOwner(StageStatique.getStage2());
            s.initModality(Modality.WINDOW_MODAL); 
            s.showAndWait();
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
            Planning proc=new Planning();
            proc.setId(((ProcedureLibelle)listeProc.getSelectionModel().getSelectedItem()).getId());
            proc=(Planning)bs.findById(proc);
            bs.delete(proc);
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
            ProcedureLibelle pl=new ProcedureLibelle();
            pl.setIdDossier(dossierLib.getId());
            
            JRBeanCollectionDataSource collections=new JRBeanCollectionDataSource(service.findProcedure(pl));
            map.put("PROCEDURES", collections);
            map.put("NUMERO_DOSSIER", dossierLib.getNumeroDossier());
            map.put("NOM_DOSSIER", dossierLib.getVnomDossier());
            reportUtil=new ReportUtil();
            String pathReport=ReportUtil.pathDocs+"/report/procedures-dossier";
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
