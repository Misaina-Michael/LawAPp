/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import static java.lang.Float.parseFloat;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.evenement.EvtTarif;
import modeles.intervenants.Intervenant;
import modeles.intervenants.TarifIntervenant;
import modeles.intervenants.TarifSpecial;
import modeles.parametres.TypeTarifEvt;
import services.BaseService;
import services.IntervenantService;
import statiques.ObjetStatique;
import utilitaire.ComboboxUtilitaire;
import utilitaire.FieldValidationUtil;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class AddTarifSpecialController implements Initializable {

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
    private Intervenant interv;
    private TextField[] nombre;
    private TextField[] texte;
    private TextField[] heure;
    private TarifSpecialController tarifSpecialController;
    private Stage stagePrec;
    IntervenantService intervServ = new IntervenantService();
    FieldValidationUtil fieldUtil = new FieldValidationUtil();
    BaseService bs = new BaseService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
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
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void intializeCombobox() throws Exception {
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
    public void saveTarifSpecial(ActionEvent event) throws Exception {
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
                evtTarif.setType("ts");
                bs.save(evtTarif);
                EvtTarif evtt = new EvtTarif();
                evtt.setId(evtTarif.getId());
                TarifIntervenant tarifIntervenant = new TarifIntervenant();
                tarifIntervenant.setIdIntervenant(this.getInterv().getId());
                tarifIntervenant.setIdEvtTarif(evtt.getId());
                tarifIntervenant.setTaux((float) 100);
                tarifIntervenant.setMt((float) 0);
                intervServ.saveTarifIntervenant(tarifIntervenant);
                this.getTarifSpecialController().initialiseTableau();
                this.getStagePrec().close();

            } else {
                this.fieldUtil.setFocusError(tx);
            }
        } catch (java.text.ParseException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle(ex.getMessage());
            alert.setHeaderText(ex.getMessage());
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

        } catch (NumberFormatException exep) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur ");
            alert.setHeaderText("Erreur d'entrée");
            alert.setContentText("Erreur d'entrée de nombre");
            alert.showAndWait();

        } catch (Exception exep) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur d'entrée");
            alert.setContentText(exep.getMessage());
            alert.showAndWait();

        }

    }

    public Intervenant getInterv() {
        return interv;
    }

    public void setInterv(Intervenant interv) {
        this.interv = interv;
    }

    public TextField[] getNombre() {
        return nombre;
    }

    public void setNombre(TextField[] nombre) {
        this.nombre = nombre;
    }

    public TextField[] getTexte() {
        return texte;
    }

    public void setTexte(TextField[] texte) {
        this.texte = texte;
    }

    public TextField[] getHeure() {
        return heure;
    }

    public void setHeure(TextField[] heure) {
        this.heure = heure;
    }

    public TarifSpecialController getTarifSpecialController() {
        return tarifSpecialController;
    }

    public void setTarifSpecialController(TarifSpecialController tarifSpecialController) {
        this.tarifSpecialController = tarifSpecialController;
    }

    public Stage getStagePrec() {
        return stagePrec;
    }

    public void setStagePrec(Stage stagePrec) {
        this.stagePrec = stagePrec;
    }

}
