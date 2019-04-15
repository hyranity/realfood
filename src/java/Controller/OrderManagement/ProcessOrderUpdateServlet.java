/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.OrderManagement;

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
import Model.*;
import java.util.*;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import util.Auto;
import util.CodeGenerator;

/**
 *
 * @author mast3
 */
@WebServlet(name = "ProcessOrderUpdateServlet", urlPatterns = {"/ProcessOrderUpdateServlet"})
public class ProcessOrderUpdateServlet extends HttpServlet {

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
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {

            // Attempt to get session variables
            Studentorder studOrder = new Studentorder();
            Studentorder currentStudOrder = new Studentorder();
            boolean isARefund = false;
            try {
                studOrder = (Studentorder) session.getAttribute("studOrderEdit");
                currentStudOrder = (Studentorder) session.getAttribute("currentStudOrder");
                isARefund = (boolean) session.getAttribute("isARefund");
                // Exception trigger
                currentStudOrder.getOrderid(); // If this is null, it will cause an exception, which will redirect the student to first step.

                if (studOrder == null) {
                    response.sendRedirect("DisplayOrdersServlet");
                    return;
                }

            } catch (Exception e) {
                //If there's an error, redirect the student to the first step
                response.sendRedirect("DisplayOrdersServlet");
                return;
            }

            try {
                // Get student from session, which won't be null if the previous validations are passed
                Student student = (Student) session.getAttribute("stud");

                // Get the latest student details
                student = em.find(Student.class, student.getStudentid());

                int price = currentStudOrder.getTotalprice() - studOrder.getTotalprice();

                // Charge the student (or Refund the student if its negative, since x - -y = x + y)
                student.setCredits(student.getCredits() - price);

                // Save to database
                utx.begin();
                em.merge(student);
                utx.commit();


                // Initiate the element
                Studentorder so = studOrder;

                // COUPON CODE REGENERATION
                String couponCode = "";
                int loopCount = 0;

                boolean codeExists = false;
                boolean codeExistsInDB = false;
                do {
                    codeExists = false;
                    codeExistsInDB = false;
                    loopCount++;
                    try {
                        //Generate coupon code
                        CodeGenerator codeGenerator = new CodeGenerator();
                        couponCode = codeGenerator.generateCode(7);

                        // Check database to ensure that there's no duplication
                        TypedQuery<Studentorder> checkQuery = em.createQuery("SELECT so FROM Studentorder so WHERE so.couponcode = :couponCode", Studentorder.class).setParameter("couponCode", couponCode);
                        Studentorder tempStudentOrder = checkQuery.getSingleResult();

                        if (tempStudentOrder.getOrderid() != null) {
                            codeExists = true;
                            codeExistsInDB = true;
                            System.out.println("Same coupon in DB! = " + couponCode);
                        } else {
                            codeExists = false;
                        }

                    } catch (NoResultException e) {
                        // No problem if no results or is null
                    }

                    if (loopCount == 100) {
                        System.out.println("ERROR: LOOP BREAK TO STOP INFINITE LOOP! ");
                        System.out.println("");
                        break;
                    }

                } while (codeExists || codeExistsInDB);

                // If code is unique, set it
                if (!codeExists) {
                    studOrder.setCouponcode(couponCode);
                }

                // Set all the necessary fields
                studOrder.setChosendate(currentStudOrder.getChosendate());
                studOrder.setDatecreated(currentStudOrder.getDatecreated());
                studOrder.setStudentid(student); // Also set student ID
                studOrder.setIscanceled(false);
                studOrder.setCouponcode(couponCode);
                studOrder.setIsredeemed(false);
                studOrder.setTotalprice(studOrder.getTotalprice());

                studOrder.setOrderid(currentStudOrder.getOrderid());    // Set order ID 

                for (Ordermeal om : studOrder.getOrdermealList()) {
                    om.setOrderid(studOrder);
                }
                utx.begin();
                // Delete all the relationships with the current order
                    Query deleteQuery = em.createQuery("DELETE FROM Ordermeal om WHERE om.orderid = :orderId").setParameter("orderId", studOrder);
                    deleteQuery.executeUpdate();
                    System.out.println("Ordermeal relationships deleted.");

                // Update  database
                
                em.merge(studOrder);
                utx.commit();

                //Update session
                session.setAttribute("stud", student);
                session.setAttribute("studOrder", studOrder);

                //Redirect to my orders page
                request.setAttribute("successMsg", "You have successfully updated your order.");
                request.getRequestDispatcher("viewOrder.jsp" + studOrder.getOrderid()).forward(request, response);
                return;

            } catch (ConstraintViolationException ex) {

                System.out.println("ERROR: Couldn't merge student after payment.");
                System.out.println(ex.getConstraintViolations());
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Couldn't process payment!");
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
