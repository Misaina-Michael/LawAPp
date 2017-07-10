/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.facturation;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.dossiers.Dossier;
import modeles.facturation.FactureLibelle;
import services.BaseService;
import statiques.StageStatique;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ListeFactureController implements Initializable {

    @FXML private TableView tabFact;
    @FXML private TableColumn colNo;
    @FXML private TableColumn colDate;
    @FXML private TableColumn colDossier;
    @FXML private TableColumn colTotal;
    @FXML private TableColumn colReglee;
    @FXML private TableColumn colSanssuite;
    
    private List<FactureLibelle> listeFact;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try
        {
            initializeListe(new FactureLibelle());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void initializeListe(FactureLibelle fact)
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            List<FactureLibelle> lis=(List<FactureLibelle>)(List<?>)bs.find(fact);
            colDate.setCellValueFactory(new PropertyValueFactory<FactureLibelle, Date>("dateFacture"));
            colNo.setCellValueFactory(new PropertyValueFactory<FactureLibelle, String>("id"));
            colDossier.setCellValueFactory(new PropertyValueFactory<FactureLibelle, String>("vnomDossier"));
            colTotal.setCellValueFactory(new PropertyValueFactory<FactureLibelle, Float>("totalTtc"));
            colReglee.setCellValueFactory(new PropertyValueFactory<FactureLibelle, Boolean>("reglee"));
            colSanssuite.setCellValueFactory(new PropertyValueFactory<FactureLibelle, Boolean>("sansSuite"));
            
            
            ObservableList<FactureLibelle> data;
            data =FXCollections.observableArrayList();
            data.setAll(lis);

            tabFact.setItems(data);
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
            colSanssuite.setCellFactory(column -> {
                return new TableCell<FactureLibelle,Boolean>() {
                    protected void updateItem(Boolean item, boolean empty) {
                        setAlignment(Pos.CENTER);
                        if(item!=null){
                            if(item) setText("Oui");
                            else setText("Non");
                        }
                    }
                };
            });
            colReglee.setCellFactory(column -> {
                return new TableCell<FactureLibelle,Boolean>() {
                    protected void updateItem(Boolean item, boolean empty) {
                        setAlignment(Pos.CENTER);
                        if(item!=null){
                            if(item) setText("Oui");
                            else setText("Non");
                        }
                        
                    }
                };
            });
            colTotal.setCellFactory(column -> {
                return new TableCell<FactureLibelle,Float>() {
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
//                        if(item==null || empty) setText("0.00");
                        if(item!=null && !empty)
                        {
                            setAlignment(Pos.CENTER_RIGHT);
                            setText(new DecimalFormat("#,##0.00").format(item));
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
    public void itemClicked(MouseEvent event)
    {
        Stage s=null;
        BaseService service=null;
        try
        {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        
                s=new Stage();
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/facturation/PanneauFact.fxml"));
                Parent root = (AnchorPane)loader.load();
                PanneauFactController panneauCtrl=loader.<PanneauFactController>getController();
                panneauCtrl.setFactLibelle((FactureLibelle)tabFact.getSelectionModel().getSelectedItem());
                Dossier d=new Dossier();
                d.setId(panneauCtrl.getFactLibelle().getIdDossier());
                service=new BaseService();
                d=(Dossier)service.findById(d);
                panneauCtrl.refresh();
                panneauCtrl.setDossier(d);
                panneauCtrl.fiche();
                Scene sc=new Scene(root);
                
                panneauCtrl.setDossier(d);
                s.setTitle("Facture N° "+panneauCtrl.getFactLibelle().getId()+" : "+d.getVnomDossier()+" "+d.getId());
                s.setScene(sc);
                StageStatique.setStage2(s);
                s.initOwner(StageStatique.getStage1());
                s.initModality(Modality.WINDOW_MODAL); 
                s.showAndWait();
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
