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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.dossiers.DossierLibelle;
import modeles.evenement.EvtTarif;
import modeles.intervenants.Intervenant;
import modeles.parametres.Juridiction;
import modeles.planning.Planning;
import modeles.planning.PlanningLibelle;
import services.BaseService;
import services.PlanningService;
import statiques.ObjetStatique;
import statiques.StageStatique;
import ui.controllers.ListeGeneriqueController;
import utilitaire.ComboBoxUtil;
import utilitaire.FieldValidationUtil;
import utilitaire.UiUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misainajjjllll
 */
public class FormulairePlanningController implements Initializable {
    @FXML private TextField debut;
    @FXML private TextField fin;
    @FXML private TextField ville;
    @FXML private TextField rappel;
    @FXML private TextField libevt;
    @FXML private TextArea libelle;
    @FXML private DatePicker daty;
    @FXML private ComboBox uniteRappel;
    @FXML private ComboBox transport;
    @FXML private ComboBox juridiction;
    @FXML private ComboBox intervenant;
    @FXML private ComboBox gestionnaire;
    
    private TextField[] fieldToCheck;
    private PlanningLibelle plLib;
    private DossierLibelle dossierLib;
    private Stage stage;
    private ListePlanningDossierController predCtrl;
    private ListeGeneriqueController listeCtrl;
    /**
     * Initializes the controller class.
     */
    
    public void initializeChamps()
    {
        
        
        try
        {
            ComboBoxUtil cbu=new ComboBoxUtil();
            Util util=new Util();
            
            debut.setText("00:00");
            fin.setText("00:00");
            ville.setText(plLib.getVille());
    //        rappel.setText(plLib.getRappel());
            libevt.setText(plLib.getLibelleEvt());
            libelle.setText(plLib.getLibelle());
    //        daty.setValue();
            cbu.fillComboBox(transport, ObjetStatique.getTransport(), plLib.getTransport());
            cbu.fillComboBox(uniteRappel, ObjetStatique.getUniteRappel(), plLib.getTransport());
            cbu.fillComboBox(juridiction, (List<BaseModele>)(List<?>)ObjetStatique.getJuridictions(), plLib.getIdJur(), new String[]{"code"});
            BaseService bs=new BaseService();
            Intervenant gestCritere=new Intervenant();
            gestCritere.setGestionnaire(Boolean.TRUE);
            cbu.fillComboBox(gestionnaire, (List<BaseModele>)(List<?>)bs.find(gestCritere), plLib.getIdGestionnaire(), new String[]{"nom", "prenom"});
            
            cbu.fillComboBox(intervenant, (List<BaseModele>)(List<?>)bs.find(new Intervenant()), plLib.getIdIntervenantpl(), new String[]{"nom", "prenom"});
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
            fieldToCheck=new TextField[]{libevt,debut,fin};
            return fieldValidationUtil.checkValueFromTextField(fieldToCheck);
        }
        catch(Exception ex){
            throw ex;
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void valider(ActionEvent ae)
    {
        PlanningService ps=new PlanningService();
        Util u=new Util();
        
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldValidationUtil.setFocusError(fieldToCheck);
            Planning nouveau=new Planning();
            nouveau.setDatePlanning(u.datePickerToDate(daty));
            nouveau.setHeureDebut(u.toTime(debut.getText()));
            nouveau.setHeureFin(u.toTime(fin.getText()));
            nouveau.setRappel(new Integer(rappel.getText()));
            nouveau.setUniteRappel(uniteRappel.getSelectionModel().getSelectedItem().toString());
            nouveau.setIdIntervenant(((Intervenant)intervenant.getSelectionModel().getSelectedItem()).getId());
            
            if(((Intervenant)gestionnaire.getSelectionModel().getSelectedItem())!=null)
            {
                nouveau.setIdGestionnaire(((Intervenant)gestionnaire.getSelectionModel().getSelectedItem()).getId());
            }
            else nouveau.setIdGestionnaire(0);
            
            nouveau.setTransport(transport.getSelectionModel().getSelectedItem().toString());
            nouveau.setIdJur(((Juridiction)juridiction.getSelectionModel().getSelectedItem()).getId());
            nouveau.setVille(ville.getText());
            nouveau.setLibelle(libelle.getText());
            nouveau.setNotes(libelle.getText());
            nouveau.setIdEvt(((EvtTarif)listeCtrl.getItemSelected()).getId());
            nouveau.setPlanifiee(true);
            nouveau.setIdDossier(dossierLib.getId());
            nouveau.setIdTypeProcedure(ObjetStatique.getTypeProcedure().get(0).getId());
            ps.createPlanning(nouveau);
            
            stage.close();
            predCtrl.initPrim();
        }
        catch(NumberFormatException ex){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le champ est peut être vide ou le format n'est pas valide");
            alert.show();
            
        }
        catch(Exception ex)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }
    public void onInputHeureFin(KeyEvent ae)
    {
        System.out.println(ae.getCode());
        System.out.println(ae.getText());
        System.out.println(ae.getCharacter());
    }
    public void selectEvt(ActionEvent ae)
    {
        Stage s=null;
        try
        {
            s=new Stage();
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeCtrl=loader.<ListeGeneriqueController>getController();
            listeCtrl.setBase(new EvtTarif());
            String[] cols=new String[1];
            cols[0]="libelle";
            listeCtrl.setCols(cols);
            listeCtrl.setChamp(libevt);
         
            
            listeCtrl.initializeListe();
            Scene scene = new Scene(root);
            s.setScene(scene);
            listeCtrl.setStage(s);
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

    public PlanningLibelle getPlLib() {
        return plLib;
    }

    public void setPlLib(PlanningLibelle plLib) {
        this.plLib = plLib;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ListePlanningDossierController getPredCtrl() {
        return predCtrl;
    }

    public void setPredCtrl(ListePlanningDossierController predCtrl) {
        this.predCtrl = predCtrl;
    }

    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }
    
    
    
}