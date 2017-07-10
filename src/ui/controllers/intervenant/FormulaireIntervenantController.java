/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modeles.evenement.EvtTarif;
import modeles.intervenants.Intervenant;
import modeles.intervenants.TarifIntervenant;
import modeles.parametres.Fonction;
import services.BaseService;
import services.EvenementService;
import services.IntervenantService;
import statiques.ObjetStatique;
import utilitaire.ComboboxUtilitaire;
import utilitaire.FieldValidationUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class FormulaireIntervenantController implements Initializable {

    EvenementService evenementService = new EvenementService();
    IntervenantService intervenantService = new IntervenantService();
    @FXML
    private TextField code;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private ComboBox fonction;
    @FXML
    private TextField compteTiers;
    @FXML
    private TextArea adresse;
    @FXML
    private TextField pays;
    @FXML
    private TextField codePostale;
    @FXML
    private TextField ville;
    @FXML
    private TextField bureau;
    @FXML
    private TextField domicile;
    @FXML
    private TextField fax;
    @FXML
    private TextField email;
    @FXML
    private TextField mobile;
    @FXML
    private TextField seuilBas;
    @FXML
    private TextField seuilIntermediaire;
    @FXML
    private TextField seuilHaut;
    @FXML
    private TextField seuilJournalier;
    @FXML
    private TextField seuilHoraire;
    @FXML
    private TextField bic;
    @FXML
    private TextField rib;
    @FXML
    private TextField iban;
    @FXML
    private TextArea commentaire;
    @FXML
    private CheckBox gestionnaire;
    @FXML
    private AnchorPane AnchorpaneformulaireIntervenant;
    private IntervenantAccueilController accueilContr;
    private Stage stage;
    private TextField[] tx;
    private TextField[] timeField;
    private TextField [] nombres;
    BaseService bs = new BaseService();
    FieldValidationUtil fieldUtil = new FieldValidationUtil();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            TextField [] nombres = new TextField[4];
            nombres [0] = seuilBas;
            nombres [1] = seuilIntermediaire;
            nombres [2] = seuilHaut;
            nombres [3] = seuilJournalier;
            this.setNombres(nombres);
            TextField[] tx = new TextField[7];
            tx[0] = nom;
            tx[1] = prenom;
            tx[2] = email;
            tx[3] = mobile;
            tx[4] = pays;
            tx[5] = codePostale;
            tx[6] = ville;
            this.setTx(tx);
            TextField[] timeField = new TextField[1];
            timeField[0] = seuilHoraire;
            this.setTimeField(timeField);
            fieldUtil.checkValueFromTextField(this.getTx());
            fieldUtil.checkTimeField(this.getTimeField());
            fieldUtil.setChampsChiffreZero(this.getNombres());
            fieldUtil.setChampsTimeZero(this.getTimeField());
            fieldUtil.onLeaveNumberField(this.getNombres());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void intializeComboboxContact() {
        ObservableList<Fonction> dataTitre;
        dataTitre = FXCollections.observableArrayList();
        dataTitre.setAll(ObjetStatique.getFonctions());
        fonction.getSelectionModel().selectFirst();
        String[] colons = new String[1];
        colons[0] = "libelle";
        fonction.getSelectionModel().select(ObjetStatique.getFonctions().get(0));
        fonction.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
        fonction.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
        fonction.setItems(dataTitre);
    }

    @FXML
    public void sauvegarderIntervenant(ActionEvent event) {
        Util util = new Util();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            System.out.println(fieldUtil.checkValueFromTextField(this.getTx()));
            if (fieldUtil.checkValueFromTextField(this.getTx()) ) {
                Fonction fonctionSelected = (Fonction) this.fonction.getSelectionModel().getSelectedItem();
                //System.out.println("Fonction selectionne" + fonctionSelected.getLibelle());
                Intervenant intervenant = new Intervenant();
                intervenant.setCode(code.getText());
                intervenant.setNom(nom.getText());
                intervenant.setPrenom(prenom.getText());
                intervenant.setIdFonction(fonctionSelected.getId());
                intervenant.setIntervenant(fonctionSelected.getIntervenant());
                intervenant.setClient(fonctionSelected.getClient());
                intervenant.setDossier(fonctionSelected.getDossier());
                intervenant.setContact(fonctionSelected.getContact());
                intervenant.setPlanning(fonctionSelected.getPlanning());
                intervenant.setFactures(fonctionSelected.getFactures());
                intervenant.setFeuilledeTemps(fonctionSelected.getFeuilledeTemps());
                intervenant.setUtilisateur(fonctionSelected.getUtilisateur());
                intervenant.setComptetiers(compteTiers.getText());
                intervenant.setAdresse(adresse.getText());
                intervenant.setPays(pays.getText());
                if (util.isInteger(codePostale.getText())) {
                    intervenant.setCodepostal(parseInt(codePostale.getText()));
                } else {
                    intervenant.setCodepostal(null);
                }
                intervenant.setVille(ville.getText());
                intervenant.setBureau(bureau.getText());
                intervenant.setDomicile(domicile.getText());
                intervenant.setFax(fax.getText());
                intervenant.setEmail(email.getText());
                intervenant.setMobile(mobile.getText());
                intervenant.setGestionnaire(gestionnaire.isSelected());
                intervenant.setSeuilbas(parseFloat(seuilBas.getText()));
                intervenant.setSeuilintermediaire(parseFloat(seuilIntermediaire.getText()));
                intervenant.setSeuilhaut(parseFloat(seuilHaut.getText()));
                intervenant.setSeuiljournalier(parseFloat(seuilJournalier.getText()));
                java.sql.Time time = new java.sql.Time(formatter.parse(seuilHoraire.getText()).getTime());
                intervenant.setSeuilhoraire(time);
                intervenant.setBic(bic.getText());
                intervenant.setRib(rib.getText());
                intervenant.setIban(iban.getText());
                intervenant.setIban(iban.getText());
                intervenant.setIdTypeUser(2);
                intervenant.setCommentaire(commentaire.getText());
                IntervenantService intServ = new IntervenantService();
                intServ.saveIntervenant(intervenant);
                int dernieriD = intervenant.getId();
                TarifIntervenant tarifIntervenant;
                EvtTarif evt = new EvtTarif();
                evt.setType("tn");
                List<EvtTarif> evtTarif = evenementService.findEvtTarif(evt);
                for (int compteur = 0; compteur < evtTarif.size(); compteur++) {
                    tarifIntervenant = new TarifIntervenant();
                    tarifIntervenant.setIdIntervenant(dernieriD);
                    tarifIntervenant.setIdEvtTarif(evtTarif.get(compteur).getId());
                    tarifIntervenant.setTaux((float) 100);
                    tarifIntervenant.setMt((float) 0);
                    
                    intervenantService.saveTarifIntervenant(tarifIntervenant);
                }
                this.getAccueilContr().initialiseListeViewIntervenant();
                this.getStage().close();
            }
            else {
                fieldUtil.setFocusError(this.getTx());
            }

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur d'entrée");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

        }

    }

    public TextField[] getTimeField() {
        return timeField;
    }

    public void setTimeField(TextField[] timeField) {
        this.timeField = timeField;
    }

    public TextField[] getTx() {
        return tx;
    }

    public void setTx(TextField[] tx) {
        this.tx = tx;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public IntervenantAccueilController getAccueilContr() {
        return accueilContr;
    }

    public void setAccueilContr(IntervenantAccueilController accueilContr) {
        this.accueilContr = accueilContr;
    }

    public TextField[] getNombres() {
        return nombres;
    }

    public void setNombres(TextField[] nombres) {
        this.nombres = nombres;
    }
    
}
