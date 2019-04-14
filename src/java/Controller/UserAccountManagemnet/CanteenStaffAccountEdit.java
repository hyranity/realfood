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
@WebServlet(name = "CanteenStaffAccountEdit", urlPatterns = {"/CanteenStaffAccountEdit"})
public class CanteenStaffAccountEdit extends HttpServlet {

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

        
        // Allow staff only
        if (!permission.equalsIgnoreCase("canteenStaff")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            String password = "";
            String cPassword = "";
            String currentPassword = "";

            try {
                currentPassword = request.getParameter("currentPassword");
            } catch (Exception ex) {
                // Means user entered this servlet incorrectly, hence redirect
                response.sendRedirect("dashboardCanteenStaff.jsp");
            }

            if (password == null || cPassword == null || currentPassword == null) {
                //Means user entered this servlet wrongly, hence redirect
                response.sendRedirect("dashboardCanteenStaff.jsp");
            }

            boolean passwordUpdated = false;

            // Get staff from session
            Staff staff = new Staff();

            try {
                staff = (Staff) session.getAttribute("staff");

                if (staff == null) {
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
                request.getRequestDispatcher("staffProfile.jsp").forward(request, response);
                return;
            }

            // Ensure that the password is correct first, or else details are not updated
            Hasher checkPass = new Hasher(currentPassword, staff.getPasswordsalt());

            // If not same
            if (!checkPass.getHashedPassword().equals(staff.getPassword())) {
                request.setAttribute("errorMsg", "You've entered your current password wrongly. Please try again");
                request.getRequestDispatcher("staffProfile.jsp").forward(request, response);
                return;
            }

            // Attemps to get password
            try {
                password = request.getParameter("password");
                cPassword = request.getParameter("cPassword");

                if (password != "" && cPassword != "") {

                    if (!cPassword.equals(password)) {
                        // If password entered is not the same
                        request.setAttribute("errorMsg", "Your new password and the confirmation do not match.");
                        request.getRequestDispatcher("staffProfile.jsp").forward(request, response);
                        return;
                    }

                    Hasher hasher = new Hasher(password);
                    staff.setPassword(hasher.getHashedPassword());
                    staff.setPasswordsalt(hasher.getSalt());
                    passwordUpdated = true;
                } else if (cPassword == "" && password == "") {

                    // Nothing happens if both are empty
                } else {
                    if (cPassword == "" && password == "") {
                        request.setAttribute("errorMsg", "To update your password, fill in all password fields.");
                        request.getRequestDispatcher("staffProfile.jsp").forward(request, response);
                        return;
                    }

                    if (cPassword == "") {
                        request.setAttribute("errorMsg", "Please enter confirmation password.");
                        request.getRequestDispatcher("staffProfile.jsp").forward(request, response);
                        return;
                    }

                    if (password == "") {
                        request.setAttribute("errorMsg", "Please enter new password if you wish to change it.");
                        request.getRequestDispatcher("staffProfile.jsp").forward(request, response);
                        return;
                    }
                }

            } catch (Exception ex) {
                // If not updated its okay, since the user didn't edit anything
            }

            try {

                // Save into database
                utx.begin();
                em.merge(staff);
                utx.commit();

                // Update the one in session
                session.setAttribute("staff", staff);

                String successMsg = "";
                if (passwordUpdated) {
                    successMsg = "Your password has been updated.";
                } else {
                    successMsg = "Nothing has been updated.";
                }

                // Notify staff
                request.setAttribute("successMsg", successMsg);
                request.getRequestDispatcher("staffProfile.jsp").forward(request, response);
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
