/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.OrderManagement;

import Controller.MealManagement.*;
import Model.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
import util.Auto;

/**
 *
 * @author mast3
 */
@WebServlet(name = "OrderCancellationServlet", urlPatterns = {"/OrderCancellationServlet"})
public class OrderCancellationServlet extends HttpServlet {

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
        String orderId = "";
        Student student = new Student();
        try {
            permission = (String) session.getAttribute("permission");
            student = (Student) session.getAttribute("stud");
            
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
        // Allow staff only
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {

            try{
                orderId = request.getParameter("orderId");
                
                
                // Obtain order object from database
                Studentorder studOrder = em.find(Studentorder.class, orderId);
                
                double refundRate = 0.8;
                int creditRefunded = (int) java.lang.Math.round(refundRate * studOrder.getTotalprice());
                
                // Refund the student
                student.setCredits(student.getCredits() + creditRefunded);
                
                utx.begin();
                em.merge(student);
                utx.commit();

                // Cancel the order
                    studOrder.setIscanceled(true);
                    studOrder.setDatecanceled(Auto.getToday());
                    request.setAttribute("successMsg", "Order has been cancelled.");

               

                // Get related list of Ordermeal objects
                TypedQuery<Ordermeal> query = em.createQuery("SELECT om FROM Ordermeal om where om.orderid = :orderId", Ordermeal.class).setParameter("orderId", studOrder);
                List<Ordermeal> orderMealLists = query.getResultList();

                for (Ordermeal om : orderMealLists) {
                    // Cancel the associative entities
                   om.setIscanceled(true);
                    
                   utx.begin();
                   em.merge(om);
                   utx.commit();
                }
                
                 // Update the order object
                utx.begin();
                em.merge(studOrder);
                utx.commit();
                
                request.setAttribute("successMsg", "Your order has been canceled.");
                request.getRequestDispatcher("ViewOrderServlet"+orderId).forward(request, response);
                return;
                
            } catch (ConstraintViolationException e) {
                System.out.println(e.getConstraintViolations());
            } catch (Exception ex) {
                System.out.println("ERROR: Could not cancel order: " + ex.getMessage());
                request.setAttribute("errorMsg", "Oops! Order cancellation did not succeed for some reason.");
                request.getRequestDispatcher("viewOrder.jsp").forward(request, response);
                ex.printStackTrace();
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
