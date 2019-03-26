/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.*;
import util.*;
import java.sql.*;
import java.util.ArrayList;
import Exception.*;
import util.Auto.*;

public class StaffDAO {

    public static void main(String[] args) {
    }
    
    // Checks the entered staff role, whether it is valid or not
    public static boolean isRoleValid(String role){
        if(Auto.eqIgnore(role, "canteen manager"))
            return true;
        else if(Auto.eqIgnore(role, "school staff"))
            return true;
        else if(Auto.eqIgnore(role, "canteen staff"))
            return true;
        else
            return false;
    }

    // Verifies a staff by comparing his/her hashed password with the entered password
     public static boolean verifyStaff(String enteredPassword, String studId){
       Staff staff = loadRecord(studId);
       
       Hasher hasher = new Hasher(enteredPassword, staff.getPasswordsalt());
       
       //Compare hashedPassword
       if(staff.getPassword().equals(hasher.getHashedPassword()))
           return true;
       return false;
   }
   
   // Creates a new staff record
   public static void createStaff(Staff staff){
       staff.setDatejoined(Auto.getToday());
       Hasher hasher = new Hasher(staff.getPassword());
       staff.setPassword(hasher.getHashedPassword());
       staff.setPasswordsalt(hasher.getSalt());
       
       staff.setIshired(true);
       
       insertRecord(staff);
   }

    // Insert records
    private static void insertRecord(Staff staff) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(SQLUtil.generateInsertQuery("staff"));
            stmt.setString(1, staff.getStaffid());
            stmt.setString(2, staff.getFirstname());
            stmt.setString(3, staff.getLastname());
            stmt.setString(4, staff.getGender()+"");
            stmt.setBoolean(5, staff.getIshired());
            stmt.setDate(6, SQLUtil.getSQLDate(staff.getDatejoined()));
            stmt.setString(7, staff.getStaffrole());
            stmt.setString(8, staff.getMykad());
            stmt.setString(9, staff.getPasswordsalt());
            stmt.setString(10, staff.getPassword());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //getRecord
    public static ArrayList<Staff> getRecords() {
        ArrayList<Staff> staffList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from staff");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Staff staff = new Staff();
                staff.setStaffid(rs.getString(1));
                staff.setFirstname(rs.getString(2));
                staff.setLastname(rs.getString(3));
                staff.setGender(rs.getString(4).charAt(0));
                staff.setIshired(rs.getBoolean(5));
                staff.setDatejoined(rs.getDate(6));
                staff.setStaffrole(rs.getString(7));
                staff.setMykad(rs.getString(8));
                staff.setPasswordsalt(rs.getString(9));
                staff.setPassword(rs.getString(10));
                staffList.add(staff);
            }
        } catch (SQLException ex) {
            System.out.println("Unable to obtain staff records: " + ex.getMessage());
        }
        
        return staffList;
    }


  // find the record with the indicated primary key
    public static Staff loadRecord(String id) {
        Staff staff = new Staff();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from staff where staffid = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                staff.setStaffid(rs.getString(1));
                staff.setFirstname(rs.getString(2));
                staff.setLastname(rs.getString(3));
                staff.setGender(rs.getString(4).charAt(0));
                staff.setIshired(rs.getBoolean(5));
                staff.setDatejoined(rs.getDate(6));
                staff.setStaffrole(rs.getString(7));
                staff.setMykad(rs.getString(8));
                staff.setPasswordsalt(rs.getString(9));
                staff.setPassword(rs.getString(10));
            }
        } catch (SQLException ex) {
            System.out.println("Unable to load staff record: " + ex.getMessage());
        }
        
        return staff;
    }
    
    // update a single record
    public static void updateRecord(Staff staff) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("update staff set firstName = ?, lastName = ?, gender = ?, ishired = ?, datejoined = ?, staffrole = ?, mykad = ?, passwordsalt = ?, password = ? where staffid = ?"); 
            
            
            stmt.setString(1, staff.getFirstname());
            stmt.setString(2, staff.getLastname());
            stmt.setString(3, staff.getGender()+"");
            stmt.setBoolean(4, staff.getIshired());
            stmt.setDate(5, SQLUtil.getSQLDate(staff.getDatejoined()));
            stmt.setString(6, staff.getStaffrole());
            stmt.setString(7, staff.getMykad());
            stmt.setString(8, staff.getPasswordsalt());
            stmt.setString(9, staff.getPassword());
            stmt.setString(10, staff.getStaffid());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Unable to update staff record" + ex.getMessage());
        }
    }
    
    // Does appropriate procedures to set the hiring status of a staff
    public static void setHiredStatus(String id, boolean isHired){
        // Load the record into staff object
        Staff staff = loadRecord(id);
        
        // Set the attribute
        staff.setIshired(isHired);
        
        //Update the record
        updateRecord(staff);
    }
    
    
}
