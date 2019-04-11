/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.OrderManagement;

import Model.Meal;
import Model.Mealfood;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mast3
 */
@WebServlet(name = "DateSelectionServlet", urlPatterns = {"/DateSelectionServlet"})
public class DateSelectionServlet extends HttpServlet {

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

        // If user is not logged in, redirect to login page
        // Allow student only
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {

            try {

                String dateValue[];
                List<Date> chosenDates = new ArrayList();

                try {
                    dateValue = request.getParameterValues("chosenDates");
                   int exceptionTrigger = dateValue.length;
                } catch (NullPointerException e) {
                    request.setAttribute("errorMsg", "Please select a date!");
                    request.getRequestDispatcher("calendarStudent.jsp").forward(request, response);
                    return;
                }
                
                for (int i = 0; i < dateValue.length; i++) {
                    // Split the date value
                    int indexOfDivider = dateValue[i].indexOf('/');                                                                             // Locate the first divider
                    int day = Integer.parseInt(dateValue[i].substring(0, indexOfDivider));                                    // Extract the first char to the one before the divider
                    String currentDateValue = dateValue[i].substring(indexOfDivider, dateValue[i].length());    // Extract the new string, from the divider to the end of the string
                    indexOfDivider = dateValue[i].indexOf('/');                                                                                    // Repeat the steps
                    int month = Integer.parseInt(dateValue[i].substring(0, indexOfDivider));                              // Extract the month
                    currentDateValue = dateValue[i].substring(indexOfDivider, dateValue[i].length());               // Extract the new string, from the divider to the end of the string
                    indexOfDivider = dateValue[i].indexOf('/');                                                                                   // Repeat the steps
                    int year = Integer.parseInt(dateValue[i].substring(0, indexOfDivider)) + 2000;                         // Extract the year, plus 2000
                    
                    Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateValue[i]);

                    //NOTE: Apologize for using deprecated methods and classes, but it's the simplest I could understand and hence used due to time constraints
                    // Set date fields
                    
                    chosenDates.add(date);
                }
                
                
                // Set the result to session
                session.setAttribute("chosenDates", chosenDates);
                

                // Send the formatted list to JSP
                request.setAttribute("queryResult", "");
                request.getRequestDispatcher("DisplayMealsServlet").forward(request, response);
                return;

            } catch (Exception e) {
                System.out.println("Could not obtain date values: " + e.getMessage());
                e.printStackTrace();
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
