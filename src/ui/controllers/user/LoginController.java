/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.user;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modeles.intervenants.Intervenant;
import modeles.parametres.UserSession;
import services.BaseService;
import services.IntervenantService;
import statiques.StageStatique;
import ui.controllers.PrimaryStageController;
import ui.controllers.Stages;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PasswordField password;
    @FXML
    private TextField login;
    private static Stage stg;
    private Stage stageLogin;
    Pane p = new Pane ();
    
    public static Stage getStg() {
        return stg;
    }

    public static void setStg(Stage stg) {
        LoginController.stg = stg;
    }
    
    public Stage getStageLogin() {
        return stageLogin;
    }

    public void setStageLogin(Stage stageLogin) {
        this.stageLogin = stageLogin;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            //login.setText("MaitreSolo");
            //password.setText("123456789");
    }
    
    @FXML
    public void onValidationLogin(ActionEvent event) {
        try {
            IntervenantService intervenantService = new IntervenantService();
            Intervenant intervenant = new Intervenant();
            intervenant.setLogin(login.getText());
            intervenant.setMdp(password.getText());
            BaseService bs = new BaseService();
            List<Intervenant> listeInterv = intervenantService.findWhere(intervenant);

            int res = listeInterv.size();
            if (res == 1) {
                UserSession.setIntervenantUserSession(listeInterv.get(0));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/PrimaryStage.fxml"));
                Parent root = (Pane) loader.load();
                PrimaryStageController primaryController = loader.<PrimaryStageController>getController();           
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                PrimaryStageController.setStg(stage);
                primaryController.setCurrentStage(stage);
                StageStatique.setStage1(stage);
                this.getStageLogin().close();
                stage.show();
            } else {
                System.out.println("login  invalide");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
