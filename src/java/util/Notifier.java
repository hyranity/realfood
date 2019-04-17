/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Model.*;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
import util.Auto;
import util.Email;

public class Notifier {


    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    public static void main(){
        
    }

    public Notifier(EntityManager em, UserTransaction utx) {
        this.em = em;
        this.utx = utx;
    }

    public Notification constructNotification(String title, String message) {
        Notification notification = new Notification();
        notification = new Notification();
        notification.setTitle(title);
        notification.setDescription(message);
        notification.setIsread(null);
        notification.setDateissued(Auto.getToday());
       
        //Generate ID
        TypedQuery<Notifier> query = em.createQuery("SELECT n FROM Notification n", Notifier.class);
        int count = query.getResultList().size();

        try {
            notification.setNotificationid(Auto.generateID("N", 10, count));    // Set notification ID
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return notification;
    }

    

    

    public void sendNotification(Student student, Notification notification) {
        // Add current notification to DB
        try {
            utx.begin();
            em.persist(notification);
             utx.commit();
              
        } catch (ConstraintViolationException ex) {
            // Display error messages if any
            System.out.println(ex.getConstraintViolations());
        } catch (Exception ex){
            ex.printStackTrace();
        }

        notification.setStudentid(student);
        notification.setIsread(false);

        

        // Update object
       List<Notification> nList =  student.getNotificationList();
     
        nList.add(notification);
        
        student.setNotificationList(nList);

        try {
             utx.begin();
        em.merge(student);
            utx.commit();
        } catch (ConstraintViolationException ex) {
            // Display error messages if any
            System.out.println(ex.getConstraintViolations());
        } catch (Exception ex){
           ex.printStackTrace();
        }
        System.out.println("Notification " + notification.getNotificationid() + " has been sent out.");
    }

   

    // Notify a single student
    public void notify(String title, String message, Student student) {
        Notification notification = new Notification();
        notification.setDateissued(Auto.getToday());
        notification.setDescription(message);
        notification.setTitle(title);
        notification.setIsread(false);
        notification.setStudentid(student);
        
         //Generate ID
        TypedQuery<Notifier> query = em.createQuery("SELECT n FROM Notification n", Notifier.class);
        int count = query.getResultList().size();
        
         try {
            notification.setNotificationid(Auto.generateID("N", 10, count));    // Set notification ID
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        System.out.println(notification.getIsread());
        //Send notification
        sendNotification(student, notification);

        //Send email
        Email email = new Email(student.getEmail(), title, message);
        email.sendEmail();
    }
}
