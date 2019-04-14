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
import javax.persistence.Query;
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
@WebServlet(name = "MealDetailsEditServlet", urlPatterns = {"/MealDetailsEditServlet"})
public class MealDetailsEditServlet extends HttpServlet {

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

        
        // Allow staff only
        if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {

            //Values
            Meal meal = (Meal) session.getAttribute("meal"); // This is the meal object

            // If the parameter's values are null, then it means the user typed in this servlet's URL instead of following the steps. 
            //Hence, redirect to first page.
            if (meal == null) {
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
                    imageLink = request.getParameter("imageLink");

                } catch (NullPointerException e) {
                    System.out.println("ERROR: Could not obtain values: " + e.getMessage());
                    request.setAttribute("errorMsg", "Make sure that all fields are filled in.");
                    request.getRequestDispatcher("mealDetailsEdit.jsp").forward(request, response);
                    return;
                }

                // CHECK FOR DUPLICATE MEAL NAMES
                Meal mealChecking = new Meal();
                boolean existsAlready = true;
                utx.begin();
                TypedQuery<Meal> query = em.createQuery("SELECT m FROM Meal m WHERE m.mealname = :mealname", Meal.class).setParameter("mealname", mealName.trim()); // Query for getting meal with the same name

                try {
                    mealChecking = query.getSingleResult();
                    if(mealChecking.getMealid().equals(meal.getMealid()))
                        existsAlready = false;
                } catch (NoResultException ex) {
                    existsAlready = false;
                }
                // Checks for meal with the same name; if there is, show an error to the user
                if (existsAlready) {
                    request.setAttribute("errorMsg", "Oops! A meal with the same name already exists.");
                    request.getRequestDispatcher("mealDetailsEdit.jsp").forward(request, response);
                    return;
                } else {

                    // Ensure that meal time is filled in
                    try {
                        if (mealTime.length < 1) {
                            System.out.println("ERROR: Mealtime has not been selected: ");
                            request.setAttribute("errorMsg", "Please select a time for the meal (breakfast and/or lunch).");
                            request.getRequestDispatcher("mealDetailsEdit.jsp").forward(request, response);
                            return;
                        }
                    } catch (NullPointerException ex) {
                        request.setAttribute("errorMsg", "Please select a time for the meal (breakfast and/or lunch).");
                        request.getRequestDispatcher("mealDetailsEdit.jsp").forward(request, response);
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

                    //Generate ID
                    meal.setMealid(request.getParameter("mealId"));
                    System.out.println(imageLink.length());
                    if(imageLink.length()>200){
                        request.setAttribute("errorMsg", "Image URL is too long!");
                        System.out.println("Image URL is too long.");
                        request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                        return;
                    }

                    // Set values
                    meal.setIsdiscontinued(false);
                    meal.setMealname(mealName);
                    meal.setDescription(description);
                    meal.setDateadded(Auto.getToday());
                    meal.setMealimagelink(imageLink);
                    meal.setPrice(price);

                    // Set default boolean values
                    meal.setIslunch(false);
                    meal.setIsbreakfast(false);

                    for (int i = 0; i < mealTime.length; i++) {
                        if (mealTime[i].equalsIgnoreCase("breakfast")) // If chosen meal time is breakfast, set as breakfast
                        {
                            meal.setIsbreakfast(true);
                        }

                        if (mealTime[i].equalsIgnoreCase("lunch")) // If chosen meal time is lunch, set as lunch
                        {
                            meal.setIslunch(true);
                        }

                        if (!mealTime[i].equalsIgnoreCase("lunch") && !mealTime[i].equalsIgnoreCase("breakfast")) {   // If chosen meal time is neither breakfast nor lunch (highly unlikely due to validations made)
                            // Return an error since meal time is neither breakfast nor lunch
                            System.out.println("ERROR: Meal time is not breakfast nor lunch. It is: " + mealTime[i]);
                            request.setAttribute("errorMsg", "Please ensure that your meal time is correctly chosen.");
                            request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                        }

                    }
                    
                    // Delete all the relationships with the current meal
                    Query deleteQuery = em.createQuery("DELETE FROM Mealfood mf WHERE mf.mealid = :mealid").setParameter("mealid", meal);
                    deleteQuery.executeUpdate();
                    System.out.println("Mealfood relationships deleted.");
                    
                    /*
                    Meal existingMeal = em.find(Meal.class, meal.getMealid());
                    
                    // Compare existing meal with updated meal
                     for (int i = 0; i < existingMeal.getMealfoodList().size(); i++) {
                        // Get existing mealfood
                        Mealfood existingMf = existingMeal.getMealfoodList().get(i);
                        
                        // Loop through every mealfood in the updated one
                        for(Mealfood mf : meal.getMealfoodList()){
                            
                            //If new food component already is in the old one, just update the quantity
                            if(existingMf.getFoodid().getFoodid() != mf.getFoodid().getFoodid()){
                                mf.setQuantity();
                            }
                        }
                    }
                    */
                    
                    int count = 0;


                    // Set the meal ID of child objects
                    for (int i = 0; i < meal.getMealfoodList().size(); i++) {
                        meal.getMealfoodList().get(i).setMealid(meal);
                        count++;
                    }

                    //Update meal object
                    em.merge(meal);
                    utx.commit();
                    
                    // Clear meal session
                    session.setAttribute("meal", null);

                    //Next step's page
                    response.sendRedirect("ManageMealsServlet");
                    return;
                }

            } catch (ConstraintViolationException e) {
                System.out.println(e.getConstraintViolations());
            } catch (Exception ex) {
                System.out.println("ERROR: Could not finalize meal: " + ex.getMessage());
                request.setAttribute("errorMsg", "Oops! Meal creation did not succeed for some reason.");
                request.getRequestDispatcher("mealDetailsEdit.jsp").forward(request, response);
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
