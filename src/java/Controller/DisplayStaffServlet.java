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
        
        HttpSession session = session = request.getSession(false);
        
        // clear the session of any unwanted obejcts
        session.setAttribute("staffForEdit", null);

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

            // Allow staff only
            if (!permission.equalsIgnoreCase("manager")) {
                request.setAttribute("errorMsg", "You are not allowed to visit that page.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            } else  {
               Staff manager = (Staff) session.getAttribute("staff");

                List<Staff> staffList = new ArrayList();
                String queryResults = ""; //This is used to print the data in the JSP

                //Following are parts of each record to simplify programming of loop
                String outerPartOne = "<div class=\"record\">";
                String id = "";
                String fname = "";
                String lname = "";
                String badge = "";
                String breaks = "<br/><br/>";
                String status = "";
                
                String outerPartTwo = "</div>";

                try {
                    utx.begin();
                    TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.staffid != :staffId", Staff.class).setParameter("staffId", manager.getStaffid());
                    staffList = query.getResultList();
                    utx.commit();
                } catch (Exception e) {
                    System.out.println("ERROR: Couldn't obtain all staff objects: " + e.getMessage());
                }

                for (Staff canteenStaff : staffList) {
                    if(canteenStaff.getIshired())
                        status = "<p class=\"status\" style=\"color: green; font-weight: bold;\">Hired</p>";
                    else
                        status = "<p class=\"status\" style=\"color: red; font-weight: bold;\">Dismissed</p>";
                    
                    if(canteenStaff.getStaffrole().equalsIgnoreCase("manager"))
                        badge = "<span class=\"badge badge-danger\">Manager</span>";
                    else
                        badge = "<span class=\"badge badge-primary\">Canteen Staff</span>";

                    id = "<h6>" + canteenStaff.getStaffid() + "</h6>";
                    fname = "<p>" + canteenStaff.getFirstname() + "</p>";
                    lname = "<p>" + canteenStaff.getLastname() + "</p>";
                    String editButton = "<a href=\"DisplayStaffForEdit?staffId=" + canteenStaff.getStaffid()  +" \"><div class=\"editButton\">Edit</div></a>";
                    queryResults += outerPartOne + id + fname + lname + badge + breaks + status + editButton + outerPartTwo;
                }

                request.setAttribute("queryResults", queryResults);
                request.getRequestDispatcher("displayStaff.jsp").forward(request, response);
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
