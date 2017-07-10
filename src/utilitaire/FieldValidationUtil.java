/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import javafx.scene.control.TextField;
import ui.listener.FocusChangeListener;
import ui.listener.MoneyChangeListener;
import ui.listener.TimeChangeListener;

/**
 *
 * @author RABENANTOANDRO
 */
public class FieldValidationUtil {
    //Ne fonctionne que avec java fx
    public Boolean checkValueFromTextField(TextField[] tx) throws Exception {
        try {
            int c = 0;
            for (int x = 0; x < tx.length; x++) {
                System.out.println("textcheck" + tx[x].getText());
                if (tx[x].getText().isEmpty() || tx[x] == null) {
                    //System.out.println("textfield null" + tx[x].getId());
                    tx[x].focusedProperty().addListener(new FocusChangeListener(tx[x]));
                    c++;
                }
            }

            if (c > 0) {

                return false;

            }
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    public Boolean checkTimeField(TextField[] tx) throws Exception {
        UiUtil ui = new UiUtil();
        try {
            int c = 0;
            for (int x = 0; x < tx.length; x++) {
                if (tx[x].getText().isEmpty() || tx[x] == null || !ui.isTimeStampValid(tx[x].getText())) {

                    tx[x].focusedProperty().addListener(new TimeChangeListener(tx[x]));

                    c++;
                }
            }
            if (c > 0) {
                return false;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }
     public Boolean checkNumberField(TextField[] tx) throws Exception {
        Util ui = new Util();
        try {
            int c = 0;
            for (int x = 0; x < tx.length; x++) {
                if (tx[x].getText().isEmpty() || tx[x] == null || !ui.isInteger(tx[x].getText())) {
                    
                    c++;
                }
            }
            if (c > 0) {
                return false;
            }
        } catch (Exception ex) {
            throw ex;
        }
        return true;
    }

    public void onLeaveNumberField(TextField[] tx) throws Exception {
        Util util = new Util();
        try {
            int c = 0;
            for (int x = 0; x < tx.length; x++) {
                tx[x].focusedProperty().addListener(new MoneyChangeListener(tx[x]));
                c++;
            }
           
        } catch (Exception ex) {
            throw ex;
        }
        
    }

    public void setFocusError(TextField[] tx) throws Exception {
        try {
            if (!checkValueFromTextField(tx)) {
                for (int x = 0; x < tx.length; x++) {
                    if (tx[x].getText().isEmpty() || tx[x] == null) {
                        tx[x].setStyle("-fx-border-color:red ");
                    }
                }
                throw new Exception("Veuillez renseigner les champs obligatoires");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void setFocusErrorNumber(TextField[] tx) throws Exception {
        try {
            if (!checkValueFromTextField(tx)) {
                for (int x = 0; x < tx.length; x++) {
                    if (tx[x].getText().isEmpty() || tx[x] == null) {
                        tx[x].setStyle("-fx-border-color:red ");
                    }
                }
                throw new Exception("Veuillez renseigner les champs obligatoires");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void setChampsChiffreZero(TextField[] tx) throws Exception {
        try {
            for (int x = 0; x < tx.length; x++) {

                tx[x].setText("0");

            }
        } catch (Exception ex) {
            throw ex;
        }
    }
    public void setChampsTimeZero(TextField[] tx) throws Exception {
        try {
            for (int x = 0; x < tx.length; x++) {

                tx[x].setText("00:00");

            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
