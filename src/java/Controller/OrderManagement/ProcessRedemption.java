/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.OrderManagement;

import Controller.MealManagement.*;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
import util.Auto;

/**
 *
 * @author mast3
 */
@WebServlet(name = "ProcessRedemption", urlPatterns = {"/ProcessRedemption"})
public class ProcessRedemption extends HttpServlet {

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
        String[] chosenMeals;
        Student student = new Student();
        try {
            permission = (String) session.getAttribute("permission");
            student = (Student) session.getAttribute("stud");
            
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
            
            Studentorder so = new Studentorder();
               try {
                so = (Studentorder) session.getAttribute("studentOrderRedeem");
            } catch (Exception ex) {
                response.sendRedirect("redeemOrder.jsp");
            }
                

            try{
               chosenMeals = request.getParameterValues("chosenMeals");
               boolean hasAnyUnredeemed = false;
                System.out.println(chosenMeals[0]);
               // Loop through every selected meal
                for (int i = 0; i < chosenMeals.length; i++) {
                    
                    // Loop through the order list of meals
                    for(Ordermeal om : so.getOrdermealList()){
                        
                        // If meal ID match
                        if(om.getOrdermealid().equals(Integer.parseInt(chosenMeals[i]))){
                           om.setIsredeemed(true);
                            System.out.println(om.getMealid() + " is redeemed.");
                        }
                        
                        if(!om.getIsredeemed())
                            hasAnyUnredeemed = true;
                    }
                }
                
                // If no unredeemed left, then redeem the whole order
                if(!hasAnyUnredeemed)
                    so.setIsredeemed(true);
                
                utx.begin();
                em.merge(so);
                utx.commit();
                
                //clear session
                session.setAttribute("studentOrderRedeem", null);
                
                request.setAttribute("successMsg", "Meals successfully redeemed.");
                request.getRequestDispatcher("FindOrderServlet?couponCode=" + so.getCouponcode()).forward(request, response);
                return;
                
            } catch (ConstraintViolationException e) {
                System.out.println(e.getConstraintViolations());
                request.setAttribute("errorMsg", "Oops! We could not redeem the meal(s) for some reason.");
               request.getRequestDispatcher("FindOrderServlet?couponCode=" + so.getCouponcode()).forward(request, response);
                e.printStackTrace();
                return;
            } catch (Exception ex) {
                System.out.println("ERROR: Could not redeem meals: " + ex.getMessage());
                request.setAttribute("errorMsg", "Oops! We could not redeem the meal(s) for some reason.");
               request.getRequestDispatcher("FindOrderServlet?couponCode=" + so.getCouponcode()).forward(request, response);
                ex.printStackTrace();
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
