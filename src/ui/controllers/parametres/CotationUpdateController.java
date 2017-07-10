/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.parametres;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.evenement.EvtTarif;
import modeles.evenement.EvtTarifLibelle;
import modeles.parametres.TypeTarifEvt;
import services.BaseService;
import statiques.ObjetStatique;
import ui.listener.FocusChangeListener;
import utilitaire.ComboboxUtilitaire;
import utilitaire.FieldValidationUtil;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class CotationUpdateController implements Initializable {

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
    private EvtTarifLibelle evtTarif;
    private ParametresController paramContr;
    BaseService bs = new BaseService();
    FieldValidationUtil utilValid  = new FieldValidationUtil();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TextField[] tx = new TextField[3];
        tx[0] = libelle;
        tx[1] = code;
        tx[2] = montant;
        TextField [] timeField = new TextField[1];
        timeField[0] = duree;
        try {
            utilValid.checkValueFromTextField(tx);
            utilValid.checkTimeField(timeField);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void intializeCombobox() {
        try {
            ObservableList<TypeTarifEvt> dataTitre;
            dataTitre = FXCollections.observableArrayList();
            dataTitre.setAll(ObjetStatique.getTypeTarifEvt());
            List<BaseModele> fct = bs.find(new TypeTarifEvt());
            TypeTarifEvt tte = new TypeTarifEvt();
            tte.setId(this.getEvtTarif().getIdTypeTarif());
            TypeTarifEvt f = (TypeTarifEvt) bs.findById(tte);
            String[] colons = new String[1];
            colons[0] = "libelle";
            typeTarifCbx.getSelectionModel().select(f);
            typeTarifCbx.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
            typeTarifCbx.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
            typeTarifCbx.setItems(dataTitre);
            libelle.setText(this.getEvtTarif().getLibelle());
            code.setText(this.getEvtTarif().getCode());
            duree.setText(new SimpleDateFormat("HH:mm").format(this.getEvtTarif().getDuree()));
            numero.setText(this.getEvtTarif().getNumero());
            montant.setText(this.getEvtTarif().getMontant().toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void validerAjoutAction(ActionEvent event) throws Exception {
        TextField[] tx = new TextField[3];
        tx[0] = libelle;
        tx[1] = code;
        tx[2] = montant;
        TextField [] timeField = new TextField[1];
        tx[0] = duree;

        try {
            TypeTarifEvt typeTarif = (TypeTarifEvt) typeTarifCbx.getSelectionModel().getSelectedItem();
            EvtTarif evtTarif = new EvtTarif();
            if (utilValid.checkValueFromTextField(tx) || utilValid.checkTimeField(timeField)) {
                evtTarif.setId(this.getEvtTarif().getId());
                evtTarif.setIdTypeTarif(typeTarif.getId());
                evtTarif.setLibelle(libelle.getText());
                evtTarif.setCode(code.getText());
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                java.sql.Time timeValue = new java.sql.Time(formatter.parse(duree.getText()).getTime());
                evtTarif.setDuree(timeValue);
                evtTarif.setNumero(numero.getText());
                evtTarif.setMontant(Float.parseFloat(montant.getText()));
                bs.update(evtTarif);
                this.getParamContr().initialiseTableViewCotation();
                this.getStg().close();
            } else {
               utilValid.setFocusError(tx);

            }
        } catch (java.text.ParseException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(ex.getMessage());
            alert.setHeaderText(ex.getMessage());
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            
        } 
        catch (NumberFormatException exep) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ");
            alert.setHeaderText("Erreur d'entrée");
            alert.setContentText("nombre non valide");          
            alert.showAndWait();
           
        }
        catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fill Text Fields");
            alert.setHeaderText("Certains champs sont obligatoires.");
            alert.setContentText("Veuillez remplir les champs manquants");
            alert.showAndWait();
        }

    }

   

    public Stage getStg() {
        return stg;
    }

    public void setStg(Stage stg) {
        this.stg = stg;
    }

    public EvtTarifLibelle getEvtTarif() {
        return evtTarif;
    }

    public void setEvtTarif(EvtTarifLibelle evtTarif) {
        this.evtTarif = evtTarif;
    }

    public ParametresController getParamContr() {
        return paramContr;
    }

    public void setParamContr(ParametresController paramContr) {
        this.paramContr = paramContr;
    }

}
