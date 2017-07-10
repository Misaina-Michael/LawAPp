/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.evenement;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.BaseModele;
import modeles.evenement.EvenementDossier;
import modeles.evenement.EvtDossierLibelle;
import modeles.intervenants.Intervenant;
import modeles.intervenants.TarifsNS;
import services.BaseService;

import services.EvenementService;
import services.IntervenantService;
import statiques.StageStatique;
import ui.controllers.ListeGeneriqueController;
import utilitaire.ComboBoxUtil;
import utilitaire.FieldValidationUtil;
import utilitaire.UiUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class FormulaireController implements Initializable {

    @FXML
    private TextField daty;

    @FXML
    private TextField libelle;
    @FXML
    private TextField montant;
    @FXML
    private TextField duree;
    @FXML
    private TextArea note;
    @FXML
    private ComboBox intervenant;
    @FXML
    private ComboBox demandeur;
    @FXML
    private CheckBox sansDuree;
    private Stage stage;
    private EvenementDossier evtDoss;
    private TarifsNS tiLibCrit;
    private TarifsNS tarInit;
    private ListeGeneriqueController listeCtrl;
    private ListeController precCtrl;
    private String action;
    private TextField[] fieldToCheck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initializeChamp() {

        libelle.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                try {
                    if (action.compareToIgnoreCase("update") == 0) {
                        BaseService bs = new BaseService();
                        TarifsNS tn = new TarifsNS();
                        tn.setIdintervenant(evtDoss.getIdIntervenant());
                        tn.setIdevttarif(evtDoss.getIdEvtTarif());
                        tn = (TarifsNS) ((List<TarifsNS>) (List<?>) bs.find(tn)).get(0);
                        listeCtrl = new ListeGeneriqueController();
                        listeCtrl.setItemSelected(tn);
                    } else {
                        duree.setText(((TarifsNS) listeCtrl.getItemSelected()).getDuree().toString());
                        montant.setText(((TarifsNS) listeCtrl.getItemSelected()).getMt().toString());
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        Util u = new Util();
        UiUtil ui = new UiUtil();

        ComboBoxUtil cbu = new ComboBoxUtil();
        IntervenantService iserv = null;

        List<Intervenant> listInter = new ArrayList<Intervenant>();
        try {
            ui.addTextLimiter(duree, 5);
            iserv = new IntervenantService();
            Intervenant interCritere = new Intervenant();
//            interCritere.setIdTypeUser(2);
            listInter = iserv.find(interCritere);

            List<Intervenant> listeDem = new ArrayList<Intervenant>(listInter.size());
            listeDem.addAll(listInter);
            Intervenant dem0 = new Intervenant();
            dem0.setId(0);
            dem0.setNom("");
            dem0.setPrenom("");
            listeDem.add(0, dem0);
            cbu.fillComboBox(demandeur, (List<BaseModele>) (List<?>) listeDem, 0, new String[]{"nom", "prenom"});

            //selection intervenant
            ObservableList<Intervenant> dataInter;
            dataInter = FXCollections.observableArrayList();
            dataInter.setAll(listInter);
            intervenant.setItems(dataInter);
            int ind = 0;
            int i = 0;
            for (Intervenant t : listInter) {
                if (Objects.equals(evtDoss.getIdIntervenant(), t.getId())) {
                    ind = i;
                    break;
                }
                i++;
            }
            intervenant.getSelectionModel().select(ind);
            String[] cols = new String[2];
            cols[0] = "nom";
            cols[1] = "prenom";
            intervenant.setCellFactory(cbu.buildCellFactory(cols, " "));
            daty.setText(u.dateToString(evtDoss.getDaty()));

            montant.setText(evtDoss.getMt().toString());
            System.out.println("__________ " + evtDoss.getDuree().toString());
            System.out.println("noty " + evtDoss.getNote().toString());
            duree.setText(evtDoss.getDuree().toString());
            note.setText(evtDoss.getNote());

            tiLibCrit = new TarifsNS();
            tiLibCrit.setIdevttarif(evtDoss.getIdEvtTarif());
            tiLibCrit.setIdintervenant(evtDoss.getIdIntervenant());
            List<BaseModele> bm = iserv.find(tiLibCrit);
            List<TarifsNS> listTil = new ArrayList<TarifsNS>(bm.size());
            for (BaseModele b : bm) {
                listTil.add((TarifsNS) b);
            }
            tarInit = new TarifsNS();
            tarInit.initValues();
            if (listTil.size() > 0) {
                tarInit = listTil.get(0);
            }
            libelle.setText(tarInit.getLibelle());
            System.out.println("__________ " + evtDoss.getDuree().toString());
//            duree.setText("iii");

            duree.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                    try {
                        EvenementService evtserv = new EvenementService();
                        Float mont = evtserv.calculMtInterv(u.toTime(newValue), ((Intervenant) intervenant.getSelectionModel().getSelectedItem()).getId(), ((TarifsNS) listeCtrl.getItemSelected()).getIdevttarif());
                        montant.setText(mont.toString());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            isValidTf();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Boolean isValidTf() throws Exception {
        try {
            FieldValidationUtil fieldValidationUtil = new FieldValidationUtil();
            fieldToCheck = new TextField[]{daty, libelle, duree};

            return fieldValidationUtil.checkValueFromTextField(fieldToCheck);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void intervChange(ActionEvent ae) {
        listeCtrl.setItemSelected(null);
        libelle.setText("");
        duree.setText("00:00");
        montant.setText("0");
    }

    public void selectEvt(ActionEvent ae) {
        Stage s;
        try {
            s = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/ListeGenerique.fxml"));
            Parent root = (Pane) loader.load();
            listeCtrl = loader.<ListeGeneriqueController>getController();
            TarifsNS tl = new TarifsNS();
            tl.setIdintervenant(((Intervenant) intervenant.getSelectionModel().getSelectedItem()).getId());

            listeCtrl.setBase(tl);
            String[] cols = new String[1];
            cols[0] = "val";
          

            listeCtrl.setCols(cols);
            listeCtrl.setChamp(libelle);

            listeCtrl.initializeListe();
            Scene scene = new Scene(root);
            s.setScene(scene);
            listeCtrl.setStage(s);
            StageStatique.setStage4(s);
            s.initOwner(StageStatique.getStage3());
            s.initModality(Modality.WINDOW_MODAL);
            s.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void valider(ActionEvent ae) {
        EvenementService es = null;
        EvenementDossier ed = new EvenementDossier();
        Util u = new Util();
        try {
            FieldValidationUtil fieldValidationUtil = new FieldValidationUtil();
            fieldValidationUtil.setFocusError(fieldToCheck);
            ed.setDaty(new Date(u.checkDate(daty.getText())));
            ed.setAfacturer(true);
            ed.setIdIntervenant(((Intervenant) intervenant.getSelectionModel().getSelectedItem()).getId());

            Integer idDemSel = ((Intervenant) demandeur.getSelectionModel().getSelectedItem()).getId();
            if (!idDemSel.equals(0)) {
                ed.setDemandeur(idDemSel);
            }
            ed.setIdDossier(evtDoss.getIdDossier());
            ed.setMt(new Float(montant.getText()));
            ed.setNote(note.getText());
            ed.setDuree(u.toTime(duree.getText()));
            ed.setIdEvtTarif(((TarifsNS) listeCtrl.getItemSelected()).getIdevttarif());
            ed.setImprimer(Boolean.FALSE);
            es = new EvenementService();
            if (action.compareToIgnoreCase("save") == 0) {
                es.save(ed);
            } else {
                ed.setId(evtDoss.getId());
                es.update(ed);
            }

            stage.close();
            EvtDossierLibelle el = new EvtDossierLibelle();
            el.setAfacturer(true);
            el.setIdDossier(precCtrl.getDossierLib().getId());
            precCtrl.initPrim(el);
            precCtrl.initializeSearch();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public void estSansDuree(ActionEvent ae) {
        try {
            if (sansDuree.isSelected()) {
                duree.setText("00:00");
                montant.setText("0.0");
                duree.setDisable(true);
                montant.setDisable(true);
            } else {
                duree.setDisable(false);
                montant.setDisable(false);
                duree.setText(((TarifsNS) listeCtrl.getItemSelected()).getDuree().toString());
                montant.setText(((TarifsNS) listeCtrl.getItemSelected()).getMt().toString());
                if (action.compareToIgnoreCase("update") == 0) {
                    BaseService bs = new BaseService();
                    TarifsNS tn = new TarifsNS();
                    tn.setIdintervenant(evtDoss.getIdIntervenant());
                    tn.setIdevttarif(evtDoss.getIdEvtTarif());
                    tn = (TarifsNS) ((List<TarifsNS>) (List<?>) bs.find(tn)).get(0);
                    listeCtrl = new ListeGeneriqueController();
                    listeCtrl.setItemSelected(tn);
                    duree.setText(evtDoss.getDuree().toString());
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void dureeReleased(ContextMenuEvent ke) {
        System.out.println(duree.getText());
    }

    public EvenementDossier getEvtDoss() {
        return evtDoss;
    }

    public void setEvtDoss(EvenementDossier evtDoss) {
        this.evtDoss = evtDoss;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ListeController getPrecCtrl() {
        return precCtrl;
    }

    public void setPrecCtrl(ListeController precCtrl) {
        this.precCtrl = precCtrl;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
