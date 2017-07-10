/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.planning;

import java.sql.Time;
import java.util.Date;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class Planning extends BaseModele{
    private Integer idDossier;
    private Integer idJur;
    private Integer idTypeProcedure;
    private String notes;
    private Date dateProcedure;
    private Boolean planifiee;
    private Date datePlanning;
    private Integer rappel;
    private String uniteRappel;
    private Time heureDebut;
    private Time heureFin;
    private Integer idEvt;
    private Integer idGestionnaire;
    private Integer idIntervenant;
    private String libelle;
    private String ville;
    private String transport;

    public Integer getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
    }

    public Integer getIdJur() {
        return idJur;
    }

    public void setIdJur(Integer idJur) {
        this.idJur = idJur;
    }

    public Integer getIdTypeProcedure() {
        return idTypeProcedure;
    }

    public void setIdTypeProcedure(Integer idTypeProcedure) {
        this.idTypeProcedure = idTypeProcedure;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDateProcedure() {
        return dateProcedure;
    }

    public void setDateProcedure(Date dateProcedure) {
        this.dateProcedure = dateProcedure;
    }

    public Boolean getPlanifiee() {
        return planifiee;
    }

    public void setPlanifiee(Boolean planifiee) {
        this.planifiee = planifiee;
    }

    public Date getDatePlanning() {
        return datePlanning;
    }

    public void setDatePlanning(Date datePlanning) {
        this.datePlanning = datePlanning;
    }

    public Integer getRappel() {
        return rappel;
    }

    public void setRappel(Integer rappel) {
        this.rappel = rappel;
    }

    public String getUniteRappel() {
        return uniteRappel;
    }

    public void setUniteRappel(String uniteRappel) {
        this.uniteRappel = uniteRappel;
    }

    public Time getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Time heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Time getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Time heureFin) {
        this.heureFin = heureFin;
    }

    public Integer getIdEvt() {
        return idEvt;
    }

    public void setIdEvt(Integer idEvt) {
        this.idEvt = idEvt;
    }

    public Integer getIdGestionnaire() {
        return idGestionnaire;
    }

    public void setIdGestionnaire(Integer idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public Integer getIdIntervenant() {
        return idIntervenant;
    }

    public void setIdIntervenant(Integer idIntervenant) {
        this.idIntervenant = idIntervenant;
    }
    
    
    
}
