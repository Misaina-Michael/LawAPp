/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.dossiers.Dossier;
import modeles.dossiers.DossierLibelle;
import modeles.evenement.EvtDossierLibelle;
import modeles.intervenants.IntervDossierLibelle;
import modeles.parametres.TypeFacturationDossier;
import services.BaseService;
import statiques.StageStatique;


/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class PanneauDossierController implements Initializable {
    @FXML private Pane panPrincipal;
    @FXML private Label nomDossier;
    @FXML private Button docButton;
    @FXML private Button interlButton;
    @FXML private Button planningButton;
    @FXML private Button procedureButton;
    @FXML private Button evenementButton;
    @FXML private Button facturation;
    @FXML private Button fiche;
    @FXML private Button faitsButton;
    
    private Stage currentStage;
    private Button menuActive;
    private DossierLibelle dossierLib;
    private Dossier dossier;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try
        {
            setMenuActive(fiche);
            changeMenuActive(fiche);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }    
    
    public void initializeFiche()
    {
        try
        {
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void viewFiche(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            viewFicheFunction();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void viewFicheFunction() throws Exception
    {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/Fiche.fxml"));
            Parent root = (Pane)loader.load();
            FicheController ficheCtrl=loader.<FicheController>getController();
            
            ficheCtrl.setDossierLib(dossierLib);
            IntervDossierLibelle idl=new IntervDossierLibelle();
            idl.setIdDossier(dossierLib.getId());
            BaseService bs=new BaseService();
            ficheCtrl.setIntervs((List<IntervDossierLibelle>)(List<?>)bs.find(idl));
            for(IntervDossierLibelle iii:ficheCtrl.getIntervs())
            {
                System.out.println(iii.getId());
            }
            ficheCtrl.initialize();
           
            panPrincipal.getChildren().clear();
            panPrincipal.getChildren().add(root);
            
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    
    public void viewFacturation(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/FacturationDossier.fxml"));
            Parent root = (Pane)loader.load();
            FacturationDossierController factCtrl=loader.<FacturationDossierController>getController();
            TypeFacturationDossier tf=new TypeFacturationDossier();
            tf.setIdDossier(dossierLib.getId());
            BaseService bs=new BaseService();
            factCtrl.setTypeFacturation((TypeFacturationDossier)bs.find(tf).get(0));
            factCtrl.setDossier(dossierLib);
            factCtrl.initializeChamp();
           
            panPrincipal.getChildren().clear();
            panPrincipal.getChildren().add(root);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void viewProcedure(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/ListeProcedure.fxml"));
            Parent root = (Pane)loader.load();
            ListeProcedureController listCtrl=loader.<ListeProcedureController>getController();
      
            BaseService bs=new BaseService();
            
            listCtrl.setDossierLib(dossierLib);
            listCtrl.initPrim();
            panPrincipal.getChildren().clear();
            panPrincipal.getChildren().add(root);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }        
    public void viewEvenement(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/evenement/Liste.fxml"));
            Parent root = (Pane)loader.load();
            ui.controllers.evenement.ListeController listeCtrl=loader.<ui.controllers.evenement.ListeController>getController();
            listeCtrl.setDossierLib(dossierLib);
            EvtDossierLibelle el=new EvtDossierLibelle();
            el.setIdDossier(dossierLib.getId());
            
            listeCtrl.initializeSearch();
            listeCtrl.initPrim(listeCtrl.search());
           
            panPrincipal.getChildren().clear();
            panPrincipal.getChildren().add(root);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }        

    public void viewPlanning(ActionEvent ae)
    {
        try
        {
           changeMenuActive((Button)ae.getSource());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/ListePlanningDossier.fxml"));
            Parent root = (Pane)loader.load();
            ListePlanningDossierController listeCtrl=loader.<ListePlanningDossierController>getController();
            listeCtrl.setDossierLib(dossierLib);
                    
            listeCtrl.initPrim();
           
            panPrincipal.getChildren().clear();
            panPrincipal.getChildren().add(root);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }    
      
    public void viewInterlocuteurs(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            Stage st=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/Interlocuteurs.fxml"));
            Parent root = (Pane)loader.load();
            InterlocuteursController listeCtrl=loader.<InterlocuteursController>getController();
            listeCtrl.setDossier(dossierLib);
                    
            listeCtrl.initList();
            Scene sc=new Scene(root);
//            listeCtrl.setCurrentStage(getCurrentStage());
            st.setScene(sc);
            StageStatique.setStage3(st);
            st.initOwner(StageStatique.getStage2());
            st.initModality(Modality.WINDOW_MODAL); 
            st.showAndWait();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void viewDocuments(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/Documents.fxml"));
            Parent root = (Pane)loader.load();
            DocumentsController docCtrl=loader.<DocumentsController>getController();
            docCtrl.setDossierLib(dossierLib);
            docCtrl.initTiroirs();
            panPrincipal.getChildren().clear();
            panPrincipal.getChildren().add(root);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void faits(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/dossier/Faits.fxml"));
            Parent root = (Pane)loader.load();
            
            FaitsController faitsController=loader.<FaitsController>getController();
            faitsController.setDossierLib(dossierLib);
            faitsController.initFaits();
            
            panPrincipal.getChildren().clear();
            panPrincipal.getChildren().add(root);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void changeMenuActive(Button button)
    {
        menuActive.getStyleClass().remove("default-active");
        button.getStyleClass().add("default-active");
        setMenuActive(button);
    }
    
    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    public Pane getPanPrincipal() {
        return panPrincipal;
    }

    public void setPanPrincipal(Pane panPrincipal) {
        this.panPrincipal = panPrincipal;
    }

    public Label getNomDossier() {
        return nomDossier;
    }

    public void setNomDossier(Label nomDossier) {
        this.nomDossier = nomDossier;
    }

    public Button getMenuActive() {
        return menuActive;
    }

    public void setMenuActive(Button menuActive) {
        this.menuActive = menuActive;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
    
    
    
}
