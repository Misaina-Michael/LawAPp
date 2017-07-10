/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.crud;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modeles.BaseModele;
import services.BaseService;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ListeController implements Initializable {
    @FXML private TableView tableView;
    @FXML private Label titre;
    @FXML private Button buttonCreate;
    @FXML private Button buttonUpdate;
    @FXML private Button buttonDelete;
    private String titreLabel; 
    private BaseModele modele;
    private String[] fieldsToShow;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void init(BaseModele baseModele)
    {
        try
        {
            tableView.getColumns().clear();
            Util util=new Util();
            Field[] fields=util.getAllFields(baseModele.getClass());
            titre.setText(titreLabel);
            
            for(int i=0; i<fields.length; i++)
            {
                for(int ind=0;ind<fieldsToShow.length;ind++)
                {
                    if(fields[i].getName().compareToIgnoreCase(fieldsToShow[ind])==0)
                    {
                        TableColumn col=new TableColumn();
                        col.setText(fields[i].getName());
                        col.setCellValueFactory(new PropertyValueFactory<>(fields[i].getName()));
                        tableView.getColumns().add(col);
                        col.setMinWidth(200);
                    }
                }
            }
            
            
            BaseService bs=new BaseService();
            List<BaseModele> listeData=bs.find(baseModele);
            ObservableList<BaseModele> data;
            data =FXCollections.observableArrayList();
            data.setAll(listeData);
            tableView.setItems(data);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void disableActions(){
        buttonCreate.setDisable(true);
        buttonUpdate.setDisable(true);
        buttonDelete.setDisable(true);
        
    }
    public void itemClicked(MouseEvent mouseEvent)
    {
        
    }
    public void create(ActionEvent event)
    {
        Stage s=null;
        try
        {
            s=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/crud/Formulaire.fxml"));
            Parent root = (AnchorPane)loader.load();
            FormulaireController formCtrl=loader.<FormulaireController>getController();
            formCtrl.setAction("create");
            formCtrl.setModele(modele);
            formCtrl.init();
            formCtrl.setListeCtrlPrec(this);
            formCtrl.setStage(s);
            Scene scene = new Scene(root);
            s.setScene(scene);
            s.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void update(ActionEvent event)
    {
        Stage s=null;
        try
        {
            s=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/crud/Formulaire.fxml"));
            Parent root = (AnchorPane)loader.load();
            FormulaireController formCtrl=loader.<FormulaireController>getController();
            BaseService bs=new BaseService();
            BaseModele bm=bs.findById((BaseModele)tableView.getSelectionModel().getSelectedItem());
            formCtrl.setAction("update");
            formCtrl.setModele(bm);
            formCtrl.init();
            formCtrl.setListeCtrlPrec(this);
            formCtrl.setStage(s);
            Scene scene = new Scene(root);
            s.setScene(scene);
            s.show();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void delete(ActionEvent event)
    {
        try
        {
            BaseService bs=new BaseService();
            BaseModele bm=bs.findById((BaseModele)tableView.getSelectionModel().getSelectedItem());
            bs.delete(bm);
            BaseModele bm0=modele.getClass().newInstance();
            bm0.initValues();
            init(bm0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public String getTitreLabel() {
        return titreLabel;
    }

    public void setTitreLabel(String titreLabel) {
        this.titreLabel = titreLabel;
    }

    public BaseModele getModele() {
        return modele;
    }

    public void setModele(BaseModele modele) {
        this.modele = modele;
    }

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    public String[] getFieldsToShow() {
        return fieldsToShow;
    }

    public void setFieldsToShow(String[] fieldsToShow) {
        this.fieldsToShow = fieldsToShow;
    }
    
    
    
}

