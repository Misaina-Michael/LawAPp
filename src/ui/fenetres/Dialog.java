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
import javafx.stage.Stage;

/**
 *
 * @author Misaina
 */
public class Dialog extends Application{

    
    @Override
    public void start(Stage stage) throws Exception {
        

    }
    public void open(String ressource) throws Exception
    {
        Stage stage=new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(ressource));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    
}
