/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modeles.BaseModele;
import modeles.clients.Client;
import modeles.clients.ClientLibelle;
import modeles.contact.Contact;
import modeles.contact.ContactClient;
import modeles.contact.ContactClientLibelle;
import modeles.parametres.FacturationClientLibelle;
import modeles.parametres.TypeFacturationClient;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Misaina
 */
public class ClientService extends BaseService{
    public Map produceMapFicheClient(Integer idClient) throws Exception
    {
        Session sess=null;
        Map<String, Object> map=null;
        try
        {
            map=new HashMap<String, Object>();
            sess=this.getDao().getSessionFact().openSession();
            //client
            ClientLibelle clientLib=new ClientLibelle();
            clientLib.setId(idClient);
            clientLib=(ClientLibelle)this.findById(clientLib, sess);
            map.put("ClientLibelle", clientLib);
            
            //facturation client
            FacturationClientLibelle facturationClient=new FacturationClientLibelle();
            facturationClient.setIdClient(idClient);
            facturationClient=((List<FacturationClientLibelle>)(List<?>)this.find(facturationClient, sess)).get(0);
            map.put("Facturation", facturationClient);
            
            //contacts client
            ContactClientLibelle contact=new ContactClientLibelle();
            contact.setIdClient(idClient);
            List<ContactClientLibelle> contactsClient=(List<ContactClientLibelle>)(List<?>)this.find(contact, sess);
            JRBeanCollectionDataSource collectionContact=new JRBeanCollectionDataSource(contactsClient);
            map.put("Contacts", collectionContact);
            return map;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            if(sess!=null) sess.close();
        }
        
    }
    public List<Client> findClient(Client c) throws Exception
    {
        List<Client> res=new ArrayList<Client>();
        for(BaseModele b:this.getDao().find(c))
        {
            res.add((Client)b);
        }
        return res;
    }
    public List<ClientLibelle> findClientLib(ClientLibelle c) throws Exception
    {
        List<ClientLibelle> res=new ArrayList<ClientLibelle>();
        for(BaseModele b:this.getDao().find(c))
        {
            res.add((ClientLibelle)b);
        }
        return res;
    }
    
    public void saveClient(Client cl, TypeFacturationClient tf) throws Exception
    {
        Session sess=null;
        Transaction tr=null;
        try
        {
            sess=this.getDao().getSessionFact().openSession();
            tr=sess.beginTransaction();
            this.save(cl, sess);
            tf.setIdClient(cl.getId());
            this.save(tf, sess);
            Contact contact=new Contact();
            contact.setNom(cl.getNom());
            contact.setPrenom("");
            contact.setFonction("");
            contact.setVille("");
            contact.setCodePostal(101);
            contact.setPays("Madagascar");
            contact.setIdTitre(cl.getIdTitre());
            contact.setEnSommeil(Boolean.FALSE);
            
            this.save(contact, sess);
            ContactClient cc=new ContactClient();
            cc.setIdClient(cl.getId());
            cc.setIdContact(contact.getId());
            this.save(cc, sess);
            tr.commit();
        }
        catch(Exception ex)
        {
            tr.rollback();
            throw ex;
        }
    }
    
    
}
