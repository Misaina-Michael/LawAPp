/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.contact;
import java.util.Date;
import modeles.BaseModele;
/**
 *
 * @author RABENANTOANDRO
 */
public class Contact extends BaseModele {
    private Integer idContact;
    private String nom;
    private Integer idTitre ;
    private String prenom;
    private String fonction;
    private String adresse;
    private Integer codePostal;
    private String  ville;
    private String pays;
    private String standard;
    private String bureau;
    private String fax;
    private String email;
    private String telex;
    private String web;
    private String mobile;
    private String autre;
    private Boolean  enSommeil; 
    private Date dateCreation;
    private Date dateModification;

    public Contact() 
    {
    }
    
    
    
    public Contact(Integer idContact, String nom, Integer idTitre, String prenom, String fonction, String adresse, Integer codePostal, String ville, String pays, String standard, String bureau, String fax, String email, String telex, String web, String mobile, String autre, Boolean enSommeil, Date dateCreation, Date dateModification) {
        this.idContact = idContact;
        this.nom = nom;
        this.idTitre = idTitre;
        this.prenom = prenom;
        this.fonction = fonction;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
        this.standard = standard;
        this.bureau = bureau;
        this.fax = fax;
        this.email = email;
        this.telex = telex;
        this.web = web;
        this.mobile = mobile;
        this.autre = autre;
        this.enSommeil = enSommeil;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getIdTitre() {
        return idTitre;
    }

    public void setIdTitre(Integer idTitre) {
        this.idTitre = idTitre;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getBureau() {
        return bureau;
    }

    public void setBureau(String bureau) {
        this.bureau = bureau;
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

    public String getTelex() {
        return telex;
    }

    public void setTelex(String telex) {
        this.telex = telex;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAutre() {
        return autre;
    }

    public void setAutre(String autre) {
        this.autre = autre;
    }

    public Boolean getEnSommeil() {
        return enSommeil;
    }

    public void setEnSommeil(Boolean enSommeil) {
        this.enSommeil = enSommeil;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }
    
  
   

}
