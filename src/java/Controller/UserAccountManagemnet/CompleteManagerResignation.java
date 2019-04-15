/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.UserAccountManagemnet;

import Model.Staff;
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
import javax.validation.ConstraintViolationException;
import util.*;

/**
 *
 * IMPORTANT: This is to be run when admin account is to be set to default
 * settings (eg. when manager is changed)
 */
@WebServlet(name = "CompleteManagerResignation", urlPatterns = {"/CompleteManagerResignation"})
public class CompleteManagerResignation extends HttpServlet {

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

        // Allow manager only
        if (!permission.equals("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            try {

                AdminReset ar = new AdminReset(em, utx);
                Staff manager = new Staff();
                boolean exists = false;

                // Obtain any hired manager account
                try {
                    TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.staffrole = :role and s.ishired = true", Staff.class).setParameter("role", "manager");
                    manager = query.getSingleResult();

                    if (manager.getIshired()) {
                        request.setAttribute("successMsg", "You cannot create a new manager account if there's already an active one.");
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        return;
                    }

                } catch (NoResultException e) {
                    // If null, this error is okay because it means there's no existing manager (admin) account. Program will proceed to create the account.
                    System.out.println("ERROR: Unable to get admin account: " + e.getMessage());

                }
                utx.begin();
                    em.persist(ar.createAdminAccount());
                utx.commit();

                //clear staff in session
                session.setAttribute("staffForEdit", null);
                session.setAttribute("staff", null);

                request.setAttribute("successMsg", "Manager resignation complete.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
                
                } catch (ConstraintViolationException ex) {
                System.out.println(ex.getConstraintViolations());
            }
            
        catch (Exception ex) {
                System.out.println("Unable to resolve admin account: " + ex.getMessage());
                ex.printStackTrace();
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
