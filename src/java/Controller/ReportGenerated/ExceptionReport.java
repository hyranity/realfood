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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import util.Auto;
import util.SQLUtil;
import static util.SQLUtil.connectDB;

/**
 *
 * @author Richard Khoo
 */
@WebServlet(name = "ExceptionReport", urlPatterns = {"/ExceptionReport"})
public class ExceptionReport extends HttpServlet {

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
        if (!permission.equalsIgnoreCase("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            String month = "";

            // Create the calendars
            Calendar firstDay = Calendar.getInstance();
            Calendar lastDay = Calendar.getInstance();

            try {
                month = request.getParameter("month");
                month.charAt(0);
                
                if(month == null){
                    response.sendRedirect("dashboardManager.jsp");
                }
            } catch (Exception ex) {
                // Display error messages if any
                response.sendRedirect("dashboardManager.jsp");
            }

            //Get the chosen month and set the calendars
            int monthNum = Auto.getMonthInt(month); // Convert month to int
            firstDay.set(Calendar.MONTH, monthNum);  // Set the month of the "beginning day" calendar object
            lastDay.set(Calendar.MONTH, monthNum); // Set the month of the "last day" calendar object
            firstDay.set(Calendar.DAY_OF_MONTH, 1); // Set the beginning of chosen month to be first day
            lastDay.set(Calendar.DAY_OF_MONTH, lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));  // Set the end of chosen month to be the last day

            // The range of chosen month is now firstDay - lastDay
            try {
                Connection conn = connectDB();
                PreparedStatement stmt = conn.prepareStatement("select m.MEALID, m.mealname, sum(om.quantity * m.price / 100) as cash, sum(om.QUANTITY) as quantity from meal m right join ordermeal om on om.MEALID = m.MEALID right join studentorder so on so.orderid = om.ORDERID where so.DATECANCELED between ? and ? and so.iscanceled =  true group by m.mealid, m.MEALNAME order by cash desc");
                stmt.setDate(1, SQLUtil.getSQLDate(Auto.calToDate(firstDay)));
                stmt.setDate(2, SQLUtil.getSQLDate(Auto.calToDate(lastDay)));
                ResultSet rs = stmt.executeQuery();

                String outputMonthly = "";
                int count = 0;
                int totalAmount = 0;
                while (rs.next()) {
                    count++;
                    outputMonthly += "<tr>"
                            + "<td>" + count + "</td>"
                            + "<td>" + rs.getString(1) + "</td>"
                            + "<td>" + rs.getString(2) + "</td>"
                            + "<td>" + rs.getString(4) + "</td>"
                            + "<td>" + rs.getString(3) + "</td>"
                            + "</tr>";
                    totalAmount += rs.getInt(3);
                }
                
                request.setAttribute("month", month.toUpperCase());
                request.setAttribute("totalAmount", totalAmount);
                request.setAttribute("outputMonthly", outputMonthly);
                request.getRequestDispatcher("exceptionReport.jsp").forward(request, response);
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
