/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.evenement;

import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import modeles.BaseModele;
import modeles.dossiers.DossierLibelle;
import modeles.evenement.ChronoEvtThread;
import modeles.evenement.EvenementDossier;
import modeles.evenement.EvtDossierLibelle;
import modeles.evenement.EvtTarif;
import modeles.evenement.TempsTravail;
import modeles.evenement.ThreadsChrono;
import modeles.facturation.FactureLibelle;
import modeles.intervenants.Intervenant;
import modeles.parametres.TypeTarifEvt;
import org.hibernate.exception.ConstraintViolationException;
import services.BaseService;
import services.EvenementService;
import statiques.ObjetStatique;
import statiques.StageStatique;
import ui.controllers.facturation.PreFacturationController;
import utilitaire.Chrono;
import utilitaire.ComboBoxUtil;
import utilitaire.ReportUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author Misaina
 */
public class ListeController implements Initializable {

    @FXML
    private TableColumn colDate;
    @FXML
    private TableColumn colInterv;
    @FXML
    private TableColumn colMt;
    @FXML
    private AnchorPane parent;

    @FXML
    private TableColumn colDuree;
    @FXML
    private TableColumn colDemand;
//    @FXML private TableColumn colNote;
    @FXML
    private TableColumn colEvt;
    @FXML
    private TableColumn colSelect;
    @FXML
    private TableView liste;

    @FXML
    private GridPane gridTarif;
    @FXML
    private DatePicker dateDe;
    @FXML
    private DatePicker dateA;
    @FXML
    private ComboBox tarifSearch;
    @FXML
    private ComboBox intervSearch;
    @FXML
    private ComboBox facturee;
    @FXML
    private ComboBox evtSearch;
    @FXML
    private Pane paneChrono;
    @FXML
    private Pane paneJour;
    @FXML
    private Label dureeDuJour;
    private List<EvtDossierLibelle> list = new ArrayList<EvtDossierLibelle>();
    private DossierLibelle dossierLib;
    private EvtDossierLibelle evtLib;
    private List<EvtDossierLibelle> listeAfacturer;
    private List<EvtDossierLibelle> listeAimprimer;
    private ChronoEvtThread currentChronoThread;
    private List<EvtDossierLibelle> listeActuelle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeSearch();
    }

    public void initializeSearch() {
        ComboBoxUtil cbu = new ComboBoxUtil();
        BaseService bs = null;
        try {
            bs = new BaseService();
            List<BaseModele> in = new ArrayList<BaseModele>();
            Intervenant bi = new Intervenant();
            bi.setId(0);
            bi.setNom("Tous");
            bi.setPrenom("");
            in.add(bi);
            in.addAll((List<BaseModele>) (List<?>) bs.find(new Intervenant()));

            List<BaseModele> ts = new ArrayList<BaseModele>();
            TypeTarifEvt tte = new TypeTarifEvt();
            tte.setId(0);
            tte.setLibelle("Tous");
            ts.add(tte);
            ts.addAll((List<BaseModele>) (List<?>) ObjetStatique.getTypeTarifEvt());

            List<BaseModele> es = new ArrayList<BaseModele>();
            EvtTarif evtt = new EvtTarif();
            evtt.setId(0);
            evtt.setLibelle("Tous");

            es.add(evtt);
            tte.setLibelle("Tous");
            es.addAll((List<BaseModele>) (List<?>) bs.find(new EvtTarif()));
            cbu.fillComboBox(intervSearch, in, 0, new String[]{"nom", "prenom"});
            cbu.fillComboBox(tarifSearch, ts, 0, new String[]{"libelle"});
            cbu.fillComboBox(evtSearch, es, 0, new String[]{"libelle"});
            cbu.fillComboBox(facturee, new String[]{"Tous", "Facturée", "Non facturée"}, "Non facturée");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initPrim(EvtDossierLibelle el) {

        try {
            Util util = new Util();
            EvenementService ds = new EvenementService();

            List<EvtDossierLibelle> liste = ds.findEvtDossierLib(el);
            listeAfacturer = new ArrayList<EvtDossierLibelle>();
            listeAimprimer = new ArrayList<EvtDossierLibelle>();
//            List<Integer> tabInt=new ArrayList<Integer>();
//            int compteur=0;
//            for (EvtDossierLibelle evtDl : liste) {
//                if (dateDe.getValue() != null) {
//                    System.out.println("datede : "+evtDl.getDaty().before(util.datePickerToDate(dateDe)));
//                    if(evtDl.getDaty().before(util.datePickerToDate(dateDe))) 
//                        tabInt.add(compteur);
//                        continue;
//                }
//                if (dateA.getValue() != null) {
//                    System.out.println("datea");
//                    if(evtDl.getDaty().after(util.datePickerToDate(dateA))) 
//                        tabInt.add(compteur);
//                     
//                }
//                compteur++;
//            }
//    
//            int indice=0;
            Iterator<EvtDossierLibelle> i = liste.iterator();
            while (i.hasNext()) {
                EvtDossierLibelle o = i.next();
                if (dateDe.getValue() != null) {
                    System.out.println("datede : "+o.getDaty().before(util.datePickerToDate(dateDe)));
                    if(o.getDaty().before(util.datePickerToDate(dateDe))) {
//                        tabInt.add(compteur);
                        i.remove();
                        continue;
                    }
                }
                if (dateA.getValue() != null) {
                    System.out.println("datea");
                    if(o.getDaty().after(util.datePickerToDate(dateA))) {
//                        tabInt.add(compteur);
                        i.remove();
                    }
                     
                }
//                indice++;
            }
            

            listeActuelle = new ArrayList<EvtDossierLibelle>(liste.size());
            listeActuelle.addAll(liste);
            initializeListe(liste);
            initTotalTarif();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initTotalTarif() {
        try {
            Util util = new Util();
            int col = 0;
            gridTarif.getChildren().clear();
            for (TypeTarifEvt te : ObjetStatique.getTypeTarifEvt()) {
                Float mt = new Float(0);
                Calendar cal = Calendar.getInstance();
                Time t = new Time(0);
                t.setSeconds(0);
                t.setMinutes(0);
                t.setHours(0);
                cal.setTime(t);
                for (EvtDossierLibelle ev : listeActuelle) {
                    if (ev.getIdTypeTarif().equals(te.getId())) {
                        mt += ev.getMt();
                        cal.add(Calendar.SECOND, ev.getDuree().getSeconds());
                        cal.add(Calendar.MINUTE, ev.getDuree().getMinutes());
                        cal.add(Calendar.HOUR, ev.getDuree().getHours());
                    }
                }
                String style = "-fx-font-weight:bold;-fx-font-size:12px;";
                Label lib = new Label(te.getLibelle().toUpperCase());
                lib.setStyle(style);
                gridTarif.add(lib, col, 0);
                gridTarif.add(new Label(new DecimalFormat("#,##0.00").format(mt) + " Ar"), col, 1);
                gridTarif.add(new Label(util.addPrefix(2, cal.getTime().getHours() + "", "0") + "h" + util.addPrefix(2, cal.getTime().getMinutes() + "", "0")), col, 2);
                col++;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void initializeListe(List<EvtDossierLibelle> dataListe) {
        try {
//            colSelect.setCellValueFactory(value);
            colDate.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, Date>("daty"));
            

            
            colEvt.setCellFactory(new Callback<TableColumn<String, String>, TableCell<String, String>>() {
                @Override
                public TableCell<String, String> call(TableColumn<String, String> param) {
                    return new TableCell<String, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            try {
                                for (int c = 0; c < listeActuelle.size(); c++) {
                                    setText(item);
                                    if (this.getTableRow().getIndex()==c &&  !listeActuelle.get(c).getAfacturer()) {
                                            setStyle("-fx-background-color:#58a646");
                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                    };
                }
            });
            colInterv.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("nomInterv"));
            colDemand.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("nomDemandeur"));
            colMt.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, Float>("mt"));
//            colNote.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("note"));
            colDuree.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, Time>("duree"));
            colEvt.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, String>("libelle"));
            /*colSelect.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EvtDossierLibelle, CheckBox>, ObservableValue<CheckBox>>() {
                @Override
                public ObservableValue<CheckBox> call(
                        TableColumn.CellDataFeatures<EvtDossierLibelle, CheckBox> arg0) {
                    EvtDossierLibelle ev = arg0.getValue();
                    CheckBox checkBox = new CheckBox();
                    if(!ev.getAfacturer()) checkBox.setDisable(true);
                    checkBox.selectedProperty().setValue(false);
                    checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                        public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean old_val, Boolean selected) {
                            if(!selected) listeAfacturer.remove(ev);
                            else listeAfacturer.add(ev);
                        }
                    });
                    return new SimpleObjectProperty<CheckBox>(checkBox);
                }
            });*/
            Callback<TableColumn<EvtDossierLibelle, Boolean>, TableCell<EvtDossierLibelle, Boolean>> booleanCellFactory = new Callback<TableColumn<EvtDossierLibelle, Boolean>, TableCell<EvtDossierLibelle, Boolean>>() {
                @Override
                public TableCell<EvtDossierLibelle, Boolean> call(TableColumn<EvtDossierLibelle, Boolean> p) {
//                    this.setStyle("-fx.background-color:red");
                    return new CheckBoxCell();
                }
            };
            colSelect.setCellValueFactory(new PropertyValueFactory<EvtDossierLibelle, Boolean>("imprimer"));
            colSelect.setCellFactory(booleanCellFactory);
            ObservableList<EvtDossierLibelle> data;
            data = FXCollections.observableArrayList();
            data.setAll(dataListe);

            liste.setItems(data);
            colDate.setCellFactory(column -> {
                return new TableCell<FactureLibelle, Date>() {
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                        }
                    }
                };
            });
            colMt.setCellFactory(column -> {
                return new TableCell<FactureLibelle, Float>() {
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER_RIGHT);
                        if (item != null) {
                            setText(new DecimalFormat("#,##0.00").format(item));
                        }
                    }
                };
            });
            colDuree.setCellFactory(column -> {
                return new TableCell<FactureLibelle, Time>() {
                    protected void updateItem(Time item, boolean empty) {
                        super.updateItem(item, empty);
                        setAlignment(Pos.CENTER);
                        if (item != null) {
                            setText(new SimpleDateFormat("HH:mm").format(item));
                        }
                    }
                };
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void itemSelected(MouseEvent me) {
        try {
            showChronoEvt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void showChronoEvt() throws Exception {

        BaseService bs = null;
        try {
            Button stop = new Button("ARRETER");
            Button start = new Button("DEMARRER");

            bs = new BaseService();
            TempsTravail tempsJour = new TempsTravail();
            tempsJour.setDateTravail(new Date());
            tempsJour.setIdEvtDossier(((EvtDossierLibelle) liste.getSelectionModel().getSelectedItem()).getId());
            List<TempsTravail> listeJour = (List<TempsTravail>) (List<?>) bs.find(tempsJour);
            Calendar cal = Calendar.getInstance();
            Time t = new Time(0);
            t.setSeconds(0);
            t.setMinutes(0);
            t.setHours(0);
            cal.setTime(t);
            for (TempsTravail tt : listeJour) {
                cal.add(Calendar.SECOND, tt.getDureeEnSeconde().intValue());
            }
            Util util = new Util();
            dureeDuJour.setText(util.addPrefix(2, cal.getTime().getHours() + "", "0") + ":" + util.addPrefix(2, cal.getTime().getMinutes() + "", "0") + ":" + util.addPrefix(2, cal.getTime().getSeconds() + "", "0"));
            paneJour.setVisible(true);
            EvtDossierLibelle evtSelected = (EvtDossierLibelle) liste.getSelectionModel().getSelectedItem();
            boolean exist = false;
            Chrono ch = new Chrono();
            ch.setStop(stop);
            ch.setStart(start);
            ch.init(Duration.seconds(0));
            for (ChronoEvtThread cet : ThreadsChrono.getListeEvt()) {
                if (evtSelected.getId().equals(cet.getEvt().getId())) {
                    exist = true;
                    ch = cet.getChrono();
                    break;
                }
            }
            paneChrono.getChildren().clear();
            paneChrono.getChildren().add(ch);

            start.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        ChronoEvtThread chronoTh = new ChronoEvtThread();
                        chronoTh.setEvt(evtSelected);
                        chronoTh.setChrono(new Chrono());

                        ThreadsChrono.getListeEvt().add(chronoTh);
                        start.setText("DEMARRE");
                        chronoTh.start();

                        Chrono ch = new Chrono();
                        ch.setStop(stop);
                        ch.setStart(start);
                        ch.init(Duration.seconds(0));
                        ch.getChronoComponent().play();
                        paneChrono.getChildren().clear();
                        paneChrono.getChildren().add(ch);
                        currentChronoThread = chronoTh;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            stop.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    try {
                        currentChronoThread.getChrono().getChronoComponent().stop();
                        BaseService baseServ = new BaseService();
                        for (ChronoEvtThread cet : ThreadsChrono.getListeEvt()) {
                            if (currentChronoThread.getEvt().getId().equals(cet.getEvt().getId())) {
                                cet.getChrono().getChronoComponent().stop();
                                start.setText("DEMARRER");
                                cet.getChrono().init(Duration.seconds(cet.getChrono().getChronoComponent().getTime()));
                                paneChrono.getChildren().clear();
                                paneChrono.getChildren().add(cet.getChrono());
                                ThreadsChrono.getListeEvt().remove(cet);

//                                    insert dans tempstravail
                                TempsTravail temps = new TempsTravail();
                                temps.setDateTravail(new Date());
                                temps.setDureeEnSeconde(cet.getChrono().getChronoComponent().getTime());
                                temps.setIdEvtDossier(cet.getEvt().getId());
                                baseServ.save(temps);
                                break;
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void modifierEvt(ActionEvent event) {

        Stage s = null;
        try {
            EvtDossierLibelle sele = (EvtDossierLibelle) liste.getSelectionModel().getSelectedItem();
            if (!sele.getAfacturer()) {
                throw new Exception("Cette tâche a été déjà facturée. Vous ne pouvez plus la modifier.");
            }
            s = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/evenement/Formulaire.fxml"));
            Parent root = (AnchorPane) loader.load();
            ui.controllers.evenement.FormulaireController formCtrl = loader.<ui.controllers.evenement.FormulaireController>getController();
            EvenementDossier evd = new EvenementDossier();
            evd.setId(sele.getId());
            BaseService bs = new BaseService();
            evd = (EvenementDossier) bs.findById(evd);
            formCtrl.setAction("update");
            formCtrl.setEvtDoss(evd);
            formCtrl.setPrecCtrl(this);
            formCtrl.initializeChamp();

            Scene scene = new Scene(root);
            s.setScene(scene);
            formCtrl.setStage(s);
            StageStatique.setStage3(s);
            s.initOwner(StageStatique.getStage2());
            s.initModality(Modality.WINDOW_MODAL); 
            s.showAndWait();

        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public void rechercher(ActionEvent ae) {
        try {
            initPrim(search());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void delete(ActionEvent event) {
        try {
            EvtDossierLibelle sele = (EvtDossierLibelle) liste.getSelectionModel().getSelectedItem();
            if (!sele.getAfacturer()) {
                throw new Exception("Cette tâche a été déjà facturée. Vous ne pouvez plus la supprimer.");
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Confirmez vous la suppression de la tâche " + sele.getLibelle() + " de l'intervenant " + sele.getNomInterv() + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                EvenementDossier evd = new EvenementDossier();
                evd.setId(sele.getId());
                BaseService bs = new BaseService();
                evd = (EvenementDossier) bs.findById(evd);
                bs.delete(evd);
                this.initPrim(this.search());
            } else {
                alert.close();
            }
        } catch (ConstraintViolationException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Impossible de supprimer cette tâche. Il existe des références dans la base de données. Veuillez d'avord supprimer ces références");
            alert.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(ex.getMessage());
            alert.show();
        }
    }

    public EvtDossierLibelle search() {
        EvtDossierLibelle el = new EvtDossierLibelle();
        el.setIdDossier(dossierLib.getId());
        if (((EvtTarif) evtSearch.getSelectionModel().getSelectedItem()).getId() != 0) {
            el.setIdEvtTarif(((EvtTarif) evtSearch.getSelectionModel().getSelectedItem()).getId());
        }
        if (((Intervenant) intervSearch.getSelectionModel().getSelectedItem()).getId() != 0) {
            el.setIdIntervenant(((Intervenant) intervSearch.getSelectionModel().getSelectedItem()).getId());
        }
        if (((TypeTarifEvt) tarifSearch.getSelectionModel().getSelectedItem()).getId() != 0) {
            el.setIdTypeTarif(((TypeTarifEvt) tarifSearch.getSelectionModel().getSelectedItem()).getId());
        }
        //facturée
        if (facturee.getSelectionModel().getSelectedIndex() == 1) {
            el.setAfacturer(false);
        }
        //non facturée
        if (facturee.getSelectionModel().getSelectedIndex() == 2) {
            el.setAfacturer(true);
        }
        return el;
    }

    public void genererFacture(ActionEvent ae) {
        Stage s = null;
        try {
            s = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/facturation/PreFacturation.fxml"));
            Parent root = (AnchorPane) loader.load();
            PreFacturationController prefactCtrl = loader.<PreFacturationController>getController();

            prefactCtrl.setDossierLib(dossierLib);
            prefactCtrl.setListeEvt(listeAfacturer);
            prefactCtrl.initializeDossier();
            prefactCtrl.initAll();
            prefactCtrl.setStage(s);
            prefactCtrl.setPrecCtrl(this);
            Scene scene = new Scene(root);
            s.setScene(scene);
            StageStatique.setStage3(s);
            s.initOwner(StageStatique.getStage2());
            s.initModality(Modality.WINDOW_MODAL); 
            s.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void create(ActionEvent ae) {
        Stage s = null;
        try {
            s = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fxml/evenement/Formulaire.fxml"));
            Parent root = (AnchorPane) loader.load();
            ui.controllers.evenement.FormulaireController formCtrl = loader.<ui.controllers.evenement.FormulaireController>getController();
            EvenementDossier evd = new EvenementDossier();

            evd.initValues();
            evd.setIdDossier(dossierLib.getId());
            formCtrl.setAction("save");
            formCtrl.setEvtDoss(evd);
            formCtrl.setPrecCtrl(this);
            formCtrl.initializeChamp();

            Scene scene = new Scene(root);
            s.setScene(scene);
            formCtrl.setStage(s);
            StageStatique.setStage3(s);
            s.initOwner(StageStatique.getStage2());
            s.initModality(Modality.WINDOW_MODAL); 
            s.showAndWait();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void print(ActionEvent ae) {
        EvenementService serv = null;
        ReportUtil reportUtil = new ReportUtil();
        try {
            serv = new EvenementService();
            
            Map map = serv.produceMapEvtDossier(listeAimprimer, dossierLib);
            String pathReport = ReportUtil.pathDocs + "/report/evt-dossier - Copie";
            reportUtil.showViewer(map, pathReport);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void printImage(ActionEvent ae) {
        try {
//            PrinterJob job = PrinterJob.createPrinterJob();
//            if (job != null) {
//              job.showPrintDialog(window); // Window must be your main Stage
//                job.printPage(parent);
//                job.endJob();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public DossierLibelle getDossierLib() {
        return dossierLib;
    }

    public void setDossierLib(DossierLibelle dossierLib) {
        this.dossierLib = dossierLib;
    }
    class CheckBoxCell extends TableCell<EvtDossierLibelle, Boolean> {

        private CheckBox checkbox = new CheckBox();

        public CheckBoxCell() {
            checkbox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                   
                    if (!checkbox.isSelected()) {
                        list.remove(liste.getItems().get(getTableRow().getIndex()));
                        EvtDossierLibelle user = getTableView().getItems().get(getTableRow().getIndex());
                        user.setImprimer(false);
                        listeAfacturer.remove(user);
                        listeAimprimer.remove(user);
                        
                    } else {
                        list.add((EvtDossierLibelle) liste.getItems().get(getTableRow().getIndex()));
                        EvtDossierLibelle user = getTableView().getItems().get(getTableRow().getIndex());
                        user.setImprimer(true);
                        listeAimprimer.add(user);
                        if(user.getAfacturer()){
                            listeAfacturer.add(user);
                        }
                    }
                }
            });
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty && item != null) {
                checkbox.setAlignment(Pos.CENTER);
                checkbox.setSelected(item);
                setAlignment(Pos.CENTER);
                setGraphic(checkbox);
                
            }
        }
    }

}
