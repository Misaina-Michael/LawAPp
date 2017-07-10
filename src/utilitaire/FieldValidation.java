/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ui.listener.FocusChangeListener;

/**
 *
 * @author RABENANTOANDRO
 */
public class FieldValidation {

    public int getNumberTextField(GridPane gridPane) {
        int compteur = 0;
        for (int x = 0; x < gridPane.getChildren().size(); x++) {
            Node n = gridPane.getChildren().get(x);
            if (n.getClass().getName().compareTo("javafx.scene.control.TextField") == 0) {
                compteur++;
            }
        }
        return compteur;
    }

    public Boolean checkValueFromTextField(TextField[] tx) throws Exception {
        try {
            int c = 0;
            for (int x = 0; x < tx.length; x++) {
                if (tx[x].getText().isEmpty() || tx[x] == null) {
                    tx[x].focusedProperty().addListener(new FocusChangeListener(tx[x]));
                    c++;
                }
            }
            if (c >= 1) {
                return false;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    

}
