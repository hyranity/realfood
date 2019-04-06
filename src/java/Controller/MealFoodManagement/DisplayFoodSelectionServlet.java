/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealFoodManagement;

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
@WebServlet(name = "DisplayFoodSelectionServlet", urlPatterns = {"/DisplayFoodSelectionServlet"})
public class DisplayFoodSelectionServlet extends HttpServlet {

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

            try {
                utx.begin();

                // Get all food
                TypedQuery<Food> query = em.createQuery("SELECT f FROM Food f", Food.class);
                List<Food> foodList = query.getResultList();
                utx.commit();

                String queryResult = "";
                 int fourCount = 0;
                 
                 // Format it for display
                for (int i=0; i<foodList.size(); i++) {
                   
                    
                    Food food = foodList.get(i);
                    
                    if(i!=0 && i % 4 == 0 && i != foodList.size() - 1){
                        queryResult += "<tr>";
                        fourCount++;
                    }
                    
                    
                    
                    String outsideOpen = "<td>";
                    String checkbox = "<input type=\"checkbox\" id=\"" + food.getFoodid() +"\"/>";
                    String labelOpen = "<label for=\"" + food.getFoodid() +"\">";
                    String divOpen = "<div class=\"record\">";
                    String id = "<h6>" + food.getFoodid() +"</h6>";
                    String breaks = "<br/><br/>";
                    String calories = "<p class=\"calories\">" + food.getCalories() +"</p>";
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
                request.getRequestDispatcher("foodSelectionForMeal.jsp").forward(request, response);
                return;
                

            } catch (Exception e) {
                System.out.println("Could not obtain food list: " + e.getMessage());
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
