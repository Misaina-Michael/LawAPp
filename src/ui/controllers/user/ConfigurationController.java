/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.user;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import modeles.intervenants.Intervenant;
import services.IntervenantService;
import utilitaire.ListViewUtil;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class ConfigurationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    List<Intervenant> liste = null;
    IntervenantService intervServ = new IntervenantService();
    @FXML
    ListView listViewUsers;
    @FXML
    public Pane ficheUserPane;
    @FXML
    public Pane droitUserPane;
    ConfigurationController cof = this;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.initialiseListViewUser();
    }

    public void actualiseListViewUser() throws Exception {
        try {
            List<Intervenant>liste = intervServ.find(new Intervenant());
            ObservableList<Intervenant> data;
            data = FXCollections.observableArrayList();
            data.setAll(liste);
            listViewUsers.setItems(data);
            String[] nomCols = new String[2];
            nomCols[0] = "nom";
            nomCols[1] = "prenom";
            listViewUsers.setCellFactory(new ListViewUtil().buildCellFactory(nomCols, " "));
        } catch (Exception ex) {
            throw ex;
        }

    }

    public void initialiseListViewUser() {
        try {
            ConfigurationController temp = this;
             List<Intervenant> lb = intervServ.find(new Intervenant());
            ObservableList<Intervenant> data;
            data = FXCollections.observableArrayList();
            for(int x = 0 ; x < lb.size() ; x++){
                System.out.println("huhu" + lb.get(x).getLogin());
            }
            data.setAll(lb);
            listViewUsers.setItems(data);
            String[] nomCols = new String[2];
            nomCols[0] = "nom";
            nomCols[1] = "prenom";
            listViewUsers.setCellFactory(new ListViewUtil().buildCellFactory(nomCols, " "));
            listViewUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Intervenant>() {
                @Override
                public void changed(ObservableValue<? extends Intervenant> observable, Intervenant oldValue, Intervenant newValue) {
                    try {
                        ficheUserPane.getChildren().clear();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/user/FicheUser.fxml"));
                        Parent root = (Pane) loader.load();
                        FicheUserController ficheUser = loader.<FicheUserController>getController();
                        ficheUser.setConfig(cof);
                        ficheUser.setIntervenant(newValue);
                        ficheUser.IntitialiseDataFicheUser();                   
                        ficheUserPane.getChildren().add(root);
                        
                        droitUserPane.getChildren().clear();
                        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/ui/fxml/user/DroitUser.fxml"));
                        Parent root2 = (Pane) loader2.load();
                        DroitUserController droituser = loader2.<DroitUserController>getController();
                        droituser.setIntervenantDroit(newValue);
                        droituser.initialiseDroitsUser();
                        droituser.setConfigController(temp);
                        droitUserPane.getChildren().add(root2);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
