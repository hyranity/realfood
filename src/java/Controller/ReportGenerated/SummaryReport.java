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
import java.util.Locale;
import javax.servlet.http.HttpSession;
import util.Auto;
import util.SQLUtil;
import static util.SQLUtil.connectDB;

/**
 *
 * @author Richard Khoo
 */
@WebServlet(name = "SummaryReport", urlPatterns = {"/SummaryReport"})
public class SummaryReport extends HttpServlet {

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
            String selection = "";
            String outputSemiAnnual = "";
            String year = "";

            int totalAmount = 0;

            // Create the calendars
            Calendar firstDay = Calendar.getInstance();
            Calendar lastDay = Calendar.getInstance();

            try {
                selection = request.getParameter("selection");
                year = request.getParameter("year");


                if (selection == "" || year == "") {
                    request.setAttribute("errorMsg", "Oops! Please fill in all fields.");
                    request.getRequestDispatcher("selectReport.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                response.sendRedirect("dashboardManager.jsp");
            }

            String title = "";
            int monthNum = 0;
            // If first half, show first six months
            if (selection.equalsIgnoreCase("firstHalf")) {
                title = "January to June " + Integer.parseInt(year);
                monthNum = 0;
            } else if (selection.equalsIgnoreCase("secondHalf")) {
                title = "July to December " + Integer.parseInt(year);
                monthNum = 6;
            } else {
                //Redirect, since error
                response.sendRedirect("dashboardManager.jsp");
            }

            int monthCount = 0;
            for (int i = 0; i < 6; i++) {
                String outputMonthly = "";
                monthNum++;
                monthNum = i;

                //Get the chosen month and set the calendars
                firstDay.set(Calendar.MONTH, monthNum);  // Set the month of the "beginning day" calendar object
                firstDay.set(Calendar.YEAR, Integer.parseInt(year));
                lastDay.set(Calendar.MONTH, monthNum); // Set the month of the "last day" calendar object
                lastDay.set(Calendar.YEAR, Integer.parseInt(year));
                firstDay.set(Calendar.DAY_OF_MONTH, 1); // Set the beginning of chosen month to be first day
                lastDay.set(Calendar.DAY_OF_MONTH, lastDay.getActualMaximum(Calendar.DAY_OF_MONTH));  // Set the end of chosen month to be the last day

                // The range of chosen month is now firstDay - lastDay
                try {
                    Connection conn = connectDB();
                    PreparedStatement stmt = conn.prepareStatement("select m.MEALID, m.mealname, sum(om.quantity * m.price / 100) as cash, sum(om.QUANTITY) as quantity from meal m right join ordermeal om on om.MEALID = m.MEALID right join studentorder so on so.orderid = om.ORDERID where so.DATECREATED between ? and ? group by m.mealid, m.MEALNAME order by cash desc fetch first 3 rows only");
                    stmt.setDate(1, SQLUtil.getSQLDate(Auto.calToDate(firstDay)));
                    stmt.setDate(2, SQLUtil.getSQLDate(Auto.calToDate(lastDay)));
                    ResultSet rs = stmt.executeQuery();

                    int count = 0;
                    int totalCash = 0;
                    boolean isBeginning = true;
                    boolean hasResults = false;
                    while (rs.next()) {
                        if (isBeginning) {
                            // Add month header
                            outputMonthly += "<tr>\n"
                                    + "      <th scope=\"col\">" + firstDay.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH) + "</th>\n"
                                    + "      <th scope=\"col\"></th>\n"
                                    + "      <th scope=\"col\"></th>\n"
                                    + "      <th scope=\"col\"></th>\n"
                                    + "      <th scope=\"col\"></th>\n"
                                    + "    </tr>    ";
                        }
                        count++;
                        outputMonthly += "<tr>"
                                + "<td></td>"
                                + "<td>" + rs.getString(1) + "</td>"
                                + "<td>" + rs.getString(2) + "</td>"
                                + "<td>" + rs.getString(4) + "</td>"
                                + "<td>" + rs.getString(3) + "</td>"
                                + "</tr>";
                        totalAmount += rs.getInt(3);
                        totalCash += rs.getInt(3);
                        isBeginning = false;
                        hasResults = true;
                    }

                    if (hasResults) {
                        // Add month footer
                        outputMonthly += "<tr>"
                                + "<td></td>"
                                + "<td></td>"
                                + "<td></td>"
                                + "<th>Subtotal</th>"
                                + "<th> RM " + totalCash + "</th>"
                                + "</tr>";
                    }

                    // Add to the overall query
                    outputSemiAnnual += outputMonthly;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            request.setAttribute("selection", selection.toUpperCase());
            request.setAttribute("title", title);
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("outputOverall", outputSemiAnnual);
            request.getRequestDispatcher("summaryReport.jsp").forward(request, response);
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
