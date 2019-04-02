/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Schoolsystemstudent;
import Model.Staff;
import Model.Student;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import static javax.servlet.SessionTrackingMode.URL;
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

        String userType = "";
        String id = "";
        String password = "";
        String email = "";
        
        URL referer = new URL(request.getHeader("referer"));
        String previousUrl = referer.getPath().substring(request.getContextPath().length());
        

        // Test for NULL values
        try {
            userType = request.getParameter("userType");
            id = request.getParameter("id");
            password = request.getParameter("password");
            email = request.getParameter("email");
        } catch (NullPointerException ex) {
            //If the values are null
            request.setAttribute("errorMsg", "Oops! It seems that your values are empty. Ensure that all fields are filled.");
            request.getRequestDispatcher(previousUrl).forward(request, response);
        }

        if (userType == null) {
            //If the values are null
            System.out.println("userType is null");
            request.setAttribute("errorMsg", "Oops! Registration failed. Please try again without repeatedly refreshing the page.");
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }

        // If entered from student registration form
        if (userType.equalsIgnoreCase("student")) {

            try {

                Schoolsystemstudent ss = new Schoolsystemstudent();
                 utx.begin();

                //Searches the "external database" School System for an enrolled student with the given ID.
                // TypedQuery<Schoolsystemstudent> query = em.createQuery("SELECT ss FROM Schoolsystemstudent ss WHERE ss.studentid = :studentid and ss.isenrolled = true", Schoolsystemstudent.class).setParameter("studentid", request.getParameter("studentId"));
                //ss = query.getSingleResult();   
                // Get existing data from School System dummy table
                ss = em.find(Schoolsystemstudent.class, id);

                // If student does not exist
                if (ss == null) {
                    System.out.println("ERROR! No student found!");
                    request.setAttribute("errorMsg", "Oops! This student does not exist at the school. Are you sure that the ID is correct?");
                    request.getRequestDispatcher(previousUrl).forward(request, response);
                } else if (!ss.getIsenrolled()) {  // If student is no longer enrolled
                    System.out.println("ERROR! The student is no longer enrolled.");
                    request.setAttribute("errorMsg", "Oops! This student is no longer enrolled. We only accept enrolled students.");
                    request.getRequestDispatcher(previousUrl).forward(request, response);

                } else {
                    //Transfer existing student details into the new account
                    Student stud = new Student();
                    stud.setStudentid(id);
                    stud.setFirstname(ss.getFirstname());
                    stud.setLastname(ss.getLastname());
                    stud.setEmail(email); //Email can be student's personal email
                    stud.setGender(ss.getGender());
                    stud.setMykad(ss.getMykad());

                    //Set fixed values
                    stud.setCredits(1000);

                    //Hash the password and store the salt 
                    Hasher hasher = new Hasher(request.getParameter("password"));
                    stud.setPassword(hasher.getHashedPassword());
                    stud.setPasswordsalt(hasher.getSalt());

                   
                    em.persist(stud);
                    utx.commit();
                }

            } catch (Exception  ex) {
                System.out.println("ERROR: Unable to create student object: " + ex.getMessage());
                // This will be triggered if something went wrong.
                request.setAttribute("errorMsg", "Oops! We couldn't register you for some reason.");
                request.getRequestDispatcher("studentRegistration.jsp").forward(request, response);
            }
        } else {
            try {
                Staff staff = new Staff();
                utx.begin();

                // Search for existing staff
                staff = em.find(Staff.class, request.getParameter("staffid"));

                // If there's an error, provide appropriate error messages
                if (staff.getStaffid() != null) // If staff ID already exists
                {
                    System.out.println("ERROR! No staff found!");
                } else {
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
