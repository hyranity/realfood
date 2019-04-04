/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import Model.Staff;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mast3
 */
@WebServlet(name = "DisplayStaffServlet", urlPatterns = {"/DisplayStaffServlet"})
public class DisplayStaffServlet extends HttpServlet {

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
        // If user is not logged in, redirect to login page
        if (session.getAttribute("staff") == null) {
            request.setAttribute("errorMsg", "Oops! Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
            
        } else {
            //Ensure that this is the manager.
            Staff manager = (Staff) session.getAttribute("staff");

            //If not manager, log him/her out and give a warning.
            if (!manager.getStaffrole().equalsIgnoreCase("manager")) {
                session.invalidate();
                request.setAttribute("errorMsg", "Hey! You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else {

                List<Staff> staffList = new ArrayList();
                String queryResults = ""; //This is used to print the data in the JSP

                //Following are parts of each record to simplify programming of loop
                String outerPartOne = "<div class=\"record\">";
                String id = "";
                String fname = "";
                String lname = "";
                String badge = "<span class=\"badge badge-primary\">Canteen Staff</span>";
                String breaks = "<br/><br/>";
                String status = "";
                String editButton = "<a href=\"\"><div class=\"editButton\">Edit</div></a>";
                String outerPartTwo = "</div>";

                try {
                    utx.begin();
                    TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.staffrole = :role", Staff.class).setParameter("role", "canteenStaff");
                    staffList = query.getResultList();
                    utx.commit();
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't obtain all staff objects: " + e.getMessage());
                }

                for (Staff canteenStaff : staffList) {

                    id = "<h6>" + canteenStaff.getStaffid() + "</h6>";
                    fname = "<p>" + canteenStaff.getFirstname() + "</p>";
                    lname = "<p>" + canteenStaff.getLastname() + "</p>";
                    status = "<p class=\"status\" style=\"color: green; font-weight: bold;\">" + canteenStaff.getFirstname() + "</p>";

                    queryResults += outerPartOne + id + fname + lname + badge + breaks + status + editButton + outerPartTwo;
                }

                request.setAttribute("queryResults", queryResults);
                request.getRequestDispatcher("displayStaff.jsp").forward(request, response);
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
