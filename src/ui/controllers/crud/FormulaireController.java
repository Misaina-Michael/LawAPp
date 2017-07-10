/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.crud;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modeles.BaseModele;
import services.BaseService;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FormulaireController implements Initializable {
    private BaseModele modele;
    private Node[] champs;
    private Field[] fields;
    private String action;
    private ListeController listeCtrlPrec;
    private Stage stage;
    @FXML private GridPane paneChamps;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    public void init()
    {
        Util util=new Util();
        
        try
        {
            fields=util.getAllFields(modele.getClass());
            champs=new Node[fields.length];
            for(int i=0; i<fields.length; i++)
            {
                Method met=modele.getClass().getMethod("get"+util.premierMaj(fields[i].getName()), null);
                Object obj=met.invoke(modele, null);
                System.out.println("ceci est un test" + obj.getClass().getName());
                Node node=this.getNodeObject(obj);
                paneChamps.add(node, 1, i);
                paneChamps.add(new Label(fields[i].getName()), 0, i);
                champs[i]=node;
                if(i==0)
                {
                    node.setDisable(true);
                }
            }
          
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public Node getNodeObject(Object obj)
    {
        Node res=null;
        Class cl=obj.getClass();
        String name=cl.getName();
        switch(name)
        {
            case "java.lang.String":
                res=new TextField(obj.toString());
                break;
            case "java.lang.Float":
                res=new TextField(obj.toString());
                break;
            case "java.lang.Integer":
                res=new TextField(obj.toString());
                break;
            case "java.util.Date":
                res=new DatePicker();
                break;
        }
        return res;
    }
    public void validAction(ActionEvent event)
    {
        BaseService bs=null;
        try
        {
            bs=new BaseService();
            Util util=new Util();
            int indice=0;
            for(int i=indice; i<this.getFields().length; i++)
            {
                Class[] type=new Class[1];
                type[0]=this.getFields()[i].getType();
                Method met=modele.getClass().getMethod("set"+util.premierMaj(this.getFields()[i].getName()), type);
                met.invoke(modele, this.setValueObjectFromNode(((TextField)champs[i]).getText(), type[0]));
                
            } 
            switch(action)
            {
                case "create":
                    bs.save(modele);
                    break;
                case "update":
                    System.out.println("____________update");
                    bs.update(modele);
                    break;
            }
            this.getStage().close();
            BaseModele newModele=listeCtrlPrec.getModele().getClass().newInstance();
            newModele.initValues();
            listeCtrlPrec.init(newModele);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }
    public Object setValueObjectFromNode(Object obj, Class cl)
    {
        String name=cl.getName();
        Object res=null;        
        switch(name)
        {
            case "java.lang.Integer":
                res=new Integer(obj.toString());
                break;
            case "java.lang.Float":
                res=new Float(obj.toString());
                break;
            case "java.lang.String":
                res=obj.toString();
                break;
//            case "javafx.scene.control.Da":
//                res=((TextField)node).getText();
//                break;
//            case "javafx.scene.control.Label":
//                res=((TextField)node).getText();
//                break;
        }
        return res;
    }
    public BaseModele getModele() {
        return modele;
    }

    public void setModele(BaseModele modele) {
        this.modele = modele;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ListeController getListeCtrlPrec() {
        return listeCtrlPrec;
    }

    public void setListeCtrlPrec(ListeController listeCtrlPrec) {
        this.listeCtrlPrec = listeCtrlPrec;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    
    
}
