/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modeles.dossiers.DossierLibelle;
import utilitaire.ConstanteDirectory;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class AddDirController implements Initializable {
    private DossierLibelle dossier;
    @FXML private TextField nomTiroir;
   
    Stage stage;
    DocumentsController documentsController;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void addDir(ActionEvent ae){
        try{
            System.out.println(dossier);
            System.out.println(nomTiroir);
            System.out.println(ConstanteDirectory.defaultDirectoryServer);
            new File(ConstanteDirectory.defaultDirectoryServer+dossier.getNumeroDossier()+"/"+nomTiroir.getText()).mkdir();
            documentsController.initTiroirs();
            stage.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public DossierLibelle getDossier() {
        return dossier;
    }

    public void setDossier(DossierLibelle dossier) {
        this.dossier = dossier;
    }
    
    
    
}
