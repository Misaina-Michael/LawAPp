/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.contact.Contact;
import modeles.dossiers.ContactDossier;
import modeles.dossiers.ContactDossierLibelle;
import modeles.dossiers.DossierLibelle;
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
public class InterlocuteursController implements Initializable {
    @FXML private ListView listeInterlocuteurs;
    @FXML Text infoInterlocuteur;
    
    private DossierLibelle dossier;
    private ListeGeneriqueController listeContactCtrl;
    private List<ContactDossierLibelle> liste;
    private Stage currentStage;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void initList()
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            ContactDossierLibelle contDoss=new ContactDossierLibelle();
            contDoss.setIdDossier(dossier.getId());
            contDoss.setTypeContact("INTR");
            liste=(List<ContactDossierLibelle>)(List<?>)bs.find(contDoss);
            
            ObservableList<ContactDossierLibelle> data;
            data =FXCollections.observableArrayList();
            data.setAll(liste);
            listeInterlocuteurs.setItems(data);
            listeInterlocuteurs.setCellFactory(new ListViewUtil().buildCellFactory(new String[]{"nom", "prenom"}," "));
            listeInterlocuteurs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ContactDossierLibelle>() {
                public void changed(ObservableValue<? extends ContactDossierLibelle> observable,ContactDossierLibelle oldValue, ContactDossierLibelle newValue) {
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
                        infoInterlocuteur.setText(nom
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
    
    public void addContact(ActionEvent ae)
    {
        Stage stage;
        try
        {
            stage=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeContactCtrl=loader.<ListeGeneriqueController>getController();
            listeContactCtrl.setBase(new Contact());
            listeContactCtrl.getValider().setVisible(false);
            listeContactCtrl.getCustomValid().setVisible(true);
            
            listeContactCtrl.getCustomValid().setOnAction(new EventHandler<ActionEvent>() {    
                public void handle(ActionEvent event) { 
                    BaseService bs=null;
                    try
                    {
                        bs=new BaseService();
                        listeContactCtrl.validAction(event);
                        ContactDossier cDoss=new ContactDossier();
                        cDoss.setIdDossier(dossier.getId());
                        cDoss.setTypeContact("INTR");
                        cDoss.setIdContact(listeContactCtrl.getItemSelected().getId());
                        int mtovy=0;
                        for(ContactDossierLibelle cdl:liste)
                        {
                            if(cDoss.getIdContact().equals(cdl.getIdContact()))
                            {
                                mtovy++;
                                break;
                            }
                        }
                        if(mtovy==0) bs.save(cDoss);
                        initList();
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
            StageStatique.setStage4(stage);
            stage.initOwner(StageStatique.getStage3());
            stage.initModality(Modality.WINDOW_MODAL); 
            stage.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void delContact(ActionEvent ae)
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            ContactDossierLibelle cSelected=(ContactDossierLibelle)listeInterlocuteurs.getSelectionModel().getSelectedItem();
            ContactDossier cRemove=new ContactDossier();
            cRemove.setId(cSelected.getId());
            bs.delete(cRemove);
            initList();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public DossierLibelle getDossier() {
        return dossier;
    }

    public void setDossier(DossierLibelle dossier) {
        this.dossier = dossier;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
    
    
}
