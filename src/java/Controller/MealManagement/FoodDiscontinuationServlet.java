/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealManagement;

import Model.Food;
import Model.Meal;
import Model.Mealfood;
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
@WebServlet(name = "FoodDiscontinuationServlet", urlPatterns = {"/FoodDiscontinuationServlet"})
public class FoodDiscontinuationServlet extends HttpServlet {

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
            foodId = request.getParameter("foodId");

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

                // Obtain food object from database
                Food food = em.find(Food.class, foodId);
                System.out.println(food.getFoodid());

                // If the food is currently discontinued
                if (food.getIsdiscontinued()) {
                    // Toggle it
                    food.setIsdiscontinued(false);
                    food.setDatediscontinued(null);
                } else {
                    // If the food is currently not discontinued
                    // Toggle it
                    food.setIsdiscontinued(true);
                   food.setDatediscontinued(Auto.getToday());
                }
                
                // Update the food object
                em.merge(food);
                utx.commit();

                utx.begin();
                // Get related list of Mealfood objects
                TypedQuery<Mealfood> query = em.createQuery("SELECT mf FROM Mealfood mf where mf.foodid = :foodId", Mealfood.class).setParameter("foodId", food);
                List<Mealfood> mealFoodList = query.getResultList();

                for (Mealfood mf : mealFoodList) {
                    // If the mealFood is currently discontinued
                    if (mf.getIsdiscontinued()) {
                        // Toggle it
                        mf.setIsdiscontinued(false);
                    } else {
                        // If the mealFood is currently not discontinued
                        // Toggle it
                        mf.setIsdiscontinued(true);
                    }   
                    
                    
                    //Update the objects
                    em.merge(mf);
                }

                utx.commit();
                
                request.setAttribute("successMsg", "Food has been edited.");
                request.getRequestDispatcher("EditFoodServlet?foodId=" + foodId).forward(request, response);

            } catch (ConstraintViolationException e) {
                System.out.println(e.getConstraintViolations());
            } catch (Exception ex) {
                System.out.println("ERROR: Could not discontinue food: " + ex.getMessage());
                request.setAttribute("errorMsg", "Oops! Food discontinuation did not succeed for some reason.");
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
