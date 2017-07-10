/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.intervenant;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import modeles.affichage.ColonneDate;
import modeles.affichage.EspaceString;
import modeles.affichage.EvtDossierLibGroupBy;
import modeles.evenement.EvtDossierLibelle;
import modeles.intervenants.Intervenant;
import services.EvenementService;
import statiques.StageStatique;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class IntervenantPlanningController implements Initializable {

    @FXML
    private TableView planningTableView;
    @FXML
    private TableView nomDossier;
    @FXML
    private TableColumn nomCompletDossier;
    @FXML
    private Button dateSuivant;
    @FXML
    private Button datePrecedent;
    @FXML
    private Label dateIntervalle;
    /**
     * Initializes the controller class.
     */
    public static Date debutIntervalle = null;
    public static Date finIntervalle = null;
    public static Integer IdDossier;
    private Intervenant intervenant;
    Util util = new Util();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomDossier.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        EvtDossierLibGroupBy tb = (EvtDossierLibGroupBy) nomDossier.getSelectionModel().getSelectedItem();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/intervenant/DetailPlanning.fxml"));
                        Parent root = (Pane) loader.load();
                        DetailPlanningController detailPlanning = loader.<DetailPlanningController>getController();
                        detailPlanning.setIdDossier(tb.getIdDossier());
                        detailPlanning.LoadDetailPlanningTableView();
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setTitle("Taches du dossier " + tb.getNomDossier() + " entre le " + util.dateToString(debutIntervalle) + " et le " + util.dateToString(finIntervalle));
                        //detailstarif.setStagedetail(stage);
                        stage.setScene(scene);
                         StageStatique.setStage2(stage);
                        stage.initOwner(StageStatique.getStage1());
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.showAndWait();
                        //stage.show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    List<ColonneDate> getDays() throws Exception {
        List<ColonneDate> res = new ArrayList<ColonneDate>();
        Date fromDate = new Date();
        debutIntervalle = fromDate;
        Calendar c = Calendar.getInstance();
        c.setTime(debutIntervalle);
        c.add(Calendar.DATE, 7);
        Date toDate = c.getTime();
        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(toDate);
        lastDate.add(Calendar.DATE, 0);
        finIntervalle = lastDate.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(debutIntervalle);
        cal.add(Calendar.DATE, -1);
        ColonneDate cd;
        while (cal.before(lastDate)) {
            cd = new ColonneDate();
            cal.add(Calendar.DATE, 1);
            cd.setNomCol(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRENCH) + " " + cal.getTime().getDate());
            cd.setDate(cal.getTime());
            res.add(cd);
        }
        return res;
    }
    //L argument de cette date sera la fin de l intervalle en cours

    public List<ColonneDate> getDaysOnAddition(Date d) {
        List<ColonneDate> res = new ArrayList<ColonneDate>();
        //d = finIntervalle;   
        debutIntervalle = d;
        Calendar c = Calendar.getInstance();
        c.setTime(debutIntervalle);
        c.add(Calendar.DATE, 7);

        Date toDate = c.getTime();
        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(toDate);
        lastDate.add(Calendar.DATE, 0);
        finIntervalle = lastDate.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(debutIntervalle);
        cal.add(Calendar.DATE, -1);
        ColonneDate cd;
        while (cal.before(lastDate)) {
            cd = new ColonneDate();
            cal.add(Calendar.DATE, 1);
            cd.setNomCol(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRENCH) + " " + cal.getTime().getDate());
            cd.setDate(cal.getTime());
            res.add(cd);
        }
        return res;
    }

    //Date d equivaut au debutintervalle
    public List<ColonneDate> getDaysOnSoustraction(Date d) {
        List<ColonneDate> res = new ArrayList<ColonneDate>();
        finIntervalle = d;
        System.out.println("fin Intervalle attr = " + finIntervalle);
        Calendar calSous = Calendar.getInstance();
        calSous.setTime(finIntervalle);
        calSous.add(Calendar.DATE, -7);
        Date dateSoustraite = calSous.getTime();
        debutIntervalle = dateSoustraite;
        Calendar c = Calendar.getInstance();
        c.setTime(dateSoustraite);
        c.add(Calendar.DATE, 7);
        Date toDate = c.getTime();

        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(toDate);
        lastDate.add(Calendar.DATE, 0);
        finIntervalle = lastDate.getTime();
        System.out.println("fin Intervalle lastDate.getTime() = " + finIntervalle);
        Calendar cal = Calendar.getInstance();
        cal.setTime(debutIntervalle);
        cal.add(Calendar.DATE, -1);
        ColonneDate cd;
        while (cal.before(lastDate)) {
            cd = new ColonneDate();
            cal.add(Calendar.DATE, 1);
            cd.setNomCol(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRENCH) + " " + cal.getTime().getDate());
            cd.setDate(cal.getTime());
            res.add(cd);
        }
        return res;
    }

    @FXML
    public void dateSuivantAction(ActionEvent event) {

        try {
            this.reinitialiseTableView("suivant");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void datePrecedentAction(ActionEvent event) {
        try {
            this.reinitialiseTableView("precedent");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
   
    public void reinitialiseTableView(String ordre) throws Exception {
        List<ColonneDate> colonnedate = null;
        if (ordre.equalsIgnoreCase("suivant")) {
            colonnedate = getDaysOnAddition(finIntervalle);
        } else {
            colonnedate = getDaysOnSoustraction(debutIntervalle);
        }
       
        EvenementService evtService = new EvenementService();
        List<EvtDossierLibGroupBy> evtGroupby = evtService.FindEventBetween2DatesGroupBy(debutIntervalle, finIntervalle,this.getIntervenant().getId());
        List<EvtDossierLibelle> evtdlb = evtService.FindEventBetween2Dates(debutIntervalle, finIntervalle,this.getIntervenant().getId());
        this.dateIntervalle.setText(util.dateToString(debutIntervalle) + " et le " + util.dateToString(finIntervalle));

        List<String> dataEspace = new ArrayList<String>();
        for (int c = 0; c < evtGroupby.size(); c++) {
            dataEspace.add(new String("Aucun Planning"));
        }
        ObservableList<String> data = FXCollections.observableArrayList();
        for (int compteur1 = 0; compteur1 < dataEspace.size(); compteur1++) {
            data.add(dataEspace.get(compteur1));
        }
        planningTableView.getColumns().clear();
        planningTableView.setItems(data);
        ObservableList<EvtDossierLibGroupBy> datadossiergroupBy = FXCollections.observableArrayList();
        for (int compteur1 = 0; compteur1 < evtGroupby.size(); compteur1++) {
            datadossiergroupBy.add(evtGroupby.get(compteur1));
        }
        nomCompletDossier.setCellValueFactory(new PropertyValueFactory<>("nomDossier"));
        nomDossier.getColumns().clear();
        nomDossier.setItems(datadossiergroupBy);
        nomDossier.getColumns().addAll(nomCompletDossier);
        TableColumn tb;
        Calendar cal;
        String formatedDate = null;
        for (int compteur = 0; compteur < colonnedate.size(); compteur++) {
            cal = Calendar.getInstance();
            cal.setTime(colonnedate.get(compteur).getDate());
            formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
            tb = new TableColumn(colonnedate.get(compteur).getNomCol());
            planningTableView.getColumns().add(tb);
            tb.setId(formatedDate);
            tb.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String, String> p) {
                    return new ReadOnlyObjectWrapper(p.getValue());
                }
            });
            tb.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                @Override
                public TableCell<String, String> call(TableColumn<String, String> param) {
                    return new TableCell<String, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            try {
                                List<EspaceString> estring = new ArrayList<EspaceString>();
                                for (int c = 0; c < evtGroupby.size(); c++) {
                                    for (int c1 = 0; c1 < evtdlb.size(); c1++) {
                                        if (evtGroupby.get(c).getIdDossier().toString().equalsIgnoreCase(evtdlb.get(c1).getIdDossier().toString())) {
                                            estring.add(new EspaceString(evtdlb.get(c1).getDaty(), c));
                                        } else {
                                        }
                                    }
                                }

                                String formatteddate = null;
                                Calendar cal;
                                for (int c = 0; c < estring.size(); c++) {
                                    cal = Calendar.getInstance();
                                    cal.setTime(estring.get(c).getDate());
                                    formatteddate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
                                    if (this.getTableRow() != null && item != null && this.getTableRow().getIndex() == estring.get(c).getPosition() && this.getTableColumn().getId().equalsIgnoreCase(formatteddate)) {
                                        setStyle("-fx-background-color:green");

                                    } else {
                                        this.setText("");
                                    }

                                }
                            } catch (Exception ex) {
                                throw ex;
                            }

                        }
                    };
                }
            });

        }
    }

    public void intialiseTablieView() throws Exception {
        List<ColonneDate> colonnedate = getDays();
        EvenementService evtService = new EvenementService();
        List<EvtDossierLibGroupBy> evtGroupby = evtService.FindEventBetween2DatesGroupBy(debutIntervalle, finIntervalle,this.getIntervenant().getId());
        List<EvtDossierLibelle> evtdlb = evtService.FindEventBetween2Dates(debutIntervalle, finIntervalle,this.getIntervenant().getId());
        this.dateIntervalle.setText(util.dateToString(debutIntervalle) + " et le " + util.dateToString(finIntervalle));

        List<String> dataEspace = new ArrayList<String>();
        for (int c = 0; c < evtGroupby.size(); c++) {
            dataEspace.add(new String("Aucun Planning"));
        }
        ObservableList<String> data = FXCollections.observableArrayList();
        for (int compteur1 = 0; compteur1 < dataEspace.size(); compteur1++) {
            data.add(dataEspace.get(compteur1));
        }
        planningTableView.getColumns().clear();
        planningTableView.setItems(data);
        ObservableList<EvtDossierLibGroupBy> datadossiergroupBy = FXCollections.observableArrayList();
        for (int compteur1 = 0; compteur1 < evtGroupby.size(); compteur1++) {
            datadossiergroupBy.add(evtGroupby.get(compteur1));
        }
        nomCompletDossier.setCellValueFactory(new PropertyValueFactory<>("nomDossier"));
        nomDossier.getColumns().clear();
        nomDossier.setItems(datadossiergroupBy);
        nomDossier.getColumns().addAll(nomCompletDossier);
        TableColumn tb;
        Calendar cal;
        String formatedDate = null;
        for (int compteur = 0; compteur < colonnedate.size(); compteur++) {
            cal = Calendar.getInstance();
            cal.setTime(colonnedate.get(compteur).getDate());
            formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
            tb = new TableColumn(colonnedate.get(compteur).getNomCol());
            planningTableView.getColumns().add(tb);
            tb.setId(formatedDate);
            tb.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String, String> p) {
                    return new ReadOnlyObjectWrapper(p.getValue());
                }
            });
            tb.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                @Override
                public TableCell<String, String> call(TableColumn<String, String> param) {
                    return new TableCell<String, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            try {
                                List<EspaceString> estring = new ArrayList<EspaceString>();
                                for (int c = 0; c < evtGroupby.size(); c++) {
                                    for (int c1 = 0; c1 < evtdlb.size(); c1++) {
                                        if (evtGroupby.get(c).getIdDossier().toString().equalsIgnoreCase(evtdlb.get(c1).getIdDossier().toString())) {
                                            estring.add(new EspaceString(evtdlb.get(c1).getDaty(), c));
                                        } else {
                                        }
                                    }
                                }
                                String formatteddate = null;
                                Calendar cal;
                                for (int c = 0; c < estring.size(); c++) {
                                    cal = Calendar.getInstance();
                                    cal.setTime(estring.get(c).getDate());
                                    formatteddate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
                                    if (this.getTableRow() != null && item != null && this.getTableRow().getIndex() == estring.get(c).getPosition() && this.getTableColumn().getId().equalsIgnoreCase(formatteddate)) {
                                        setStyle("-fx-background-color:green");

                                    }

                                }
                            } catch (Exception ex) {

                            }

                        }
                    };
                }
            });

        }

    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

}
