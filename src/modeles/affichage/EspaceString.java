/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeles.affichage;

import java.util.Date;

/**
 *
 * @author RABENANTOANDRO
 */
public class EspaceString {
    private Date date;
    private Integer position;

  

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Date getDate() {
        return date;
    }

    public EspaceString(Date date, Integer position) {
        this.date = date;
        this.position = position;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EspaceString() {
    }

   

   
    
  
    
}
