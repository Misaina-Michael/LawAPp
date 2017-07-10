/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modeles.intervenants.Intervenant;
import modeles.parametres.Fonction;
import services.BaseService;
import services.IntervenantService;
import statiques.ObjetStatique;
import statiques.StageStatique;
import utilitaire.ComboboxUtilitaire;
import utilitaire.ListViewUtil;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class IntervenantAccueilController implements Initializable {

    IntervenantService intervServ = new IntervenantService();
    private Intervenant intervenanttemp;
    @FXML
    public ListView listViewIntervenant;
    @FXML
    public Pane panneauFicheIntervenant;
    @FXML
    public Pane panetarifnormaux;
    @FXML
    public Pane dossierIntervenantpane;
    @FXML
    public Tab tarifNormauxPane;
    @FXML
    public TabPane tabpaneinfocontact;
    @FXML
    public Pane panePlanning;
    @FXML
    public TextField nominterv;
    @FXML
    public ComboBox fonction;
    @FXML
    public Button ficheButton;
    @FXML
    public Pane intervenantAccueilPane;
    @FXML
    public Button tNormauxButton;
    @FXML
    public Button dossierButton;
    @FXML
    Label intervenantName;
    private Button bouttonEnCours;
    private IntervenantAccueilController interv;

    public IntervenantAccueilController getInterv() {
        return interv;
    }

    public void setInterv(IntervenantAccueilController interv) {
        this.interv = interv;
    }

    public Button getBouttonEnCours() {
        return bouttonEnCours;
    }

    public void setBouttonEnCours(Button bouttonEnCours) {
        this.bouttonEnCours = bouttonEnCours;
    }

    public Intervenant getIntervenanttemp() {
        return intervenanttemp;
    }

    public void setIntervenanttemp(Intervenant intervenanttemp) {
        this.intervenanttemp = intervenanttemp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<Fonction> dataTitre;
        dataTitre = FXCollections.observableArrayList();
        dataTitre.setAll(ObjetStatique.getFonctions());
        dataTitre.add(0, new Fonction("Tous"));
        fonction.getSelectionModel().selectFirst();
        String[] colons = new String[1];
        colons[0] = "libelle";
        fonction.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
        fonction.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
        fonction.setItems(dataTitre);
        //fonction.setValue(dataTitre.get(0));
        this.setInterv(this);
        this.initialiseListeViewIntervenant();

    }

    public void ficheButton() {

    }

    public void initialiseListeViewIntervenant() {
        try {
            BaseService base = new BaseService();
            IntervenantAccueilController temp = this;
            List<Intervenant> liste = intervServ.find(new Intervenant());
            ObservableList<Intervenant> data;
            data = FXCollections.observableArrayList();
            data.setAll(liste);
            listViewIntervenant.setItems(data);
            String[] nomCols = new String[2];
            nomCols[0] = "nom";
            nomCols[1] = "prenom";
            this.setBouttonEnCours(ficheButton);
            listViewIntervenant.setCellFactory(new ListViewUtil().buildCellFactory(nomCols, " "));
            listViewIntervenant.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Intervenant>() {
                public void changed(ObservableValue<? extends Intervenant> observable, Intervenant oldValue, Intervenant nv) {
                    try {
                        Intervenant bm = new Intervenant();
                        bm.setId(nv.getId());

                        Intervenant intervenSelected = (Intervenant) base.findById(bm);
                        intervenantName.setText(intervenSelected.getNom() + " " + intervenSelected.getPrenom());
                        setIntervenanttemp(intervenSelected);
                        if (getBouttonEnCours().getId().toString().compareTo("ficheButton") == 0) {
                            fichePane();
                        } else if (getBouttonEnCours().getId().toString().compareTo("tNormauxButton") == 0) {
                            tarifNormauxPane();
                        } else if (getBouttonEnCours().getId().toString().compareTo("dossierButton") == 0) {
                            dossierPane();

                        } else if (getBouttonEnCours().getId().toString().compareTo("tarifspecial") == 0) {
                            tarifSpecialPane();
                        } else {
                            planningPane();
                        }

                    } catch (Exception ex) {
                        ex.getMessage();
                    }
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(IntervenantAccueilController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    public void validerNomAction(ActionEvent event) {
        try {
            Fonction fonctionSelected = (Fonction) this.fonction.getSelectionModel().getSelectedItem();
            Intervenant interv = new Intervenant();
            if (fonctionSelected.getLibelle().equalsIgnoreCase("Tous")) {
                interv.setNom(nominterv.getText());
            } else {
                interv.setNom(nominterv.getText());
                interv.setIdFonction(fonctionSelected.getId());
            }

            List<Intervenant> listInterv = intervServ.find(interv);
            ObservableList<Intervenant> data;
            data = FXCollections.observableArrayList();
            data.setAll(listInterv);
            listViewIntervenant.setItems(data);
            String[] nomCols = new String[2];
            nomCols[0] = "nom";
            nomCols[1] = "prenom";
            listViewIntervenant.setCellFactory(new ListViewUtil().buildCellFactory(nomCols, " "));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void onselectFonction(ActionEvent event) {
        try {
            Fonction fonctionSelected = (Fonction) this.fonction.getSelectionModel().getSelectedItem();
            Intervenant interv = new Intervenant();
            if (fonctionSelected.getLibelle().equalsIgnoreCase("Tous")) {
                interv.setNom(nominterv.getText());
            } else {
                interv.setNom(nominterv.getText());
                interv.setIdFonction(fonctionSelected.getId());
            }

            List<Intervenant> listInterv = intervServ.find(interv);
            ObservableList<Intervenant> data;
            data = FXCollections.observableArrayList();
            data.setAll(listInterv);
            listViewIntervenant.setItems(data);
            String[] nomCols = new String[2];
            nomCols[0] = "nom";
            nomCols[1] = "prenom";
            listViewIntervenant.setCellFactory(new ListViewUtil().buildCellFactory(nomCols, " "));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void tarifNormalAction(ActionEvent event) {
        try {
            this.setBouttonEnCours((Button) event.getSource());
            this.tarifNormauxPane();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void dossierButtonAction(ActionEvent event) {
        try {

            this.setBouttonEnCours((Button) event.getSource());
            this.dossierPane();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void planningButtonAction(ActionEvent event) {
        try {
            this.setBouttonEnCours((Button) event.getSource());
            this.planningPane();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void ficheButtonAction(ActionEvent event) {
        try {
            this.setBouttonEnCours((Button) event.getSource());
            this.fichePane();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void tarifSpecialButtonAction(ActionEvent event) {
        try {
            this.setBouttonEnCours((Button) event.getSource());
            this.tarifSpecialPane();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void newclicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/FormulaireIntervenant.fxml"));
            Parent root = (Pane) loader.load();
            FormulaireIntervenantController formulaireIntervenant = loader.<FormulaireIntervenantController>getController();
            formulaireIntervenant.intializeComboboxContact();
            formulaireIntervenant.setAccueilContr(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Nouvel Intervenant");
            stage.setScene(scene);
            formulaireIntervenant.setStage(stage);
            StageStatique.setStage2(stage);
            stage.initOwner(StageStatique.getStage1());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void tarifNormauxPane() throws Exception {
        try {
            intervenantAccueilPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/TarifsNormaux.fxml"));
            Parent root = (Pane) loader.load();
            TarifsNormauxController fichetarifs = loader.<TarifsNormauxController>getController();
            fichetarifs.setIntervenant(getIntervenanttemp());
            fichetarifs.intializeTableaulisteContact();
            intervenantAccueilPane.getChildren().add(root);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void fichePane() throws Exception {
        try {
            intervenantAccueilPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/FicheIntervenant.fxml"));
            Parent root = (Pane) loader.load();
            FicheIntervenantController ficheint = loader.<FicheIntervenantController>getController();
            ficheint.setIntervenant(getIntervenanttemp());
            ficheint.setIntervController(this.getInterv());
            ficheint.intialiseInfoIntervenant();

            intervenantAccueilPane.getChildren().add(root);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void planningPane() throws Exception {
        try {
            intervenantAccueilPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/IntervenantPlanning.fxml"));
            Parent root = (Pane) loader.load();
            IntervenantPlanningController intervenantPlanning = loader.<IntervenantPlanningController>getController();
            intervenantPlanning.setIntervenant(getIntervenanttemp());
            intervenantPlanning.intialiseTablieView();

            intervenantAccueilPane.getChildren().add(root);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void dossierPane() throws Exception {
        try {
            intervenantAccueilPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/DossierIntervenant.fxml"));
            Parent root = (Pane) loader.load();
            DossierIntervenantController dossiInterv = loader.<DossierIntervenantController>getController();
            dossiInterv.setIntervenant(getIntervenanttemp());
            dossiInterv.InitialiseTableauIntervenant();
            intervenantAccueilPane.getChildren().add(root);

        } catch (Exception ex) {
            throw ex;
        }
    }

    public void tarifSpecialPane() throws Exception {
        try {
            intervenantAccueilPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/TarifSpecial.fxml"));
            Parent root = (Pane) loader.load();
            TarifSpecialController tarifspec = loader.<TarifSpecialController>getController();
            tarifspec.setIntervenant(getIntervenanttemp());
            tarifspec.initialiseTableau();
            intervenantAccueilPane.getChildren().add(root);

        } catch (Exception ex) {
            throw ex;
        }
    }

}
