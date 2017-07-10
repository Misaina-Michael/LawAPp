/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.dossier;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.clients.Client;
import modeles.dossiers.Dossier;
import modeles.dossiers.DossierLibelle;
import modeles.parametres.Juridiction;
import modeles.parametres.Nature;
import services.BaseService;
import statiques.ObjetStatique;
import statiques.StageStatique;
import ui.controllers.ListeGeneriqueController;
import utilitaire.ComboBoxUtil;
import utilitaire.FieldValidationUtil;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ModifInfoDossierController implements Initializable {
    @FXML private TextField nomclient;
    @FXML private TextField adversaire;
    @FXML private TextField lieu;
    @FXML private DatePicker dateOuverture;
    @FXML private ComboBox juridiction;
    @FXML private ComboBox nature;
    @FXML private TextField noProcedure;
    @FXML private TextField region;
    @FXML private TextField vnomDossier;
    
    private TextField[] fieldToCheck;
    private ListeGeneriqueController listeCtrl;
    private FicheController precCtrl;
    private Stage stage;
    
    
    
    private DossierLibelle dossierLib;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void initChamps(){
        ComboBoxUtil comboUtil=null;
        try{
            Client currentClient=new Client();
            currentClient.setId(dossierLib.getIdClient());
            listeCtrl=new ListeGeneriqueController();
            listeCtrl.setItemSelected(currentClient);
            comboUtil=new ComboBoxUtil();
            vnomDossier.setText(dossierLib.getVnomDossier());
            nomclient.setText(dossierLib.getNomClient());
            adversaire.setText(dossierLib.getNomAdversaire());
            lieu.setText(dossierLib.getLieu());
//            LocalDate localDateProc=dossierLib.getDateOuverture();
//            dateOuverture.setValue(LocalDate("2016-02-02"));
            noProcedure.setText(dossierLib.getNoProcedure());
            region.setText(dossierLib.getRegion());
            comboUtil.fillComboBox(nature, (List<BaseModele>)(List<?>)ObjetStatique.getNatures(), dossierLib.getIdNature(), new String[]{"libelle"});
            comboUtil.fillComboBox(juridiction, (List<BaseModele>)(List<?>)ObjetStatique.getJuridictions(), dossierLib.getIdJuridiction(), new String[]{"code"});
            isValidTf();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public Boolean isValidTf()throws Exception
    {
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldToCheck=new TextField[]{vnomDossier, nomclient, adversaire};
            
            return fieldValidationUtil.checkValueFromTextField(fieldToCheck);
        }
        catch(Exception ex){
            throw ex;
        }
    }
    public void valider(ActionEvent event)
    {
        BaseService bs=null;
        try
        {
            FieldValidationUtil fieldValidationUtil=new FieldValidationUtil();
            fieldValidationUtil.setFocusError(fieldToCheck);
            bs=new BaseService();
            Dossier dossier=new Dossier();
            dossier.setId(dossierLib.getId());
            dossier=(Dossier)bs.findById(dossier);
            dossier.setIdJuridiction(((Juridiction)juridiction.getSelectionModel().getSelectedItem()).getId());
            dossier.setIdNature(((Nature)nature.getSelectionModel().getSelectedItem()).getId());
            dossier.setNoProcedure(noProcedure.getText());
            dossier.setRegion(region.getText());
            dossier.setVnomDossier(vnomDossier.getText());
            dossier.setNomAdversaire(adversaire.getText());
            dossier.setIdClient(listeCtrl.getItemSelected().getId());
            bs.update(dossier);
            precCtrl.initialize();
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
    
    public void selectClient(ActionEvent event)
    {
        Stage stage=new Stage();
        try
        {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane)loader.load();
            listeCtrl=loader.<ListeGeneriqueController>getController();
            listeCtrl.setBase(new Client());
            String[] cols=new String[1];
            cols[0]="nom";
            listeCtrl.setCols(cols);
            listeCtrl.setChamp(nomclient);
            listeCtrl.initializeListe();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            listeCtrl.setStage(stage);
            StageStatique.setStage4(stage);
            stage.initOwner(StageStatique.getStage3());
            stage.initModality(Modality.WINDOW_MODAL); 
            stage.showAndWait();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public FicheController getPrecCtrl() {
        return precCtrl;
    }

    public void setPrecCtrl(FicheController precCtrl) {
        this.precCtrl = precCtrl;
    }
    
    
}
