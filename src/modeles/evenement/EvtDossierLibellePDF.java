/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.evenement;

import java.sql.Time;
import java.util.List;

/**
 *
 * @author Misaina
 */
public class EvtDossierLibellePDF {
    private Integer idTypeTarif;
    private String typeTarif;
    private List<EvtDossierLibelle> listeEvt;
    private Float totHt;
    private Time totDuree;

    public Integer getIdTypeTarif() {
        return idTypeTarif;
    }

    public void setIdTypeTarif(Integer idTypeTarif) {
        this.idTypeTarif = idTypeTarif;
    }

    public String getTypeTarif() {
        return typeTarif;
    }

    public void setTypeTarif(String typeTarif) {
        this.typeTarif = typeTarif;
    }

    public List<EvtDossierLibelle> getListeEvt() {
        return listeEvt;
    }

    public void setListeEvt(List<EvtDossierLibelle> listeEvt) {
        this.listeEvt = listeEvt;
    }

    public Float getTotHt() {
        return totHt;
    }

    public void setTotHt(Float totHt) {
        this.totHt = totHt;
    }

    public Time getTotDuree() {
        return totDuree;
    }

    public void setTotDuree(Time totDuree) {
        this.totDuree = totDuree;
    }

    
    
}
