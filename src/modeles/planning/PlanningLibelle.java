/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.planning;

import java.sql.Time;
import java.util.Date;
import modeles.BaseModele;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Misaina
 */
public class PlanningLibelle extends BaseModele{
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
    private String codeEvt;
    private String libelleEvt;
    private Integer idGestionnaire;
    private Integer idIntervenantpl;
    private String codeIntervenant;
    private String codeGestionnaire;
    private String libelle;
    private String juridiction;
    private String ville;
    private String transport;
    private String nomIntervenant;
    private String prenomIntervenant;
    private String prenomGestionnaire;
    private String nomGestionnaire;
    private String vnomDossier;

    public String getVnomDossier() {
        return vnomDossier;
    }

    public void setVnomDossier(String vnomDossier) {
        this.vnomDossier = vnomDossier;
    }
    
    
    
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
        Configuration config = new Configuration();

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

    public String getCodeEvt() {
        return codeEvt;
    }

    public void setCodeEvt(String codeEvt) {
        this.codeEvt = codeEvt;
    }

    public String getLibelleEvt() {
        return libelleEvt;
    }

    public void setLibelleEvt(String libelleEvt) {
        this.libelleEvt = libelleEvt;
    }

    public Integer getIdGestionnaire() {
        return idGestionnaire;
    }

    public void setIdGestionnaire(Integer idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
    }

    public Integer getIdIntervenantpl() {
        return idIntervenantpl;
    }

    public void setIdIntervenantpl(Integer idIntervenantpl) {
        this.idIntervenantpl = idIntervenantpl;
    }

    

    
    public String getJuridiction() {
        return juridiction;
    }

    public void setJuridiction(String juridiction) {
        this.juridiction = juridiction;
    }

    public String getCodeIntervenant() {
        return codeIntervenant;
    }

    public void setCodeIntervenant(String codeIntervenant) {
        this.codeIntervenant = codeIntervenant;
    }

    public String getCodeGestionnaire() {
        return codeGestionnaire;
    }

    public void setCodeGestionnaire(String codeGestionnaire) {
        this.codeGestionnaire = codeGestionnaire;
    }

    public String getNomIntervenant() {
        return nomIntervenant;
    }

    public void setNomIntervenant(String nomIntervenant) {
        this.nomIntervenant = nomIntervenant;
    }

    public String getPrenomIntervenant() {
        return prenomIntervenant;
    }

    public void setPrenomIntervenant(String prenomIntervenant) {
        this.prenomIntervenant = prenomIntervenant;
    }

    public String getPrenomGestionnaire() {
        return prenomGestionnaire;
    }

    public void setPrenomGestionnaire(String prenomGestionnaire) {
        this.prenomGestionnaire = prenomGestionnaire;
    }

    public String getNomGestionnaire() {
        return nomGestionnaire;
    }

    public void setNomGestionnaire(String nomGestionnaire) {
        this.nomGestionnaire = nomGestionnaire;
    }

    public String getNomCompletIntervenant() {
        return nomIntervenant+" "+prenomIntervenant;
    }

   
}
