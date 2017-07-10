/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import modeles.BaseModele;
import modeles.intervenants.Intervenant;
import services.BaseService;

/**
 *
 * @author Misaina
 */
public class ComboBoxUtil {
    public Callback<ListView<BaseModele>, ListCell<BaseModele>> buildCellFactory(final String[] nomAttrCols, final String separateur)
    {
        
        return new Callback<ListView<BaseModele>, ListCell<BaseModele>>() {
            @Override
            public ListCell<BaseModele> call(ListView<BaseModele> param) {
     
                 final ListCell<BaseModele> cell = new ListCell<BaseModele>(){
                   @Override
                     public void updateItem(BaseModele item, boolean empty){
                       super.updateItem(item, empty);
                       if(!empty) {
                           try {
                               setText(item.getValueAttrs(nomAttrCols, separateur));
                           } catch (NoSuchMethodException ex) {
                               Logger.getLogger(ComboBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
                           } catch (IllegalAccessException ex) {
                               Logger.getLogger(ComboBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
                           } catch (InvocationTargetException ex) {
                               Logger.getLogger(ComboBoxUtil.class.getName()).log(Level.SEVERE, null, ex);
                           }
                            setGraphic(null);
                        } else {
                            setText(null);
                        }
                     }
                    
                    
                };
                 return cell;
           }
       };
    }
    
    public void fillComboBox(ComboBox combo, List<BaseModele> data, Integer bm, String[] col)
    {
        ObservableList<BaseModele> ob;
                ob =FXCollections.observableArrayList();
                ob.setAll(data);
            combo.setItems(ob);
            int ind=0;
            int i=0;
           
            for(Object t:data)
            {
                if(Objects.equals(bm, ((BaseModele)t).getId())) 
                {
                    ind=i;
                    break;
                }
                i++;
            }
            ComboBoxUtil cbu=new ComboBoxUtil();
            
            combo.setCellFactory(cbu.buildCellFactory(col, " "));
            combo.getSelectionModel().select(ind);
    }
    public void fillComboBox(ComboBox combo, String[] data, String bm)
    {
        ObservableList<String> ob;
                ob =FXCollections.observableArrayList();
                ob.setAll(data);
            combo.setItems(ob);
            int ind=0;
           
            for(int i=0; i<data.length; i++)
            {
                if(data[i].compareToIgnoreCase(bm)==0) 
                {
                    ind=i;
                    break;
                }
            }
            
            combo.getSelectionModel().select(ind);
    }
}
