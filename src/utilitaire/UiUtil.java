/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaire;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Misaina
 */
public class UiUtil {
    
    public void changePanneauPrincipal(Pane scene, String resourceFxml) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource(resourceFxml));
        scene.getChildren().clear();
        scene.getChildren().add(root);
    }
    /**
    * Adds a static mask to the specified text field.
    * @param tf  the text field.
    * @param mask  the mask to apply.
    * Example of usage: addMask(txtDate, "  /  /    ");
    */
    public  void addTextLimiter( TextField tf,  int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed( ObservableValue<? extends String> ov,  String oldValue,  String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
   public void addMask( TextField tf,  String mask, String[] separateur) {
       tf.setText(mask);
       addTextLimiter(tf, mask.length());

//       tf.textProperty().addListener(new ChangeListener<String>() {
//           @Override
//           public void changed( ObservableValue<? extends String> ov,  String oldValue,  String newValue) {
//               String value = stripMask(tf.getText(), mask);
//               System.out.println("chan");
//               tf.setText(merge(value, mask));
//           }
//       });

       tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle( KeyEvent e) {
               System.out.println(tf.getCaretPosition());
               int caretPosition = tf.getCaretPosition();
//               tf.positionCaret(caretPosition + 1);
                int mtovy=0;
               for(int i=0; i<separateur.length; i++)
               {
                   if(tf.getText().charAt(caretPosition)==separateur[i].charAt(0))
                   {
                       mtovy++;
                       tf.positionCaret(caretPosition + 1);
                       break;
                   }
               }
               if(mtovy==0)
               {
                   String valueTf=tf.getText();
               }
               
//               if (caretPosition < mask.length()-1 && mask.charAt(caretPosition) != ' ' && e.getCode() != KeyCode.BACK_SPACE && e.getCode() != KeyCode.LEFT) {
//             
//                   tf.positionCaret(caretPosition + 1);
//               }
           }
       });
   }

   public String merge( String value,  String mask) {
        StringBuilder sb = new StringBuilder(mask);
       int k = 0;
       for (int i = 0; i < mask.length(); i++) {
           if (mask.charAt(i) == ' ' && k < value.length()) {
               sb.setCharAt(i, value.charAt(k));
               k++;
           }
       }
       return sb.toString();
   }

   public String stripMask(String text,  String mask) {
        Set<String> maskChars = new HashSet<>();
       for (int i = 0; i < mask.length(); i++) {
           char c = mask.charAt(i);
           if (c != ' ') {
               maskChars.add(String.valueOf(c));
           }
       }
       for (String c : maskChars) {
           text = text.replace(c, "");
       }
       return text;
   }
   public  Boolean isTimeStampValid(String inputString) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm");
        try {
            format.parse(inputString);
            return true;
            
        } catch (ParseException e) {
            return false;
        }
        
    }
    
}
