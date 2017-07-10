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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.evenement.EvtTarif;
import modeles.intervenants.Intervenant;
import modeles.intervenants.TarifSpecial;
import modeles.intervenants.TarifSpeciaux;
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
public class UpdateTarifSpecialController implements Initializable {

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
   
    private TextField[] nombre;
    private TextField[] texte;
    private TextField[] heure;
    private Intervenant interv;
    private TarifSpeciaux tarifSpecial;
    private TarifSpecialController tarifSpecialController;
    private Stage stagePrec; 
    IntervenantService intervServ = new IntervenantService();
    BaseService bs = new BaseService ();
    FieldValidationUtil fieldUtil = new FieldValidationUtil();

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
            fieldUtil.checkValueFromTextField(tx);
            fieldUtil.checkTimeField(timeField);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public void initialiseField() throws Exception{
        try {
            ObservableList<TypeTarifEvt> dataTitre;
            dataTitre = FXCollections.observableArrayList();
            dataTitre.setAll(ObjetStatique.getTypeTarifEvt());
            List<BaseModele> fct = bs.find(new TypeTarifEvt());
            TypeTarifEvt tte = new TypeTarifEvt();
            tte.setId(this.getTarifSpecial().getIdTypeTarif());
            EvtTarif evtTarif = new EvtTarif();
            evtTarif.setId(this.getTarifSpecial().getIdevttarif());
            EvtTarif ev = (EvtTarif) bs.findById(evtTarif);
           
            System.out.println("dsadsadsa "  + this.getTarifSpecial().getIdevttarif());
            TypeTarifEvt f = (TypeTarifEvt) bs.findById(tte);
            String[] colons = new String[1];
            colons[0] = "libelle";
            typeTarifCbx.getSelectionModel().select(f);
            typeTarifCbx.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
            typeTarifCbx.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
            typeTarifCbx.setItems(dataTitre);
            libelle.setText(ev.getLibelle());
            code.setText(ev.getCode());
            duree.setText(new SimpleDateFormat("HH:mm").format(ev.getDuree()));
            numero.setText(ev.getNumero());
            montant.setText(ev.getMontant().toString());

        } catch (Exception ex) {
           throw ex;
        }
    }
    @FXML
    public void update(ActionEvent event) throws Exception {
          TextField[] tx = new TextField[3];
        tx[0] = libelle;
        tx[1] = code;
        tx[2] = montant;
        TextField [] timeField = new TextField[1];
        tx[0] = duree;

        try {
            TypeTarifEvt typeTarif = (TypeTarifEvt) typeTarifCbx.getSelectionModel().getSelectedItem();
            EvtTarif evtTarif = new EvtTarif();
            if (fieldUtil.checkValueFromTextField(tx) || fieldUtil.checkTimeField(timeField)) {
                evtTarif.setId(this.getTarifSpecial().getIdevttarif());
                evtTarif.setIdTypeTarif(typeTarif.getId());
                evtTarif.setLibelle(libelle.getText());
                evtTarif.setCode(code.getText());
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                java.sql.Time timeValue = new java.sql.Time(formatter.parse(duree.getText()).getTime());
                evtTarif.setDuree(timeValue);
                evtTarif.setNumero(numero.getText());
                evtTarif.setMontant(Float.parseFloat(montant.getText()));
                bs.update(evtTarif);
                this.getTarifSpecialController().initialiseTableau();
                this.getStagePrec().close();
            } else {
               fieldUtil.setFocusError(tx);

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

    public TarifSpeciaux getTarifSpecial() {
        return tarifSpecial;
    }

    public void setTarifSpecial(TarifSpeciaux tarifSpecial) {
        this.tarifSpecial = tarifSpecial;
    }
    
   
    
}
