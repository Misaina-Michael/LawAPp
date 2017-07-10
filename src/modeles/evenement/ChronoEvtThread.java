/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.evenement;

import java.util.Date;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.util.Duration;
import services.BaseService;
import utilitaire.Chrono;

/**
 *
 * @author Misaina
 */
public class ChronoEvtThread extends Thread{
    private EvtDossierLibelle evt;
    private Chrono chrono;
    
    public void run()
    {
//        chrono=new Chrono();
        try {
            Button stop=new Button("Arrêter");
            Button start=new Button("Démarrer"); 
            chrono.setStart(start);
            chrono.setStop(stop); 
            start.setOnAction(new EventHandler<ActionEvent>() {			
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            chrono.init(Duration.seconds(0));
                            chrono.getChronoComponent().play();
                            start.setText("Démarré");
                            
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
            });
            stop.setOnAction(new EventHandler<ActionEvent>() {			
                    @Override
                    public void handle(ActionEvent arg0) {
                        try {
                            BaseService b=new BaseService();
                            start.setText("Démarrer");
                            chrono.init(Duration.seconds(chrono.getChronoComponent().getTime()));
                            chrono.getChronoComponent().stop();
//                          insert dans tempstravail
                            TempsTravail temps=new TempsTravail();
                            temps.setDateTravail(new Date());
                            temps.setDureeEnSeconde(chrono.getChronoComponent().getTime());
                            temps.setIdEvtDossier(evt.getId());
                            b.save(temps);
                        } 
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        
                    }
            });
            
            chrono.init(Duration.seconds(0));
            chrono.getChronoComponent().play();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public EvtDossierLibelle getEvt() {
        return evt;
    }

    public void setEvt(EvtDossierLibelle evt) {
        this.evt = evt;
    }

    public Chrono getChrono() {
        return chrono;
    }

    public void setChrono(Chrono chrono) {
        this.chrono = chrono;
    }

}
