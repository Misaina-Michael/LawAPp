/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.evenement;

import java.sql.Time;
import java.util.Date;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class EvenementDossier extends BaseModele{
    private Integer idDossier;
    private Integer idEvtTarif;
    private Integer idIntervenant;
    private Time duree;
    private Float mt;
    private Date daty;
    private Boolean afacturer;
    private String note;
    private Integer demandeur;
    private Boolean imprimer;

    public Boolean getImprimer() {
        return imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        this.imprimer = imprimer;
    }
    
    public Integer getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
    }

    public Integer getIdEvtTarif() {
        return idEvtTarif;
    }

    public void setIdEvtTarif(Integer idEvtTarif) {
        this.idEvtTarif = idEvtTarif;
    }

    public Integer getIdIntervenant() {
        return idIntervenant;
    }

    public void setIdIntervenant(Integer idIntervenant) {
        this.idIntervenant = idIntervenant;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Float getMt() {
        return mt;
    }

    public void setMt(Float mt) {
        this.mt = mt;
    }

    public Date getDaty() {
        return daty;
    }

    public void setDaty(Date daty) {
        this.daty = daty;
    }

    public Boolean getAfacturer() {
        return afacturer;
    }

    public void setAfacturer(Boolean afacturer) {
        this.afacturer = afacturer;
    }

    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Integer idDemandeur) {
        this.demandeur = idDemandeur;
    }

   
    
}
