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
import javax.validation.ConstraintViolationException;
import util.Hasher;

/**
 *
 * @author Richard Khoo
 */
@WebServlet(name = "CanteenStaffAccountEditByManager", urlPatterns = {"/CanteenStaffAccountEditByManager"})
public class CanteenStaffAccountEditByManager extends HttpServlet {

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
        if (!permission.equalsIgnoreCase("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;

        } else {
            String email = request.getParameter("email");
            String managerPassword = request.getParameter("managerPassword");
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String myKad = request.getParameter("myKAD");
            String inputGender = request.getParameter("gender");
            String successMsg = "";

            try {
                managerPassword = request.getParameter("managerPassword");
            } catch (Exception ex) {
                // Means user entered this servlet incorrectly, hence redirect
                System.out.println("Can't obtain manager password");
                response.sendRedirect("dashboardManager.jsp");
                return;
            }

            if (email == null || fname == null || lname == null || managerPassword == null || myKad == null) {
                //Means user entered this servlet wrongly, hence redirect
                System.out.println("One field is null.");
                response.sendRedirect("dashboardManager.jsp");
                return;
            }

            // Get staff  from session
            Staff staff = new Staff();
            Staff manager = new Staff();

            try {
                staff = (Staff) session.getAttribute("staffForEdit");
                manager = (Staff) session.getAttribute("staff");

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

            if (managerPassword == "") {
                request.setAttribute("errorMsg", "Please enter your password to save the changes.");
                request.getRequestDispatcher("studentProfile.jsp").forward(request, response);
                return;
            }

            // Ensure that the password is correct first, or else details are not updated
            Hasher checkPass = new Hasher(managerPassword, manager.getPasswordsalt());

            // If not same
            if (!checkPass.getHashedPassword().equals(manager.getPassword())) {
                request.setAttribute("errorMsg", "You've entered your password wrongly. Please try again");
                request.getRequestDispatcher("editStaff.jsp").forward(request, response);
                return;
            }
            
             // Update gender if different
            char gender = ' ';
            if (inputGender.equalsIgnoreCase("male") || inputGender.equalsIgnoreCase("m")) {
                gender = 'M';
            } else if (inputGender.equalsIgnoreCase("female") || inputGender.equalsIgnoreCase("f")) {
                gender = 'F';
            } else {
                request.setAttribute("errorMsg", "Sorry, we accept genders to be either Male or Female.");
                request.getRequestDispatcher("editStaff.jsp").forward(request, response);
                return;
            }

            // Update first name if different
            if (!fname.equals(staff.getFirstname())) {
                staff.setFirstname(fname);

                if (successMsg != "") {
                    successMsg += ", ";
                }

                successMsg += "first name";
            }

            // Update last name if different
            if (!lname.equals(staff.getLastname())) {
                staff.setLastname(lname);

                if (successMsg != "") {
                    successMsg += ", ";
                }

                successMsg += "last name";
            }

            if (gender != staff.getGender()) {
                staff.setGender(gender);

                if (successMsg != "") {
                    successMsg += ", ";
                }

                successMsg += "gender";
            }

            // Update email if different
            if (!email.equals(staff.getEmail())) {
                staff.setEmail(email);

                if (successMsg != "") {
                    successMsg += ", ";
                }

                successMsg += "email";
            }

            // Update myKad if different
            if (!myKad.equals(staff.getMykad())) {
                staff.setMykad(myKad);

                if (successMsg != "") {
                    successMsg += ", ";
                }

                successMsg += "MyKAD";
            }

            try {

                // Save into database
                utx.begin();
                em.merge(staff);
                utx.commit();

                // Update the one in session
                session.setAttribute("staffForEdit", staff);

                if (successMsg == "") {
                    successMsg = "Nothing has been updated";
                } else {
                    successMsg = "The following has been updated: " + successMsg;
                }

                // Notify student
                request.setAttribute("successMsg", successMsg);
                request.getRequestDispatcher("editStaff.jsp").forward(request, response);
                return;
            } catch (ConstraintViolationException ex) {
                System.out.println(ex.getConstraintViolations());
                request.setAttribute("errorMsg", "Oops, seems we can't update the details for some reason");
                request.getRequestDispatcher("editStaff.jsp").forward(request, response);
                return;

            } catch (Exception ex) {
                request.setAttribute("errorMsg", "Oops, seems we can't update the details for some reason");
                request.getRequestDispatcher("editStaff.jsp").forward(request, response);
                return;
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
