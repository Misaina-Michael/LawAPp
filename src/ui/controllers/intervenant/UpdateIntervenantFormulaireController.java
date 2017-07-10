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
import javafx.stage.Stage;
import modeles.intervenants.Intervenant;
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
public class UpdateIntervenantFormulaireController implements Initializable {

    /**
     * Initializes the controller class.
     */
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
    private Stage stage;
    private IntervenantAccueilController accueilContr;
    private Intervenant intervenant;
    BaseService bs = new BaseService();
    IntervenantService intServ = new IntervenantService();
    private TextField[] tx;
    private TextField[] timeField;
    private TextField[] nombres;
    FieldValidationUtil fieldUtil = new FieldValidationUtil();

    public IntervenantAccueilController getAccueilContr() {
        return accueilContr;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public void setAccueilContr(IntervenantAccueilController accueilContr) {
        this.accueilContr = accueilContr;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            TextField[] nombres = new TextField[4];
            nombres[0] = seuilBas;
            nombres[1] = seuilIntermediaire;
            nombres[2] = seuilHaut;
            nombres[3] = seuilJournalier;
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur d'entrée");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();

        }

    }

    public void intializeComboboxContact() {
        try {
            ObservableList<Fonction> dataTitre;
            dataTitre = FXCollections.observableArrayList();
            dataTitre.setAll(ObjetStatique.getFonctions());
            fonction.getSelectionModel().selectFirst();
            Fonction fct = new Fonction();
            fct.setId(intervenant.getIdFonction());
            Fonction f = (Fonction) bs.findById(fct);
            fonction.setValue(f.getLibelle());
            String[] colons = new String[1];
            colons[0] = "libelle";
            fonction.getSelectionModel().select(f);

            fonction.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
            fonction.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
            fonction.setItems(dataTitre);
            nom.setText(intervenant.getNom());
            prenom.setText(intervenant.getPrenom());
            adresse.setText(intervenant.getAdresse());
            pays.setText(intervenant.getPays());
            ville.setText(intervenant.getVille());
            gestionnaire.setSelected(intervenant.getGestionnaire());
            //codePostale.setText(intervenant.getCodepostal().toString());
            compteTiers.setText(intervenant.getComptetiers());
            bureau.setText(intervenant.getBureau());
            domicile.setText(intervenant.getDomicile());
            fax.setText(intervenant.getFax());
            email.setText(intervenant.getEmail());
            mobile.setText(intervenant.getMobile());
            commentaire.setText(intervenant.getCommentaire());
            if (intervenant.getCodepostal() == null) {
                codePostale.setText("0");
            } else {
                codePostale.setText(intervenant.getCodepostal().toString());
            }
            if (intervenant.getSeuilbas() == null) {
                seuilBas.setText("0");
            } else {
                seuilBas.setText(intervenant.getSeuilbas().toString());
            }
            if (intervenant.getSeuilintermediaire() == null) {
                seuilIntermediaire.setText("0");
            } else {
                seuilIntermediaire.setText(intervenant.getSeuilintermediaire().toString());
            }
            if (intervenant.getSeuilhaut() == null) {
                seuilHaut.setText("0");
            } else {
                seuilHaut.setText(intervenant.getSeuilhaut().toString());
            }
            if (intervenant.getSeuiljournalier() == null) {
                seuilJournalier.setText("0");
            } else {
                seuilJournalier.setText(intervenant.getSeuiljournalier().toString());
            }
            if (intervenant.getSeuilhoraire() == null) {
                seuilHoraire.setText("0");
            } else {
                seuilHoraire.setText(intervenant.getSeuilhoraire().toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void sauvegarderIntervenant(ActionEvent event) {
        Util util = new Util();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        System.out.println("test" + this.getTx());
        try {
            if (fieldUtil.checkValueFromTextField(this.getTx())) {
                Fonction fonctionSelected = (Fonction) this.fonction.getSelectionModel().getSelectedItem();
                //System.out.println("Fonction selectionne" + fonctionSelected.getLibelle());
                Intervenant intervenant = new Intervenant();
                intervenant.setId(this.getIntervenant().getId());
                intervenant.setCode(code.getText());
                intervenant.setNom(nom.getText());
                intervenant.setPrenom(prenom.getText());
                intervenant.setIdFonction(fonctionSelected.getId());
                intervenant.setIntervenant(fonctionSelected.getIntervenant());
                intervenant.setClient(fonctionSelected.getClient());
                intervenant.setDossier(fonctionSelected.getDossier());
                intervenant.setContact(fonctionSelected.getContact());
                intervenant.setComptetiers(compteTiers.getText());
                intervenant.setAdresse(adresse.getText());
                intervenant.setPays(pays.getText());
                intervenant.setCodepostal(parseInt(codePostale.getText()));
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
                intervenant.setIdTypeUser(2);
                intervenant.setCommentaire(commentaire.getText());
                intServ.updateIntervenant(intervenant);

                this.getAccueilContr().initialiseListeViewIntervenant();
                this.getStage().close();
            } else {
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

    public TextField[] getTx() {
        return tx;
    }

    public void setTx(TextField[] tx) {
        this.tx = tx;
    }

    public TextField[] getTimeField() {
        return timeField;
    }

    public void setTimeField(TextField[] timeField) {
        this.timeField = timeField;
    }

    public TextField[] getNombres() {
        return nombres;
    }

    public void setNombres(TextField[] nombres) {
        this.nombres = nombres;
    }

}
