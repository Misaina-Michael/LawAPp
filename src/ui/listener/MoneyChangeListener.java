/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import utilitaire.Util;

/**
 *
 * @author RABENANTOANDRO
 */
public class MoneyChangeListener implements ChangeListener<Boolean> {
    private TextField tx ;
    Util util = new Util ( );
    public MoneyChangeListener(TextField tx) {
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
            
            if(/*this.getTx().getText().isEmpty() || */!util.isInteger(this.getTx().getText())){
                 this.getTx().setStyle("-fx-border-color:#ff8c00 ");
                 this.getTx().setText("0");
;                 
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
