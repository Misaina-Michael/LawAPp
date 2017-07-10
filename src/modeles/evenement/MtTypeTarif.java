/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.evenement;

import java.sql.Time;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class MtTypeTarif extends BaseModele{
    private Float mtTotal;
    private Time dureeTotal;
    
    private String libTypeTarif;

    public Float getMtTotal() {
        return mtTotal;
    }

    public void setMtTotal(Float mtTotal) {
        this.mtTotal = mtTotal;
    }

    public Time getDureeTotal() {
        return dureeTotal;
    }

    public void setDureeTotal(Time dureeTotal) {
        this.dureeTotal = dureeTotal;
    }


    public String getLibTypeTarif() {
        return libTypeTarif;
    }

    public void setLibTypeTarif(String libTypeTarif) {
        this.libTypeTarif = libTypeTarif;
    }
    
    
    
}
