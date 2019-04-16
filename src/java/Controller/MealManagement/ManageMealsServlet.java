/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealManagement;

import Model.Meal;
import Model.Mealfood;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author mast3
 */
public class ManageMealsServlet extends HttpServlet {
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

        
        // Allow staff only
        if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            
            // Get any error messages from the later pages
            try {
                String errorMsg = request.getParameter("errorMsg");
                
                if(errorMsg != null){
                    request.setAttribute("errorMsg", "Sorry, it seems like there's no food for you to add to a meal.");
                    request.getRequestDispatcher("manageMeals.jsp").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                
            }

            try {
                
                // Get all meal
                TypedQuery<Meal> query =  em.createQuery("SELECT m FROM Meal m", Meal.class);
                List<Meal> mealList = query.getResultList();

                String queryResult = "";
                String mealTime = "";
                String mealTimeClass = "";
                String components ="";
                int fourCount = 2;

                // Format it for display
                for (int i = 0; i < mealList.size(); i++) {
                    components = "";
                    Meal meal = mealList.get(i);
                    
                    // Create the food components string
                    for(int j=0; j< meal.getMealfoodList().size(); j++){
                        Mealfood mf = meal.getMealfoodList().get(j);
                        
                        // If the current iteration is more than the first one, put comma.
                        if(j>0)
                            components += ", ";
                        
                        components += mf.getFoodid().getFoodname();
                    }

                    // Formatting when to show on new row
                    if (i != 0 && i % 4 == 0 && i != mealList.size() - 1) {
                        queryResult += "<tr>";
                        fourCount++;
                    }
                    
                     // Determine meal time (breakfast or lunch)
                    if(meal.getIsbreakfast() && meal.getIslunch()){
                        mealTime = "All Day";
                        mealTimeClass = "both";
                    }
                    else if(meal.getIsbreakfast()){
                        mealTime = "Breakfast";
                        mealTimeClass = "breakfast";
                    }
                    else if(meal.getIslunch()){
                        mealTime = "Lunch";
                        mealTimeClass = "lunch";
                    }
                    else{
                        mealTime = "All Day";
                        mealTimeClass = "both";
                    }
                    
                    String discontinuedStyle = "";
                    if(meal.getIsdiscontinued())
                        discontinuedStyle = "style=\"background-color: darkred\"";

                    queryResult += "<td>\n"
                            + "                            <label class=\"meal\" for=\""+ meal.getMealid() +"\" " + discontinuedStyle +">\n"
                            + "                                <h5>"+ meal.getMealname() +"</h5>\n"
                            + "                                <div style=\"position: relative;\">\n"
                            + "                                    <h6 class=\""+ mealTimeClass +"\">"+ mealTime +"</h6>\n"
                            + "                                    <img src=\""+ meal.getMealimagelink()+"\" alt=\"\"/>\n"
                            + "                                </div>\n"
                            + "                                <p class=\"description\">"+ meal.getDescription() +"</p>\n"
                            + "                                <p class=\"calories\">"+ meal.getTotalcalories() +" Calories</p>\n"
                            + "                                <a href=\"\"><div class=\"editButton\">Edit</div></a>\n"
                            + "                                <div class=\"foodPart\">\n"
                            + "                                    <p class=\"componentTitle\">Consists of:</p>\n"
                            + "                                    <p class=\"component\">"+ components +"</p>\n"
                            + "                                </div>\n"
                            + "                                <p class=\"price\">"+ meal.getPrice() +" credits</p>\n"
                            + "                                <a href=\"DisplayFoodSelectionServletForEdit?mealId="+ meal.getMealid() + "\"><div class=\"editMealButton\">Manage</div></a>\n"
                            + "                            </label>\n"
                            + "                        </td>";

                   if (fourCount == 4) {
                        queryResult += "</tr>";
                        fourCount = 0;
                    }
                     fourCount++;
                }
                
                try {
                    request.setAttribute("successMsg", request.getAttribute("successMsg"));
                } catch (Exception ex) {
                    // No error if no message is shown
                }

                // Send the formatted list to JSP
                request.setAttribute("queryResult", queryResult);
                
                request.getRequestDispatcher("manageMeals.jsp").forward(request, response);
                return;

            } catch (Exception e) {
                System.out.println("Could not obtain meal list: " + e.getMessage());
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
