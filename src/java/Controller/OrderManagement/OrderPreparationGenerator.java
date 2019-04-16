/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.OrderManagement;

import Controller.MealManagement.*;
import Model.Meal;
import Model.Mealfood;
import Model.Ordermeal;
import Model.Studentorder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
import util.Auto;
import util.SQLUtil;
import static util.SQLUtil.connectDB;

/**
 *
 * @author mast3
 */
@WebServlet(name = "OrderPreparationGenerator", urlPatterns = {"/OrderPreparationGenerator"})
public class OrderPreparationGenerator extends HttpServlet {

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
        if (!permission.equalsIgnoreCase("canteenStaff")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {

            String month = "";
            String day = "";
            String year = "";
            int totalFood = 0, totalMeal = 0;

            // Create the calendar
            Calendar chosenDay = Calendar.getInstance();

            try {
                month = request.getParameter("month");
                day = request.getParameter("day");
                year = request.getParameter("year");
                
               

                if (month == "" || day == "" || year == "" || month == null || day == null || year == null) {
                    request.setAttribute("errorMsg", "Oops! Please fill in all fields.");
                    request.getRequestDispatcher("dashboardCanteenStaff.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                // If error, means manager accessed this directly. Hence, redirect
                response.sendRedirect("dashboardCanteenStaff.jsp");
            }
            
            //Get the chosen month and set the calendars
            int monthNum = Auto.getMonthInt(month); // Convert month to int
            chosenDay.set(Calendar.MONTH, monthNum);
            chosenDay.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
            chosenDay.set(Calendar.YEAR, Integer.parseInt(year));
            String queryResult = "";
            // Get all orders
            try {
                Connection conn = connectDB();
                PreparedStatement stmt = conn.prepareStatement("select m.MEALID, m.MEALNAME, sum(om.QUANTITY) from meal m right join ordermeal om on om.MEALID = m.MEALID right join studentorder so on so.orderid = om.ORDERID where so.CHOSENDATE = ? group by m.MEALID, m.MEALNAME");
                stmt.setDate(1, SQLUtil.getSQLDate(Auto.calToDate(chosenDay)));
                ResultSet rs = stmt.executeQuery();

                int count = 0;
                boolean isBeginning = true;
                boolean hasResults = false;
                while (rs.next()) {
                    count++;
                    queryResult += "<tr>"
                            + "<td style=\"color: gold\">" + rs.getString(2) + "</td>"
                            + "<td style=\"color: gold\">" + rs.getInt(3) + "</td>"
                            + "<td>" + "</td>"
                            + "<td>" + "</td>"
                            + "<td></td>"
                            + "</tr>";
                    
                    totalMeal += rs.getInt(3);

                    PreparedStatement stmt2 = conn.prepareStatement("select f.FOODNAME, mf.QUANTITY from mealfood mf join food f on f.FOODID = mf.FOODID where mf.MEALID = ?");
                    stmt2.setString(1, rs.getString(1));
                    ResultSet rs2 = stmt2.executeQuery();

                    int count2 = 0;
                    while (rs2.next()) {
                        count2++;
                        queryResult += "<tr>"
                                + "<td></td>"
                                + "<td></td>"
                                + "<td>" + rs2.getString(1) + "</td>"
                                + "<td>" + rs2.getString(2) + "</td>"
                                + "<td>" + rs2.getInt(2)*rs.getInt(3) + "</td>"
                                
                                + "</tr>";
                        
                        totalFood += rs2.getInt(2)*rs.getInt(3);
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Send the formatted list to JSP
            request.setAttribute("totalMeal", totalMeal);
            request.setAttribute("totalFood", totalFood);
            request.setAttribute("queryResult", queryResult);
            request.getRequestDispatcher("orderPrep.jsp").forward(request, response);
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
