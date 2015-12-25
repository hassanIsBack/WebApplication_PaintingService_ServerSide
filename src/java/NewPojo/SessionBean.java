/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewPojo;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import org.hibernate.Session;

/**
 *
 * @author Hossain
 */
@Stateless
@LocalBean
public class SessionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //---------------------Calculate the price for different materials-------------------------------
    public void calculatePrice(String name, String phone, String wallMaterial,
            int wallArea, int numberOfCoatings, int ceilingArea) {
        float price = 0;

        switch (wallMaterial) {
            case "wood":
                price += 20;
                break;
            case "brick":
                price += 30;
                break;
            case "metal":
                price += 40;
                break;
        }
        //price for paint
        price *= wallArea;
        //every round costs 50% extra.
        for (int i = 0; i < numberOfCoatings; i++) {
            price += price * 0.5;
        }
        //ceiling cost 25/m*m
        price += ceilingArea * 25;
        saveInfo(name, phone, wallMaterial, wallArea, numberOfCoatings, ceilingArea, price);
        System.out.println("**** IN Session Bean!!! SaveInfo()****");
    }
//-------------------save the data into database------------------------------------

    private void saveInfo(String name, String phone, String wallMaterial,
            int wallArea, int numberOfCoatings, int ceilingArea, float price) {
        Info information = null;
        information = new Info();
        information.setCustomerName(name);
        information.setPhone(phone);
        information.setWallMaterial(wallMaterial);
        information.setWallArea(wallArea);
        information.setNumberOfCoating(numberOfCoatings);
        information.setCeilingArea(ceilingArea);
        information.setPrice(price);

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(information);
        session.getTransaction().commit();
        session.close();
    }
}
