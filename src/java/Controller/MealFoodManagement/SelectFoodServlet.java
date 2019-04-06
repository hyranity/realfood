/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealFoodManagement;

import Model.Food;
import Model.Meal;
import Model.Mealfood;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@WebServlet(name = "SelectFoodServlet", urlPatterns = {"/SelectFoodServlet"})
public class SelectFoodServlet extends HttpServlet {
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
        String previousUrl = "";

        try {
            permission = (String) session.getAttribute("permission");
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
        }

        System.out.println("HeyLOL");
        try {
            previousUrl = request.getHeader("referer");
            if (previousUrl.equalsIgnoreCase("foodSelectionForMeal.jsp")) {
                response.sendRedirect("dashboardCanteenStaff");
                return;
            }
        } catch (Exception ex) {
            request.setAttribute("errorMsg", "Oops! Please don't access that page directly.");
            request.getRequestDispatcher("dashboardCanteenStaff.jsp").forward(request, response);
            return;
        }

        // Get array of food IDs from form
        String[] componentId = request.getParameterValues("componentId");

        // If the parameter's values are null, then it means the user typed in this servlet's URL instead of following the steps. 
        //Hence, redirect to first page.
        if (componentId == null) {
            response.sendRedirect("foodSelectionForMeal.jsp");
            return;
        }

        //Values
        Meal meal = new Meal(); // This is the meal object
        List<Mealfood> mealFoodList = new ArrayList(); // List of associative entities. Each meal component belongs to 1

        //STEP 1 - SELECT MEAL COMPONENTS (FOOD)
        try {

            utx.begin();

            for (int i = 0; i < componentId.length; i++) {
                //Obtain each food using the foodID from the array.
                Food food = em.find(Food.class, componentId[i]);

                // Store the obtained food object into mealFoodList
                Mealfood mf = new Mealfood();
                mf.setFoodid(food);
                mealFoodList.add(mf);
            }

            utx.commit();

            //Save into session first
            meal.setMealfoodList(mealFoodList);
            session.setAttribute("mealFoodList", mealFoodList);

            //Update step status
            session.setAttribute("step", "stepTwo");

            //Print the chosen food for next page
            for(Mealfood mf : mealFoodList){
                System.out.println(mf.getFoodid().getFoodname());
            }
            
            //Next step's page
            response.sendRedirect("foodQuantity.jsp");

            // END OF STEP 1
        } catch (Exception ex) {
            System.out.println("ERROR: Could not add food into foodList: " + ex.getMessage());
            request.setAttribute("errorMsg", "Oops! Your food selection failed for some reason.");
            request.getRequestDispatcher("foodSelection.jsp").forward(request, response);
            return;
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
