/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import util.*;

/**
 *
 * IMPORTANT: This is to be run when admin account is to be set to default settings (eg. when manager is changed)
 */
@WebServlet(name = "AdminResetServlet", urlPatterns = {"/AdminResetServlet"})
public class AdminResetServlet extends HttpServlet {

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
            utx.begin();
            AdminReset ar = new AdminReset();
            Staff manager = new Staff();

            try {
                TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.staffid = :staffid and s.staffrole = :role", Staff.class).setParameter("staffid", "EMPMAN").setParameter("role", "manager");
                manager = query.getSingleResult();
                
                
            } catch (Exception e) {
                // If null, this error is okay because it means there's no existing manager (admin) account. Program will proceed to create the account.
                System.out.println("ERROR: Unable to get admin account: " + e.getMessage());
                
            }

            if (manager == null) {
                System.out.println("NO ACCOUNT DETECTED: Proceeding make new one.");
                em.persist(ar.createAdminAccount());

            } else {
                System.out.println("EXISTING ACCOUNT DETECTED: Proceeding to reset it.");
                em.merge(ar.createAdminAccount());
            }

            utx.commit();
        } catch (Exception ex) {
            System.out.println("Unable to resolve admin account: " + ex.getMessage());
            ex.printStackTrace();
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
