/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import modeles.dossiers.Dossier;
import modeles.dossiers.DossierLibelle;
import services.BaseService;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FaitsController implements Initializable {

    @FXML
    private TextArea faits;
    

    private DossierLibelle dossierLib;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void initFaits(){
        faits.setText(dossierLib.getFaits());
    }
            

    public void valider(ActionEvent ae) {
        try {
            BaseService bs = new BaseService();
            Dossier dossier = new Dossier();
            dossier.setId(dossierLib.getId());
            dossier = (Dossier)bs.findById(dossier);
            dossier.setFaits(faits.getText());
            dossierLib.setFaits(faits.getText());
            bs.update(dossier);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }

 
    

}
