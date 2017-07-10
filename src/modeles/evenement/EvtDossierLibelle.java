/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.evenement;

import java.sql.Time;
import java.util.Date;
import modeles.BaseModele;
import utilitaire.Chrono;

/**
 *
 * @author Misaina
 */
public class EvtDossierLibelle extends BaseModele {

    private Integer idDossier;
    private Integer idEvtTarif;
    private Integer idIntervenant;
    private Time duree;
    private Float mt;
    private Date daty;

    private Boolean afacturer;
    private String note;
    private Integer demandeur;
    /**/
    private String nomDemandeur;
    private String codeInterv;
    private String nomInterv;
    private String libelle;
    private String code;
    private Integer idTypeTarif;
    private String libTypeTarif;
    private String numeroDossier;
    private String nomDossier;
    private String nomClient;
    private String nomDossierComplet;
    private String vnomDossier;
    private Boolean imprimer;

    public Boolean getImprimer() {
        return imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        this.imprimer = imprimer;
    }

    public String getNomDemandeur() {
        return nomDemandeur;
    }

    public String getNomDossierComplet() {
        return nomDossierComplet;
    }

    public void setNomDossierComplet() {
        this.nomDossierComplet = this.getNomClient() + " # " + this.getNomDossier() + " " + this.getNumeroDossier();
    }

    public void setNomDemandeur(String nomDemandeur) {
        this.nomDemandeur = nomDemandeur;
    }

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public String getNomDossier() {
        return nomDossier;
    }

    public void setNomDossier(String nomDossier) {
        this.nomDossier = nomDossier;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

//    public Chrono getChrono() {
//        return chrono;
//    }
//
//    public void setChrono(Chrono chrono) {
//        this.chrono = chrono;
//    }
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

    public void setDemandeur(Integer demandeur) {
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

    public String getVnomDossier() {
        return vnomDossier;
    }

    public void setVnomDossier(String vnomDossier) {
        this.vnomDossier = vnomDossier;
    }
    
  
    
}
