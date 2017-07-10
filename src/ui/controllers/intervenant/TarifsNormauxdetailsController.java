/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modeles.intervenants.TarifIntervLibelle;
import modeles.intervenants.TarifIntervenant;
import modeles.intervenants.TarifNormaux;
import services.BaseService;
import services.IntervenantService;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class TarifsNormauxdetailsController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    private Integer temp;
    private TarifNormaux tarifnormaux;
    private Stage stagedetail;
    private TarifsNormauxController tnController ;
    @FXML private TextField type;
    @FXML private TextField libelle;
    @FXML private TextField duree;
    @FXML private TextField base;
    @FXML private TextField taux;
    @FXML private TextField montant;
    @FXML private Button saveTaux;

    public TarifsNormauxController getTnController() {
        return tnController;
    }

    public void setTnController(TarifsNormauxController tnController) {
        this.tnController = tnController;
    }
    
    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Stage getStagedetail() {
        return stagedetail;
    }

    public void setStagedetail(Stage stagedetail) {
        this.stagedetail = stagedetail;
    }

    public TarifNormaux getTarifnormaux() {
        return tarifnormaux;
    }

    public void setTarifnormaux(TarifNormaux tarifnormaux) {
        this.tarifnormaux = tarifnormaux;
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void InitializeChamps(){
        type.setText(this.getTarifnormaux().getLibelleTypeTarif());
        libelle.setText(this.getTarifnormaux().getLibelle());
        duree.setText(this.getTarifnormaux().getDuree().toString());
        base.setText(this.getTarifnormaux().getMtevt().toString());
        taux.setText(this.getTarifnormaux().getTaux().toString());
        montant.setText(this.getTarifnormaux().getMt().toString());
       
        this.setTemp(this.getTarifnormaux().getIdtarifinterv());   
    }
    @FXML
    public void saveTaux(ActionEvent event)
    {
        try{
            TarifIntervenant tarif = new TarifIntervenant();
            tarif.setId(this.getTarifnormaux().getId());
            tarif.setMt(new Float(this.montant.getText()));
            tarif.setTaux(new Float(this.taux.getText()));
            tarif.setIdIntervenant(this.getTarifnormaux().getIdintervenant());
            tarif.setIdEvtTarif(this.getTarifnormaux().getIdevttarif());
            IntervenantService is = new IntervenantService ();
            is.updateTarifIntervenant(tarif);
            this.getTnController().intializeTableaulisteContact();
            this.getStagedetail().close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
      
    }
}
