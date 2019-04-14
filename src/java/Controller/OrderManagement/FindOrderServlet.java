/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.OrderManagement;

import Controller.MealManagement.*;
import Model.Meal;
import Model.Mealfood;
import Model.Studentorder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author mast3
 */
@WebServlet(name = "FindOrderServlet", urlPatterns = {"/FindOrderServlet"})
public class FindOrderServlet extends HttpServlet {

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

        
        // Allow student only
        if (!permission.equalsIgnoreCase("canteenStaff")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            boolean justAccessed = true;
            //Displays messages if any
            try {
                request.setAttribute("successMsg", request.getAttribute("successMsg"));
                request.setAttribute("errorMsg", request.getAttribute("errorMsg"));
                
                if(request.getAttribute("successMsg") != null)
                    justAccessed = false;
            } catch (Exception ex) {
                // No messages is fine.
                
            }

            Studentorder so = new Studentorder();
            String couponCode = "";

            try {
                couponCode = request.getParameter("couponCode");
                TypedQuery<Studentorder> query = em.createQuery("SELECT so FROM Studentorder so WHERE so.couponcode = :couponCode", Studentorder.class).setParameter("couponCode", couponCode);
                so = query.getSingleResult();
            } catch (Exception e) {
                // If exception is thrown, just show error message to staff 
                request.setAttribute("errorMsg", "This coupon code is invalid.");
                request.getRequestDispatcher("redeemOrder.jsp").forward(request, response);
                return;
            }

            try {
                
                if(so.getIscanceled()){
                    request.setAttribute("errorMsg", "This order has already been canceled.");
                request.getRequestDispatcher("redeemOrder.jsp").forward(request, response);
                return;
                }
                
                if(so.getIsredeemed() && justAccessed){
                    request.setAttribute("errorMsg", "This order has already been redeemed.");
                request.getRequestDispatcher("redeemOrder.jsp").forward(request, response);
                return;
                }
                
                // Check for expiry
                Calendar cal = Calendar.getInstance();
                cal.setTime(so.getChosendate());
                Calendar today = Calendar.getInstance();
                
                 if(today.get(Calendar.DATE) > cal.get(Calendar.DATE)){
                    request.setAttribute("errorMsg", "This order has already expired.");
                request.getRequestDispatcher("redeemOrder.jsp").forward(request, response);
                return;
                }
                

                // Set studentOrder to session so it can be displayed
                session.setAttribute("studentOrderRedeem", so);
                
                request.getRequestDispatcher("redeemMeals.jsp").forward(request, response);
                return;

            } catch (Exception e) {
                System.out.println("Could not obtain order list: " + e.getMessage());
                e.printStackTrace();
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
