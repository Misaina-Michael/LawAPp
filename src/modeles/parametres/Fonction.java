/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.parametres;

import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class Fonction extends BaseModele {

    private String libelle;
    private String intervenant;
    private String contact;
    private String client;
    private String dossier;
    private String factures;
    private String feuilledeTemps;
    private String planning;
    private String utilisateur;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String toString() {
        return getLibelle();
    }

    public String getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(String intervenant) {
        this.intervenant = intervenant;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Fonction(String libelle) {
        this.libelle = libelle;

    }

    public Fonction() {
    }

    public String getDossier() {
        return dossier;
    }

    public void setDossier(String dossier) {
        this.dossier = dossier;
    }

    public String getFactures() {
        return factures;
    }

    public void setFactures(String factures) {
        this.factures = factures;
    }

    public String getFeuilledeTemps() {
        return feuilledeTemps;
    }

    public void setFeuilledeTemps(String feuilledeTemps) {
        this.feuilledeTemps = feuilledeTemps;
    }

   

    public String getPlanning() {
        return planning;
    }

    public void setPlanning(String planning) {
        this.planning = planning;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }
    
}
