/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.fenetres;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import services.ObjetStatiqueService;
import ui.controllers.user.LoginController;

/**
 *
 * @author Misaina
 */
public class PrimaryStage extends Application{

    
    @Override
    public void start(Stage stage) throws Exception {
        
        this.loadObjetStatique();
        //Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/PrimaryStage.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/ui/fxml/user/Login.fxml"));
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/user/Login.fxml"));
        Parent root = (Pane)loader.load();
        LoginController loginController = loader.<LoginController>getController();
        loginController.setStageLogin(stage);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        stage.show();
       
    }
    public void loadObjetStatique() throws Exception
    {
        ObjetStatiqueService o=new ObjetStatiqueService();
        o.loadAll();
    }
    public static void main (String [] args){
        launch();
    }
}
