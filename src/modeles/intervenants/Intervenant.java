/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.intervenants;

import java.sql.Time;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class Intervenant extends BaseModele {

    private Integer idFonction;
    private Integer idTypeUser;
    private String nom;
    private String prenom;
    private Boolean gestionnaire;
    private Integer demandeur;
    private String comptetiers;
    private String adresse;
    private String pays;
    private Integer codepostal;
    private String ville;
    private String bureau;
    private String domicile;
    private String fax;
    private String email;
    private String mobile;
    private String web;
    private String toque;
    private Float seuilbas;
    private Float seuilintermediaire;
    private Float seuilhaut;
    private Float seuiljournalier;
    private java.sql.Time seuilhoraire;
    private String titulaire;
    private String rib;
    private String domiciliation;
    private String iban;
    private String bic;
    private String commentaire;
    private String login;
    private String mdp;
    private String code;
    private String intervenant;
    private String contact;
    private String client;
    private String dossier;
    private String factures;
    private String feuilledeTemps;
    private String planning;
    private String utilisateur;

    /* public String toString()
    {
        return getCode();
    }*/
    public Intervenant() {
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

    public String getDossier() {
        return dossier;
    }

    public void setDossier(String dossier) {
        this.dossier = dossier;
    }

    public Integer getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(Integer idFonction) {
        this.idFonction = idFonction;
    }

    public Integer getIdTypeUser() {
        return idTypeUser;
    }

    public void setIdTypeUser(Integer idTypeUser) {
        this.idTypeUser = idTypeUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Boolean getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(Boolean gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public Integer getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Integer demandeur) {
        this.demandeur = demandeur;
    }

    public String getComptetiers() {
        return comptetiers;
    }

    public void setComptetiers(String comptetiers) {
        this.comptetiers = comptetiers;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Integer getCodepostal() {
        return codepostal;
    }

    public void setCodepostal(Integer codepostal) {
        this.codepostal = codepostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getBureau() {
        return bureau;
    }

    public void setBureau(String bureau) {
        this.bureau = bureau;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getToque() {
        return toque;
    }

    public void setToque(String toque) {
        this.toque = toque;
    }

    public Float getSeuilbas() {
        return seuilbas;
    }

    public void setSeuilbas(Float seuilbas) {
        this.seuilbas = seuilbas;
    }

    public Float getSeuilintermediaire() {
        return seuilintermediaire;
    }

    public void setSeuilintermediaire(Float seuilintermediaire) {
        this.seuilintermediaire = seuilintermediaire;
    }

    public Float getSeuilhaut() {
        return seuilhaut;
    }

    public void setSeuilhaut(Float seuilhaut) {
        this.seuilhaut = seuilhaut;
    }

    public Float getSeuiljournalier() {
        return seuiljournalier;
    }

    public void setSeuiljournalier(Float seuiljournalier) {
        this.seuiljournalier = seuiljournalier;
    }

    public Time getSeuilhoraire() {
        return seuilhoraire;
    }

    public void setSeuilhoraire(Time seuilhoraire) {
        this.seuilhoraire = seuilhoraire;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getDomiciliation() {
        return domiciliation;
    }

    public void setDomiciliation(String domiciliation) {
        this.domiciliation = domiciliation;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String toString() {
        return getNom() + " " + getPrenom();
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
