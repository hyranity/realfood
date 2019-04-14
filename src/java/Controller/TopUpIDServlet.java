/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.OrderManagement.*;
import Controller.MealManagement.*;
import Model.Meal;
import Model.Mealfood;
import Model.Student;
import Model.Studentorder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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

@WebServlet(name = "TopUpIDServlet", urlPatterns = {"/TopUpIDServlet"})
public class TopUpIDServlet extends HttpServlet {
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

        
        // Allow student only
        if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equalsIgnoreCase("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            
            String studentId = "";
            Student stud = new Student();
            
            try {
                 studentId = request.getParameter("studentId");
            } catch (Exception ex) {
                // Display error messages if any
                System.out.println("ERROR: " + ex.getMessage());
                request.setAttribute("errorMsg", "Hmm...we could not read your student ID as input for some reason.");
                request.getRequestDispatcher("topUpValue.jsp").forward(request, response);
                return;
            }
            
            stud = em.find(Student.class, studentId);
            
            // If the student ID is incorrect, show error messages and redirect to the previous page
            if(stud == null){
                request.setAttribute("errorMsg", "Oops! This student does not exist. Ensure that the student ID is correct.");
                request.getRequestDispatcher("topUp.jsp").forward(request, response);
                return;
            }

            try {

                // Set student to session so it can be displayed
               session.setAttribute("studTopUp", stud);
               
                request.getRequestDispatcher("topUpValue.jsp").forward(request, response);
                return;

            } catch (Exception e) {
                request.setAttribute("errorMsg", "Hmm...we could not read your student ID as input for some reason.");
                request.getRequestDispatcher("topUpValue.jsp").forward(request, response);
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
