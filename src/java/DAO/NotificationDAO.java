/*
 * NOTE: This is the DAO of both Notification and NotificationStudent
 */
package DAO;

import Model.*;
import Exception.IDGenerationException;
import util.*;
import java.sql.*;
import java.util.ArrayList;

public class NotificationDAO {
    
    public static void main(String[] args){
    }
    

    public static void createNotification(String name, String description, ArrayList<Student> studList) {
        try {
            Notification notification = new Notification();
            notification.setNotificationid(Auto.generateID("NF", "notification", 10));
            notification.setNotificationname(name);
            notification.setDescription(description);
            notification.setDateissued(Auto.getToday());
            notifyStudents(studList, notification);
        } catch (IDGenerationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Loop to create associative entities
    private static void notifyStudents(ArrayList<Student> studList, Notification notification) {
        insertNotification(notification);
        for (Student student : studList) {
            insertNotificationStudent(student.getStudentid(), notification.getNotificationid());
        }
    }

    // Insert records
    private static Notification insertNotification(Notification notification) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(SQLUtil.generateInsertQuery("notification"));
            stmt.setString(1, notification.getNotificationid());
            stmt.setString(2, notification.getNotificationname());
            stmt.setString(3, notification.getDescription());
            stmt.setDate(4, SQLUtil.getSQLDate(notification.getDateissued()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return notification;
    }

    //Insert associative record
    private static void insertNotificationStudent(String studID, String notificationID) {

        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(SQLUtil.generateInsertQuery("notificationstudent"));
            stmt.setString(1, Auto.generateID("NS", "notificationstudent", 12));
            stmt.setString(2, studID);
            stmt.setString(3, notificationID);
            stmt.setBoolean(4, false);
            stmt.executeUpdate();
        } catch (SQLException | IDGenerationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Obtain all records
    public static ArrayList<Notification> getNotificationRecords() {
        ArrayList<Notification> notificationList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from notification");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationid(rs.getString(1));
                notification.setNotificationname(rs.getString(2));
                notification.setDescription(rs.getString(3));
                notification.setDateissued(rs.getDate(4));

                notificationList.add(notification);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return notificationList;
    }

    // Obtain notification records based on a query
    public static ArrayList<Notification> getNotificationRecords(String query) {
        ArrayList<Notification> notificationList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationid(rs.getString(1));
                notification.setNotificationname(rs.getString(2));
                notification.setDescription(rs.getString(3));
                notification.setDateissued(rs.getDate(4));

                notificationList.add(notification);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return notificationList;
    }
    
        // Obtain notificationStudent records 
    public static ArrayList<Notificationstudent> getNotificationStudentRecords() {
        ArrayList<Notificationstudent> notificationList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from notificationstudent");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notificationstudent ns = new Notificationstudent();
                ns.setNotificationstudentid(rs.getString(1));
                ns.setNotificationid(NotificationDAO.loadRecord(rs.getString(3)));
                ns.setStudentid(StudentDAO.loadRecord(rs.getString(2)));
                ns.setIsread(rs.getBoolean(4));
                notificationList.add(ns);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return notificationList;
    }

    // Obtain notificationStudent records based on a query
    public static ArrayList<Notificationstudent> getNotificationStudentRecords(String query) {
        ArrayList<Notificationstudent> notificationList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Notificationstudent ns = new Notificationstudent();
                ns.setNotificationstudentid(rs.getString(1));
                ns.setNotificationid(NotificationDAO.loadRecord(rs.getString(3)));
                ns.setStudentid(StudentDAO.loadRecord(rs.getString(2)));
                ns.setIsread(rs.getBoolean(4));
                notificationList.add(ns);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return notificationList;
    }

    // find the record with the indicated primary key
    public static Notification loadRecord(String id) {
        Notification notification = new Notification();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from notification where notificationid = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                notification.setNotificationid(rs.getString(1));
                notification.setNotificationname(rs.getString(2));
                notification.setDescription(rs.getString(3));
                notification.setDateissued(rs.getDate(4));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return notification;
    }

    // update a single record
    public static void updateRecord(Notification notification) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("update notification set notificationname = ?, description = ?, dateissued = ? where notificationid = ?");

            stmt.setString(1, notification.getNotificationname());
            stmt.setString(2, notification.getDescription());
            stmt.setDate(3, SQLUtil.getSQLDate(notification.getDateissued()));
            stmt.setString(4, notification.getNotificationid());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
