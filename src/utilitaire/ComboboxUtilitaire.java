/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import modeles.BaseModele;
import modeles.parametres.Titre;

/**
 *
 * @author RABENANTOANDRO
 */
public class ComboboxUtilitaire {
        public Callback<ListView<BaseModele >, ListCell<BaseModele >> comboboxCallback(final String[] nomAttrCols, final String separateur){
            return new Callback<ListView<BaseModele >, ListCell<BaseModele >>(){
              
                public ListCell<BaseModele> call(ListView<BaseModele> param) {
                    ListCell cell = new ListCell<BaseModele >() {
                      
                        @Override
                        protected void updateItem(BaseModele t, boolean bln) {
                            super.updateItem(t, bln); 
                            if (bln) {
                                setText("");
                            } else {
                                try {
                                    setText(t.getValueAttrs(nomAttrCols, separateur));
                                } catch (NoSuchMethodException ex) {
                                    Logger.getLogger(ComboboxUtilitaire.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(ComboboxUtilitaire.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InvocationTargetException ex) {
                                    Logger.getLogger(ComboboxUtilitaire.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    };
                    return cell;
                }
            };
        }
        public ListCell<BaseModele> setButtonValue (final String[] nomAttrCols, final String separateur){
            return new ListCell<BaseModele>() {
                protected void updateItem(BaseModele item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        //setGraphic(null);
                    } else {
                        try {
                            setText(item.getValueAttrs(nomAttrCols, separateur));
                        } catch (NoSuchMethodException ex) {
                            Logger.getLogger(ComboboxUtilitaire.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(ComboboxUtilitaire.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(ComboboxUtilitaire.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                 
            };
        }
}
