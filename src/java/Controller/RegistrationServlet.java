/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Schoolsystem;
import Model.Staff;
import Model.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import util.Auto;
import util.Hasher;

/**
 *
 * @author mast3
 */
@WebServlet(name = "RegistrationServlet", urlPatterns = {"/RegistrationServlet"})
public class RegistrationServlet extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //Creates an object. This is where the new details will be temporarily stored.
        
        String userType = request.getParameter("userType");
        
        if(userType.equalsIgnoreCase("student")){
            try {
                Schoolsystem ss = new Schoolsystem();
                utx.begin();
                
                //Searches the "external database" School System for an enrolled student with the given ID.
              // TypedQuery<Schoolsystem> query = em.createQuery("SELECT ss FROM Schoolsystem ss WHERE ss.studentid = :studentid and ss.isenrolled = true", Schoolsystem.class).setParameter("studentid", request.getParameter("studentId"));
              //ss = query.getSingleResult();   
              
              ss = em.find(Schoolsystem.class, request.getParameter("studentId"));
                
                
                // If there's an error, provide appropriate error messages
                if(ss.getStudentid() == null ) // If student ID is incorrect
                    System.out.println("ERROR! No student found!"); 
                else if(!ss.getIsenrolled()){  // If student is no longer enrolled
                    System.out.println("ERROR! The student is no longer enrolled.");
                }
                else{
                    //Transfer existing student details into the new account
                    Student stud = new Student();
                    stud.setStudentid(request.getParameter("studentId"));
                    stud.setFirstname(ss.getFirstname());
                    stud.setLastname(ss.getLastname());
                    stud.setEmail(request.getParameter("email")); //Email can be student's personal email
                    stud.setGender(ss.getGender());
                    stud.setMykad(ss.getMykad());
                    
                    //Set fixed values
                    stud.setCredits(1000);
                    
                    //Hash the password and store the salt 
                    Hasher hasher = new Hasher(request.getParameter("password"));
                    stud.setPassword(hasher.getHashedPassword());
                    stud.setPasswordsalt(hasher.getSalt());
                    
                    System.out.println(stud.getStudentid());
                }
                
            } catch (Exception ex) {
                System.out.println("ERROR: Unable to create student object: " + ex.getMessage());
                // This will be triggered if the student ID is incorrect or student is no longer enrolled.
            }
        }
        else{
            try {
                Staff staff = new Staff();
                utx.begin();
                
             
              // Search for existing staff
              staff = em.find(Staff.class, request.getParameter("staffid"));
                
                
                // If there's an error, provide appropriate error messages
                if(staff.getStaffid() != null ) // If staff ID already exists
                    System.out.println("ERROR! No staff found!"); 
                else{
                    //Transfer existing student details into the new account
                    staff.setStaffid(request.getParameter("studentId"));
                    staff.setFirstname(staff.getFirstname());
                    staff.setLastname(staff.getLastname());
                    staff.setEmail(request.getParameter("email")); //Email can be student's personal email
                    staff.setGender(staff.getGender());
                    staff.setMykad(staff.getMykad());
                    
                    // Set fixed values
                    staff.setDatejoined(Auto.getToday());
                    staff.setStaffrole("canteenStaff");     // This is because there is only 1 manager account (admin account).
                    
                    //Hash the password and store the salt 
                    Hasher hasher = new Hasher(request.getParameter("password"));
                    staff.setPassword(hasher.getHashedPassword());
                    staff.setPasswordsalt(hasher.getSalt());
                    
                    //Insert the staff object
                }
                
            } catch (Exception ex) {
                System.out.println("ERROR: Unable to create staff object: " + ex.getMessage());
                // This will be triggered if the student ID is incorrect or student is no longer enrolled.
            }
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
