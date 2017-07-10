/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import utilitaire.UiUtil;

/**
 *
 * @author RABENANTOANDRO
 */
public class TimeChangeListener implements ChangeListener<Boolean> {

    private TextField tx;
     UiUtil fieldUtil = new UiUtil ();
    public TimeChangeListener(TextField tx) {
        this.setTx(tx);
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
         if (newValue)
        {
            this.getTx().setStyle(null);
        }
        else
        {
            if(this.getTx().getText().isEmpty() || !fieldUtil.isTimeStampValid(this.getTx().getText())){
                //System.out.println("ceci est un test");
                 this.getTx().setStyle("-fx-border-color:red ");
                 this.getTx().setText("00:00");
            }
            else {this.getTx().setStyle(null);}
           
        }
    }

    

    public TextField getTx() {
        return tx;
    }

    public void setTx(TextField tx) {
        this.tx = tx;
    }

}
