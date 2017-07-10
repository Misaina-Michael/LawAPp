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
public class DescTitre extends BaseModele{
    private Integer idtitre;
    private String titre;
    private String entete;
    private String corps;
    private String pied;
    private String langue;

    public Integer getIdtitre() {
        return idtitre;
    }

    public void setIdtitre(Integer idtitre) {
        this.idtitre = idtitre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEntete() {
        return entete;
    }

    public void setEntete(String entete) {
        this.entete = entete;
    }

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public String getPied() {
        return pied;
    }

    public void setPied(String pied) {
        this.pied = pied;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }
    
    
}
