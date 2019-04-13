/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.UserAccountManagemnet;

import Model.Staff;
import Model.Student;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import util.*;

/**
 *
 * @author Johann
 */
@WebServlet(name = "GenerateResetToken", urlPatterns = {"/GenerateResetToken"})
public class GenerateResetToken extends HttpServlet {

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

        try {
            // Create session
            HttpSession session = request.getSession(true);

            //Generate random token
            CodeGenerator cg = new CodeGenerator();
            String token = cg.generateCode(50);

            // Store token in session
            session.setAttribute("token", token);

            // Create the email
            String title = "RealFood Account Password Reset";
            String body = "Hey there! Please click on this link to reset your password: http://localhost:8080/RealFood/forgetPassword.jsp?inputToken=" + token;
            String recipient = request.getParameter("recipientEmail");

            // Check if the staff with this email exists or not
            TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.email = :email", Staff.class).setParameter("email", recipient);
            Staff staffChecking = new Staff();

            boolean exists = true;
            try {
                staffChecking = query.getSingleResult();
            } catch (NoResultException ex) {
                // If null, means no existing user
                System.out.println("No staff found with this email");
                exists = false;
            }

            if (exists) {
                session.setAttribute("user", staffChecking);
                session.setAttribute("type", "staff");
            }

            if (!exists) {
                // Check if the user with this email exists or not
                TypedQuery<Student> stuQuery = em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class).setParameter("email", recipient);

                Student studChecking = new Student();

                exists = true;
                try {
                    studChecking = stuQuery.getSingleResult();
                } catch (NoResultException ex) {
                    // If null, means no existing user
                    System.out.println("No student found with this email. ERROR!");
                    exists = false;
                }

                if (exists) {
                    session.setAttribute("user", studChecking);
                    session.setAttribute("type", "student");
                }

                if (!exists) {
                    request.setAttribute("errorMsg", "Oops! This email does not exist here.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            }

            Email email = new Email(recipient, title, body);
            email.sendEmail();

            request.setAttribute("successMsg", "Email with the link has been sent to you.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;

        } catch (Exception ex) {
            request.setAttribute("errorMsg", "Hmm, it seems like we can't send you the link. Is your email correct?");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
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
