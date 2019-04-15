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
import javax.persistence.NoResultException;
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
import util.Email;
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
        String previousUrl = "";

        try {
            URL referer = new URL(request.getHeader("referer"));
            previousUrl = referer.getPath().substring(request.getContextPath().length());
        } catch (Exception ex) {
            request.setAttribute("errorMsg", "Oops! Please don't access that page directly.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

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
            return;
        }

        if (userType == null) {
            //If the values are null
            System.out.println("userType is null");
            request.setAttribute("errorMsg", "Oops! Registration failed. Please try again without repeatedly refreshing the page.");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }

        //Verify email of BOTH tables.
        // Find existing student details to prevent duplicate emails
        TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE  s.email = :email", Student.class).setParameter("email", email);
        Student studChecking = new Student();

        boolean exists = true;
        try {
            studChecking = query.getSingleResult();
        } catch (NoResultException ex) {
            // If null, means no existing user
            System.out.println("No result. This is unique");
            exists = false;
        }

        if (exists) {
            if (userType.equalsIgnoreCase("staff")) {
                request.setAttribute("errorMsg", "Hey! You entered a student's email. You can't use that.");
                request.getRequestDispatcher("staffRegistration.jsp").forward(request, response);
                return;
            } else if (userType.equalsIgnoreCase("student")) {
                request.setAttribute("errorMsg", "Hold on! You have already registered. Please login!");
                request.getRequestDispatcher("studentRegistration.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("errorMsg", "Unknown registration detected.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        }

        // Find existing staff details to prevent duplicate records
        TypedQuery<Staff> staffQuery = em.createQuery("SELECT s FROM Staff s WHERE s.email = :email", Staff.class).setParameter("email", email);
        Staff staffChecking = new Staff();

        exists = true;
        try {
            staffChecking = staffQuery.getSingleResult();
        } catch (NoResultException ex) {
            // If null, means no existing user
            exists = false;
        }

        if (exists) {
            if (userType.equalsIgnoreCase("staff")) {
                request.setAttribute("errorMsg", "Hold on! This staff has already been registered. Please login!");
                request.getRequestDispatcher("staffRegistration.jsp").forward(request, response);
                return;
            } else if (userType.equalsIgnoreCase("student")) {
                request.setAttribute("errorMsg", "Sorry, that email is already taken..");
                request.getRequestDispatcher("studentRegistration.jsp").forward(request, response);
                return;
            } else {
                request.setAttribute("errorMsg", "Unknown registration detected.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        }

        // If entered from student registration form
        if (userType.equalsIgnoreCase("student")) {

            try {

                Schoolsystemstudent ss = new Schoolsystemstudent();

                //Searches the "external database" School System for an enrolled student with the given ID.
                // TypedQuery<Schoolsystemstudent> query = em.createQuery("SELECT ss FROM Schoolsystemstudent ss WHERE ss.studentid = :studentid and ss.isenrolled = true", Schoolsystemstudent.class).setParameter("studentid", request.getParameter("studentId"));
                //ss = query.getSingleResult();   
                // Get existing data from School System dummy table
                ss = em.find(Schoolsystemstudent.class, id);

                // If student does not exist
                if (ss == null) {
                    System.out.println("ERROR! No student found!");
                    request.setAttribute("errorMsg", "Oops! This student does not exist at the school. Are you sure that the ID is correct?");
                    request.getRequestDispatcher("studentRegistration.jsp").forward(request, response);
                    return;
                } else if (!ss.getIsenrolled()) {  // If student is no longer enrolled
                    System.out.println("ERROR! The student is no longer enrolled.");
                    request.setAttribute("errorMsg", "Oops! This student is no longer enrolled. We only accept enrolled students.");
                    request.getRequestDispatcher("studentRegistration.jsp").forward(request, response);
                    return;

                } else {
                    // Find existing student details to prevent duplicate records
                    if (em.find(Student.class, id) != null) {
                        request.setAttribute("errorMsg", "Hold on! You have already registered. Please login!");
                        request.getRequestDispatcher(previousUrl).forward(request, response);
                        return;
                    }

                    //Transfer existing student details into the new account
                    Student stud = new Student();
                    stud.setStudentid(id);
                    stud.setFirstname(ss.getFirstname());
                    stud.setLastname(ss.getLastname());
                    stud.setEmail(email); //Email can be student's personal email
                    stud.setGender(ss.getGender());
                    stud.setMykad(ss.getMykad());
                    stud.setDatejoined(Auto.getToday());

                    //Set fixed values
                    stud.setCredits(1000);

                    //Hash the password and store the salt 
                    Hasher hasher = new Hasher(request.getParameter("password"));
                    stud.setPassword(hasher.getHashedPassword());
                    stud.setPasswordsalt(hasher.getSalt());

                    // Insert the student object
                    utx.begin();
                    em.persist(stud);
                    utx.commit();
                    
                    // Email the student
                    Email notifyEmail = new Email(stud.getEmail(), "Welcome to RealFood!", "Hey " + stud.getFirstname() + ", welcome to RealFood Canteen! We hope that you'll have a good (and delicious) time with us! Here's 1000 credits to get you started.");
                    notifyEmail.sendEmail();
                    
                    //Login successful message
                    request.setAttribute("accountMsg", "Your registration is successful! You may login now.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

            } catch (Exception ex) {
                System.out.println("ERROR: Unable to create student object: " + ex.getMessage());
                // This will be triggered if something went wrong.
                request.setAttribute("errorMsg", "Oops! We couldn't register you for some reason.");
                request.getRequestDispatcher("studentRegistration.jsp").forward(request, response);
                ex.printStackTrace();
                return;
            }
        } else {
            try {
                Staff staff = new Staff();

                staff = new Staff();

                //Generate ID
                staffQuery = em.createQuery("SELECT s FROM Staff s", Staff.class);
                int count = staffQuery.getResultList().size();

                //Transfer existing student details into the new account
                staff.setStaffid(Auto.generateID("EMP", 8, count));    // Set staff ID
                staff.setFirstname(request.getParameter("fname"));
                staff.setLastname(request.getParameter("lname"));
                staff.setEmail(request.getParameter("email")); //Email can be student's personal email
                staff.setGender(request.getParameter("gender").charAt(0));
                staff.setMykad(request.getParameter("myKad"));
                staff.setIshired(true);

                // Set fixed values
                staff.setDatejoined(Auto.getToday());
                
                staff.setStaffrole("canteenStaff");     // This is because there is only 1 manager account (admin account).

                //Hash the password and store the salt 
                Hasher hasher = new Hasher(request.getParameter("password"));
                staff.setPassword(hasher.getHashedPassword());
                staff.setPasswordsalt(hasher.getSalt());

                //Insert the staff object
                utx.begin();
                em.persist(staff);
                utx.commit();
                
                // Email the student
                    Email notifyEmail = new Email(staff.getEmail(), "Welcome to RealFood!", "Hey " + staff.getFirstname() + ", you have been registered as a Canteen Staff. Welcome to RealFood Canteen! We hope that you'll have a good time working with us.");
                    notifyEmail.sendEmail();

                //Login successful message
                request.setAttribute("accountMsg", "Staff registration is successful! The staff may login now.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR: Unable to create staff object: " + ex.getMessage());
                request.setAttribute("errorMsg", "Oops! We couldn't register the staff for some reason.");
                request.getRequestDispatcher("staffRegistration.jsp").forward(request, response);
                return;
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
