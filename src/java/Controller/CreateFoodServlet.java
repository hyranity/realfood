/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.*;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import util.*;

/**
 *
 * @author mast3
 */
@WebServlet(name = "CreateFoodServlet", urlPatterns = {"/CreateFoodServlet"})
public class CreateFoodServlet extends HttpServlet {

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

        // If user is not logged in, redirect to login page
        if (permission == null) {
            request.setAttribute("errorMsg", "Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            // Allow staff only
            if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            Food food = new Food();
            food.setFoodname(request.getParameter("foodName").trim()); // Uses trim() to remove any unexpected whitespaces (spaces)
            food.setDateadded(Auto.getToday()); // Set the date of food added to today
            food.setIsdiscontinued(false);

            try {
                food.setCalories(Integer.parseInt(request.getParameter("calories"))); // Convert calories to int
            } catch (NullPointerException e) {
                // This is highly unlikely to be triggered due to measures we've taken to ensure validity of input. But this is for "just in case".
                request.setAttribute("errorMsg", "Oops! The number of calories is not numerical. Please ensure that it is a number.");
                request.getRequestDispatcher("createFood.jsp").forward(request, response);
                return;
            }

            try {

                utx.begin();
                TypedQuery<Food> query = em.createQuery("SELECT f FROM Food f WHERE f.foodname = :foodname", Food.class).setParameter("foodname", food.getFoodname().trim()); // Query for getting food with the same name
                Food foodChecking = query.getSingleResult();

                // Checks for food with the same name; if there is, show an error to the user
                if (foodChecking != null) {
                    request.setAttribute("errorMsg", "Oops! A food with the same name has already been added.");
                    request.getRequestDispatcher("createFood.jsp").forward(request, response);
                    return;
                } else {

                    // Insert new food details
                    em.persist(food);
                    utx.commit();
                }
            } catch (Exception e) {
                System.out.println("Unable to create food.");
                request.setAttribute("errorMsg", "Oops! The new food couldn't be created for some reason.");
                request.getRequestDispatcher("createFood.jsp").forward(request, response);
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
