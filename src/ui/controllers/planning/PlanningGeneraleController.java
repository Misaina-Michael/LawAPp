/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.controllers.planning;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import modeles.BaseModele;
import modeles.affichage.ColonneDate;
import modeles.intervenants.Intervenant;
import modeles.parametres.Fonction;
import modeles.planning.PlanningLibelle;
import services.BaseService;
import services.IntervenantService;
import services.PlanningService;
import statiques.ObjetStatique;
import utilitaire.ComboboxUtilitaire;
import utilitaire.Util;

/**
 * FXML Controller class
 *
 * @author RABENANTOANDRO
 */
public class PlanningGeneraleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private GridPane gridjour1;
    @FXML
    private GridPane gridjour2;
    @FXML
    private GridPane gridjour3;
    @FXML
    private GridPane gridjour4;
    @FXML
    private GridPane gridjour5;
    @FXML
    private Label jour1;
    @FXML
    private Label jour2;
    @FXML
    private Label jour3;
    @FXML
    private Label jour4;
    @FXML
    private Label jour5;
    @FXML
    private Label intervalleDate;
    @FXML
    private ComboBox comboboxIntervenant;
    Util util = new Util();
    public static Date debutIntervalle = null;
    public static Date finIntervalle = null;
    PlanningService ps = new PlanningService();
    BaseService bs = new BaseService();
    IntervenantService is = new IntervenantService();

    public void initialize(URL url, ResourceBundle rb) {
        try {
            gridjour1.setVgap(5);
            gridjour1.setPadding(new Insets(0, 0, 0, 0));
            gridjour2.setVgap(5);
            gridjour2.setPadding(new Insets(0, 0, 0, 0));
            gridjour3.setVgap(5);
            gridjour3.setPadding(new Insets(0, 0, 0, 0));
            gridjour4.setVgap(5);
            gridjour4.setPadding(new Insets(0, 0, 0, 0));
            gridjour5.setVgap(5);

            gridjour5.setPadding(new Insets(0, 0, 0, 0));
            this.setCombobox();
            this.setDays();
            this.actualisetitreDates();
        } catch (Exception ex) {
           ex.printStackTrace();
                   
        }

    }

    public void setCombobox() throws Exception {

        try {
            List<Intervenant> liste = is.find(new Intervenant());
            ObservableList<Intervenant> dataTitre;
            dataTitre = FXCollections.observableArrayList();
            dataTitre.setAll(liste);           
            String[] colons = new String[2];
            colons[0] = "nom";
            colons[1] = "prenom";
            comboboxIntervenant.getSelectionModel().selectFirst();
            comboboxIntervenant.setCellFactory(new ComboboxUtilitaire().comboboxCallback(colons, ""));
            comboboxIntervenant.setButtonCell(new ComboboxUtilitaire().setButtonValue(colons, ""));
            comboboxIntervenant.setItems(dataTitre);
        } catch (Exception ex) {    
            throw ex;
        }

    }
    @FXML
    public void onSelectIntervenant(ActionEvent event) {
        try {
            this.actualiseData();
            
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
     @FXML
    public void actualisebutton(ActionEvent event) {
        try {
            this.setDays();
            
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void actualisetitreDates() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(debutIntervalle);
        String deb = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRENCH) + " " + cal.getTime().getDate() + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(finIntervalle);
        String fin = cal1.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRENCH) + " " + cal1.getTime().getDate() + " " + cal1.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        this.intervalleDate.setText("" + util.premierMaj(deb) + " au " + util.premierMaj(fin) + " " + cal1.get(Calendar.YEAR));
    }

    List<GridPane> GetListGridPanes() {
        List<GridPane> gp = new ArrayList<GridPane>();
        gp.add(0, gridjour1);
        gp.add(1, gridjour2);
        gp.add(2, gridjour3);
        gp.add(3, gridjour4);
        gp.add(4, gridjour5);
        return gp;
    }

    public void setDays() {
        try {
           
            
            List<ColonneDate> colonnedate = this.getDays();
            List<PlanningLibelle> pldate = null;
            PlanningLibelle pdate ;
            Pane p = null;
            List<GridPane> lgp = this.GetListGridPanes();
            int comteur = 0;
            for (int c = 0; c < colonnedate.size(); c++) {
                pdate = new PlanningLibelle();
               
                pdate.setDatePlanning(colonnedate.get(c).getDate());
                pldate = ps.findPlanning(pdate);
                for (int c2 = 0; c2 < pldate.size(); c2++) {

                    p = new Pane();
                    p.setMinHeight(50);
                    p.setStyle("-fx-background-color: #58a646;");
                    Label label = new Label(pldate.get(c2).getHeureDebut() + " - " + pldate.get(c2).getHeureDebut() + "\n"  + "Dossier : "+ pldate.get(c2).getVnomDossier() + "\n" + pldate.get(c2).getLibelleEvt() + "\n" + pldate.get(c2).getNomIntervenant() + " " + pldate.get(c2).getPrenomIntervenant() + "\n" + pldate.get(c2).getLibelle());
                    
                    label.setTextFill(Color.web("white"));
                    p.getChildren().addAll(label);
                    lgp.get(comteur).add(p, 0, c2);
                }
                comteur++;
            }
            jour1.setText(colonnedate.get(0).getNomCol());
            jour2.setText(colonnedate.get(1).getNomCol());
            jour3.setText(colonnedate.get(2).getNomCol());
            jour4.setText(colonnedate.get(3).getNomCol());
            jour5.setText(colonnedate.get(4).getNomCol());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    public void actualiseData() {
        try {
            
            Intervenant intervenant = (Intervenant) comboboxIntervenant.getSelectionModel().getSelectedItem();
            List<ColonneDate> colonnedate = this.getDays();
            List<PlanningLibelle> pldate = null;
            PlanningLibelle pdate ;
            Pane p = null;
            List<GridPane> lgp = this.GetListGridPanes();
            int comteur = 0;
            int[] list = new int[colonnedate.size()];
            for (int co = 0; co < colonnedate.size(); co++) {
                list[co] = lgp.get(co).getChildren().size();
            }
            for (int g = 0; g < list.length; g++) {
                for (int x = 0; x < list[g]; x++) {
                    lgp.get(g).getChildren().remove(0);
                }
            }
           // int comteur = 0;
            for (int c = 0; c < colonnedate.size(); c++) {
                pdate = new PlanningLibelle();
                pdate.setIdIntervenantpl(intervenant.getId());
                pdate.setDatePlanning(colonnedate.get(c).getDate());
                pldate = ps.findPlanning(pdate);
                for (int c2 = 0; c2 < pldate.size(); c2++) {

                    p = new Pane();
                    p.setMinHeight(50);
                    p.setStyle("-fx-background-color: #58a646;");
                    Label label = new Label(pldate.get(c2).getHeureDebut() + " - " + pldate.get(c2).getHeureDebut() + "\n"  + "Dossier : "+ pldate.get(c2).getVnomDossier() + "\n" + pldate.get(c2).getLibelleEvt() + "\n" + pldate.get(c2).getNomIntervenant() + " " + pldate.get(c2).getPrenomIntervenant() + "\n" + pldate.get(c2).getLibelle());
                    label.setTextFill(Color.web("white"));
                    p.getChildren().addAll(label);
                    lgp.get(comteur).add(p, 0, c2);
                }
                comteur++;
            }
            jour1.setText(colonnedate.get(0).getNomCol());
            jour2.setText(colonnedate.get(1).getNomCol());
            jour3.setText(colonnedate.get(2).getNomCol());
            jour4.setText(colonnedate.get(3).getNomCol());
            jour5.setText(colonnedate.get(4).getNomCol());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void DateSuivante() {
        try {
            Intervenant interv = (Intervenant) comboboxIntervenant.getSelectionModel().getSelectedItem();
            List<ColonneDate> colonnedate = this.getDaysOnAddition(finIntervalle);
            List<PlanningLibelle> pldate = null;
            PlanningLibelle pdate = null;
            Pane p = null;
            List<GridPane> lgp = this.GetListGridPanes();
            
            int comteur = 0;
            int[] list = new int[colonnedate.size()];
            for (int co = 0; co < colonnedate.size(); co++) {
                list[co] = lgp.get(co).getChildren().size();
            }
            for (int g = 0; g < list.length; g++) {
                for (int x = 0; x < list[g]; x++) {
                    lgp.get(g).getChildren().remove(0);
                }
            }

            for (int c = 0; c < colonnedate.size(); c++) {
                pdate = new PlanningLibelle();
                if(interv==null) {   pdate.setIdIntervenantpl(null); }
                else pdate.setIdIntervenantpl(interv.getId());
                pdate.setDatePlanning(colonnedate.get(c).getDate());
                pldate = ps.findPlanning(pdate);

                for (int c2 = 0; c2 < pldate.size(); c2++) {

                    p = new Pane();
                    p.setMinHeight(50);
                    p.setStyle("-fx-background-color: #58a646;");
                    Label label = new Label(pldate.get(c2).getHeureDebut() + " - " + pldate.get(c2).getHeureDebut() + "\n"+ "Dossier : " + pldate.get(c2).getVnomDossier() + "\n" + pldate.get(c2).getLibelleEvt() + "\n" + pldate.get(c2).getNomIntervenant() + " " + pldate.get(c2).getPrenomIntervenant() + "\n" + pldate.get(c2).getLibelle());
                    
                    label.setTextFill(Color.web("white"));
                    p.getChildren().addAll(label);
                    lgp.get(comteur).add(p, 0, c2);
                }
                comteur++;
            }
            jour1.setText(colonnedate.get(0).getNomCol());
            jour2.setText(colonnedate.get(1).getNomCol());
            jour3.setText(colonnedate.get(2).getNomCol());
            jour4.setText(colonnedate.get(3).getNomCol());
            jour5.setText(colonnedate.get(4).getNomCol());
            this.actualisetitreDates();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void DatePrecedent() {
        try {
             Intervenant interv = (Intervenant) comboboxIntervenant.getSelectionModel().getSelectedItem();
            List<ColonneDate> colonnedate = this.getDaysOnSoustraction(debutIntervalle);
            List<PlanningLibelle> pldate = null;
            PlanningLibelle pdate = null;
            Pane p = null;
            List<GridPane> lgp = this.GetListGridPanes();
            int[] list = new int[colonnedate.size()];
            for (int co = 0; co < colonnedate.size(); co++) {
                list[co] = lgp.get(co).getChildren().size();
            }
            for (int g = 0; g < list.length; g++) {
                for (int x = 0; x < list[g]; x++) {
                    lgp.get(g).getChildren().remove(0);
                }
            }
            int comteur = 0;
            for (int c = 0; c < colonnedate.size(); c++) {
                pdate = new PlanningLibelle();
                if(interv==null) {   pdate.setIdIntervenantpl(null); }
                else pdate.setIdIntervenantpl(interv.getId());  
                pdate.setDatePlanning(colonnedate.get(c).getDate());
                pldate = ps.findPlanning(pdate);
                lgp.get(comteur).getChildren().clear();
                for (int c2 = 0; c2 < pldate.size(); c2++) {

                    p = new Pane();
                    p.setMinHeight(50);
                    p.setStyle("-fx-background-color: #58a646;");
                    Label label = new Label(pldate.get(c2).getHeureDebut() + " - " + pldate.get(c2).getHeureDebut() + "\n"  + "Dossier : "+ pldate.get(c2).getVnomDossier()+ "\n" + pldate.get(c2).getLibelleEvt() + "\n" + pldate.get(c2).getNomIntervenant() + " " + pldate.get(c2).getPrenomIntervenant() + "\n" + pldate.get(c2).getLibelle());
                    p.getChildren().addAll(label);
                    label.setTextFill(Color.web("white"));
                    lgp.get(comteur).add(p, 0, c2);
                }
                comteur++;
            }
            jour1.setText(colonnedate.get(0).getNomCol());
            jour2.setText(colonnedate.get(1).getNomCol());
            jour3.setText(colonnedate.get(2).getNomCol());
            jour4.setText(colonnedate.get(3).getNomCol());
            jour5.setText(colonnedate.get(4).getNomCol());
            this.actualisetitreDates();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void onSuivantAction(ActionEvent event) {

        try {
            this.DateSuivante();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    public void precedentButtonAction(ActionEvent event) {

        try {
            this.DatePrecedent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    List<ColonneDate> getDays() throws Exception {
        List<ColonneDate> res = new ArrayList<ColonneDate>();
        Date fromDate = new Date();
        debutIntervalle = fromDate;
        Calendar c = Calendar.getInstance();
        c.setTime(debutIntervalle);
        c.add(Calendar.DATE, 4);
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
            cd.setNomCol(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRENCH) + " " + cal.getTime().getDate() + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
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
        c.add(Calendar.DATE, 4);

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
            cd.setNomCol(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRENCH) + " " + cal.getTime().getDate() + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
            cd.setDate(cal.getTime());
            res.add(cd);
        }
        return res;
    }

    //Date d equivaut au debutintervalle
    public List<ColonneDate> getDaysOnSoustraction(Date d) {
        List<ColonneDate> res = new ArrayList<ColonneDate>();
        finIntervalle = d;
        //System.out.println("fin Intervalle attr = " + finIntervalle);
        Calendar calSous = Calendar.getInstance();
        calSous.setTime(finIntervalle);
        calSous.add(Calendar.DATE, -4);
        Date dateSoustraite = calSous.getTime();
        debutIntervalle = dateSoustraite;
        Calendar c = Calendar.getInstance();
        c.setTime(dateSoustraite);
        c.add(Calendar.DATE, 4);
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
            cd.setNomCol(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.FRENCH) + " " + cal.getTime().getDate() + " " + cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
            cd.setDate(cal.getTime());
            res.add(cd);
        }
        return res;
    }

}
