/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealManagement;

import Model.Food;
import Model.Food;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "ProcessFoodEditServlet", urlPatterns = {"/ProcessFoodEditServlet"})
public class ProcessFoodEditServlet extends HttpServlet {

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
        String foodId = "";
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

        // If user is not logged in, redirect to login page
        // Allow staff only
        if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {

            try {
                utx.begin();
                String foodName = request.getParameter("foodName");
                int calories = Integer.parseInt(request.getParameter("calories"));
                foodId = request.getParameter("foodId");
                
                if(foodId == null){
                    // If user attempts to access this page indirectly
                    response.sendRedirect("dashboardCanteenStaff.jsp");
                }
                
                // Load the food object
                Food food = em.find(Food.class, foodId);
                
                // CHECK FOR DUPLICATE FOOD NAMES
                Food foodChecking = new Food();
                boolean existsAlready = true;
                
                TypedQuery<Food> query = em.createQuery("SELECT f FROM Food f WHERE f.foodname = :foodName", Food.class).setParameter("foodName", foodName.trim()); // Query for getting food with the same name
                
                try {
                    foodChecking = query.getSingleResult();
                    
                    // If the current food has the same ID as the one in the database, it's the same one.
                    if(foodChecking.getFoodid().equals(food.getFoodid()))
                        existsAlready = false;
                    
                } catch (NoResultException ex) {
                    existsAlready = false;
                }
                
                utx.commit();
                
                // Checks for food with the same name; if there is, show an error to the user
                if (existsAlready) {
                request.setAttribute("errorMsg", "Oops! There's already a food with that name.");
                request.getRequestDispatcher("EditFoodServlet?foodId=" + foodId).forward(request, response);
                return;
                } else {
                    
                    //Update the food details
                    food.setFoodname(foodName);
                    food.setCalories(calories);
                    
                    //Update the database
                    utx.begin();
                    em.merge(food);
                    utx.commit();
                        

                    request.setAttribute("successMsg", "Food has been edited.");
                    request.getRequestDispatcher("EditFoodServlet?foodId=" + foodId).forward(request, response);
                    return;
                }

            } catch (ConstraintViolationException e) {
                System.out.println(e.getConstraintViolations());
            } catch (Exception ex) {
                System.out.println("ERROR: Could not update food: " + ex.getMessage());
                request.setAttribute("errorMsg", "Oops! Food update did not succeed for some reason.");
                request.getRequestDispatcher("EditFoodServlet?foodId=" + foodId).forward(request, response);
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
