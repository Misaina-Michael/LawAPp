/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.contact;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
//import modeles.Person;
import modeles.contact.Contact;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modeles.clients.Client;
import services.ClientService;
import services.ContactService;
import ui.controllers.contact.FicheContactController;
import services.ContactService;
import utilitaire.ListViewUtil;
import modeles.contact.Contact;
import modeles.parametres.Titre;
import services.BaseService;
//import services.TitreService;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class PanneauPrincipaleContactController implements Initializable {

    /**
     * Initializes the controller class.
     */
    //private List<Contact> liste=null;
    ContactService contactService = new ContactService();
//    TitreService titreService = new TitreService();
    @FXML
    public Pane panneauPrincipalContact;
    @FXML
    private TableView listeContacts;
    @FXML
    private TableColumn nom;
    @FXML
    private TableColumn prenom;
    @FXML
    private DatePicker dd;

    @FXML
    private TableColumn adresse;
     @FXML
    private TableColumn pays;
      @FXML
    private TableColumn ville;
    @FXML
    public ListView listeviewContact;
    @FXML
    public Pane listviewPane;
    @FXML
    public FicheContactController fc;
    public PanneauPrincipaleContactController pc = this;
    private Button nouveauContactButton;
    private final StringProperty selectedValue = new SimpleStringProperty(this, "selectedValue", "");

    public final StringProperty selectedValueProperty() {
        return selectedValue;
    }

    public final void setSelectedValue(String value) {
        selectedValue.set(value);
    }

    public final String getSelectedValue() {
        return selectedValue.get();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
         pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
       PanneauPrincipaleContactController pctemp  = this; 
        listeContacts.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        panneauPrincipalContact.getChildren().clear();
                        Contact tb = (Contact) listeContacts.getSelectionModel().getSelectedItem();
                        Contact cn = new Contact();
                        cn.setId(tb.getId());
                        Contact contactselected = (Contact) contactService.findContactById(cn);
                        Titre ti = new Titre();
                        ti.setId(contactselected.getIdTitre());
                        BaseService bs = new BaseService();
                        Titre titreselected = (Titre) bs.findById(ti);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/contact/FicheContact.fxml"));
                        Parent root = (Pane) loader.load();

                        FicheContactController ficheContact = loader.<FicheContactController>getController();
                        ficheContact.setContact(contactselected);
                        ficheContact.setTitret(titreselected);
                        ficheContact.setDisableButtonContact(true);
                        ficheContact.setPpcController(pctemp);
                        ficheContact.initializeInfoContact();
                        
                        //ficheContact.ppcController = pc;
                        fc = ficheContact;
                        panneauPrincipalContact.getChildren().add(root);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(FicheContactController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(PanneauPrincipaleContactController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.tableaulisteContact();
        this.initializeFormulaire();
        //this.initializeListeViewContact();

    }

    public void tableaulisteContact() {
        try {
            List<Contact> dataContact = contactService.findContact(new Contact());
            ObservableList<Contact> data = FXCollections.observableArrayList();
            for (int compteur1 = 0; compteur1 < dataContact.size(); compteur1++) {
                data.add(dataContact.get(compteur1));
            }
            listeContacts.getColumns().clear();
            listeContacts.setItems(data);
            listeContacts.getColumns().addAll(nom, prenom, adresse,pays,ville);

        } catch (Exception ex) {
            Logger.getLogger(PanneauPrincipaleContactController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initializeFormulaire() {
        try {
            panneauPrincipalContact.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/contact/FicheContact.fxml"));
            Parent root = (Pane) loader.load();
            FicheContactController ficheContact = loader.<FicheContactController>getController();
            ficheContact.setPpcController(this); 
            //ficheContact.clearinfo();
            panneauPrincipalContact.getChildren().add(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    //Evenememnt lors du clique de nouveau Contact
    @FXML
    public void newclicked(ActionEvent event) {
        try {

            panneauPrincipalContact.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/contact/FicheContact.fxml"));
            Parent root = (Pane) loader.load();
            FicheContactController ficheContact = loader.<FicheContactController>getController();
            ficheContact.setPpcController(this);
            ficheContact.clearinfo();
            ficheContact.setDisableButtonContact(false);
            panneauPrincipalContact.getChildren().add(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void reloadListeViewContact() {
        listviewPane.getChildren().clear();

    }

    /*public void initializeListeViewContact() {

        try {

            List<Contact> liste = contactService.findContact(new Contact());
            ObservableList<Contact> data;
            data = FXCollections.observableArrayList();
            data.setAll(liste);
            listeviewContact.setItems(data);
            String[] nomCols = new String[2];
            nomCols[0] = "nom";
            nomCols[1] = "prenom";

            listeviewContact.setCellFactory(new ListViewUtil().buildCellFactory(nomCols, " "));
            listeviewContact.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Contact>() {

                @Override
                public void changed(ObservableValue<? extends Contact> observable, Contact oldValue, Contact newValue) {
                    try {

                        panneauPrincipalContact.getChildren().clear();
                        Contact cn = new Contact();
                        cn.setId(newValue.getId());
                        Contact contactselected = (Contact) contactService.findContactById(cn);
                        Titre ti = new Titre();
                        ti.setId(contactselected.getIdTitre());
                        BaseService bs = new BaseService();
                        Titre titreselected = (Titre) bs.findById(ti);

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/contact/FicheContact.fxml"));
                        Parent root = (Pane) loader.load();

                        FicheContactController ficheContact = loader.<FicheContactController>getController();
                        ficheContact.setContact(contactselected);
                        ficheContact.setTitret(titreselected);

                        ficheContact.initializeInfoContact();
                        //ficheContact.ppcController = pc;
                        fc = ficheContact;
                        panneauPrincipalContact.getChildren().add(root);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

        } catch (Exception ex) {
            Logger.getLogger(PanneauPrincipaleContactController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }*/

}
