/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.dossiers;

import java.util.Date;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class DossierLibelle extends BaseModele {

    private String nomDossier;
    private String nomAdversaire;
    private String lieu;
    private Date dateOuverture;
    private String noProcedure;
    private String region;
    private Boolean enCours;
    private String commentaire;
    private Integer idGestionnaire;
    private String nomGestionnaire;
    private String codeGestionnaire;
    private String numeroDossier;
    private Integer idClient;
    private String nomClient;
    private Integer idJuridiction;
    private String juridiction;
    private Integer idNature;
    private String nature;
    private String vnomDossier;
    private String faits;

    public DossierLibelle() {

    }

    public DossierLibelle(String nomDossier, String nomAdversaire, String lieu, Date dateOuverture, String noProcedure, String region, Boolean enCours, String commentaire, Integer idGestionnaire, String nomGestionnaire, String codeGestionnaire, String numeroDossier, Integer idClient, String nomClient, Integer idJuridiction, String juridiction, Integer idNature, String nature) {
        this.nomDossier = nomDossier;
        this.nomAdversaire = nomAdversaire;
        this.lieu = lieu;
        this.dateOuverture = dateOuverture;
        this.noProcedure = noProcedure;
        this.region = region;
        this.enCours = enCours;
        this.commentaire = commentaire;
        this.idGestionnaire = idGestionnaire;
        this.nomGestionnaire = nomGestionnaire;
        this.codeGestionnaire = codeGestionnaire;
        this.numeroDossier = numeroDossier;
        this.idClient = idClient;
        this.nomClient = nomClient;
        this.idJuridiction = idJuridiction;
        this.juridiction = juridiction;
        this.idNature = idNature;
        this.nature = nature;
    }

    public String getNomAdversaire() {
        return nomAdversaire;
    }

    public void setNomAdversaire(String nomAdversaire) {
        this.nomAdversaire = nomAdversaire;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public String getNoProcedure() {
        return noProcedure;
    }

    public void setNoProcedure(String noProcedure) {
        this.noProcedure = noProcedure;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getEnCours() {
        return enCours;
    }

    public void setEnCours(Boolean enCours) {
        this.enCours = enCours;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getIdGestionnaire() {
        return idGestionnaire;
    }

    public void setIdGestionnaire(Integer idGestionnaire) {
        this.idGestionnaire = idGestionnaire;
    }

    public String getNumeroDossier() {
        return numeroDossier;
    }

    public void setNumeroDossier(String numeroDossier) {
        this.numeroDossier = numeroDossier;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdJuridiction() {
        return idJuridiction;
    }

    public void setIdJuridiction(Integer idJuridiction) {
        this.idJuridiction = idJuridiction;
    }

    public Integer getIdNature() {
        return idNature;
    }

    public void setIdNature(Integer idNature) {
        this.idNature = idNature;
    }

    public String getNomDossier() {
        return nomDossier;
    }

    public void setNomDossier(String nomDossier) {
        this.nomDossier = nomDossier;
    }

    public String getNomGestionnaire() {
        return nomGestionnaire;
    }

    public void setNomGestionnaire(String nomGestionnaire) {
        this.nomGestionnaire = nomGestionnaire;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getJuridiction() {
        return juridiction;
    }

    public void setJuridiction(String juridiction) {
        this.juridiction = juridiction;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getCodeGestionnaire() {
        return codeGestionnaire;
    }

    public void setCodeGestionnaire(String codeGestionnaire) {
        this.codeGestionnaire = codeGestionnaire;
    }

    public String getVnomDossier() {
        return vnomDossier;
    }

    public void setVnomDossier(String vnomDossier) {
        this.vnomDossier = vnomDossier;
    }

    public String getFaits() {
        return faits;
    }

    public void setFaits(String faits) {
        this.faits = faits;
    }

}
