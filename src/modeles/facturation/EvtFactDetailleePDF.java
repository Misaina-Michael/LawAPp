/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.facturation;

import java.util.List;

/**
 *
 * @author Misaina
 */
public class EvtFactDetailleePDF {
    private Integer idTypeTarif;
    private String typeTarif;
    private List<FactureEvtLibelle> listeEvt;
    private Float totHt;
    private Float totSoumisTvaHt;
    private Float tva;
    private Float totTtc;
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

    public List<FactureEvtLibelle> getListeEvt() {
        return listeEvt;
    }

    public void setListeEvt(List<FactureEvtLibelle> listeEvt) {
        this.listeEvt = listeEvt;
    }

    public Float getTotHt() {
        return totHt;
    }

    public void setTotHt(Float totHt) {
        this.totHt = totHt;
    }

    public Float getTotSoumisTvaHt() {
        return totSoumisTvaHt;
    }

    public void setTotSoumisTvaHt(Float totSoumisTvaHt) {
        this.totSoumisTvaHt = totSoumisTvaHt;
    }

    public Float getTva() {
        return tva;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }

    public Float getTotTtc() {
        return totTtc;
    }

    public void setTotTtc(Float totTtc) {
        this.totTtc = totTtc;
    }
    
}
