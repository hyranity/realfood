/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.UserAccountManagemnet;

import Model.*;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.persistence.*;
import javax.annotation.*;
import javax.servlet.http.HttpSession;
import javax.transaction.*;
import util.Hasher;

/**
 *
 * @author Richard Khoo
 */
@WebServlet(name = "StudentAccountManagement", urlPatterns = {"/StudentAccountManagement"})
public class StudentAccountManagement extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String permission = "";

        try {
            permission = (String) session.getAttribute("permission");

            if (permission == null) {
                request.setAttribute("errorMsg", "Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

        } catch (NullPointerException ex) {
            request.setAttribute("errorMsg", "Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        
        // Allow student only
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            String email = "";
            String password = "";
            String cPassword = "";
            String currentPassword = "";

            try {
                currentPassword = request.getParameter("currentPassword");
            } catch (Exception ex) {
                // Means user entered this servlet incorrectly, hence redirect
                response.sendRedirect("LoadStudentDashboard");
            }

            if (email == null || password == null || cPassword == null || currentPassword == null) {
                //Means user entered this servlet wrongly, hence redirect
                response.sendRedirect("LoadStudentDashboard");
            }

            boolean emailUpdated = false;
            boolean passwordUpdated = false;

            // Get student from session
            Student student = new Student();

            try {
                student = (Student) session.getAttribute("stud");

                if (student == null) {
                    request.setAttribute("errorMsg", "Please login.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

            } catch (Exception ex) {
                // if null, means not logged in
                request.setAttribute("errorMsg", "Please login.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            if (currentPassword == "") {
                request.setAttribute("errorMsg", "Please enter current password to save the changes.");
                request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                return;
            }

            // Ensure that the password is correct first, or else details are not updated
            Hasher checkPass = new Hasher(currentPassword, student.getPasswordsalt());

            // If not same
            if (!checkPass.getHashedPassword().equals(student.getPassword())) {
                request.setAttribute("errorMsg", "You've entered your current password wrongly. Please try again");
                request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                return;
            }

            // Attempts to get email
            try {
                email = request.getParameter("email");

                if (!email.equals(student.getEmail())) {
                    if (email == "") {
                        request.setAttribute("errorMsg", "Please don't leave your email blank.");
                        request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                        return;
                    } else {
                        // Update the email if the field is not empty
                        student.setEmail(email);
                        emailUpdated = true;
                    }
                }

            } catch (Exception ex) {
                // If not updated its okay, since the user didn't edit anything
            }

            // Attemps to get password
            try {
                password = request.getParameter("password");
                cPassword = request.getParameter("cPassword");
                
                 if(password != "" && cPassword != ""){

                    if (!cPassword.equals(password)) {
                        // If password entered is not the same
                        request.setAttribute("errorMsg", "Your new password and the confirmation do not match.");
                        request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                        return;
                    }

                    Hasher hasher = new Hasher(password);
                    student.setPassword(hasher.getHashedPassword());
                    student.setPasswordsalt(hasher.getSalt());
                    passwordUpdated = true;
                    }
                 else if (cPassword == "" && password == "") {
                    
                   // Nothing happens if both are empty
      
                }
                 else{
                     if (cPassword == "" && password == "") {
                        request.setAttribute("errorMsg", "To update your password, fill in all password fields.");
                        request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                        return;
                    }
                    
                    if (cPassword == "") {
                        request.setAttribute("errorMsg", "Please enter confirmation password.");
                        request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                        return;
                    }
                    
                    if (password == "") {
                        request.setAttribute("errorMsg", "Please enter new password if you wish to change it.");
                        request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                        return;
                    }
                 }
                
                

            } catch (Exception ex) {
                // If not updated its okay, since the user didn't edit anything
            }

            try {

                // Save into database
                utx.begin();
                em.merge(student);
                utx.commit();

                // Update the one in session
                session.setAttribute("stud", student);

                String successMsg = "";
                if (emailUpdated && passwordUpdated) {
                    successMsg = "Your email and password has been updated.";
                } else if (passwordUpdated) {
                    successMsg = "Your password has been updated.";
                } else if (emailUpdated) {
                    successMsg = "Your email has been updated.";
                } else {
                    successMsg = "Nothing has been updated.";

                }

                // Notify student
                request.setAttribute("successMsg", successMsg);
                request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                return;
            } catch (Exception ex) {

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
