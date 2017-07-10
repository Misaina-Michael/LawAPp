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
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.dossiers.DossierLibelle;
import modeles.evenement.EvtTarif;
import modeles.intervenants.Intervenant;
import modeles.parametres.Juridiction;
import modeles.parametres.TypeProcedure;
import modeles.planning.Planning;
import services.BaseService;
import services.PlanningService;
import statiques.ObjetStatique;
import statiques.StageStatique;
import ui.controllers.ListeGeneriqueController;
import utilitaire.ComboBoxUtil;
import utilitaire.FieldValidationUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FormulaireProcedureController implements Initializable {
    @FXML private DatePicker daty;
    @FXML private DatePicker daty1;
    @FXML private TextArea notes;
    @FXML private TextField type;
    @FXML private TextField ville;
    @FXML private TextField debut;
    @FXML private TextField fin;
    @FXML private TextField libevt;
    @FXML private TextField rappel;
    @FXML private TextArea libelle;
    @FXML private CheckBox plannifiee;
    @FXML private ComboBox juridiction;
    @FXML private ComboBox uniteRappel;
    @FXML private ComboBox transport;
    @FXML private ComboBox intervenant;
    @FXML private ComboBox gestionnaire;
    @FXML private AnchorPane panPlanification;
    
    private TextField[] fieldToCheck;
    private TextField[] fieldPlanningToCheck;
    ListeGeneriqueController listeCtrl;
    ListeGeneriqueController listeEvtCtrl;
    private ListeProcedureController precCtrl;
    private DossierLibelle dossierLib;
    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panPlanification.setVisible(plannifiee.isSelected());
    }    
    public void initializeChamps()
    {
        try
        {
            ComboBoxUtil cbu=new ComboBoxUtil();
//            debut.setText(plLib.getHeureDebut().toString());
            fin.setText("00:00");
            debut.setText("00:00");
    //        rappel.setText(plLib.getRappel());
//            libevt.setText(plLib.getLibelleEvt());
//            libelle.setText(plLib.getLibelle());
    //        daty.setValue();
            cbu.fillComboBox(transport, ObjetStatique.getTransport(), "");
            cbu.fillComboBox(uniteRappel, ObjetStatique.getUniteRappel(), "");
            cbu.fillComboBox(juridiction, (List<BaseModele>)(List<?>)ObjetStatique.getJuridictions(), 0, new String[]{"code"});
            BaseService bs=new BaseService();
            Intervenant gestCritere=new Intervenant();
            gestCritere.setGestionnaire(Boolean.TRUE);
            cbu.fillComboBox(gestionnaire, (List<BaseModele>)(List<?>)bs.find(gestCritere), 0, new String[]{"nom", "prenom"});
            
            cbu.fillComboBox(intervenant, (List<BaseModele>)(List<?>)bs.find(new Intervenant()), 0, new String[]{"nom", "prenom"});
            isValidTf();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public Boolean isValidTf()throws Exception
    {
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldToCheck=new TextField[]{type};
            fieldPlanningToCheck=new TextField[]{libevt,debut,fin};
            fieldValidationUtil.checkValueFromTextField(fieldToCheck);
            return fieldValidationUtil.checkValueFromTextField(fieldPlanningToCheck);
        }
        catch(Exception ex){
            throw ex;
        }
    }
    public void selectType(ActionEvent ae)
    {
        Stage stage=new Stage();
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeCtrl=loader.<ListeGeneriqueController>getController();
            listeCtrl.setBase(new TypeProcedure());
            String[] cols=new String[1];
            cols[0]="libelle";
            listeCtrl.setCols(cols);
            listeCtrl.setChamp(type);
         
            
            listeCtrl.initializeListe();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            listeCtrl.setStage(stage);
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
    public void createProc(ActionEvent ae)
    {
        PlanningService ps=null;
        Util u=new Util();
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldValidationUtil.setFocusError(fieldToCheck);
            System.out.println("plannifiee "+plannifiee.isSelected());
            ps=new PlanningService();
            Planning p=new Planning();
            
            p.setDateProcedure(u.datePickerToDate(daty));
            p.setIdDossier(dossierLib.getId());
            p.setNotes(notes.getText());
            p.setIdTypeProcedure(listeCtrl.getItemSelected().getId());
            p.setPlanifiee(plannifiee.isSelected());
           
            if(plannifiee.isSelected())
            {
                fieldValidationUtil.setFocusError(fieldPlanningToCheck);
                p.setDatePlanning(u.datePickerToDate(daty));
                p.setHeureDebut(u.toTime(debut.getText()));
                p.setHeureFin(u.toTime(fin.getText()));
                p.setRappel(new Integer(rappel.getText()));
                p.setUniteRappel(uniteRappel.getSelectionModel().getSelectedItem().toString());
                p.setIdIntervenant(((Intervenant)intervenant.getSelectionModel().getSelectedItem()).getId());

                if(((Intervenant)gestionnaire.getSelectionModel().getSelectedItem())!=null)
                {
                    p.setIdGestionnaire(((Intervenant)gestionnaire.getSelectionModel().getSelectedItem()).getId());
                }
                else p.setIdGestionnaire(0);

                p.setTransport(transport.getSelectionModel().getSelectedItem().toString());
                p.setIdJur(((Juridiction)juridiction.getSelectionModel().getSelectedItem()).getId());
                p.setVille(ville.getText());
                p.setLibelle(libelle.getText());
                p.setNotes(libelle.getText());
                p.setIdEvt(((EvtTarif)listeEvtCtrl.getItemSelected()).getId());
                p.setPlanifiee(true);
                p.setIdDossier(dossierLib.getId());
            }
            
            ps.createPlanning(p);
            stage.close();
            precCtrl.initPrim();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public void pannifier(ActionEvent ae)
    {
        
        if(plannifiee.isSelected())this.getStage().setHeight(700);
        else this.getStage().setHeight(330);
        panPlanification.setVisible(plannifiee.isSelected());
        
    }
    public void selectEvt(ActionEvent ae)
    {
        Stage s=null;
        try
        {
            s=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeEvtCtrl=loader.<ListeGeneriqueController>getController();
            listeEvtCtrl.setBase(new EvtTarif());
            String[] cols=new String[1];
            cols[0]="libelle";
            listeEvtCtrl.setCols(cols);
            listeEvtCtrl.setChamp(libevt);
         
            
            listeEvtCtrl.initializeListe();
            Scene scene = new Scene(root);
            s.setScene(scene);
            listeEvtCtrl.setStage(s);
            StageStatique.setStage4(s);
            s.initOwner(StageStatique.getStage3());
            s.initModality(Modality.WINDOW_MODAL); 
            s.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }

    public ListeProcedureController getPrecCtrl() {
        return precCtrl;
    }

    public void setPrecCtrl(ListeProcedureController precCtrl) {
        this.precCtrl = precCtrl;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    
}
