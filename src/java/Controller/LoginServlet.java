/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import Model.*;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import util.Hasher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mast3
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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

        // Get the required values
        String id = "";
        String password = "";

        try {
            id = request.getParameter("id");
            password = request.getParameter("password");
        } catch (NullPointerException ex) {
            System.out.println("ERROR: Unable to obtain id & password: " + ex.getMessage());
        }
        //If the ID is too short, send an error message
        if (id.length() < 4) {
            System.out.println("ID entered is not recognized. User entered: " + id);    // Do appropriate error messages
            request.setAttribute("errorMsg", "Oops! We don't know what that ID means. Make sure you've entered it correctly.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        String prefix = id.substring(0, 3); //This will either be STU (student login) or EMP (employee login)

        // Create objects
        Student stud = new Student();
        Staff staff = new Staff();

        try {
            utx.begin();

            if (prefix.equalsIgnoreCase("STU")) {                                  // STUDENT  LOGIN ==========================
                // If prefix = STU, look from student table
                stud = em.find(Student.class, id);

                // If there's no student record, return error message
                if (stud == null) {
                    System.out.println("ERROR: Student does not exist.");    // Do appropriate error messages
                    request.setAttribute("errorMsg", "Oops! Seems like you've entered an incorrect student ID or password.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {

                    // Following block of code is to validate student's enrolment status
                    Schoolsystemstudent ss = new Schoolsystemstudent();
                    ss = em.find(Schoolsystemstudent.class, id);
                    if (!ss.getIsenrolled() || ss == null) {
                        System.out.println("ERROR: Student is no longer enrolled or does not exist in School system."); // Do appropriate error messages
                        request.setAttribute("errorMsg", "Oh no, this student is no longer enrolled to this school. We only allow existing students....sorry.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    } else {

                        // Compare password
                        Hasher hasher = new Hasher(password, stud.getPasswordsalt());    // Use the stored student's salt to hash to login password

                        // Compares the hashed password (from login) with hashed password (from DB)
                        if (hasher.getHashedPassword().equals(stud.getPassword())) {

                            //Password is correct, grant access by creating a session by creating a session for STUDENT
                            System.out.println("SUCCESS: Student login is successful.");
                            HttpSession session = request.getSession(true);
                            session.setAttribute("stud", stud);
                            request.getRequestDispatcher("dashboardStudent.jsp").forward(request, response);

                        } else {
                            // Password is not the same; perform error messages
                            System.out.println("ERROR: Incorrect password");
                            request.setAttribute("errorMsg", "Oops! Seems like you've entered an incorrect student ID or password.");
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                        }
                    }
                }
            } else if (prefix.equalsIgnoreCase("EMP")) {                            // STAFF LOGIN ==========================
                // If prefix = EMP, look from staff table
                staff = em.find(Staff.class, id);

                // If there's no staff record, return error message
                if (staff == null) {
                    System.out.println("ERROR: Staff does not exist.");    // Do appropriate error messages
                    request.setAttribute("errorMsg", "Oops! Seems like you've entered an incorrect staff ID or password.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {

                    // Compare password
                    Hasher hasher = new Hasher(password, staff.getPasswordsalt());    // Use the stored student's salt to hash to login password

                    // Compares the hashed password (from login) with hashed password (from DB)
                    if (hasher.getHashedPassword().equals(staff.getPassword())) {

                        //Password is correct, grant access by creating a session for STAFF
                        HttpSession session = request.getSession(true);
                        session.setAttribute("staff", staff);
                        request.getRequestDispatcher("dashboardCanteenStaff.jsp").forward(request, response);

                    } else {
                        // Password is not the same; perform error messages
                        System.out.println("ERROR: Incorrect password");
                        request.setAttribute("errorMsg", "Oops! Seems like you've entered an incorrect staff ID or password.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                    }
                }
            } else {
                // Else, invalid ID or no ID prefix.
                System.out.println("ID entered is not recognized. User entered: " + id);    // Do appropriate error messages
                request.setAttribute("errorMsg", "Oops! That ID is incorrect. Make sure you've entered it correctly.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (NotSupportedException | SystemException ex) {
            System.out.println("ERROR: Unable to retrieve record: " + ex.getMessage());
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
