/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.*;
import Exception.IDGenerationException;
import util.*;
import java.sql.*;
import java.util.ArrayList;
import util.*;


public class StudentOrderDAO {

    public static void main(String[] args) {
       cancelOrder("O000000001");
    }
    
    public static void createOrder(String studId){
        Student stud = StudentDAO.loadRecord(studId);
        Studentorder so = new Studentorder();
        CodeGenerator cg = new CodeGenerator();
        
        try{
            so.setOrderid(Auto.generateID("O", "order", 10));
            so.setStudentid(stud);
            so.setChosendate(so.getChosendate());
            so.setCouponcode(cg.generateCode());
            so.setIspaid(false);
            so.setIsredeemed(false);
            so.setIscanceled(false);
            so.setTotalprice(0);
        }
        catch(IDGenerationException ex){
            System.out.println(ex.getMessage());
        }
        
        insertRecord(so);
        
    }

    // Insert records
    public static void insertRecord(Studentorder so) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(SQLUtil.generateInsertQuery("studentorder"));
            stmt.setString(1, so.getOrderid());
            stmt.setDate(2, SQLUtil.getSQLDate(so.getChosendate()));
            stmt.setString(3, so.getStudentid().getStudentid());
            stmt.setString(4, so.getCouponcode());
            stmt.setBoolean(5, so.getIspaid());
            stmt.setBoolean(6, so.getIsredeemed());
            stmt.setBoolean(7, so.getIscanceled());
            stmt.setInt(8, so.getTotalprice());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //get records
    public static ArrayList<Studentorder> getRecords() {
        ArrayList<Studentorder> soList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from food");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Studentorder so = new Studentorder();
                so.setOrderid(rs.getString(1));
                so.setChosendate(rs.getDate(2));
                so.setStudentid(StudentDAO.loadRecord(rs.getString(3))); // Stores student object
                so.setCouponcode(rs.getString(4));
                so.setIspaid(rs.getBoolean(5));
                so.setIsredeemed(rs.getBoolean(6));
                so.setIscanceled(rs.getBoolean(7));
                so.setTotalprice(8);
                
                soList.add(so);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return soList;
    }


  // find the record with the indicated primary key
    public static Studentorder loadRecord(String id) {
        Studentorder so = new Studentorder();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from studentorder where orderid = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                so.setOrderid(rs.getString(1));
                so.setChosendate(rs.getDate(2));
                so.setStudentid(StudentDAO.loadRecord(rs.getString(3))); // Stores student object
                so.setCouponcode(rs.getString(4));
                so.setIspaid(rs.getBoolean(5));
                so.setIsredeemed(rs.getBoolean(6));
                so.setIscanceled(rs.getBoolean(7));
                so.setTotalprice(8);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return so;
    }
    
    // update a single record   NOTE: StudentID shouldn't be able to change (FK) because one order uniquely belongs to one student. If he/she cancels, just set isCanceled = true;
    public static void updateRecord(Studentorder so) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("update studentorder set  chosendate = ?, couponcode = ?, ispaid = ?, isredeemed = ?, iscanceled = ?, totalprice = ? where orderid = ?");

            stmt.setDate(1, SQLUtil.getSQLDate(so.getChosendate()));
            stmt.setString(2, so.getCouponcode());
            stmt.setBoolean(3, so.getIspaid());
            stmt.setBoolean(4, so.getIsredeemed());
            stmt.setBoolean(5, so.getIscanceled());
            stmt.setInt(6, so.getTotalprice());
            stmt.setString(7, so.getOrderid());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Does appropriate procedures to cancel an order
    public static void cancelOrder(String id){
        // Load the record into food object
        Studentorder so = loadRecord(id);
        
        // Set isCanceled = true
        so.setIscanceled(true);
        
        //Update the record
        updateRecord(so);
    }
    
    
}
