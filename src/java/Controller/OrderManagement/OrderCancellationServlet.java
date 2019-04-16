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

        // Allow student only
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {

            try {
                orderId = request.getParameter("orderId");

                // Obtain order object from database
                Studentorder studOrder = em.find(Studentorder.class, orderId);

                // Count those that are not canceled.
                int totalPrice = 0;
                for (Ordermeal om : studOrder.getOrdermealList()) {
                    if (!om.getIscanceled()) {
                        totalPrice += om.getQuantity() * om.getMealid().getPrice();
                        om.setIscanceled(true);
                    }
                }
                
                // Cancel the order
                studOrder.setIscanceled(true);
                studOrder.setDatecanceled(Auto.getToday());
                request.setAttribute("successMsg", "Order has been cancelled.");


                // Update the order object
                utx.begin();
                em.merge(studOrder);
                utx.commit();


                double refundRate = 0.8;
                int creditRefunded = (int) java.lang.Math.round(refundRate * totalPrice);

                System.out.println("totalPrice before rate: " + totalPrice);

                // Refund the student
                Student stud = em.find(Student.class, student.getStudentid());
                stud.setCredits(stud.getCredits() + creditRefunded);
                
                utx.begin();
                em.merge(stud);
                utx.commit();

                // Update the student in session
                session.setAttribute("stud", stud);

                request.setAttribute("successMsg", "Your order has been canceled.");
                request.getRequestDispatcher("ViewOrderServlet?" + orderId).forward(request, response);
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
