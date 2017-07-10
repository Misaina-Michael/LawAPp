/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class ListViewUtil {
    
    public Callback<ListView<BaseModele>, ListCell<BaseModele>> buildCellFactory(final String[] nomAttrCols, final String separateur)
    {
        return new Callback<ListView<BaseModele>, ListCell<BaseModele>>() {

              public ListCell<BaseModele> call(ListView<BaseModele> param) {
                final Label leadLbl = new Label();
                final Tooltip tooltip = new Tooltip();
                final ListCell<BaseModele> cell = new ListCell<BaseModele>() {
                  @Override
                  public void updateItem(BaseModele item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                      leadLbl.setText(item.getId().toString());
                        try {
                            setText(item.getValueAttrs(nomAttrCols, separateur));
                            tooltip.setText(item.getValueAttrs(nomAttrCols, separateur));
                            setTooltip(tooltip);
                        } catch (NoSuchMethodException ex) {
                            Logger.getLogger(ListViewUtil.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(ListViewUtil.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(ListViewUtil.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                  }
                }; // ListCell
                return cell;
              }
            };
    }
}
