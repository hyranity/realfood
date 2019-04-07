/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealFoodManagement;

import Model.Meal;
import Model.Mealfood;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import util.Auto;

/**
 *
 * @author mast3
 */
@WebServlet(name = "MealFinalizationServlet", urlPatterns = {"/MealFinalizationServlet"})
public class MealFinalizationServlet extends HttpServlet {

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
            Meal meal = new Meal(); // This is the meal object
            List<Mealfood> mealFoodList = (List<Mealfood>) session.getAttribute("mealFoodList");

            // If the parameter's values are null, then it means the user typed in this servlet's URL instead of following the steps. 
            //Hence, redirect to first page.
            if (mealFoodList == null) {
                response.sendRedirect("DisplayFoodSelectionServlet");
            }

            try {

                //Declare values
                String mealName = null;
                String description = null;
                String priceStr = null;
                String imageLink = null;
                String[] mealTime;

                //Obtain values from form
                try {
                    mealName = request.getParameter("mealName");
                    description = request.getParameter("description");
                    priceStr = request.getParameter("price");
                    mealTime = request.getParameterValues("mealTime");

                } catch (NullPointerException e) {
                    System.out.println("ERROR: Could not obtain values: " + e.getMessage());
                    request.setAttribute("errorMsg", "Make sure that all fields are filled in.");
                    request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                    return;
                }

                // Ensure that meal time is filled in
                if (mealTime.length < 1) {
                    System.out.println("ERROR: Mealtime has not been selected: ");
                    request.setAttribute("errorMsg", "Please select a time for the meal (breakfast and/or lunch).");
                    request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                    return;
                }

                // Convert price to integer
                int price = 0;

                try {
                    price = Integer.parseInt(priceStr);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Price is not numerical: " + e.getMessage());
                    request.setAttribute("errorMsg", "Make sure that price is a number.");
                    request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                }

                // Set values
                meal.setIsdiscontinued(false);
                meal.setMealname(mealName);
                meal.setDescription(description);
                meal.setDateadded(Auto.getToday());
                meal.setMealimagelink(imageLink);
                
                for (int i = 0; i < mealTime.length; i++) {
                    if(mealTime[i].equalsIgnoreCase("breakfast"))   // If chosen meal time is breakfast, set as breakfast
                        meal.setIsbreakfast(true);
                    
                    if(mealTime[i].equalsIgnoreCase("lunch"))   // If chosen meal time is lunch, set as lunch
                        meal.setIslunch(true);
                    
                    if(!mealTime[i].equalsIgnoreCase("lunch") && !mealTime[i].equalsIgnoreCase("breakfast")){   // If chosen meal time is neither breakfast nor lunch (highly unlikely due to validations made)
                        // Return an error since meal time is neither breakfast nor lunch
                        System.out.println("ERROR: Meal time is not breakfast nor lunch. It is: " + mealTime[i]);
                    request.setAttribute("errorMsg", "Please ensure that your meal time is correctly chosen.");
                    request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                    }
                }

                //Save into session first
                meal.setMealfoodList(mealFoodList);
                session.setAttribute("mealFoodList", mealFoodList);
                session.setAttribute("meal", meal);
                

                //Update step status
                session.setAttribute("step", "stepThree");

                //Next step's page
                request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                return;

                // END OF STEP 1
            } catch (Exception ex) {
                System.out.println("ERROR: Could not calculate food quantity: " + ex.getMessage());
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
