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
public class MtTypeTarifLib extends BaseModele{
    private Integer idEvtTarif;
    private Integer idDossier;
    private Integer idIntervenant;
    private Date daty;
    private Boolean afacturer;
    private Float mt;
    private Time duree;
    private Integer idTypeTarif;
    private String libTypeTarif;

    public Integer getIdEvtTarif() {
        return idEvtTarif;
    }

    public void setIdEvtTarif(Integer idEvtTarif) {
        this.idEvtTarif = idEvtTarif;
    }

    public Integer getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
    }

    public Integer getIdIntervenant() {
        return idIntervenant;
    }

    public void setIdIntervenant(Integer idIntervenant) {
        this.idIntervenant = idIntervenant;
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

    public Float getMt() {
        return mt;
    }

    public void setMt(Float mt) {
        this.mt = mt;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Integer getIdTypeTarif() {
        return idTypeTarif;
    }

    public void setIdTypeTarif(Integer idTypeTarif) {
        this.idTypeTarif = idTypeTarif;
    }

    public String getLibTypeTarif() {
        return libTypeTarif;
    }

    public void setLibTypeTarif(String libTypeTarif) {
        this.libTypeTarif = libTypeTarif;
    }
    
    
    
}
