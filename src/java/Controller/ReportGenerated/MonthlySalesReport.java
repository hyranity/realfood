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
import Model.*;
import java.util.List;
import Controller.MealManagement.*;
import java.util.Calendar;
import util.Auto;

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
                String month = "";
                
                // Create the calendars
                Calendar firstDay = Calendar.getInstance();
                Calendar lastDay = Calendar.getInstance();
                       
                try {
                    month = request.getParameter("month");
                } catch (Exception ex) {
                    // Display error messages if any
                    System.out.println("ERROR: " + ex.getMessage());
                }
                
                //Get the chosen month and set the calendars
                int monthNum = Auto.getMonthInt(month); // Convert month to int
                firstDay.set(Calendar.MONTH, monthNum);  // Set the month of the "beginning day" calendar object
                lastDay.set(Calendar.MONTH, monthNum); // Set the month of the "last day" calendar object
                firstDay.set(Calendar.DAY_OF_MONTH, 1); // Set the beginning of chosen month to be first day
                lastDay.set(Calendar.DAY_OF_MONTH, lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));  // Set the end of chosen month to be the last day
                
                // The range of chosen month is now firstDay - lastDay
                
                
                Query query = em.createQuery("SELECT m FROM Meal m", Meal.class);
                List<Meal> mealList = query.getResultList();
                
                query = em.createQuery("SELECT m FROM Meal m WHERE m.mealid = :mealid", Meal.class);
                List<Meal> MealDate = query.getResultList();
                
                query = em.createQuery("SELECT m FROM Meal m WHERE m.mealid = :mealid", Meal.class);
                List<Meal> MealID = query.getResultList();
                
                query = em.createQuery("SELECT m FROM Meal m WHERE m.mealname = :mealname", Meal.class);
                List<Meal> Product = query.getResultList();
                
                query = em.createQuery("SELECT m FROM Mealfood m WHERE m.quantity = :quantity", Mealfood.class);
                List<Mealfood> quantity = query.getResultList();
                
                query = em.createQuery("SELECT m FROM Meal m WHERE m.price = :price", Meal.class);
                List<Meal> Amount = query.getResultList();
                
                String outputMonthly = "";
                
                List<Meal> mList = em.createQuery("SELECT m FROM Meal m").getResultList();
                List<Studentorder> orderList = em.createQuery("SELECT o FROM Studentorder o WHERE o.datecreated BETWEEN :firstday AND :lastday").setParameter("firstday", Auto.calToDate(firstDay), TemporalType.DATE).setParameter("lastDay", Auto.calToDate(lastDay), TemporalType.DATE).getResultList();
                for(Meal meal : mList){
                    for(Ordermeal om : meal.getOrdermealList()){
                        Calendar cal = Calendar.getInstance();
                        cal = Auto.dateToCal(om.getOrderid().getDatecreated());
                        
                        
                    }
                }
  
                for (int i = 1; i <= mealList.size(); i++) {
  
                Meal meal = mealList.get(i);
    
                 outputMonthly += "<tr>"
                  + "<td>" + i + "</td>"
                  + "<td>" + "<DATE>" + "</td>"
                  + "<td>" + MealID + "</td>"
                  + "<td>" + Product + "</td>"
                  + "<td>" + quantity + "</td>"
                  + "<td>" + Amount + "</td>"
                  + "</tr>";
                 
            }
            }
            catch(Exception ex){
                request.setAttribute("outputMonthly", ex);
                request.getRequestDispatcher("monthlySalesReport.jsp");
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
