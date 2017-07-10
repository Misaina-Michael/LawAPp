/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.List;
import modeles.intervenants.TarifNormaux;
import modeles.intervenants.TarifSpeciaux;
import modeles.intervenants.TarifsNS;
import org.hibernate.Session;
import utilitaire.HibernateUtil;

/**
 *
 * @author Misaina
 */
public class TarifsIntervenantService extends BaseService {

    public List<TarifsNS> getToutTarif(TarifsNS tarif) throws Exception {
        List<TarifsNS> resultat = null;
        Session session = null;
        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            TarifNormaux tn = new TarifNormaux();
            tn.setIdintervenant(tarif.getIdintervenant());
            TarifSpeciaux ts = new TarifSpeciaux();
            ts.setIdintervenant(tarif.getIdintervenant());
            List<TarifNormaux> tarifsNormaux = (List<TarifNormaux>)(List<?>)this.find(tn, session);
            List<TarifSpeciaux> tarifsSpeciaux = (List<TarifSpeciaux>)(List<?>)this.find(ts, session);
            
            resultat = new ArrayList<TarifsNS>(tarifsNormaux.size()+tarifsSpeciaux.size());
            
            for(TarifNormaux t : tarifsNormaux){
                TarifsNS tarifs = new TarifsNS();
                tarifs.setCode(t.getCode());
                tarifs.setDuree(t.getDuree());
                tarifs.setId(t.getId());
//                tarifs.setIdTypeTarif(t.getidtype);
                tarifs.setIdevttarif(t.getIdevttarif());
                tarifs.setIdfonction(t.getIdfonction());
                tarifs.setIdintervenant(t.getIdintervenant());
                tarifs.setIdtarifinterv(t.getIdtarifinterv());
                tarifs.setLibelle(t.getLibelle());
                tarifs.setLibelleTypeTarif(t.getLibelleTypeTarif());
                tarifs.setMt(t.getMt());
                tarifs.setMtevt(t.getMtevt());
                tarifs.setTaux(t.getTaux());
                tarifs.setType("tn");
                resultat.add(tarif);
            }
            for(TarifSpeciaux t : tarifsSpeciaux){
                TarifsNS tarifs = new TarifsNS();
                tarifs.setCode(t.getCode());
                tarifs.setDuree(t.getDuree());
                tarifs.setId(t.getId());
                tarifs.setIdevttarif(t.getIdevttarif());
                tarifs.setIdfonction(t.getIdfonction());
                tarifs.setIdintervenant(t.getIdintervenant());
                tarifs.setIdtarifinterv(t.getIdtarifinterv());
                tarifs.setLibelle(t.getLibelle());
                tarifs.setLibelleTypeTarif(t.getLibelleTypeTarif());
                tarifs.setMt(t.getMt());
                tarifs.setMtevt(t.getMtevt());
                tarifs.setTaux(t.getTaux());
                tarifs.setType("tn");
                resultat.add(tarif);
            }            
            return resultat;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
