/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Random;
import services.ObjetStatiqueService;
import services.PerfService;

/**
 *
 * @author Misaina
 */
public class Main {
    public static void main(String[] args)
    {
        try
        {
           
            
            ObjetStatiqueService os=new ObjetStatiqueService();
            os.loadAll();
            PerfService perf=new PerfService();
            perf.insertDossier(100000);
//            UiUtil u=new UiUtil();
//            String mask="  :  ";
//            
//            System.out.println(u.merge(u.stripMask(mask, mask), mask));
         
//            System.out.println("ooooooooooooo");
//          launch();  
//            ClientService cs=new ClientService();
//            cs.setDao(new HibernateDao());
//            
//            System.out.println(cs.find(new Client()).size());
//            
//            System.out.println(cs.find(new Client()).size());
//
//            Client c=new Client();
//            c.setNom("nomsss");
//            c.setAdresse("adresse");
//            c.initValues();
//            String[] r=new String[3];
//            r[0]="nom";
//            r[1]="adresse";
//            r[2]="standard";
//            System.out.println(c.getValueAttrs(r,"/"));
            
//            ListViewUtil ls=new ListViewUtil();5
//            String[] cols=new String[2];
//            cols[0]="nom";
//            cols[1]="adresse";
//           
//            System.out.println("__________"+c.getValueAttrs(cols,"/"));

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
}
