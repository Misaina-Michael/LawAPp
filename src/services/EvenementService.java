/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import modeles.BaseModele;
import modeles.affichage.EvtDossierLibGroupBy;
import modeles.dossiers.ContactDossier;
import modeles.dossiers.DossierLibelle;
import modeles.evenement.EvenementDossier;
import modeles.evenement.EvtDossierLibelle;
import modeles.evenement.EvtDossierLibellePDF;
import utilitaire.Util;
import modeles.evenement.EvtTarif;
import modeles.evenement.EvtTarifLibelle;
import modeles.evenement.MtTypeTarif;
import modeles.facturation.Facture;
import modeles.facturation.FactureEvt;
import modeles.facturation.TarifFactInterv;
import modeles.facturation.TarifFactIntervttar;
import modeles.facturation.TarifFactIntervttarLibelle;
import modeles.facturation.TarifFacture;
import modeles.intervenants.TarifNormaux;
import modeles.intervenants.TarifsNS;
import modeles.parametres.TypeFacturationDossier;
import modeles.parametres.TypeTarifEvt;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import statiques.ObjetStatique;

/**
 *
 * @author Misaina
 */
public class EvenementService extends BaseService {

    Util util = new Util();
    public List <EvtTarifLibelle> findEvtTarifLibelle (EvtTarifLibelle  f)throws Exception{
         List<EvtTarifLibelle> res = new ArrayList<EvtTarifLibelle>();
        for (BaseModele b : this.getDao().find(f)) {
            res.add((EvtTarifLibelle) b);
        }
        return res;
    }
    public Map produceMapEvtDossier(List<EvtDossierLibelle> liste, DossierLibelle dLib)throws Exception
    {
        Map<String, Object> map=null;
        Util util=null;
        try
        {
            util=new Util();
            map=new HashMap<String, Object>();

//            evt group by typetarif
            List<EvtDossierLibellePDF> listeEvtGroupe=new ArrayList<EvtDossierLibellePDF>(ObjetStatique.getTypeTarifEvt().size());
            
            for(TypeTarifEvt tarif:ObjetStatique.getTypeTarifEvt())
            {
                EvtDossierLibellePDF modeleEvtTarif=new EvtDossierLibellePDF();
                modeleEvtTarif.setIdTypeTarif(tarif.getId());
                modeleEvtTarif.setTypeTarif(tarif.getLibelle());
                modeleEvtTarif.setListeEvt(new ArrayList<EvtDossierLibelle>());
                Float totalMt=new Float(0);
                Time totalDuree=new Time(0, 0, 0);
                for(EvtDossierLibelle evt:liste)
                {    
                    if(tarif.getId().equals(evt.getIdTypeTarif()))
                    {
                        totalMt+=evt.getMt();
                        totalDuree.setMinutes(totalDuree.getMinutes()+util.timeToMinute(evt.getDuree()));
                        modeleEvtTarif.getListeEvt().add(evt);
                    }
                }
                modeleEvtTarif.setTotHt(totalMt);
                modeleEvtTarif.setTotDuree(totalDuree);
                listeEvtGroupe.add(modeleEvtTarif);
            }
            JRBeanCollectionDataSource jrCollTar = new JRBeanCollectionDataSource(listeEvtGroupe);
            map.put("LIST_EVT", jrCollTar);
            map.put("DOSSIER", dLib);
            
            return map;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    public void genererFacture(List<EvtDossierLibelle> liste, Float tva) throws Exception {
        Session sess = null;
        Transaction tr = null;
        try {
            sess = this.getDao().getSessionFact().openSession();
            tr = sess.beginTransaction();
//            udpate evenementdossier :  afacturer=>false
            for (EvtDossierLibelle e : liste) {

                EvenementDossier evt = new EvenementDossier();
                evt.setId(e.getId());
                evt = (EvenementDossier) this.findById(evt);
                evt.setAfacturer(false);
                this.update(evt, sess);
            }
//            ajout facture
//            recherche mode de facturation par défaut pour le dossier
            TypeFacturationDossier typeFactDossier = new TypeFacturationDossier();
            typeFactDossier.setIdDossier(liste.get(0).getIdDossier());
            typeFactDossier = ((List<TypeFacturationDossier>) (List<?>) this.find(typeFactDossier, sess)).get(0);

            Facture fact = new Facture();
//            a faire : trouver adresse de facturation pour dossier : OK
            ContactDossier cd = new ContactDossier();
            cd.setIdDossier(fact.getIdDossier());
            cd.setTypeContact("FACT");
            cd = ((List<ContactDossier>) (List<?>) this.find(cd)).get(0);
            fact.setIdContact(cd.getIdContact());
            fact.setIdDossier(liste.get(0).getIdDossier());
            fact.setIdTypeFacture(typeFactDossier.getIdTypeFacture());
            fact.setDateFacture(new Date());
            fact.setEditee(false);
            fact.setReglee(false);
            fact.setTva(tva);
            fact.setEditeePar("");
//            a faire : date ech = date.now + echeance dossier
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, typeFactDossier.getEcheance());
            Date toDate = cal.getTime();
            fact.setDateEcheance(toDate);
            fact.setSansSuite(false);
            fact.setIdIntervenantEdit(0);
            List<TarifFactIntervttarLibelle> listeTarifFactInterv = groupTarifIntervFactParTypeTarif(liste);
            Float mtfact = new Float(0);
            for (TarifFactIntervttarLibelle tfi : listeTarifFactInterv) {
                mtfact += tfi.getMt();
            }
            fact.setMontant(mtfact);
            this.save(fact, sess);

            //id intervenant rehetra
            List<Integer> idRecu = new ArrayList<Integer>();

            int taille = 0;
            idRecu.add(liste.get(0).getIdIntervenant());
            for (EvtDossierLibelle e : liste) {
                int mtov = 0;
                for (Integer id : idRecu) {
                    if (e.getIdIntervenant().equals(id)) {
                        mtov++;
                        break;
                    }
                }
                if (mtov == 0) {
                    idRecu.add(e.getIdIntervenant());
                }
            }

//            ajout tariffactinterv
            for (Integer it : idRecu) {
                TarifFactInterv tarfi = new TarifFactInterv();
                tarfi.setIdFacture(fact.getId());
                tarfi.setTotalHt(new Float(0));
                tarfi.setIdIntervenant(it);
                for (TarifFactIntervttarLibelle tfi : listeTarifFactInterv) {
                    if (tfi.getIdIntervenant().equals(it)) {
                        tarfi.setTotalHt(tarfi.getTotalHt() + tfi.getMt());
                    }
                }
                this.save(tarfi, sess);

                //            ajout tariffactintervttar
                for (TarifFactIntervttarLibelle tfi : listeTarifFactInterv) {
                    if (it.equals(tfi.getIdIntervenant())) {
                        TarifFactIntervttar newtfi = new TarifFactIntervttar();
                        newtfi.setIdTarifFactInterv(tarfi.getId());
                        newtfi.setIdTypeTarif(tfi.getIdTypeTarif());
                        newtfi.setMt(tfi.getMt());

                        this.save(newtfi, sess);
                    }

                }

            }
//            ajout dans tariffacture
            for (TypeTarifEvt tte : ObjetStatique.getTypeTarifEvt()) {
                TarifFacture tf = new TarifFacture();
                tf.setIdTypeTarif(tte.getId());
                tf.setIdFacture(fact.getId());
                tf.setMt(new Float(0));
                for (TarifFactIntervttarLibelle tfi : listeTarifFactInterv) {
                    if (tte.getId().equals(tfi.getIdTypeTarif())) {
                        tf.setMt(tf.getMt() + tfi.getMt());
                    }
                }
                this.save(tf, sess);
            }

//            ajout dans factureevt
            for (EvtDossierLibelle l : liste) {
                FactureEvt fe = new FactureEvt();
                fe.setIdFacture(fact.getId());
                fe.setIdEvtDossier(l.getId());
                this.save(fe, sess);
            }
            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            throw ex;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public List<TarifFactIntervttarLibelle> groupTarifIntervFactParTypeTarif(List<EvtDossierLibelle> liste) throws Exception {
        List<TarifFactIntervttarLibelle> res = null;
        try {
            List<Integer> idRecu = new ArrayList<Integer>();

            int taille = 0;
            idRecu.add(liste.get(0).getIdIntervenant());
            for (EvtDossierLibelle e : liste) {
                int mtov = 0;
                for (Integer id : idRecu) {
                    if (e.getIdIntervenant().equals(id)) {
                        mtov++;
                        break;
                    }
                }
                if (mtov == 0) {
                    idRecu.add(e.getIdIntervenant());
                }
            }
            taille = idRecu.size();
            res = new ArrayList<TarifFactIntervttarLibelle>(taille * ObjetStatique.getTypeTarifEvt().size());
            for (Integer idre : idRecu) {
                for (TypeTarifEvt tte : ObjetStatique.getTypeTarifEvt()) {
                    TarifFactIntervttarLibelle newtfi = new TarifFactIntervttarLibelle();
                    newtfi.setMt(new Float(0));
                    newtfi.setIdTypeTarif(tte.getId());

                    newtfi.setIdIntervenant(idre);
                    for (EvtDossierLibelle edl : liste) {
                        if (edl.getIdIntervenant().equals(idre) && edl.getIdTypeTarif().equals(tte.getId())) {
                            newtfi.setMt(newtfi.getMt() + edl.getMt());
                        }
                    }
                    if (idRecu.size() != 0) {
                        res.add(newtfi);
                    }
                }
            }
            return res;
        } catch (Exception ex) {
            throw ex;
        }

    }

    public List<MtTypeTarif> calculMtTarif(String[] groupBy, String[] colsHaving, String[] valueHaving) throws Exception {
        String req = "";
        Session sess = null;
        List<MtTypeTarif> res = null;
        try {
            for (int i = 0; i < groupBy.length; i++) {
                req = req + ", ";
                req = req + groupBy[i];
            }
            if (colsHaving.length > 0) {
                req = req + " having ";
                for (int i = 0; i < colsHaving.length; i++) {
                    req = req + colsHaving[i] + "='" + valueHaving[i] + "' ";
                    if (i < colsHaving.length - 1) {
                        req = req + " and ";
                    }
                }
            }

            System.out.println("requete " + req);

            sess = this.getDao().getSessionFact().openSession();
            List results = sess.createQuery("select idTypeTarif as id, sum(mt) as mttotal, sum(duree) as dureetotal, libTypeTarif from MtTypeTarifLib group by  idTypeTarif, libTypeTarif" + req + " order by idTypeTarif asc").list();
            Iterator it = results.iterator();
            res = new ArrayList<MtTypeTarif>(results.size());
            while (it.hasNext()) {
                Object[] row = (Object[]) it.next();
                MtTypeTarif temp = new MtTypeTarif();
                temp.setId((Integer) row[0]);
                temp.setMtTotal((Float) row[1]);
                temp.setDureeTotal((Time) row[2]);
                temp.setLibTypeTarif(row[3].toString());
                res.add(temp);
            }

            return res;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public List<EvtDossierLibelle> findEvtDossierLib(EvtDossierLibelle e) throws Exception {
        List<EvtDossierLibelle> res = null;
        try {
            List<BaseModele> b = this.getDao().find(e);
            res = new ArrayList<EvtDossierLibelle>(b.size());
            for (BaseModele bm : b) {
                res.add((EvtDossierLibelle) bm);
            }
            return res;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Float calculMtInterv(Time duree, int idInterv, int idEvt) throws Exception {
        Util u = new Util();

        try {
            TarifsNS crit = new TarifsNS();
            crit.setIdevttarif(idEvt);
            crit.setIdintervenant(idInterv);
//            crit.setIdEvtTarif(idEvt);
//            crit.setIdIntervenant(idInterv);
            List<TarifsNS> tarif = (List<TarifsNS>) (List<?>) this.find(crit);
            if (tarif == null) {
                throw new Exception("Cet intervenant n'a pas encore de tarifs pour les événements");
            }
            return new Float((u.timeToMinute(duree) * tarif.get(0).getMt()) / u.timeToMinute(tarif.get(0).getDuree()));
        } catch (Exception ex) {
            throw ex;

        }
    }

    public Double GetHonoraireByIntervenantbYdATE(Integer iDIntervenant, Date deb, Date fin) {
        Double res = null;
        Session sess = this.getDao().getSessionFact().openSession();
        String start = util.dateToString(deb);
        String end = util.dateToString(fin);
        String sql = "select   Sum(mt)  as montant  from evenementDossier where idintervenant = " + iDIntervenant + " and daty >= '" + start + "' and daty <= '" + fin + "'  group by idintervenant;";
        SQLQuery query = sess.createSQLQuery(sql);
        List list = query.list();
        Double val = 0.0;
        if (list.size() < 1) {
            res = val;
        } else {
            res = (Double) list.get(0);
        }
        return res;
    }

    public Double GetHonoraireByIntervenant(Integer iDIntervenant) {
        Double res = null;
        Session sess = this.getDao().getSessionFact().openSession();
     
        String sql = "select   Sum(mt)  as montant  from evenementDossier where idintervenant = " + iDIntervenant + "   group by idintervenant;";
        SQLQuery query = sess.createSQLQuery(sql);
        List list = query.list();
        Double val = 0.0;
        if (list.size() < 1) {
            res = val;
        } else {
            res = (Double )list.get(0);
        }
        return res;
    }

    public List<EvtTarif> findEvtTarif(EvtTarif e) throws Exception {
        List<EvtTarif> res = null;
        try {
            List<BaseModele> b = this.getDao().find(e);
            res = new ArrayList<EvtTarif>(b.size());
            for (BaseModele bm : b) {
                res.add((EvtTarif) bm);
            }
            return res;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<EvtDossierLibelle> FindEventBetween2DatesByDossier(Date debut, Date fin, Integer idDossier) throws Exception {
        Util u = new Util();
        Session sess = this.getDao().getSessionFact().openSession();
        List<EvtDossierLibelle> res = null;
        String start = u.dateToString(debut);
        String end = u.dateToString(fin);
        try {
            res = new ArrayList<EvtDossierLibelle>();
            System.out.println("debut " + debut);
            System.out.println("fin " + fin);
            System.out.println("idDossier " + idDossier);
            String sql = "SELECT * FROM evtdossierlibelle where iddossier=" + idDossier + " and daty >= '" + start + "' and daty <= '" + fin + "'";
            SQLQuery query = sess.createSQLQuery(sql);
            query.addEntity(EvtDossierLibelle.class);
            List evtDossierlib = query.list();
            EvtDossierLibelle evt = null;
            for (Iterator iterator = evtDossierlib.iterator(); iterator.hasNext();) {
                EvtDossierLibelle evtdoss = (EvtDossierLibelle) iterator.next();
                evt = new EvtDossierLibelle();
                evt.setId(evtdoss.getId());
                evt.setIdDossier(evtdoss.getIdDossier());
                evt.setIdEvtTarif(evtdoss.getIdEvtTarif());
                evt.setIdIntervenant(evtdoss.getIdIntervenant());
                evt.setDuree(evtdoss.getDuree());
                evt.setMt(evtdoss.getMt());
                evt.setDaty(evtdoss.getDaty());
                evt.setAfacturer(evtdoss.getAfacturer());
                evt.setNote(evtdoss.getNote());
                evt.setDemandeur(evtdoss.getDemandeur());
                evt.setNomDemandeur(evtdoss.getNomDemandeur());
                evt.setCodeInterv(evtdoss.getCodeInterv());
                evt.setNomInterv(evtdoss.getNomInterv());
                evt.setLibelle(evtdoss.getLibelle());
                evt.setCode(evtdoss.getCode());
                evt.setIdTypeTarif(evtdoss.getIdTypeTarif());
                evt.setLibTypeTarif(evtdoss.getLibTypeTarif());
                evt.setNomDossier(evtdoss.getNomDossier());
                evt.setNumeroDossier(evtdoss.getNumeroDossier());
                evt.setNomClient(evtdoss.getNomClient());
                res.add(evt);
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            sess.close();
        }
        return res;

    }

    public List<EvtDossierLibelle> FindByDateIntervenant(String date, Integer IdIntervenant) throws Exception {
        Session sess = this.getDao().getSessionFact().openSession();
        List<EvtDossierLibelle> res = null;

        try {
            res = new ArrayList<EvtDossierLibelle>();
            String sql = "SELECT * FROM evtdossierlibelle where daty = '" + date + "' and idintervenant = " + IdIntervenant + "";
            SQLQuery query = sess.createSQLQuery(sql);
            query.addEntity(EvtDossierLibelle.class);
            List evtDossierlib = query.list();
            EvtDossierLibelle evt = null;
            for (Iterator iterator = evtDossierlib.iterator(); iterator.hasNext();) {
                EvtDossierLibelle evtdoss = (EvtDossierLibelle) iterator.next();
                evt = new EvtDossierLibelle();
                evt.setId(evtdoss.getId());
                evt.setIdDossier(evtdoss.getIdDossier());
                evt.setIdEvtTarif(evtdoss.getIdEvtTarif());
                evt.setIdIntervenant(evtdoss.getIdIntervenant());
                evt.setDuree(evtdoss.getDuree());
                evt.setMt(evtdoss.getMt());
                evt.setDaty(evtdoss.getDaty());
                evt.setAfacturer(evtdoss.getAfacturer());
                evt.setNote(evtdoss.getNote());
                evt.setDemandeur(evtdoss.getDemandeur());
                evt.setCodeInterv(evtdoss.getCodeInterv());
                evt.setNomInterv(evtdoss.getNomInterv());
                evt.setLibelle(evtdoss.getLibelle());
                evt.setCode(evtdoss.getCode());
                evt.setIdTypeTarif(evtdoss.getIdTypeTarif());
                evt.setLibTypeTarif(evtdoss.getLibTypeTarif());
                evt.setNomDossier(evtdoss.getNomDossier());
                evt.setNumeroDossier(evtdoss.getNumeroDossier());
                evt.setNomClient(evtdoss.getNomClient());
                res.add(evt);
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            sess.close();
        }
        return res;

    }

    public List<EvtDossierLibelle> FindEventBetween2Dates(Date debut, Date fin, Integer idIntervenant) throws Exception {
        Util u = new Util();
        Session sess = this.getDao().getSessionFact().openSession();
        List<EvtDossierLibelle> res = null;
        String start = u.dateToString(debut);
        String end = u.dateToString(fin);
        try {
            res = new ArrayList<EvtDossierLibelle>();
            String sql = "SELECT * FROM evtdossierlibelle where daty >= '" + start + "' and daty <= '" + fin + "'  and  idintervenant = " + idIntervenant;
            SQLQuery query = sess.createSQLQuery(sql);
            query.addEntity(EvtDossierLibelle.class);
            List evtDossierlib = query.list();
            EvtDossierLibelle evt = null;
            for (Iterator iterator = evtDossierlib.iterator(); iterator.hasNext();) {
                EvtDossierLibelle evtdoss = (EvtDossierLibelle) iterator.next();
                evt = new EvtDossierLibelle();
                evt.setId(evtdoss.getId());
                evt.setIdDossier(evtdoss.getIdDossier());
                evt.setIdEvtTarif(evtdoss.getIdEvtTarif());
                evt.setIdIntervenant(evtdoss.getIdIntervenant());
                evt.setDuree(evtdoss.getDuree());
                evt.setMt(evtdoss.getMt());
                evt.setDaty(evtdoss.getDaty());
                evt.setAfacturer(evtdoss.getAfacturer());
                evt.setNote(evtdoss.getNote());
                evt.setDemandeur(evtdoss.getDemandeur());
                evt.setCodeInterv(evtdoss.getCodeInterv());
                evt.setNomInterv(evtdoss.getNomInterv());
                evt.setLibelle(evtdoss.getLibelle());
                evt.setCode(evtdoss.getCode());
                evt.setIdTypeTarif(evtdoss.getIdTypeTarif());
                evt.setLibTypeTarif(evtdoss.getLibTypeTarif());
                evt.setNomDossier(evtdoss.getNomDossier());
                evt.setNumeroDossier(evtdoss.getNumeroDossier());
                evt.setNomClient(evtdoss.getNomClient());
                res.add(evt);
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            sess.close();
        }
        return res;

    }

    public List<EvtDossierLibelle> FindEventBetween2DatesByIntervenant(Date debut, Date fin, Integer idIntervenant) throws Exception {
        Util u = new Util();
        Session sess = this.getDao().getSessionFact().openSession();
        List<EvtDossierLibelle> res = null;
        String start = u.dateToString(debut);
        String end = u.dateToString(fin);
        try {
            res = new ArrayList<EvtDossierLibelle>();
            String sql = "SELECT * FROM evtdossierlibelle where daty >= '" + start + "'  and daty <= '" + fin + "' and idintervenant =  " + idIntervenant + "";
            SQLQuery query = sess.createSQLQuery(sql);
            query.addEntity(EvtDossierLibelle.class);
            List evtDossierlib = query.list();
            EvtDossierLibelle evt = null;
            for (Iterator iterator = evtDossierlib.iterator(); iterator.hasNext();) {
                EvtDossierLibelle evtdoss = (EvtDossierLibelle) iterator.next();
                evt = new EvtDossierLibelle();
                evt.setId(evtdoss.getId());
                evt.setIdDossier(evtdoss.getIdDossier());
                evt.setIdEvtTarif(evtdoss.getIdEvtTarif());
                evt.setIdIntervenant(evtdoss.getIdIntervenant());
                evt.setDuree(evtdoss.getDuree());
                evt.setMt(evtdoss.getMt());
                evt.setDaty(evtdoss.getDaty());
                evt.setAfacturer(evtdoss.getAfacturer());
                evt.setNote(evtdoss.getNote());
                evt.setDemandeur(evtdoss.getDemandeur());
                evt.setCodeInterv(evtdoss.getCodeInterv());
                evt.setNomInterv(evtdoss.getNomInterv());
                evt.setLibelle(evtdoss.getLibelle());
                evt.setCode(evtdoss.getCode());
                evt.setIdTypeTarif(evtdoss.getIdTypeTarif());
                evt.setLibTypeTarif(evtdoss.getLibTypeTarif());
                evt.setNomDossier(evtdoss.getNomDossier());
                evt.setNumeroDossier(evtdoss.getNumeroDossier());
                evt.setNomClient(evtdoss.getNomClient());
                res.add(evt);
            }

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            sess.close();
        }
        return res;

    }

    public List<EvtDossierLibGroupBy> FindEventBetween2DatesGroupBy(Date debut, Date fin, Integer idIntervenant) throws Exception {
        Util u = new Util();
        Session sess = this.getDao().getSessionFact().openSession();
        List<EvtDossierLibGroupBy> res = null;
        String start = u.dateToString(debut);
        String end = u.dateToString(fin);

        try {
            res = new ArrayList<EvtDossierLibGroupBy>();
            String sql = "SELECT e.iddossier,dl.numerodossier,dl.vnomdossier as nomDossier,dl.nomclient   FROM evenementdossier e\n"
                    + "JOIN intervenant i ON i.idintervenant = e.idintervenant  JOIN evttarif evt ON evt.idevttarif = e.idevttarif  JOIN typetarifevt tt ON tt.idtypetarif = evt.idtypetarif JOIN dossierlibelle dl ON dl.iddossier = e.iddossier\n"
                    + "where e.daty >= '" + start + "' and e.daty <= '" + end + "' and e.idintervenant = " + idIntervenant + " group by  e.iddossier,dl.numerodossier,dl.vnomdossier,dl.nomclient";
            SQLQuery query = sess.createSQLQuery(sql);
            List list = query.list();
            Iterator iter = list.iterator();
            EvtDossierLibGroupBy evt;
            while (iter.hasNext()) {
                Object[] objArray = (Object[]) iter.next();
                evt = new EvtDossierLibGroupBy();
                evt.setIdDossier((Integer) objArray[0]);
                evt.setNumDossier(objArray[1].toString());
                evt.setNomAdversaire(objArray[2].toString());
                evt.setNomClient(objArray[3].toString());
                evt.setNomDossier(objArray[2].toString());
                res.add(evt);

            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            sess.close();
        }
        return res;

    }

    public List<EvtDossierLibGroupBy> FindEventByIntervGroupByDossier(Integer iDIntervenant, String type) throws Exception {
        Util u = new Util();
        Session sess = this.getDao().getSessionFact().openSession();
        List<EvtDossierLibGroupBy> res = null;

        try {
            String sql = null;
            if (type.equalsIgnoreCase("intervenant")) {
                sql = "SELECT e.iddossier,dl.numerodossier,dl.vnomdossier as nomDossier,dl.nomclient   FROM evenementdossier e\n"
                        + "JOIN intervenant i ON i.idintervenant = e.idintervenant  JOIN evttarif evt ON evt.idevttarif = e.idevttarif  JOIN typetarifevt tt ON tt.idtypetarif = evt.idtypetarif JOIN dossierlibelle dl ON dl.iddossier = e.iddossier\n"
                        + "where e.idIntervenant = " + iDIntervenant + " group by  e.iddossier,dl.numerodossier,dl.vnomdossier,dl.nomclient";
            } else {
                sql = "SELECT e.iddossier,dl.numerodossier,dl.vnomdossier as nomDossier,dl.nomclient   FROM evenementdossier e\n"
                        + "JOIN intervenant i ON i.idintervenant = e.demandeur  JOIN evttarif evt ON evt.idevttarif = e.idevttarif  JOIN typetarifevt tt ON tt.idtypetarif = evt.idtypetarif JOIN dossierlibelle dl ON dl.iddossier = e.iddossier\n"
                        + "where e.demandeur = " + iDIntervenant + " group by  e.iddossier,dl.numerodossier,dl.vnomdossier,dl.nomclient";
            }
            res = new ArrayList<EvtDossierLibGroupBy>();

            SQLQuery query = sess.createSQLQuery(sql);
            List list = query.list();
            Iterator iter = list.iterator();
            EvtDossierLibGroupBy evt;
            while (iter.hasNext()) {
                Object[] objArray = (Object[]) iter.next();
                evt = new EvtDossierLibGroupBy();
                evt.setIdDossier((Integer) objArray[0]);
                evt.setNumDossier(objArray[1].toString());
                evt.setNomAdversaire(objArray[2].toString());
                evt.setNomClient(objArray[3].toString());
                evt.setNomDossier(objArray[2].toString());
                res.add(evt);

            }
        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            sess.close();
        }
        return res;

    }

}
