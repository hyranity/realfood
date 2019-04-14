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
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import util.*;

/**
 *
 * @author mast3
 */
@WebServlet(name = "MealQuantityEditServlet", urlPatterns = {"/MealQuantityEditServlet"})
public class MealQuantityEditServlet extends HttpServlet {
    
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
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            
            List<Ordermeal> orderMealList = new ArrayList();
             // Verify that the student accessed this properly
            Studentorder studOrder = new Studentorder();
             
            try {
                // Load the student's order and orderMealList from session
                studOrder = (Studentorder) session.getAttribute("studOrderEdit");
                orderMealList = (List<Ordermeal>) session.getAttribute("orderMealList");
            } catch (Exception e) {
                // If any error, means that the steps are not followed correctly
                response.sendRedirect("DisplayOrdersServlet");
            }
            
           
 
            
            // If the parameter's values are null, then it means the user typed in this servlet's URL instead of following the steps. 
            //Hence, redirect to first page.
            if (orderMealList == null) {
                response.sendRedirect("DisplayFoodSelectionServlet");
            }
            
            try{

                int totalPrice = 0;
            for(int i=0; i<orderMealList.size(); i++){
                
                //Get the meal ID from the list
                String mealId = orderMealList.get(i).getMealid().getMealid();
                
                // Using the meal ID, get its respective quantities from the JSP form
                int quantity = Integer.parseInt(request.getParameter(mealId));
                
                // Insert the obtained quantity into the object from the list
                orderMealList.get(i).setQuantity(quantity);
                orderMealList.get(i).setIscanceled(false);
                orderMealList.get(i).setIsredeemed(false);
                orderMealList.get(i).setQuantity(quantity);
                
                totalPrice += quantity * orderMealList.get(i).getMealid().getPrice();
            }
            
            
            studOrder.setTotalprice(totalPrice);
            studOrder.setOrdermealList(orderMealList);
                
            
                for (int i = 0; i < orderMealList.size(); i++) {
                    System.out.println(orderMealList.get(i).getMealid().getMealid() + " x " + orderMealList.get(i).getQuantity());
                }

                //Save into session first
                session.setAttribute("orderMealList", orderMealList);
                session.setAttribute("studOrder", studOrder);
                

                //Update step status
                session.setAttribute("step", "stepThree");

                //Next step's page
                request.getRequestDispatcher("studentOrderUpdateConfirmation.jsp").forward(request, response);
                return;

                // END OF STEP 1
            } catch (Exception ex) {
                System.out.println("ERROR: Could not calculate meal quantity: " + ex.getMessage());
                ex.printStackTrace();
                request.setAttribute("errorMsg", "Oops! Meal quantity did not succeed for some reason.");
                request.getRequestDispatcher("SelectMealEditServlet").forward(request, response);
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
