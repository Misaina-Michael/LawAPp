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
public class ProcedureLibelle extends BaseModele{
    private Integer idDossier;
    private Integer idJur;
    private Integer idTypeProcedure;
    private String notes;
    private Date dateProcedure;
    private Boolean planifiee;
    private String numeroDossier;
    private String juridiction;
    private String libProcedure;

    public String getLibProcedure() {
        return libProcedure;
    }

    public void setLibProcedure(String libProcedure) {
        this.libProcedure = libProcedure;
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

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public String getJuridiction() {
        return juridiction;
    }

    public void setJuridiction(String juridiction) {
        this.juridiction = juridiction;
    }



    
    
}
