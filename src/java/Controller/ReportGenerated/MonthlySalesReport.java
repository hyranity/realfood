/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ReportGenerated;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.persistence.*;
import javax.annotation.*;
import javax.transaction.*;

/**
 *
 * @author Richard Khoo
 */
@WebServlet(name = "MonthlySalesReport", urlPatterns = {"/MonthlySalesReport"})
public class MonthlySalesReport extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try{
                Query query = em.createQuery("MealFood.findAll);
                Query query = em.createQuery("SELECT m FROM Mealfood m WHERE m da", Mealfood.class);
                List<Mealfood> mealfood = query.getResultList();
                mealfood.get(0).getDate();
                
                query = em.createQuery("SELECT m FROM Mealfood m", Mealfood.class);
                List<Mealfood> MealID = query.getResultList();
                mealfood
                
                query = em.createQuery("SELECT m FROM Meal m WHERE m.mealname = :mealname", Meal.class);
                List<Mealfood> Product = query.getResultList();
                
                query = em.createQuery("Mealfood");
                List<Mealfood> Quantity = query.getResultList();
                
                Typequery query = em.createQuery("SELECT m FROM Mealfood m", Mealfood.class);
                List<Mealfood> Amount = query.getResultList();
            }
            catch(Exception ex){
                
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
