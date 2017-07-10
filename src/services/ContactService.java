/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import java.util.ArrayList;
import java.util.List;
import modeles.BaseModele;
import modeles.clients.Client;
import modeles.contact.Contact;
/**
 *
 * @author RABENANTOANDRO
 */
public class ContactService extends BaseService {
    
    public List<Contact> findContact(Contact contact) throws Exception{
        List<Contact> res=new ArrayList<Contact>();
        for(BaseModele b:this.getDao().find(contact))
        {
            res.add((Contact)b);
        }
        return res;
    }
     public Contact findContactById(Contact contact) throws Exception{
        Contact c = (Contact) super.getDao().findById(contact);
       
        return c;
    }
     public void SaveContact(Contact contact) throws Exception{
        //Contact c = (Contact) super.getDao().findById(contact);
        super.save(contact);
        
    }
    
    
}
