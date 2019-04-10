/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealManagement;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.util.*;
import Model.*;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mast3
 */
@WebServlet(name = "DisplayFoodSelectionServletForEdit", urlPatterns = {"/DisplayFoodSelectionServletForEdit"})
public class DisplayFoodSelectionServletForEdit extends HttpServlet {

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
        
       String mealId = "";
        
        try {
            permission = (String) session.getAttribute("permission");
            
            // Get chosen meal ID
            mealId = (String) request.getParameter("mealId");
            
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

        // If user is not logged in, redirect to login page
        // Allow staff only
        if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else if(mealId == null){
            // If no meal is selected, redirect to previous page
           response.sendRedirect("ManageMealsServlet");
        }
        else{
            try {
                
                Meal meal = em.find(Meal.class, mealId);
                 
                // Put the meal into the session. This will be used on later steps.
                session.setAttribute("meal", meal);
                
                // Get all food
                TypedQuery<Food> query = em.createQuery("SELECT f FROM Food f", Food.class);
                List<Food> foodList = query.getResultList();
                

                String queryResult = "";
                String checkbox = "";
                 int fourCount = 0;
                 
                 // Format it for display
                for (int i=0; i<foodList.size(); i++) {
                   
                    
                    Food food = foodList.get(i);
                    
                    if(i!=0 && i % 4 == 0 && i != foodList.size() - 1){
                        queryResult += "<tr>";
                        fourCount++;
                    }
                    
                    String outsideOpen = "<td>";
                    
                    boolean isChecked = false;
                    
                    for (int j = 0; j < meal.getMealfoodList().size(); j++) {
                        if(foodList.get(i).getFoodid().equals(meal.getMealfoodList().get(j).getFoodid().getFoodid()))
                            isChecked = true;
                    }
                    
                    // If this food is already part of the meal, keep it checked
                    if(isChecked)
                        checkbox = "<input type=\"checkbox\"  name= \"componentId\" value=\"" + food.getFoodid() +"\" id=\"" + food.getFoodid() +"\" checked/>";
                    else
                        checkbox = "<input type=\"checkbox\"  name= \"componentId\" value=\"" + food.getFoodid() +"\" id=\"" + food.getFoodid() +"\"/>";
                    
                    String labelOpen = "<label for=\"" + food.getFoodid() +"\">";
                    String divOpen = "<div class=\"record\">";
                    String id = "<h6>" + food.getFoodid() +"</h6>";
                    String breaks = "<br/><br/>";
                    String calories = "<p class=\"calories\">" + food.getCalories() +" calories</p>";
                    String name = "<p class=\"name\">" + food.getFoodname() +"</p>";
                    String divClose = "</div>";
                    String labelClose = "</label>";
                    String outsideClose = "</td>";
                    
                    queryResult += outsideOpen + checkbox + labelOpen + divOpen + id + breaks + calories + name + divClose + labelClose + outsideClose;
                    
                    if(fourCount == 4){
                        queryResult += "</tr>";
                        fourCount = 0;
                    }
                }
                
                // Send the formatted list to JSP
                request.setAttribute("queryResult", queryResult);
                request.getRequestDispatcher("foodSelectionEdit.jsp").forward(request, response);
                return;
                

            } catch (Exception e) {
                System.out.println("Could not obtain food list: " + e.getMessage());
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
