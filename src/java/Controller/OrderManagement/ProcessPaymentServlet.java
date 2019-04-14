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

                // Get the latest student details
                student = em.find(Student.class, student.getStudentid());
                
                 // Charge the student
                    student.setCredits(student.getCredits() - (studOrder.getTotalprice()));
                    
                
                // Add to database
                    utx.begin();
                    em.merge(student);
                    utx.commit();

                //For generating ID
                TypedQuery<Studentorder> query = em.createQuery("SELECT s FROM Studentorder s", Studentorder.class);
                int count = query.getResultList().size();

                // Create duplicate date objects, depending on how many dates have been chosen
                List<Date> chosenDates = (List<Date>) session.getAttribute("chosenDates");
                int dayCount = chosenDates.size();
                


                List<Studentorder> currentOrderList = student.getStudentorderList();     // Get the current order list

                // Create the orders. One order for each date
                for (int i = 0; i < chosenDates.size(); i++) {
                    
                    // Initiate the element
                    Studentorder so= studOrder;
                    
                 

                    // COUPON CODE GENERATION
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

                            if (!codeExistsInDB) {
                                // Check the orderList to ensure that there's also no duplication
                                for (int x = 0; x < currentOrderList.size(); x++) {
                                    if (currentOrderList.get(i).getCouponcode().equals(couponCode)) {
                                        codeExists = true;
                                        break;
                                    } else {
                                        codeExists = false;
                                    }
                                }
                            }
                            if (loopCount == 100) {
                                System.out.println("ERROR: LOOP BREAK TO STOP INFINITE LOOP! ");
                                System.out.println("");
                                break;
                            }

                        } while (codeExists || codeExistsInDB);

                        // If code is unique, add into the list
                        if (!codeExists) {
                            studOrder.setCouponcode(couponCode);
                            currentOrderList.add(studOrder);
                        } else {
                            System.out.println("ERROR: Cannot add code " + couponCode + " as it already exists.");
                        }
                        
                    // Set all the necessary fields
                    studOrder.setChosendate(chosenDates.get(i));       // Store the date into the list of student orders
                    studOrder.setStudentid(student); // Also set student ID
                    studOrder.setIscanceled(false);
                    studOrder.setCouponcode(couponCode);
                    studOrder.setIsredeemed(false);
                    studOrder.setTotalprice(studOrder.getTotalprice());
                    
                    studOrder.setOrderid(Auto.generateID("O", 10, count + i));    // Set order ID 
                    
                    for(Ordermeal om : studOrder.getOrdermealList()){
                        om.setOrderid(studOrder);
                    }
               
                    currentOrderList.add(studOrder);
                    

                    
                    
                    // Add to database
                    utx.begin();
                    em.persist(studOrder);
                    utx.commit();
                }
                
                

                //Update session
                session.setAttribute("stud", student);
                session.setAttribute("studOrder", null);

                //Redirect to my orders page
                request.setAttribute("successMsg", "You have successfully made an order.");
            request.getRequestDispatcher("DisplayOrdersServlet").forward(request, response);
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
