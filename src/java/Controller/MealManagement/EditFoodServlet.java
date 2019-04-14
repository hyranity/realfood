/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealManagement;

import Model.Food;
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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import util.Auto;

/**
 *
 * @author mast3
 */
@WebServlet(name = "EditFoodServlet", urlPatterns = {"/EditFoodServlet"})
public class EditFoodServlet extends HttpServlet {

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

        String permission = (String) session.getAttribute("permission");
        String foodId = "";
        String previousUrl = "";

        // Checks for previous URL. if no previous URL detected, means the user directly accessed the pages.
        try {
            previousUrl = request.getHeader("referer");
            if (previousUrl == null) {
                request.setAttribute("errorMsg", "Direct access detected. Please don't directly access pages..");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            request.setAttribute("errorMsg", "Direct access detected. Please don't directly access pages..");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // If no parameter is detected, redirect the user back.
        try {
            foodId = request.getParameter("foodId");
        } catch (Exception e) {
            response.sendRedirect(previousUrl);
        }

        
        if (permission == null) {
            request.setAttribute("errorMsg", "Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            // Allow staff only
            if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equalsIgnoreCase("manager")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            try {
                // Get data
                utx.begin();
                Food food = em.find(Food.class, foodId);
                utx.commit();

                String discontinueButton = "";
                String discontinuedDate = "";
                String discontinueDialog = "";

                if (food.getDatediscontinued() == null) {
                    discontinuedDate = "not discontinued";
                } else {
                    discontinuedDate = Auto.dateToString(food.getDateadded());
                }
                

                // If it is not discontinued, show the appropriate text
                if (!food.getIsdiscontinued()) {
                    discontinueButton = "<div class=\"toggleDisable\" id=\"disable\">Discontinue</div>";

                    discontinueDialog = "<h5>Discontinue food?</h5>\n"
                            + "            <p>The food will be discontinued.</p>";
                } else {
                    discontinueButton = "<div class=\"toggleDisable\" id=\"enable\">Re-enable</div>";
                    
                    discontinueDialog = "<h5>Re-enable food?</h5>\n"
                            + "            <p>The food will be able to be used again.</p>";
                }

                String query = " <a href=\"#\" onclick=\"confirmtoggleDisable()\"> " + discontinueButton + "</a>\n"
                        + "                    <div>\n"
                        + "                        <input type=\"text\" value=\"" + food.getFoodid() + "\" style=\"background-color: darkgray;\" placeholder=\"Food ID\"  id=\"foodId\" name=\"foodId\" readonly/>\n"
                        + "                    </div>\n"
                        + "                    <div>\n"
                        + "                        <input type=\"text\" id=\"foodName\" placeholder=\"Food Name\" value=\"" + food.getFoodname() + "\" name=\"foodName\" />\n"
                        + "                    </div>\n"
                        + "                    <div>\n"
                        + "                        <input type=\"text\" value=\"" + food.getCalories() + "\" placeholder=\"Calories\" id=\"calories\" name=\"calories\" />\n"
                        + "                    </div>\n"
                        + "                    <div>\n"
                        + "                        <input type=\"text\" value=\"" + Auto.dateToString(food.getDateadded()) + "\" id=\"dateAdded\"  style=\"background-color: darkgray;\"  readonly/>\n"
                        + "                    </div>\n"
                        + "                    <div>\n"
                        + "                        <input type=\"text\" value=\"" + discontinuedDate + "\" id=\"dateDiscontinued\"  style=\"background-color: darkgray; font-weight: 500;\"  readonly/>\n"
                        + "                    </div>\n"
                        + "                    \n"
                        + "                    <br/>";
                
                request.setAttribute("discontinueDialog", discontinueDialog);
                request.setAttribute("query", query);
                request.setAttribute("foodId", foodId);
                request.getRequestDispatcher("editFood.jsp").forward(request, response);
                return;

            } catch (Exception e) {
                request.setAttribute("errorMsg", "Oops! Something went wrong.");
                e.printStackTrace();
                request.getRequestDispatcher("login.jsp").forward(request, response);
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
