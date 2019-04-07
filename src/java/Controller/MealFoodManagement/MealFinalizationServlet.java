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
@WebServlet(name = "MealFinalizationServlet", urlPatterns = {"/MealFinalizationServlet"})
public class MealFinalizationServlet extends HttpServlet {

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
                    request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                    return;
                }

                // CHECK FOR DUPLICATE MEAL NAMES
                Meal mealChecking = new Meal();
                boolean existsAlready = true;
                utx.begin();
                TypedQuery<Meal> query = em.createQuery("SELECT m FROM Meal m WHERE m.mealname = :mealname", Meal.class).setParameter("mealname", mealName.trim()); // Query for getting meal with the same name

                try {
                    mealChecking = query.getSingleResult();
                } catch (NoResultException ex) {
                    existsAlready = false;
                }
                // Checks for meal with the same name; if there is, show an error to the user
                if (existsAlready) {
                    request.setAttribute("errorMsg", "Oops! A meal with the same name has already been added.");
                    request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                    return;
                } else {

                    // Ensure that meal time is filled in
                    try{
                    if (mealTime.length < 1) {
                        System.out.println("ERROR: Mealtime has not been selected: ");
                        request.setAttribute("errorMsg", "Please select a time for the meal (breakfast and/or lunch).");
                        request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
                        return;
                    }
                    }
                    catch(NullPointerException ex){
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

                    //Generate ID
                    query = em.createQuery("SELECT m FROM Meal m", Meal.class);
                    int count = query.getResultList().size();
                    meal.setMealid(Auto.generateID("M", 10, count));    // Set meal ID

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
                    
                    // Get record count
                    TypedQuery<Mealfood> mfQuery = em.createQuery("SELECT mf FROM Mealfood mf", Mealfood.class); // Get total records count
                     count = mfQuery.getResultList().size(); // Set count

                    // Generate ID for the child objects
                    for (int i = 0; i <  meal.getMealfoodList().size(); i++) {
                    
                    meal.getMealfoodList().get(i).setMealfoodid(Auto.generateID("MF", 12, count));    // Set ID inside each child object of the meal
                    meal.getMealfoodList().get(i).setMealid(meal);
                    count++;
                    }
                    
                    //Persist meal object
                    em.persist(meal);
                    utx.commit();

                    //Next step's page
                    System.out.println("Success! Meal with ID " + meal.getMealid() + "  is added.");
                    response.sendRedirect("ManageMealsServlet");
                    return;
                }
                
            } catch (ConstraintViolationException e) {
                System.out.println(e.getConstraintViolations());
            } catch (Exception ex) {
                System.out.println("ERROR: Could not finalize meal: " + ex.getMessage());
                request.setAttribute("errorMsg", "Oops! Food quantity did not succeed for some reason.");
                request.getRequestDispatcher("DisplayFoodSelectionServlet").forward(request, response);
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
