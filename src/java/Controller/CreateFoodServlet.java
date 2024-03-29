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
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
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

        String permission = "";

        try {
            permission = (String) session.getAttribute("permission");
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
        }

        Food food = new Food();
        food.setFoodname(request.getParameter("foodName").trim()); // Uses trim() to remove any unexpected whitespaces (spaces)
        food.setDateadded(Auto.getToday()); // Set the date of food added to today
        food.setIsdiscontinued(false);
        food.setDatediscontinued(null);

        try {
            food.setCalories(Integer.parseInt(request.getParameter("calories"))); // Convert calories to int
        } catch (NullPointerException | NumberFormatException e) {
            // This is highly unlikely to be triggered due to measures we've taken to ensure validity of input. But this is for "just in case".
            request.setAttribute("errorMsg", "Oops! The number of calories is not numerical. Please ensure that it is a number.");
            request.getRequestDispatcher("createFood.jsp").forward(request, response);
            return;
        }

        try {

            utx.begin();
            Food foodChecking = new Food();
            boolean existsAlready = true;
            
            TypedQuery<Food> query = em.createQuery("SELECT f FROM Food f WHERE f.foodname = :foodname", Food.class).setParameter("foodname", food.getFoodname().trim()); // Query for getting food with the same name
            
            try {
                foodChecking = query.getSingleResult();
            } catch (NoResultException ex) {
                existsAlready = false;
            }
            // Checks for food with the same name; if there is, show an error to the user
            if (existsAlready) {
                request.setAttribute("errorMsg", "Oops! A food with the same name already exists.");
                request.getRequestDispatcher("createFood.jsp").forward(request, response);
                return;
            } else {

                query = em.createQuery("SELECT f FROM Food f", Food.class);
                int count = query.getResultList().size();
                food.setFoodid(Auto.generateID("F", 7, count));

                // Insert new food details
                em.persist(food);
                utx.commit();

                request.setAttribute("successMsg", "Your food has been added successfully!.");
                request.getRequestDispatcher("createFood.jsp").forward(request, response);
                return;
            }
        } catch (ConstraintViolationException e) {
            System.out.println(e.getConstraintViolations());
        } catch (Exception e) {
            System.out.println("Unable to create food: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMsg", "Oops! The new food couldn't be created for some reason.");
            request.getRequestDispatcher("createFood.jsp").forward(request, response);
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
