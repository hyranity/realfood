/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Student;
import util.*;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
   
   public static boolean verifyStudent(String enteredPassword, String studId){
       Student stud = loadRecord(studId);
       
       Hasher hasher = new Hasher(enteredPassword, stud.getPasswordsalt());
       
       //Compare hashedPassword
       if(stud.getPassword().equals(hasher.getHashedPassword()))
           return true;
       return false;
   }
   
   // Creates a new student record
   public static void createStudent(Student student){
       student.setDatejoined(Auto.getToday());
       Hasher hasher = new Hasher(student.getPassword());
       student.setPassword(hasher.getHashedPassword());
       student.setPasswordsalt(hasher.getSalt());
       
       student.setIsenrolled(true);
       
       insertRecord(student);
   }

    // Insert records
    private static void insertRecord(Student student) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(SQLUtil.generateInsertQuery("student"));
            stmt.setString(1, student.getStudentid());
            stmt.setString(2, student.getFirstname());
            stmt.setString(3, student.getLastname());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPasswordsalt());
            stmt.setString(6, student.getPassword());
            stmt.setString(7, student.getGender() + "");
            stmt.setBoolean(8, student.getIsenrolled());
            stmt.setDate(9, SQLUtil.getSQLDate(student.getDatejoined()));
            stmt.setString(10, student.getMykad());
            stmt.setInt(11, student.getCredits());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //getRecord
    public static ArrayList<Student> getRecords() {
        ArrayList<Student> studentList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from student");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setStudentid(rs.getString(1));
                student.setFirstname(rs.getString(2));
                student.setLastname(rs.getString(3));
                student.setEmail(rs.getString(4));
                student.setPasswordsalt(rs.getString(5));
                student.setPassword(rs.getString(6));
                student.setGender(rs.getString(7).charAt(0));
                student.setIsenrolled(rs.getBoolean(8));
                student.setDatejoined(rs.getDate(9));
                student.setMykad(rs.getString(10));
                student.setCredits(rs.getInt(11));
                
                studentList.add(student);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return studentList;
    }


  // find the record with the indicated primary key
    public static Student loadRecord(String id) {
        Student student = new Student();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from student where studentid = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                student.setStudentid(rs.getString(1));
                student.setFirstname(rs.getString(2));
                student.setLastname(rs.getString(3));
                student.setEmail(rs.getString(4));
                student.setPasswordsalt(rs.getString(5));
                student.setPassword(rs.getString(6));
                student.setGender(rs.getString(7).charAt(0));
                student.setIsenrolled(rs.getBoolean(8));
                student.setDatejoined(rs.getDate(9));
                student.setMykad(rs.getString(10));
                student.setCredits(rs.getInt(11));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return student;
    }
    
    // update a single record
    public static void updateRecord(Student student) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("update student set firstName = ?, lastName = ?, email = ?, passwordsalt = ?, password = ?, gender = ?, isenrolled = ?, datejoined = ?, mykad = ?, credits = ? where studentid = ?"); 
            
            stmt.setString(1, student.getFirstname());
            stmt.setString(2, student.getLastname());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getPasswordsalt());
            stmt.setString(5, student.getPassword());
            stmt.setString(6, student.getGender() + "");
            stmt.setBoolean(7, student.getIsenrolled());
            stmt.setDate(8, SQLUtil.getSQLDate(student.getDatejoined()));
            stmt.setString(9, student.getMykad());
            stmt.setInt(10, student.getCredits());
            stmt.setString(11, student.getStudentid());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Does appropriate procedures to set the enrolment status of a student
    public static void setIsEnrolled(String id, boolean isEnrolled){
        // Load the record into student object
        Student student = loadRecord(id);
        
        // Toggle the attribute
        student.setIsenrolled(isEnrolled);
        
        
        //Update the record
        updateRecord(student);
    }
    
}
