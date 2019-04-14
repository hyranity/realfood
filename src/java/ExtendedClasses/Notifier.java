/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtendedClasses;

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

    private Model.Notification notification = new Model.Notification();

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    public Notifier(EntityManager em, UserTransaction utx) {
        this.em = em;
        this.utx = utx;
    }

    public void constructNotification(String title, String message) {
        notification.setTitle(title);
        notification.setDescription(message);

        notification.setDateissued(Auto.getToday());
        //Generate ID
        TypedQuery<Notifier> query = em.createQuery("SELECT s FROM Staff s", Notifier.class);
        int count = query.getResultList().size();

        try {
            notification.setNotificationid(Auto.generateID("N", 10, count));    // Set notification ID
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteNotification() {
        // This is highly unlikely to be used, since notifications are auto-generated. But just in case of emergencies, this method will be here.
        // Deletes the notification and all associative entities

        try {
            // Delete associative entities first
            Query associativeQuery = em.createQuery("DELETE FROM Notificationstudent ns WHERE ns.notificationid = :notificationId").setParameter("notificationId", notification);
            associativeQuery.executeUpdate();

            // Delete the notification itself
            Query mainQuery = em.createQuery("DELETE FROM Notification n WHERE n.notificationid = :notificationId").setParameter("notificationId", notification.getNotificationid());
            mainQuery.executeUpdate();
        } catch (Exception ex) {
            // Display error messages if any
            ex.printStackTrace();
        }

        System.out.println("Notification " + notification.getNotificationid() + " has been deleted.");
    }

    public void sendNotification(List<Student> studentList) {
        // Add current notification to DB
        try {
            utx.begin();
            em.persist(notification);

        } catch (Exception ex) {
            // Display error messages if any
            ex.printStackTrace();
        }

        for (Student student : studentList) {
            System.out.println(student.getFirstname());
            List<Notificationstudent> nsList = student.getNotificationstudentList();
            Notificationstudent ns = new Notificationstudent();

            // Loop through each one
            ns.setStudentid(student);
            ns.setNotificationid(notification);
            ns.setIsread(false);

            nsList.add(ns);

            // Update object
            student.setNotificationstudentList(nsList);

            // Update to DB]
            em.merge(student);

        }

        try {
            // Save
            utx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Notification " + notification.getNotificationid() + " has been sent out.");
    }

    public void sendNotification(Student student) {
        // Add current notification to DB
        try {
            utx.begin();
            em.persist(notification);

        } catch (ConstraintViolationException ex) {
            // Display error messages if any
            System.out.println(ex.getConstraintViolations());
        } catch (Exception ex){
            ex.printStackTrace();
        }

        List<Notificationstudent> nsList = student.getNotificationstudentList();
        Notificationstudent ns = new Notificationstudent();

        // Loop through each one
        ns.setStudentid(student);
        ns.setNotificationid(notification);
        ns.setIsread(false);

        nsList.add(ns);

        // Update object
        student.setNotificationstudentList(nsList);

        // Update to DB]
        em.merge(student);

        try {
            // Save
            utx.commit();
        } catch (ConstraintViolationException ex) {
            // Display error messages if any
            System.out.println(ex.getConstraintViolations());
        } catch (Exception ex){
            ex.getMessage();
        }
        System.out.println("Notification " + notification.getNotificationid() + " has been sent out.");
    }

    //Notify a list of students
    public void notify(String title, String message, List<Student> studentList) {
        constructNotification(title, message);

        //Send notification
        sendNotification(studentList);

        //Send email
        for (Student student : studentList) {
            Email email = new Email(student.getEmail(), title, message);
            email.sendEmail();
        }
    }

    // Notify a single student
    public void notify(String title, String message, Student student) {
        constructNotification(title, message);

        //Send notification
        sendNotification(student);

        //Send email
        Email email = new Email(student.getEmail(), title, message);
        email.sendEmail();
    }
}
