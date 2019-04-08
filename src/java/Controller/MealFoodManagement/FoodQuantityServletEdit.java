/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealFoodManagement;

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
@WebServlet(name = "FoodQuantityServletEdit", urlPatterns = {"/FoodQuantityServletEdit"})
public class FoodQuantityServletEdit extends HttpServlet {
    
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
        
        // If user is not logged in, redirect to login page
        if (session.getAttribute("permission") == null) {
            request.setAttribute("errorMsg", "Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            // Allow staff only
            String permission = (String) session.getAttribute("permission");
            if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            //Values
            Meal meal = (Meal) session.getAttribute("meal"); // This is the meal object
           List<Mealfood> mealFoodList = (List<Mealfood>) session.getAttribute("mealFoodList"); // Get from meal
 
            
            // If the parameter's values are null, then it means the user typed in this servlet's URL instead of following the steps. 
            //Hence, redirect to first page.
            if (mealFoodList == null) {
                response.sendRedirect("DisplayFoodSelectionServlet");
            }
            
            try{

                int caloriesSum = 0;
            for(int i=0; i<mealFoodList.size(); i++){
                
                // NOTE: The list will correspond EXACLTY to the one in the form. Eg. Ice cream - ID of F1, Pizza - ID of F2. In the form, it will be Ice cream - ID of F1, quantity of 1, and so on.
                // Using the selected foods' IDs, their quantities (default is 1) are displayed (as values) on quantity JSP with the name of their IDs.
                // <div>Spaghetti, F0001<input value="2" name="F0001"/>
                // In other words, the ID is linked with the quantity when displayed. To update the list object, just 1. get current ID, 2. get the quantity linked with it, 3. update the quantity.
                
                //Get the food ID from the listI
                String foodID = mealFoodList.get(i).getFoodid().getFoodid();
                
                // Using the food ID, get its respective quantities from the JSP form
               
                int quantity = Integer.parseInt(request.getParameter(foodID));
                
                // Insert the obtained quantity into the object from the list
                mealFoodList.get(i).setQuantity(quantity);
                
                caloriesSum += quantity * mealFoodList.get(i).getFoodid().getCalories();
            }
            
            meal.setTotalcalories(caloriesSum);
            
            // Update meal object
            meal.setMealfoodList(mealFoodList);

                //Save into session first
                meal.setMealfoodList(mealFoodList);
                session.setAttribute("mealFoodList", mealFoodList);
                session.setAttribute("meal", meal);

                //Update step status
                session.setAttribute("step", "stepThree");

                //Next step's page
                request.getRequestDispatcher("mealDetailsEdit.jsp").forward(request, response);
                return;

                // END OF STEP 1
            } catch (Exception ex) {
                System.out.println("ERROR: Could not calculate food quantity: " + ex.getMessage());
                ex.printStackTrace();
                request.setAttribute("errorMsg", "Oops! Food quantity did not succeed for some reason.");
                request.getRequestDispatcher("DisplayFoodSelectionServlet").forward(request, response);
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
