/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.UserAccountManagemnet;

import Model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.persistence.*;
import javax.annotation.*;
import javax.servlet.http.HttpSession;
import javax.transaction.*;
import util.Auto;
import util.Hasher;

/**
 *
 * @author Richard Khoo
 */
@WebServlet(name = "DisplayStats", urlPatterns = {"/DisplayStats"})
public class DisplayStats extends HttpServlet {

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
        HttpSession session = request.getSession(false);

        String permission = "";
        Student studentFromSession = new Student();

        try {
            permission = (String) session.getAttribute("permission");
            studentFromSession = (Student) session.getAttribute("stud");
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
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
           try{
               
               Student stud = new Student();
               
               // Use the student object in session to get the one from database
               stud = em.find(Student.class, studentFromSession.getStudentid());
               
               // Get number of orders this past 7 days
               Calendar firstDay = Calendar.getInstance();
               firstDay.add(Calendar.DAY_OF_YEAR,  -7);
               
               System.out.println(firstDay.get(Calendar.DAY_OF_MONTH));
               
               List<Studentorder> orderList = em.createQuery("SELECT so FROM Studentorder so WHERE so.datecreated BETWEEN :firstDay AND :today").setParameter("firstDay", Auto.calToDate(firstDay), TemporalType.DATE).setParameter("today", Auto.getToday(), TemporalType.DATE).getResultList();
               
               int calorieCount = 0;
               int creditsSpent = 0;
               int orderCount = 0;
               for(Studentorder order : orderList){     // For every order
                   for(Ordermeal om : order.getOrdermealList()){        // And every meal in that order
                       calorieCount += om.getMealid().getTotalcalories();       // Add the calories
                   }
                   orderCount++;
                   creditsSpent += order.getTotalprice();       // Add the price
               }
               
                // Set the details ready, then forward
                request.setAttribute("calorieCount", calorieCount);
                request.setAttribute("creditsSpent", creditsSpent);
                request.setAttribute("orderRedeemed", orderCount);
                request.getRequestDispatcher("studentStats.jsp").forward(request, response);
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
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
