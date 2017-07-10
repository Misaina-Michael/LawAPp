/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modeles.BaseModele;
import modeles.clients.ClientLibelle;
import modeles.contact.ContactClient;
import modeles.dossiers.ContactDossier;
import modeles.dossiers.ContactDossierLibelle;
import modeles.dossiers.Dossier;
import modeles.dossiers.DossierLibelle;
import modeles.intervenants.IntervDossierLibelle;
import modeles.parametres.DefaultDir;
import modeles.parametres.FacturationDossierLibelle;
import modeles.parametres.TypeFacturationClient;
import modeles.parametres.TypeFacturationDossier;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;
import statiques.ObjetStatique;
import utilitaire.ConstanteDirectory;
import utilitaire.DefaultsDirectory;

/**
 *
 * @author Misaina
 */
public class DossierService extends BaseService {

    public Map produceMapDossierLib(String titre, List<DossierLibelle> liste) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<String, Object>();
            map.put("TITRE", titre);
            JRBeanCollectionDataSource jrCollTar = new JRBeanCollectionDataSource(liste);
            map.put("DOSSIERS", jrCollTar);
            return map;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Map produceMapFicheDossier(DossierLibelle dossierLib) throws Exception {
        Map<String, Object> map = null;
        Session sess = null;
        try {
            sess = this.getDao().getSessionFact().openSession();

            map = new HashMap<String, Object>();
            map.put("TITRE", dossierLib.getVnomDossier());

            map.put("DossierLibelle", dossierLib);

            //intervenants du dossier
            IntervDossierLibelle intervDossierLib = new IntervDossierLibelle();
            intervDossierLib.setIdDossier(dossierLib.getId());
            List<IntervDossierLibelle> listIntervDossier = (List<IntervDossierLibelle>) (List<?>) this.find(intervDossierLib, sess);
            JRBeanCollectionDataSource jrCollTar = new JRBeanCollectionDataSource(listIntervDossier);
            map.put("INTERVENANTS", jrCollTar);

            //info client
            ClientLibelle cl = new ClientLibelle();
            cl.setId(dossierLib.getIdClient());
            cl = (ClientLibelle) this.findById(cl, sess);
            map.put("Client", cl);
            //apporteur dossier
            ContactDossierLibelle cdLib = new ContactDossierLibelle();
            cdLib.setIdDossier(dossierLib.getId());
            cdLib.setTypeContact("APP");
            cdLib = (ContactDossierLibelle) this.find(cdLib, sess).get(0);
            map.put("Apporteur", cdLib);

            //facturation
            FacturationDossierLibelle factLib = new FacturationDossierLibelle();
            factLib.setIdDossier(dossierLib.getId());
            factLib = (FacturationDossierLibelle) this.find(factLib, sess).get(0);
            map.put("TypeFacturation", factLib);

            //adresse facturation
            ContactDossierLibelle cdFactLib = new ContactDossierLibelle();
            cdFactLib.setIdDossier(dossierLib.getId());
            cdFactLib.setTypeContact("FACT");
            cdFactLib = (ContactDossierLibelle) this.find(cdFactLib, sess).get(0);
            map.put("AdresseFacturation", cdLib);
            return map;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    public List<Dossier> findDossier(Dossier c) throws Exception {
        List<Dossier> res = new ArrayList<Dossier>();
        for (BaseModele b : this.getDao().find(c)) {
            res.add((Dossier) b);
        }
        return res;
    }

    public List<DossierLibelle> findDossierLib(DossierLibelle c) throws Exception {
        List<DossierLibelle> res = new ArrayList<DossierLibelle>();
        for (BaseModele b : this.getDao().find(c)) {
            res.add((DossierLibelle) b);
        }
        return res;
    }

    public void save(Dossier d) throws Exception {
        Session sess = null;
        Transaction tr = null;
        TypeFacturationDossier td = null;
        TypeFacturationClient tf = null;
        TypeFacturationClient tfc = null;
        List<BaseModele> bm = null;

        try {
            tf = new TypeFacturationClient();

            td = new TypeFacturationDossier();
            sess = this.getDao().getSessionFact().openSession();
            tr = sess.beginTransaction();
            //save dossier
            this.save(d, sess);

            //find premier contact du client
            ContactClient cc = new ContactClient();
            cc.setIdClient(d.getIdClient());
            cc = ((List<ContactClient>) (List<?>) this.find(cc)).get(0);
//            save apporteur et adresse de facturation par défaut du dossier
            ContactDossier cd = new ContactDossier();
            cd.setIdContact(cc.getIdContact());
            cd.setIdDossier(d.getId());
            cd.setTypeContact("APP");
            this.save(cd, sess);
            ContactDossier cdf = new ContactDossier();
            cdf.setIdContact(cc.getIdContact());
            cdf.setIdDossier(d.getId());
            cdf.setTypeContact("FACT");
            this.save(cdf, sess);

            //find type facturation client pour le dossier
            tf.setIdClient(d.getIdClient());
            bm = this.find(tf);
            tfc = (TypeFacturationClient) bm.get(0);

            //save type facturation dossier
            td.setIdDossier(d.getId());
            td.setCategorieComptable(tfc.getCategorieComptable());
            td.setCompteComptable(tfc.getCompteComptable());
            td.setCompteTiers(tfc.getCompteTiers());
            td.setEcheance(tfc.getEcheance());
            td.setIdMode(tfc.getIdMode());
            td.setIdTypeFacture(tfc.getIdTypeFacture());
            td.setLangue(tfc.getLangue());
            td.setMtForfait(tfc.getMtForfait());
            td.setPeriodicite(tfc.getPeriodicite());
            td.setTauxMode(tfc.getTauxMode());
            td.setTauxTva(tfc.getTauxTva());
            td.setTypeFacture(tfc.getTypeFacture());

            this.save(td, sess);

//            création des répértoires du dossier
            String nomDossierCreated = d.getNumeroDossier();
            File dirDossier = new File(ConstanteDirectory.getDefaultDirectoryServer() + nomDossierCreated);
            dirDossier.mkdir();
            for (DefaultDir defDir : ObjetStatique.getDefaultDir()) {
                File dir = new File(dirDossier.getAbsolutePath() + "/" + defDir.getNom() + "/");
                dir.mkdir();
            }
            // crétaion d'un dossier dénomé "Factures"
            File dirFacture = new File(dirDossier.getAbsolutePath() + "/"+DefaultsDirectory.FACTURES.toString()+"/");
            dirFacture.mkdir();

            tr.commit();
        } catch (Exception ex) {
            tr.rollback();
            throw ex;
        }
    }
}
