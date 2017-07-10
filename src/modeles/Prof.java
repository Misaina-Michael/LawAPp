/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles;

import java.util.Date;

/**
 *
 * @author Misaina
 */
public class Prof extends BaseModele{
    private Integer idEmp;
    private String nom;
    private Float sal;
    private Boolean conge;
    private java.util.Date naissance;

    public Prof() throws Exception{
        
    }

    public Date getNaissance() {
        return naissance;
    }

    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }

    public Prof(Integer idEmp, String nom, Float sal, Boolean conge) throws Exception{
        
        this.idEmp = idEmp;
        this.nom = nom;
        this.sal = sal;
        this.conge = conge;
    }

   
    
    public Integer getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(Integer idEmp) {
        this.idEmp = idEmp;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getSal() {
        return sal;
    }

    public void setSal(Float sal) {
        this.sal = sal;
    }

    public Boolean getConge() {
        return conge;
    }

    public void setConge(Boolean conge) {
        this.conge = conge;
    }

    
}
