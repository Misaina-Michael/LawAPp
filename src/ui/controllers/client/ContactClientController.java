/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.client;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.clients.Client;
import modeles.contact.Contact;
import modeles.contact.ContactClient;
import modeles.contact.ContactClientLibelle;
import services.BaseService;
import statiques.StageStatique;
import ui.controllers.ListeGeneriqueController;
import utilitaire.ListViewUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ContactClientController implements Initializable {
    @FXML private ListView listViewContact;
    @FXML private Pane paneFicheContact;
    @FXML private Text textInfo;
    
    private ListeGeneriqueController listeContactCtrl;
    private Client client;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void initializeListe()
    {
        BaseService bs=null;
        ListViewUtil listUtil=new ListViewUtil();
        try
        {
            bs=new BaseService();
            ContactClientLibelle critContactClient=new ContactClientLibelle();
            critContactClient.setIdClient(client.getId());
            List<ContactClientLibelle> liste=(List<ContactClientLibelle>)(List<?>)bs.find(critContactClient);
            
            ObservableList<ContactClientLibelle> data;
            data =FXCollections.observableArrayList();
            data.setAll(liste);
            listViewContact.setItems(data);
            
//            ajouter les noms d'attributs à afficher
            String[] nomCols=new String[]{"nom", "prenom"};
            
            listViewContact.setCellFactory(new ListViewUtil().buildCellFactory(nomCols," "));
            listViewContact.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContactClientLibelle>() {
                public void changed(ObservableValue<? extends ContactClientLibelle> observable,ContactClientLibelle oldValue, ContactClientLibelle newValue) {
                    try
                    {
                        Util u=new Util();
                        String nom=u.escapeNullString(newValue.getTitreContact())+" "+u.escapeNullString(newValue.toString());
                        String adresse="\n"+u.escapeNullString(newValue.getAdresse());
                        String cpVille="\n"+u.escapeNullString(newValue.getCp().toString())+" "+u.escapeNullString(newValue.getVille());
                        String pays="\n"+u.escapeNullString(newValue.getPays());
                        String contacts="\n\n\t"
                                + "Bureau : "+u.escapeNullString(newValue.getBureau())
                                + "\n\tMobile : "+u.escapeNullString(newValue.getMobile())
                                + "\n\tStandard : "+u.escapeNullString(newValue.getStandard())
                                + "\n\tFax : "+u.escapeNullString(newValue.getFax())
                                + "\n\tWeb : "+u.escapeNullString(newValue.getWeb())
                                + "\n\tAutres : "+u.escapeNullString(newValue.getAutre());
                        
                        
                        textInfo.setText(nom
                                +adresse
                                +cpVille
                                +pays
                                +contacts
                        );                        
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void lierContact(ActionEvent ae)
    {
        Stage stage=new Stage();
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeContactCtrl=loader.<ListeGeneriqueController>getController();
            listeContactCtrl.setBase(new Contact());
            listeContactCtrl.getValider().setVisible(false);
            listeContactCtrl.getCustomValid().setVisible(true);
            
            listeContactCtrl.getCustomValid().setOnAction(new EventHandler<ActionEvent>() {    
                public void handle(ActionEvent event) { 
                    System.out.println("ok");
                    BaseService bs=null;
                    try
                    {
                        bs=new BaseService();
                        listeContactCtrl.validAction(event);
                        ContactClient cc=new ContactClient();
                        cc.setIdClient(client.getId());
                        cc.setIdContact(listeContactCtrl.getItemSelected().getId());
                        bs.save(cc);
                        initializeListe();
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });
            
            String[] cols=new String[]{"nom", "prenom"};
            
            listeContactCtrl.setCols(cols);
            listeContactCtrl.setChamp(new TextField());
            listeContactCtrl.initializeListe();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            listeContactCtrl.setStage(stage);
            StageStatique.setStage2(stage);
            stage.initOwner(StageStatique.getStage1());
            stage.initModality(Modality.WINDOW_MODAL); 
            stage.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
            
    }
    
    public void enleverContact(ActionEvent ae)
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            ContactClient cc=new ContactClient();
            cc.setId(((ContactClientLibelle)listViewContact.getSelectionModel().getSelectedItem()).getId());
            bs.delete(cc);
            initializeListe();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
            
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
}
