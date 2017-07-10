/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modeles.BaseModele;
import services.BaseService;
import utilitaire.ListViewUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ListeGeneriqueController implements Initializable {

    @FXML private TextField recherche;
    @FXML private Button valider;
    @FXML private Button customValid;
    @FXML private Label titre;
    @FXML private ListView listView;
    
    private BaseModele base;
    private BaseModele itemSelected;
    private String[] cols;
    private List<BaseModele> liste;
    
    private Stage stage;
    
    // à modifier après selection
    private TextField champ;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void initializeListe()
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            
            initializeListView(bs.find(base));
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    
    public void initializeListView(List<BaseModele> b)
    {
        ObservableList<BaseModele> data;
        data =FXCollections.observableArrayList();
        data.setAll(b);

        listView.setItems(data);
        ListViewUtil lvu=new ListViewUtil();
        
        listView.setCellFactory(lvu.buildCellFactory(cols, " "));
    }
    public void validAction(ActionEvent ae)
    {
        Util util=new Util();
        try
        {
            this.setItemSelected((BaseModele)listView.getSelectionModel().getSelectedItem());
            Method me=this.getItemSelected().getClass().getMethod("get"+util.premierMaj(cols[0]), null);

            getChamp().setText(me.invoke(getItemSelected(), null).toString());
            stage.close();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    public void search(ActionEvent ae)
    {
        String val=recherche.getText();
        BaseService bs=null;
        ListViewUtil lvu=null;
        try
        {
            Class[] cl=new Class[1];
            cl[0]=String.class;
            Util util=new Util();
            Method meth=base.getClass().getMethod("set"+util.premierMaj(cols[0]), cl);
            meth.invoke(base, val);
            lvu=new ListViewUtil();
            bs=new BaseService();
            liste=bs.find(base);
            initializeListView(liste);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public BaseModele getBase() {
        return base;
    }

    public void setBase(BaseModele base) {
        this.base = base;
    }

    public String[] getCols() {
        return cols;
    }

    public void setCols(String[] cols) {
        this.cols = cols;
    }

    public BaseModele getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(BaseModele itemSelected) {
        this.itemSelected = itemSelected;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TextField getChamp() {
        return champ;
    }

    public void setChamp(TextField champ) {
        this.champ = champ;
    }

    public Button getValider() {
        return valider;
    }

    public void setValider(Button valider) {
        this.valider = valider;
    }

    public Button getCustomValid() {
        return customValid;
    }

    public void setCustomValid(Button customValid) {
        this.customValid = customValid;
    }
    
    
}
