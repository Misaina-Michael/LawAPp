/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.parametres;

import java.lang.reflect.Method;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.evenement.EvtTarif;
import modeles.intervenants.Intervenant;
import modeles.parametres.Fonction;
import modeles.parametres.TypeTarifEvt;
import services.BaseService;
import services.IntervenantService;
import statiques.ObjetStatique;
import ui.listener.FocusChangeListener;
import utilitaire.ComboboxUtilitaire;
import utilitaire.FieldValidationUtil;
import utilitaire.UiUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class CotationFormulaireController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox typeTarifCbx;
    @FXML
    private TextField libelle;
    @FXML
    private TextField code;
    @FXML
    private TextField duree;
    @FXML
    private TextField numero;
    @FXML
    private TextField montant;
    @FXML
    private GridPane gridPaneForm;
    private Stage stg;
    private ParametresController paramContr;
    BaseService bs = new BaseService();
    FieldValidationUtil fieldUtil = new FieldValidationUtil ();
    UiUtil  ut = new UiUtil ();
  
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        try {
            TextField[] tx = new TextField[3];
            tx[0] = libelle;
            tx[1] = code;
            tx[2] = montant;
            TextField[] timeField = new TextField[1];
            timeField[0] = duree;
            fieldUtil.checkValueFromTextField(tx);
            fieldUtil.checkTimeField(timeField);
        } catch (Exception ex) {
            Logger.getLogger(CotationFormulaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void intializeComboboxContact() throws Exception {
        try {
            ObservableList<TypeTarifEvt> dataTitre;
            dataTitre = FXCollections.observableArrayList();
            dataTitre.setAll(ObjetStatique.getTypeTarifEvt());
            List<BaseModele> fct = bs.find(new TypeTarifEvt());
            TypeTarifEvt tte = new TypeTarifEvt();
            tte.setId(fct.get(0).getId());
            TypeTarifEvt f = (TypeTarifEvt) bs.findById(tte);
            String[] colons = new String[1];
            colons[0] = "libelle";
            typeTarifCbx.getSelectionModel().select(f);
            typeTarifCbx.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
            typeTarifCbx.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
            typeTarifCbx.setItems(dataTitre);
            duree.setText("00:00");
            
        } catch (Exception ex) {
            throw ex;
        }

    }

    @FXML
    public void validerAjoutAction(ActionEvent event) throws Exception {
        TextField[] tx = new TextField[3];
        tx[0] = libelle;
        tx[1] = code;
        tx[2] = montant;
        TextField[] timeField = new TextField[1];
        timeField[0] = duree;

        try {
            TypeTarifEvt typeTarif = (TypeTarifEvt) typeTarifCbx.getSelectionModel().getSelectedItem();
            EvtTarif evtTarif = new EvtTarif();
            //System.out.println("champs " + fieldUtil.checkValueFromTextField(tx) + "time" + fieldUtil.checkTimeField(timeField)  );
            if (fieldUtil.checkValueFromTextField(tx) || !fieldUtil.checkTimeField(timeField)) {

                evtTarif.setIdTypeTarif(typeTarif.getId());
                evtTarif.setLibelle(libelle.getText());
                evtTarif.setCode(code.getText());
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                java.sql.Time timeValue = new java.sql.Time(formatter.parse(duree.getText()).getTime());
                evtTarif.setDuree(timeValue);
                evtTarif.setNumero(numero.getText());
                evtTarif.setMontant(Float.parseFloat(montant.getText()));
                evtTarif.setType("tn");
                bs.save(evtTarif);
                this.getParamContr().initialiseTableViewCotation();
                
                this.getStg().close();
                
            } else {           
                this.fieldUtil.setFocusError(tx);
            }
        } catch (java.text.ParseException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(ex.getMessage());
            alert.setHeaderText(ex.getMessage());
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            
        } 
        catch (NumberFormatException exep) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur ");
            alert.setHeaderText("Erreur d'entrée");
            alert.setContentText("Erreur d'entrée");          
            alert.showAndWait();
           
        }
        catch (Exception exep) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur d'entrée");
            alert.setContentText(exep.getMessage());
            alert.showAndWait();
           
        }

    }

   

    public ComboBox getTypeTarifCbx() {
        return typeTarifCbx;
    }

    public void setTypeTarifCbx(ComboBox typeTarifCbx) {
        this.typeTarifCbx = typeTarifCbx;
    }

    public TextField getLibelle() {
        return libelle;
    }

    public void setLibelle(TextField libelle) {
        this.libelle = libelle;
    }

    public TextField getCode() {
        return code;
    }

    public void setCode(TextField code) {
        this.code = code;
    }

    public TextField getDuree() {
        return duree;
    }

    public void setDuree(TextField duree) {
        this.duree = duree;
    }

    public TextField getNumero() {
        return numero;
    }

    public void setNumero(TextField numero) {
        this.numero = numero;
    }

    public TextField getMontant() {
        return montant;
    }

    public void setMontant(TextField montant) {
        this.montant = montant;
    }

    public Stage getStg() {
        return stg;
    }

    public void setStg(Stage stg) {
        this.stg = stg;
    }

    public ParametresController getParamContr() {
        return paramContr;
    }

    public void setParamContr(ParametresController paramContr) {
        this.paramContr = paramContr;
    }

}
