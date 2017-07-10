/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.evenement;

import java.util.Date;
import modeles.BaseModele;

/**
 *
 * @author Misaina
 */
public class TempsTravail extends BaseModele{
    private Date dateTravail;
    private Long dureeEnSeconde;
    private Integer idEvtDossier;

    public Date getDateTravail() {
        return dateTravail;
    }

    public void setDateTravail(Date dateTravail) {
        this.dateTravail = dateTravail;
    }

    public Long getDureeEnSeconde() {
        return dureeEnSeconde;
    }

    public void setDureeEnSeconde(Long dureeEnSeconde) {
        this.dureeEnSeconde = dureeEnSeconde;
    }

    public Integer getIdEvtDossier() {
        return idEvtDossier;
    }

    public void setIdEvtDossier(Integer idEvtDossier) {
        this.idEvtDossier = idEvtDossier;
    }

    
}
