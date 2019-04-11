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
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import util.Auto;
import util.CodeGenerator;


/**
 *
 * @author mast3
 */
@WebServlet(name = "ProcessPaymentServlet", urlPatterns = {"/ProcessPaymentServlet"})
public class ProcessPaymentServlet extends HttpServlet {

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
         
        // If user is not logged in, redirect to login page
        // Allow student only
        if (!permission.equalsIgnoreCase("student")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {
            
            // Attempt to get student order from session
            Studentorder studOrder = new Studentorder();
            try {
                studOrder = (Studentorder) session.getAttribute("studOrder");
               
                // Exception trigger
                studOrder.getOrderid(); // If this is null, it will cause an exception, which will redirect the student to first step.

                if (studOrder == null) {
                    response.sendRedirect("calendarStudent.jsp");
                    return;
                }
                
            } catch (Exception e) {
                //If there's an error, redirect the student to the first step
                response.sendRedirect("calendarStudent.jsp");
                return;
            }
 
            try {
                // Get student from session, which won't be null if the previous validations are passed
                Student student = (Student) session.getAttribute("stud");
                
               
                
                // COUPON CODE GENERATION
                String couponCode = "";
                boolean codeExists = true;
                do {
                    try {
                        //Generate coupon code
                        CodeGenerator codeGenerator = new CodeGenerator("numletters");
                         couponCode = codeGenerator.generateCode();

                        // Check database to ensure that there's no duplication
                        TypedQuery<Studentorder> checkQuery = em.createQuery("SELECT so FROM Studentorder so WHERE so.couponcode = :couponCode", Studentorder.class).setParameter("couponCode", couponCode);
                        Studentorder tempStudentOrder = checkQuery.getSingleResult();

                        if (tempStudentOrder.getOrderid() != null) {
                            codeExists = true;
                        }
                        else
                            codeExists = false;
                    } catch (NoResultException | NullPointerException e) {
                        // No problem if no results or is null
                        codeExists = false;
                    }
                } while (codeExists);

                
                //Generate ID
                TypedQuery<Studentorder> query = em.createQuery("SELECT s FROM Studentorder s", Studentorder.class);
                int count = query.getResultList().size();

                // Set all the necessary fields
                studOrder.setOrderid(Auto.generateID("O", 10, count));    // Set order ID
                studOrder.setIscanceled(false);
                studOrder.setCouponcode(couponCode);
                studOrder.setIsredeemed(false);

                // Charge the student
                student.setCredits(student.getCredits() - studOrder.getTotalprice());
                
                // Create duplicate date objects, depending on how many dates have been chosen
                String[] dateValue = (String[]) session.getAttribute("dateValue");
                int dayCount = dateValue.length;
                
                Studentorder[] soList = new Studentorder[dayCount]; // Creates an array of orders
                
                // Assign each date to each order
                for (int i = 0; i < dateValue.length; i++) {
                    // Split the date value
                    int indexOfDivider = dateValue[i].indexOf('/');                                                                             // Locate the first divisor
                    int day = Integer.parseInt(dateValue[i].substring(0, indexOfDivider));                                    // Extract the first char to the one before the divisor
                    String currentDateValue = dateValue[i].substring(indexOfDivider, dateValue[i].length());    // Extract the new string, from the divisor to the end of the string
                    indexOfDivider = dateValue[i].indexOf('/');                                                                                    // Repeat the steps
                    int month = Integer.parseInt(dateValue[i].substring(0, indexOfDivider));                              // Extract the month
                    currentDateValue = dateValue[i].substring(indexOfDivider, dateValue[i].length());               // Extract the new string, from the divisor to the end of the string
                    indexOfDivider = dateValue[i].indexOf('/');                                                                                   // Repeat the steps
                   int year = Integer.parseInt(dateValue[i].substring(0, indexOfDivider));                         // Extract the year
                   
                    Date date = new Date();
                    
                    //NOTE: Apologize for using deprecated methods and classes, but it's the simplest I could understand and hence used due to time constraints
                    // Set date fields
                    date.setDate(day);
                    date.setMonth(month);
                    date.setYear(year);
                    
                    // Current ERROR: This returns nullexception
                    soList[i].setChosendate(date);
                }
                
                // Add into student object
                student.getStudentorderList().add(studOrder);
                
                // Save to database
                utx.begin();
                em.persist(studOrder);
                utx.commit();
                
                //Update session
                session.setAttribute("stud", student);
                session.setAttribute("studOrder", null);
                
                //Redirect to my orders page
                System.out.println("Payment successful");

            } catch (ConstraintViolationException ex) {
                ex.getConstraintViolations();
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
