/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modeles.dossiers.ContactDossierLibelle;
import modeles.dossiers.DossierLibelle;
import modeles.facturation.EvtFactDetailleePDF;
import modeles.facturation.Facture;
import modeles.facturation.FactureEvtLibelle;
import modeles.facturation.FactureLibelle;
import modeles.facturation.TarifFactInterv;
import modeles.facturation.TarifFactIntervttar;
import modeles.parametres.TypeFacture;
import modeles.parametres.TypeTarifEvt;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import statiques.ObjetStatique;
import utilitaire.ChiffreLettre;
import utilitaire.ConstanteDirectory;
import utilitaire.DefaultsDirectory;
import utilitaire.ReportUtil;

/**
 *
 * @author Misaina
 */
public class FactureService extends BaseService {
    public Map produceMap(FactureLibelle factLibelle) throws Exception 
    {
        BaseService bs = null;
        ContactDossierLibelle cl = null;
        
        try {
            bs = new BaseService();
            cl = new ContactDossierLibelle();
            cl.setIdDossier(factLibelle.getIdDossier());
            cl.setTypeContact("FACT");
            cl = ((ContactDossierLibelle) (bs.find(cl).get(0)));
            
            DossierLibelle dossierLib=new DossierLibelle();
            dossierLib.setId(factLibelle.getIdDossier());
            dossierLib=((DossierLibelle) (bs.find(dossierLib).get(0)));

//            maj facture editée
            Facture fac = new Facture();
            fac.setId(factLibelle.getId());
            fac.setIdContact(factLibelle.getIdContact());
            fac.setIdDossier(factLibelle.getIdDossier());
            fac.setIdTypeFacture(factLibelle.getIdTypeFacture());
            fac.setDateFacture(factLibelle.getDateFacture());
            fac.setMontant(factLibelle.getMontant());
            fac.setEditee(true);
            fac.setDateEcheance(factLibelle.getDateEcheance());
            fac.setDateReglement(factLibelle.getDateReglement());
            fac.setReglee(factLibelle.getReglee());
            fac.setSansSuite(factLibelle.getSansSuite());
            fac.setIdIntervenantEdit(factLibelle.getIdIntervenantEdit());
            fac.setTva(factLibelle.getTva());
            bs.update(fac);

//            Float soumisTva=ttc-factLibelle.getMontant();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("NOM_CLIENT", cl.getTitreContact() + " " + cl.getNom().toUpperCase()+" "+cl.getPrenom().toUpperCase());
            map.put("ADRESSE_CLIENT", cl.getAdresse());            
            map.put("CP_VILLE_CLIENT", cl.getCp().toString() + ", " + cl.getVille());
            map.put("AFFAIRE", cl.getNom().toUpperCase() + " / " + factLibelle.getNomAdvDossier().toUpperCase());
            map.put("REF", factLibelle.getNumeroDossier());
            map.put("NO_FACT", factLibelle.getId());
            map.put("DATE_FACT", factLibelle.getDateFacture());
            map.put("DATE_FACT_LONG", new SimpleDateFormat("dd MMMMMM yyyy").format(factLibelle.getDateFacture()));
            map.put("NOM_DOSSIER", dossierLib.getVnomDossier());
            map.put("TOTAL_HT", factLibelle.getMontant());
            map.put("TVA", factLibelle.getTva());
            map.put("TOTAL_TTC", factLibelle.getTotalTtc());
            map.put("SOUMIS_TVA", (factLibelle.getTotalTtc() - factLibelle.getMontant()));
            map.put("TOTAL_LETTRE", ChiffreLettre.convertRealToString(factLibelle.getTotalTtc()) + " Ariary");
            map.put("GESTIONNAIRE", dossierLib.getNomGestionnaire());

            List<FactureEvtLibelle> listeEvt = new ArrayList<FactureEvtLibelle>();
            FactureEvtLibelle ev = new FactureEvtLibelle();
            ev.setIdFacture(factLibelle.getId());
            listeEvt = (List<FactureEvtLibelle>) (List<?>) bs.find(ev);
            
            //trouver date début et date fin pour les evt
            Collections.sort(listeEvt, new Comparator<FactureEvtLibelle>() {
                @Override
                public int compare(FactureEvtLibelle evt2, FactureEvtLibelle evt1)
                    {
                        return evt1.getDaty().compareTo(evt2.getDaty());
                    }
            });
            map.put("DATE_DEB", listeEvt.get(0).getDaty());
            map.put("DATE_FIN", listeEvt.get(listeEvt.size()-1).getDaty());
            
            List<EvtFactDetailleePDF> paramEvt = new ArrayList<EvtFactDetailleePDF>();
            for (TypeTarifEvt tte : ObjetStatique.getTypeTarifEvt()) {
                EvtFactDetailleePDF efdp = new EvtFactDetailleePDF();
                efdp.setIdTypeTarif(tte.getId());
                efdp.setTypeTarif(tte.getLibelle());
                efdp.setListeEvt(new ArrayList<FactureEvtLibelle>());
                efdp.setTotHt(new Float(0));
                for (FactureEvtLibelle fe : listeEvt) {
                    if (fe.getIdTypeTarif().equals(tte.getId())) {
                        efdp.getListeEvt().add(fe);
                        efdp.setTotHt(efdp.getTotHt()+fe.getMt());
                    }
                }
                paramEvt.add(efdp);

            }

            JRBeanCollectionDataSource jrCollTar = new JRBeanCollectionDataSource(paramEvt);
            map.put("EVT_FACTURE", jrCollTar);
            return map;
        }
        catch(Exception ex)
        {
            throw ex;
        }
            
    }
    public void editFact(FactureLibelle factLibelle) throws Exception {
        ReportUtil ru = new ReportUtil();
        try
        {
            Map map=produceMap(factLibelle);
            String pathReportName = this.getPathReport(factLibelle);
            String nomFichierFact = factLibelle.getTypeFacture()+" n° " + factLibelle.getId() + " " + factLibelle.getVnomDossier()+" "+factLibelle.getNumeroDossier();
            String pathNewFile = ConstanteDirectory.getDefaultDirectoryServer()+factLibelle.getNumeroDossier()+"/"+DefaultsDirectory.FACTURES.toString() + "/" + nomFichierFact;            
            ru.exportToDOCX(map, pathReportName, pathNewFile);
            File newFile=new File(pathNewFile+".docx");
            Desktop.getDesktop().open(newFile);
        } catch (Exception ex) {
            throw ex;
        }
    }
    public String getPathReport(FactureLibelle factLibelle)throws Exception
    {
        try
        {
            String modeleTypeFacture="fact_simple";
            for(TypeFacture tf:ObjetStatique.getTypeFacture())
            {
                if(tf.getId().equals(factLibelle.getIdTypeFacture())) modeleTypeFacture=tf.getModeleWord();
            }
            String pathReportName = ReportUtil.pathDocs + "/report/"+modeleTypeFacture;
            return pathReportName;
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    public void showPrintViewer(FactureLibelle factLibelle)throws Exception
    {
        ReportUtil reportUtil=new ReportUtil();
        try
        {
            Map map=produceMap(factLibelle);
            String pathReportName = this.getPathReport(factLibelle);
            reportUtil.showViewer(map, pathReportName);
            
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }

    public void updateMtParTarifInterv(Integer idFact, Integer idTypeTarif, Integer idIntervenant, Float newMt) throws Exception {
        Session sess = null;
        Transaction tr = null;
        try {
            sess = this.getDao().getSessionFact().openSession();
            tr = sess.beginTransaction();
            TarifFactInterv tarifAct = new TarifFactInterv();
            tarifAct.setIdIntervenant(idIntervenant);
            tarifAct.setIdFacture(idFact);
            tarifAct = ((List<TarifFactInterv>) (List<?>) this.find(tarifAct, sess)).get(0);

            TarifFactIntervttar tarifEvtAct = new TarifFactIntervttar();
            tarifEvtAct.setIdTarifFactInterv(tarifAct.getId());
            tarifEvtAct.setIdTypeTarif(idTypeTarif);
            tarifEvtAct = ((List<TarifFactIntervttar>) (List<?>) this.find(tarifEvtAct, sess)).get(0);

            Facture f = new Facture();
            f.setId(idFact);
            f = (Facture) this.findById(f);

            tarifEvtAct.setMt(newMt);

            Float oldMt = tarifAct.getTotalHt();
            tarifAct.setTotalHt(tarifAct.getTotalHt() - oldMt + newMt);
            f.setMontant(f.getMontant() - oldMt + newMt);

//            update tariffactinterv
            this.update(tarifAct, sess);
            this.update(tarifEvtAct, sess);
            this.update(f, sess);
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
}
