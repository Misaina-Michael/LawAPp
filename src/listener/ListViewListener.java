/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Misaina
 */
public class ListViewListener implements ChangeListener{
    
    private Object valueClicked;
    private Object oldValue;
    @Override
    public void changed(ObservableValue ov, Object oldValue, Object newValue) throws UnsupportedOperationException{
        setValueClicked(newValue);
    }

    public Object getValueClicked() {
        return valueClicked;
    }

    public void setValueClicked(Object valueClicked) {
        this.valueClicked = valueClicked;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }
    
}
