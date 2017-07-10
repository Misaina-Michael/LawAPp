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
 * @author RABENANTOANDRO
 */
public class FonctionTarifEvtLibelle extends BaseModele  {

   
    private Integer idFonction;
    private Integer idEvtTarif;
    private String fonctionLibelle;
    private String libelle;
    private String code;
    

    public Integer getIdFonction() {
        return idFonction;
    }

    public FonctionTarifEvtLibelle(Integer idFonction, Integer idEvtTarif, String fonctionLibelle, String libelle, String code) {
        this.idFonction = idFonction;
        this.idEvtTarif = idEvtTarif;
        this.fonctionLibelle = fonctionLibelle;
        this.libelle = libelle;
        this.code = code;
    }

    public FonctionTarifEvtLibelle() {
    }

    public void setIdFonction(Integer idFonction) {
        this.idFonction = idFonction;
    }

    public Integer getIdEvtTarif() {
        return idEvtTarif;
    }

    public void setIdEvtTarif(Integer idEvtTarif) {
        this.idEvtTarif = idEvtTarif;
    }

    public String getFonctionLibelle() {
        return fonctionLibelle;
    }

    public void setFonctionLibelle(String fonctionLibelle) {
        this.fonctionLibelle = fonctionLibelle;
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
    
   
}
