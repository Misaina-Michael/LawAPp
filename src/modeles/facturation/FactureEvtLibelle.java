/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.facturation;

import java.sql.Time;
import java.util.Date;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class FactureEvtLibelle extends BaseModele{
    			
    private Integer idDossier;			
    private Integer idEvtDossier;			
    private Integer idFacture;			
    private Integer idEvtTarif	;		
    private Integer idIntervenant;			
    private Time duree			;
    private Float mt			;
    private Date daty			;
    
    private Boolean afacturer			;
    private String note			;
    private String demandeur			;
    private String codeInterv			;
    private String nomInterv		;	
    private String libelle			;
    private String code;
    private Integer idTypeTarif;
    private String libTypeTarif;

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

    public String getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(String demandeur) {
        this.demandeur = demandeur;
    }

    public String getCodeInterv() {
        return codeInterv;
    }

    public void setCodeInterv(String codeInterv) {
        this.codeInterv = codeInterv;
    }

    public String getNomInterv() {
        return nomInterv;
    }

    public void setNomInterv(String nomInterv) {
        this.nomInterv = nomInterv;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getIdEvtDossier() {
        return idEvtDossier;
    }

    public void setIdEvtDossier(Integer idEvtDossier) {
        this.idEvtDossier = idEvtDossier;
    }

    public Integer getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(Integer idFacture) {
        this.idFacture = idFacture;
    }
    
    
}
