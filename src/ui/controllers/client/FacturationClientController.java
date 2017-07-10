/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.client;

import java.util.Objects;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modeles.clients.Client;
import modeles.parametres.ModeFacturation;
import modeles.parametres.TypeFacturationClient;
import modeles.parametres.TypeFacture;
import services.TypeFacturationClientService;
import statiques.ObjetStatique;
import utilitaire.ComboBoxUtil;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FacturationClientController implements Initializable {
    private Client client;
    private TypeFacturationClient typeFacturation;
    
    @FXML private ComboBox typeParDefaut;
    @FXML private ComboBox modeFacturation;
    @FXML private ComboBox periodicite;
    @FXML private TextField tva;
    @FXML private TextField echeance;
    @FXML private TextField compteComptable;
    @FXML private TextField compteTiers;
    @FXML private TextField categorieComptable;
    @FXML private TextField valueModeFact;
    @FXML private ComboBox langue;
    @FXML private Button validButton;
    @FXML private Label uniteValueMode;
    
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try
        {

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }    

    public void initializeChamp() throws Exception
    {
        tva.setText(typeFacturation.getTauxTva().toString());
        echeance.setText(typeFacturation.getEcheance().toString());
        compteComptable.setText(typeFacturation.getCompteComptable());
        compteTiers.setText(typeFacturation.getCompteTiers());
        categorieComptable.setText(typeFacturation.getCategorieComptable());
         
        ObservableList<TypeFacture> dataType;
            dataType =FXCollections.observableArrayList();
            dataType.setAll(ObjetStatique.getTypeFacture());
        typeParDefaut.setItems(dataType);
        
        int indice=0;
        for(int i=0; i<ObjetStatique.getTypeFacture().size(); i++)
        {
            if(Objects.equals(ObjetStatique.getTypeFacture().get(i).getId(), typeFacturation.idTypeFacture))
            {
                indice=i;
                break;
            }
        }
        typeParDefaut.getSelectionModel().select(indice);
        ComboBoxUtil cbu=new ComboBoxUtil();
        String[] colT=new String[1];
        colT[0]="libelle";
        typeParDefaut.setCellFactory(cbu.buildCellFactory(colT, ""));
        
        ObservableList<String> dataLangue;
            dataLangue =FXCollections.observableArrayList();
            dataLangue.setAll(ObjetStatique.getLangue());
        langue.setItems(dataLangue);
        int indice1=0;
        for(int i=0; i<ObjetStatique.getLangue().length; i++)
        {
            if(ObjetStatique.getLangue()[i].compareToIgnoreCase(typeFacturation.getLangue())==0)
            {
                indice1=i;
                break;
            }
        }
        langue.getSelectionModel().select(indice1);
        
        ObservableList<String> datap;
            datap =FXCollections.observableArrayList();
            datap.setAll(ObjetStatique.getPeriodicite());
        periodicite.setItems(datap);
        int indice2=0;
        for(int i=0; i<ObjetStatique.getPeriodicite().length; i++)
        {
            if(ObjetStatique.getPeriodicite()[i].compareToIgnoreCase(typeFacturation.getPeriodicite())==0)
            {
                indice2=i;
                break;
            }
        }
        periodicite.getSelectionModel().select(indice2);
        
        
        ObservableList<ModeFacturation> dataMode;
            dataMode =FXCollections.observableArrayList();
            dataMode.setAll(ObjetStatique.getModeFacturation());
        modeFacturation.setItems(dataMode);
        
        int indice3=0;
        for(int i=0; i<ObjetStatique.getModeFacturation().size(); i++)
        {
            if(Objects.equals(ObjetStatique.getModeFacturation().get(i).getId(), typeFacturation.getIdMode()))
            {
                indice3=i;
                break;
            }
        }
        modeFacturation.getSelectionModel().select(indice3);
        
        String[] colM=new String[1];
        colM[0]="libelle";
        modeFacturation.setCellFactory(cbu.buildCellFactory(colM, ""));
        
        modeFactChanged((ModeFacturation)modeFacturation.getSelectionModel().getSelectedItem());
        modeFacturation.valueProperty().addListener(new ChangeListener<ModeFacturation>() {
            @Override public void changed(ObservableValue ov, ModeFacturation old, ModeFacturation newv) {
                modeFactChanged(newv);
            }    
        });
    }
    public void modeFactChanged(ModeFacturation newv)
    {
        String unite="";
        String value="";
        switch(newv.getId())
        {
            case 1:
                unite="%";
                value=typeFacturation.getTauxMode().toString();
                valueModeFact.setDisable(false);
                break;
            case 2:
                unite="Ar";
                value=typeFacturation.getMtForfait().toString();
                valueModeFact.setDisable(false);
                break;
            case 3:
                unite="";
                value="";
                valueModeFact.setDisable(true);
                break;
        }
        uniteValueMode.setText(unite);
        valueModeFact.setText(value);
    }
    public void initializeData() throws Exception
    {
        try
        {
            modeFacturation.valueProperty().addListener(new ChangeListener<ModeFacturation>() {
                @Override public void changed(ObservableValue ov, ModeFacturation old, ModeFacturation newv) {
                    
                    System.out.println(newv.getLibelle());
                }    
            });
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TypeFacturationClient getTypeFacturation() {
        return typeFacturation;
    }

    public void setTypeFacturation(TypeFacturationClient typeFacturation) {
        this.typeFacturation = typeFacturation;
    }
    
    public void valider(ActionEvent ae) throws Exception
    {
        Integer idMode=((ModeFacturation)modeFacturation.getSelectionModel().getSelectedItem()).getId();
        try
        {
            switch(idMode)
            {
                case 1:
                    typeFacturation.setTauxMode(new Float(valueModeFact.getText()));
                    break;
                case 2:
                    typeFacturation.setMtForfait(new Float(valueModeFact.getText()));
                    break;
                
            }
            typeFacturation.setIdMode(idMode);
            typeFacturation.setIdTypeFacture(((TypeFacture)typeParDefaut.getSelectionModel().getSelectedItem()).getId());
            typeFacturation.setCategorieComptable(categorieComptable.getText());
            typeFacturation.setTauxTva(new Float(tva.getText()));
            typeFacturation.setPeriodicite(periodicite.getSelectionModel().getSelectedItem().toString());
            typeFacturation.setLangue(langue.getSelectionModel().getSelectedItem().toString());
            typeFacturation.setEcheance(new Integer(echeance.getText()));
            typeFacturation.setCompteTiers(compteTiers.getText());
            typeFacturation.setCompteComptable(compteComptable.getText());
            TypeFacturationClientService serv=new TypeFacturationClientService();
            serv.update(typeFacturation);
            stage.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
       
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    
}
