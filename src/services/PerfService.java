/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Date;
import java.util.List;
import java.util.Random;
import modeles.clients.Client;
import modeles.dossiers.Dossier;
import modeles.parametres.TypeFacturationClient;
import static oracle.net.aso.C00.d;
import org.hibernate.Session;
import org.hibernate.Transaction;
import statiques.ObjetStatique;
import utilitaire.Util;

/**
 *
 * @author Misaina
 */
public class PerfService extends BaseService{
    public void insertClient(int nbLigne) throws Exception
    {
        Session sess=null;
        Transaction tr=null;
        try{
            sess=this.getDao().getSessionFact().openSession();
            tr=sess.beginTransaction();
            for(int i=0; i<nbLigne; i++)
            {
                Client cl=new Client();
                cl.setNom("client "+i);
                cl.setIdTitre(2);
                cl.setAdresse("adresse client "+i);
                cl.setPays("Madagascar");
                cl.setVille("Antananarivo");
                cl.setCodePostal(101);
                
                TypeFacturationClient tf=new TypeFacturationClient();
                tf.setCategorieComptable("");
                tf.setCompteComptable("70");
                tf.setLangue("Francais");
                tf.setEcheance(30);
                tf.setPeriodicite("");

                tf.setCompteTiers("");
                tf.setTypeFacture("");
                tf.setPeriodicite("Mensuelle");
                tf.setTauxTva(new Float(20.0));
                tf.setTauxMode(new Float(100.0));
                tf.setMtForfait(new Float(0.0));
                tf.setIdMode(1);
                tf.setIdTypeFacture(ObjetStatique.getTypeFacture().get(0).getId());

                ClientService cs=new ClientService();
                cs.saveClient(cl, tf);
                
            }
            tr.commit();
            System.out.println("___________inserted___________");
        }
        catch(Exception ex){
            tr.rollback();
            throw ex;
        }
        finally{
            if(sess!=null) sess.close();
        }
    }
    
    public void insertDossier(int nbLigne) throws Exception
    {
        Session sess=null;
        Transaction tr=null;
        try{
            sess=this.getDao().getSessionFact().openSession();
            tr=sess.beginTransaction();
            DossierService ds=new DossierService();
            
            List<Client> clients=(List<Client>)(List<?>)this.find(new Client(), sess);
            Random rand=new Random();
            Util u=new Util();
            for(int i=18; i<nbLigne; i++)
            {
                Dossier dossier=new Dossier();
              
                Integer iddoss=ds.getNextVal("dossier_iddossier_seq");
                dossier.setId(iddoss);
                dossier.setIdClient(clients.get(rand.nextInt(clients.size()-1)).getId());
                dossier.setIdGestionnaire(13);
                dossier.setIdJuridiction(2);
                dossier.setIdNature(2);
                dossier.setDateOuverture(new Date("24/11/2016"));
                dossier.setNomAdversaire("adversaire "+iddoss);
                dossier.setRegion("analamanga");
                dossier.setEnCours(Boolean.TRUE);
                dossier.setLieu("Antananarivo");
                dossier.setNumeroDossier(u.dateToString(dossier.getDateOuverture()).substring(8, 10)+""+u.addPrefix(4, ""+iddoss, "0"));
                ds.save(dossier);
            }
            tr.commit();
            System.out.println("___________inserted___________");
        }
        catch(Exception ex){
            tr.rollback();
            throw ex;
        }
        finally{
            if(sess!=null) sess.close();
        }
    }
}
