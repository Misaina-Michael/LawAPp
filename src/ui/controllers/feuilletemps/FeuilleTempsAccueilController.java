/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.feuilletemps;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.net.URL;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import jfxtras.scene.control.LocalDatePicker;
import modeles.evenement.EvtDossierLibelle;
import modeles.intervenants.Intervenant;
import modeles.intervenants.TarifNormaux;
import modeles.parametres.TypeTarifEvt;
import services.EvenementService;
import services.IntervenantService;
import statiques.ObjetStatique;
import utilitaire.ListViewUtil;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class FeuilleTempsAccueilController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private GridPane gridTarif ;
    @FXML
    private ListView ListeIntervenantListView;
    @FXML
    private TableView EvenementTableview;
    @FXML
    private TableColumn intervenant;
    @FXML
    private TableColumn demandeur;
    @FXML
    private TableColumn duree;
    @FXML
    private TableColumn evenement;
    @FXML
    private TableColumn notes;
    @FXML
    private TableColumn date;
    @FXML
    private TableColumn dossier;
    @FXML
    private LocalDatePicker datepickerTest;
    @FXML
    private Pane paneDatePicker;
    @FXML
    public Label honoraire;
    @FXML
    public DatePicker debut;
    @FXML
    public DatePicker fin;
    @FXML
    private DatePicker test;
    private List<EvtDossierLibelle> evtDoss;
    IntervenantService intervServ = new IntervenantService();
    EvenementService evtService = new EvenementService();
    Util util = new Util();
    static Integer idIntervenant = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            this.IntitializeListIntervenant();
            //this.initalizeCalendar();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void initalizeCalendar(List<EvtDossierLibelle> listeEvt) throws Exception {
        try {
            List<EvtDossierLibelle> evtdlb = listeEvt;
            DatePicker d = new DatePicker();
            d.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    try {
                        LocalDate da = d.getValue();
                        date.setCellValueFactory(new PropertyValueFactory<>("daty"));
                        demandeur.setCellValueFactory(new PropertyValueFactory<>("nomDemandeur"));
                        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
                        evenement.setCellValueFactory(new PropertyValueFactory<>("libelle"));
                        notes.setCellValueFactory(new PropertyValueFactory<>("note"));
                        List<EvtDossierLibelle> evtList = evtService.FindByDateIntervenant(da.toString(), idIntervenant);
                        setEvtDoss(evtList);
                        initTotalTarif() ;
                        for (int c = 0; c < evtList.size(); c++) {
                            evtList.get(c).setNomDossierComplet();
                        }
                        ObservableList<EvtDossierLibelle> data = FXCollections.observableArrayList();
                        for (int compteur1 = 0; compteur1 < evtList.size(); compteur1++) {
                            data.add(evtList.get(compteur1));
                        }
                        EvenementTableview.getColumns().clear();
                        EvenementTableview.setItems(data);
                        EvenementTableview.getColumns().addAll(dossier, date, demandeur, duree, evenement, notes);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });
            Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
                public DateCell call(final DatePicker datePicker) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            Calendar cal = Calendar.getInstance();
                            for (int dc = 0; dc < evtdlb.size(); dc++) {
                                cal.setTime(evtdlb.get(dc).getDaty());
                                //System.out.println("Date selected" + cal.get(Calendar.DATE));
                                if (item.isEqual(LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE)))) {
                                    this.setStyle("-fx-background-color:#82e0bf");

                                }
                            }
                        }
                    };
                }
            };
            d.setDayCellFactory(dayCellFactory);
            DatePickerSkin datePickerSkin = new DatePickerSkin(d);
            Node x = datePickerSkin.getPopupContent();
            this.paneDatePicker.getChildren().add(x);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void validerAction(ActionEvent event) {
        try {
            EvtDossierLibelle evt = new EvtDossierLibelle();
            evt.setIdIntervenant(FeuilleTempsAccueilController.idIntervenant);
            Date deb = util.datePickerToDate(debut);
            Date fin = util.datePickerToDate(this.fin);
            List<EvtDossierLibelle> evtList = evtService.FindEventBetween2DatesByIntervenant(deb, fin, idIntervenant);
            setEvtDoss(evtList);
            initTotalTarif() ;
            for (int c = 0; c < evtList.size(); c++) {
                evtList.get(c).setNomDossierComplet();
            }
            ObservableList<EvtDossierLibelle> data = FXCollections.observableArrayList();
            for (int compteur1 = 0; compteur1 < evtList.size(); compteur1++) {
                data.add(evtList.get(compteur1));
            }
            EvenementTableview.getColumns().clear();
            EvenementTableview.setItems(data);
            EvenementTableview.getColumns().addAll(dossier, date, demandeur, duree, evenement, notes);
            DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.FRANCE);
            dfs.setGroupingSeparator(' ');
            honoraire.setText(new DecimalFormat("#,##0.00", dfs).format(evtService.GetHonoraireByIntervenantbYdATE(idIntervenant, deb, fin)) + " Ariary");

            paneDatePicker.getChildren().clear();
            initalizeCalendar(evtList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
     public void initTotalTarif() 
    {
        try
        {
            Util util=new Util();
            int col=0;
            gridTarif.getChildren().clear();
            for(TypeTarifEvt te:ObjetStatique.getTypeTarifEvt())
            {
                Float mt=new Float(0);
                Calendar cal = Calendar.getInstance();
                Time t=new Time(0);
                t.setSeconds(0);
                t.setMinutes(0);
                t.setHours(0);
                cal.setTime(t);
                for(EvtDossierLibelle ev:this.getEvtDoss())
                {
                    if(ev.getIdTypeTarif().equals(te.getId()))
                    {
                        mt+=ev.getMt();                        
                        cal.add(Calendar.SECOND, ev.getDuree().getSeconds());       
                        cal.add(Calendar.MINUTE, ev.getDuree().getMinutes());       
                        cal.add(Calendar.HOUR, ev.getDuree().getHours());       
                    }
                }
                String style="-fx-font-weight:bold;-fx-font-size:12px;";
                Label lib=new Label(te.getLibelle().toUpperCase());
                lib.setStyle(style);
                gridTarif.add(lib, col, 0);
                gridTarif.add(new Label(new DecimalFormat("#,##0.00").format(mt)+" Ar"), col, 1);
                gridTarif.add(new Label(util.addPrefix(2, cal.getTime().getHours()+"", "0")+"h"+util.addPrefix(2, cal.getTime().getMinutes()+"", "0")), col, 2);
                col++;
            }
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void IntitializeListIntervenant() {
        try {
            List<Intervenant> liste = intervServ.find(new Intervenant());
            ObservableList<Intervenant> data;
            data = FXCollections.observableArrayList();
            data.setAll(liste);
            ListeIntervenantListView.setItems(data);
            String[] nomCols = new String[2];
            nomCols[0] = "nom";
            nomCols[1] = "prenom";
            ListeIntervenantListView.setCellFactory(new ListViewUtil().buildCellFactory(nomCols, " "));
            dossier.setCellValueFactory(new PropertyValueFactory<>("nomDossierComplet"));
            date.setCellValueFactory(new PropertyValueFactory<>("daty"));
             date.setCellFactory(column -> {
                return new TableCell<EvtDossierLibelle,Date>() {
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                            System.out.println("valeur nulle");
                        } 
                        else {
                             setText (util.dateToString(item));
                        }
                    }
                };
            });
            demandeur.setCellValueFactory(new PropertyValueFactory<>("nomDemandeur"));
            duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
            evenement.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            notes.setCellValueFactory(new PropertyValueFactory<>("note"));
            ListeIntervenantListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Intervenant>() {
                public void changed(ObservableValue<? extends Intervenant> observable, Intervenant oldValue, Intervenant nv) {
                    try {
                        EvtDossierLibelle evt = new EvtDossierLibelle();
                        evt.setIdIntervenant(nv.getId());
                        List<EvtDossierLibelle> evtList = evtService.findEvtDossierLib(evt);
                        /*test tarif*/
                        setEvtDoss(evtList);
                        initTotalTarif() ;
                        for (int c = 0; c < evtList.size(); c++) {
                            evtList.get(c).setNomDossierComplet();
                        }
                        ObservableList<EvtDossierLibelle> data = FXCollections.observableArrayList();
                        for (int compteur1 = 0; compteur1 < evtList.size(); compteur1++) {
                            data.add(evtList.get(compteur1));
                        }

                        //honoraire.setText(evtService.GetHonoraireByIntervenant(nv.getId()).toString() + " Ariary");
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.FRANCE);
                        dfs.setGroupingSeparator(' ');
                        honoraire.setText(new DecimalFormat("#,##0.00", dfs).format(evtService.GetHonoraireByIntervenant(nv.getId())) + " Ariary");
                        FeuilleTempsAccueilController.idIntervenant = nv.getId();
                        paneDatePicker.getChildren().clear();
                        initalizeCalendar(evtList);
                        EvenementTableview.getColumns().clear();
                        EvenementTableview.setItems(data);
                        EvenementTableview.getColumns().addAll(dossier, date, demandeur, duree, evenement, notes);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public List<EvtDossierLibelle> getEvtDoss() {
        return evtDoss;
    }

    public void setEvtDoss(List<EvtDossierLibelle> evtDoss) {
        this.evtDoss = evtDoss;
    }

    
    

}
