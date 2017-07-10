/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

/**
 *
 * @author RABENANTOANDRO
 */
public class FocusChangeListener implements ChangeListener<Boolean> {
    private TextField tx ;
    public FocusChangeListener(TextField txt ) {
        this.setTx(txt);
    }
    
    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue)
        {
            this.getTx().setStyle(null);
        }
        else
        {
            if(this.getTx().getText().isEmpty()){
                 this.getTx().setStyle("-fx-border-color:red ");
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
