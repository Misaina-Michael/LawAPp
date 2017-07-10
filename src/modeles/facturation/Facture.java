/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.facturation;

import java.util.Date;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class Facture extends BaseModele{
    private Integer idContact;
    private Integer idDossier;
    private Integer idTypeFacture;
    private Date dateFacture;
    private Float montant;
    private Boolean editee;
    private Boolean reglee;
    private String editeePar;
    private Date dateEcheance;
    private Date dateReglement;
    private Boolean sansSuite;
    private Integer idIntervenantEdit;
    private Float tva;
  

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public Integer getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(Integer idDossier) {
        this.idDossier = idDossier;
    }

    public Integer getIdTypeFacture() {
        return idTypeFacture;
    }

    public void setIdTypeFacture(Integer idTypeFacture) {
        this.idTypeFacture = idTypeFacture;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Float getMontant() {
        return montant;
    }

    public void setMontant(Float montant) {
        this.montant = montant;
    }

    public Boolean getEditee() {
        return editee;
    }

    public void setEditee(Boolean editee) {
        this.editee = editee;
    }

    public Boolean getReglee() {
        return reglee;
    }

    public void setReglee(Boolean reglee) {
        this.reglee = reglee;
    }

    public String getEditeePar() {
        return editeePar;
    }

    public void setEditeePar(String editeePar) {
        this.editeePar = editeePar;
    }

    public Date getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(Date dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public Date getDateReglement() {
        return dateReglement;
    }

    public void setDateReglement(Date dateReglement) {
        this.dateReglement = dateReglement;
    }

    public Boolean getSansSuite() {
        return sansSuite;
    }

    public void setSansSuite(Boolean sansSuite) {
        this.sansSuite = sansSuite;
    }

    public Integer getIdIntervenantEdit() {
        return idIntervenantEdit;
    }

    public void setIdIntervenantEdit(Integer idIntervenantEdit) {
        this.idIntervenantEdit = idIntervenantEdit;
    }

    public Float getTva() {
        return tva;
    }

    public void setTva(Float tva) {
        this.tva = tva;
    }


    
    
    
}
