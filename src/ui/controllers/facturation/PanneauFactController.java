/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.facturation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import modeles.dossiers.Dossier;
import modeles.facturation.Facture;
import modeles.facturation.FactureLibelle;

import services.BaseService;
import services.FactureService;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class PanneauFactController implements Initializable {
    @FXML private Pane panneau;
    @FXML private Button reglee;
    @FXML private ImageView imageReglee;
    @FXML private Label nomFacture;
    @FXML private Button ficheButton;
    private Button menuActive;
    private FactureLibelle factLibelle;
    private Dossier dossier;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try
        {
            setMenuActive(ficheButton);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }   
    public void refresh()
    {
        try
        {
            BaseService bs=new BaseService();
            FactureLibelle factl=new FactureLibelle();
            factl.setId(factLibelle.getId());
            factLibelle=(FactureLibelle)bs.findById(factl);
            nomFacture.setText(factLibelle.getId()+" : "+factLibelle.getVnomDossier()+" "+factLibelle.getNumeroDossier());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void evtFacture(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/facturation/EvtFacture.fxml"));
            Parent root = (AnchorPane)loader.load();
            EvtFactureController ficheCtrl=loader.<EvtFactureController>getController();
            ficheCtrl.setFact(factLibelle);
            ficheCtrl.initializeListe();
            panneau.getChildren().clear();
            panneau.getChildren().add(root);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void ficheFacture(ActionEvent ae)
    {
        try
        {
            changeMenuActive((Button)ae.getSource());
            fiche();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void reglee(ActionEvent ae)
    {
        BaseService bs=new BaseService();
        try
        {
            
            Facture f=new Facture();
            f.setId(factLibelle.getId());
            f=(Facture)bs.findById(f);
            f.setReglee(true);
            factLibelle.setReglee(true);
            bs.update(f);
            fiche();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void editer(ActionEvent ae)
    {
        FactureService factServ=null;
        try
        {
            factServ=new FactureService();
            factServ.editFact(factLibelle);
            factLibelle.setEditee(true);
            fiche();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void print(ActionEvent ae)
    {
        FactureService factServ=null;
        
        try
        {
            factServ=new FactureService();
            factServ.showPrintViewer(factLibelle);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void isReglee(Boolean b)
    {
        if(b)
        {
//            imageReglee.setImage(new Image("D:/Misaina/Trav/moteur_java/LawOffice/src/ui/img/icones/red/Checked.png"));           
            reglee.setDisable(true);
        }
    }
    public void fiche()
    {
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/facturation/Fiche.fxml"));
            Parent root = (AnchorPane)loader.load();
            FicheController ficheCtrl=loader.<FicheController>getController();
            ficheCtrl.setFact(factLibelle);
            isReglee(factLibelle.getReglee());
            ficheCtrl.setDossier(this.dossier);
            ficheCtrl.initializeChamps();
            panneau.getChildren().clear();
            panneau.getChildren().add(root);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public FactureLibelle getFactLibelle() {
        return factLibelle;
    }

    public void setFactLibelle(FactureLibelle factLibelle) {
        this.factLibelle = factLibelle;
    }

    public Button getMenuActive() {
        return menuActive;
    }

    public void setMenuActive(Button menuActive) {
        this.menuActive = menuActive;
    }
    public void changeMenuActive(Button button)
    {
        menuActive.getStyleClass().remove("default-active");
        button.getStyleClass().add("default-active");
        setMenuActive(button);
    }

    public Dossier getDossier() {
        return dossier;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }
    
}
