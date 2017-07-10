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
public class Dossier extends BaseModele{
    private String nomAdversaire;
    private String lieu;
    private Date dateOuverture;
    private String noProcedure;
    private String region;
    private Boolean enCours;
    private String commentaire;
    private Integer idGestionnaire;
    private String numeroDossier;
    private Integer idClient;
    private Integer idJuridiction;
    private Integer idNature;
    private String vnomDossier;
    private String faits;

    public Dossier()
    {
        
    }
    public Dossier(String nomAdversaire, String lieu, Date dateOuverture, String noProcedure, String region, Boolean enCours, String commentaire, Integer idGestionnaire, String numeroDossier, Integer idClient, Integer idJuridiction, Integer idNature) {
        this.nomAdversaire = nomAdversaire;
        this.lieu = lieu;
        this.dateOuverture = dateOuverture;
        this.noProcedure = noProcedure;
        this.region = region;
        this.enCours = enCours;
        this.commentaire = commentaire;
        this.idGestionnaire = idGestionnaire;
        this.numeroDossier = numeroDossier;
        this.idClient = idClient;
        this.idJuridiction = idJuridiction;
        this.idNature = idNature;
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
