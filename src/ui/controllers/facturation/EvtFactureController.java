/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.facturation;

import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modeles.facturation.FactureEvtLibelle;
import modeles.facturation.FactureLibelle;
import services.BaseService;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class EvtFactureController implements Initializable {
    @FXML private TableView tabEvt;
    @FXML private TableColumn colDate;
    @FXML private TableColumn colDuree;
    @FXML private TableColumn colEvt;
    @FXML private TableColumn colMontant;
    @FXML private TableColumn colInterv;
    @FXML private Label total;
    /**
     * Initializes the controller class.
     */
    private FactureLibelle fact;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void initializeListe()
    {
        BaseService bs=new BaseService();
        try
        {
            bs=new BaseService();
            FactureEvtLibelle f=new FactureEvtLibelle();
            f.setIdFacture(fact.getId());
            List<FactureEvtLibelle> lis=(List<FactureEvtLibelle>)(List<?>)bs.find(f);
            colDate.setCellValueFactory(new PropertyValueFactory<FactureEvtLibelle, Date>("daty"));
            colInterv.setCellValueFactory(new PropertyValueFactory<FactureEvtLibelle, String>("nomInterv"));
            colEvt.setCellValueFactory(new PropertyValueFactory<FactureEvtLibelle, String>("libelle"));
            colDuree.setCellValueFactory(new PropertyValueFactory<FactureEvtLibelle, Time>("duree"));
            colMontant.setCellValueFactory(new PropertyValueFactory<FactureEvtLibelle, Float>("mt"));
            
            
            ObservableList<FactureEvtLibelle> data;
            data =FXCollections.observableArrayList();
            data.setAll(lis);
            tabEvt.setItems(data);
            
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
            colDuree.setCellFactory(column -> {
                return new TableCell<FactureLibelle,Time>() {
                    protected void updateItem(Time item, boolean empty) {
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER);
                        if(item!=null){
                            setText(new SimpleDateFormat("HH:mm").format(item));
                        }
                    }
                };
            });
            colMontant.setCellFactory(column -> {
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
            
            
            Float mt=new Float(0);
            for(FactureEvtLibelle fe:lis)
            {
                mt=mt+fe.getMt();
            }
            total.setText(new DecimalFormat("#,##0.00").format(mt)+" Ariary");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public FactureLibelle getFact() {
        return fact;
    }

    public void setFact(FactureLibelle fact) {
        this.fact = fact;
    }
    
}
