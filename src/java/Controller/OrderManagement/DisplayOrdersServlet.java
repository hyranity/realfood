/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.OrderManagement;

import Controller.MealManagement.*;
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
import java.util.*;
import Model.*;
import java.text.SimpleDateFormat;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mast3
 */
@WebServlet(name = "DisplayOrdersServlet", urlPatterns = {"/DisplayOrdersServlet"})
public class DisplayOrdersServlet extends HttpServlet {

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
        
        Student stud = new Student();
        
        try {
            permission = (String) session.getAttribute("permission");
            stud = (Student) session.getAttribute("stud");
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
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            
            
            try{
                
                // Get all orders
                TypedQuery<Studentorder> query = em.createQuery("SELECT so FROM Studentorder so where so.studentid = :studentId order by so.orderid desc", Studentorder.class).setParameter("studentId", stud);
                List<Studentorder> soList = query.getResultList();
                String queryResult = "";
                int fourCount = 2;

                // Format it for display
                for (int i = 0; i < soList.size(); i++) {
                   
                    if (fourCount == 0) {
                        queryResult += "<tr>";
                    }
                    
                    // Display meal count
                   String mealCount = soList.get(i).getOrdermealList().size() + " meal";
                   
                   SimpleDateFormat sm = new SimpleDateFormat("dd/MM/yyyy");
                   String chosenDate = sm.format(soList.get(i).getChosendate());
                   
                   if(soList.get(i).getOrdermealList().size() > 1)
                       mealCount += "s";
                   
                   String cancelStatus = "";
                   if(soList.get(i).getIscanceled())
                        cancelStatus = " <p style=\"color: red; font-weight: bold;\">CANCELLED</p>";

                    queryResult += "<td>\n"
                            + "                    <div class=\"record\">\n"
                            + "                        <h6>" + soList.get(i).getOrderid() + "</h6>\n"
                            + "                        <p>" + chosenDate + "</p>\n"
                            + "                        <p>" + mealCount + "</p>\n"
                            + "                        <p>" + soList.get(i).getTotalprice() + " credits</p>\n"
                            + cancelStatus
                            + "                        <a href=\"ViewOrderServlet?orderId=" + soList.get(i).getOrderid() + "\"><div class=\"editButton\">Manage</div></a>\n"
                            + "                    </div>\n"
                            + "                </td>";


                    if (fourCount == 4) {
                        queryResult += "</tr>";
                        fourCount = 0;
                    }
                     fourCount++;
                    
                }
                
                try {
                    request.setAttribute("sucessMsg", request.getAttribute("successMsg"));
                } catch (Exception e) {
                    // If null, it means that its not redirected from order payment servlet, so its fine
                }
                
                try {
                    request.setAttribute("errorMsg", request.getAttribute("errorMsg"));
                } catch (Exception e) {
                    // If null, it means that its not redirected from order payment servlet, so its fine
                }
                
                // Send the formatted list to JSP
                request.setAttribute("queryResult", queryResult);
                request.getRequestDispatcher("displayOrder.jsp").forward(request, response);
                return;
                

            } catch (Exception e) {
                System.out.println("Could not obtain food list: " + e.getMessage());
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
