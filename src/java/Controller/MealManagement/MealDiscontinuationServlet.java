/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.MealManagement;

import ExtendedClasses.Notifier;
import Model.Food;
import Model.Meal;
import Model.Mealfood;
import Model.Ordermeal;
import Model.Student;
import Model.Studentorder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@WebServlet(name = "MealDiscontinuationServlet", urlPatterns = {"/MealDiscontinuationServlet"})
public class MealDiscontinuationServlet extends HttpServlet {

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
        String mealId = "";
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
        if (!permission.equalsIgnoreCase("canteenStaff") && !permission.equals("manager")) {
            request.setAttribute("errorMsg", "You are not allowed to visit that page.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        } else {

            try {
                mealId = request.getParameter("mealId");

                // Obtain meal object from database
                Meal meal = em.find(Meal.class, mealId);
                System.out.println(meal.getMealid());

                // If the meal is currently discontinued
                if (meal.getIsdiscontinued()) {
                    // Toggle it
                    meal.setIsdiscontinued(false);
                    meal.setDatediscontinued(null);
                    request.setAttribute("successMsg", "Meal has been re-enabled.");
                } else {
                    // If the meal is currently not discontinued
                    // Toggle it
                    meal.setIsdiscontinued(true);
                    meal.setDatediscontinued(Auto.getToday());
                    request.setAttribute("successMsg", "Meal has been discontinued.");

                    // Get a list of affected students
                    List<Studentorder> orderList = new ArrayList();

                    boolean hasRelatedOrders = true;

                    try {
                        orderList = (List<Studentorder>) em.createQuery("SELECT so FROM Studentorder so WHERE so.iscanceled = false AND so.isredeemed = false").getResultList();
                    } catch (Exception ex) {
                        hasRelatedOrders = false;
                    }

                    if (hasRelatedOrders) {
                        for (Studentorder so : orderList) {
                            boolean isCanceled = false;
                            boolean stillHasUncanceled = false;
                            boolean isAffected = false;

                            //Refund them
                            Student stud = so.getStudentid();

                            // Get total price
                            int totalPrice = 0;
                            for (Ordermeal om : so.getOrdermealList()) {
                                System.out.println(om.getMealid().getMealid() + " AND " + meal.getMealid());
                                // Only if the associative entity has the meal, then cancel
                                if (om.getMealid().getMealid().equals(meal.getMealid())) {
                                    System.out.println("MATCH FOUND FOR ORDERMEAL " + om.getOrdermealid());
                                    if (!om.getIscanceled() && !om.getIsredeemed()) {
                                        System.out.println("Neither canceled nor redeemed");
                                        totalPrice = totalPrice + (om.getQuantity() * om.getMealid().getPrice());
                                        om.setIscanceled(true);
                                        isCanceled = true;
                                        isAffected = true; // This student is affected by the discontinuation
                                    }
                                }

                                if (!om.getIscanceled()) {
                                    stillHasUncanceled = true;
                                }
                            }

                            if (isAffected) {
                                if (isCanceled) {
                                    stud.setCredits(stud.getCredits() + totalPrice);
                                }

                                // Cancel the entire order if the only meal ordered is canceled
                                if (!stillHasUncanceled) {
                                    so.setIscanceled(true);
                                }

                                stud.setStudentorderList(orderList);
                                System.out.println(stud.getCredits());
                                // Update the student
                                utx.begin();
                                em.merge(stud);
                                utx.commit();

                                // Notify the student
                                Notifier notifier = new Notifier(em, utx);
                                String title = "Meal Discontinuation Refund";
                                String body = "Hey there! We are sorry to announce that we have discontinued the meal \"" + meal.getMealname() + "\", and hence your orders will no longer have this meal. Your order ( " + so.getOrderid() + ") has been affected. You have been fully refunded with " + totalPrice + " credits. Thank you for your understanding.";
                                notifier.notify(title, body, stud);
                            }
                        }
                    }

                }

                // Update the meal object
                utx.begin();
                em.merge(meal);
                utx.commit();

                utx.begin();
                // Get related list of Mealorder objects
                TypedQuery<Mealfood> query = em.createQuery("SELECT mf FROM Mealfood mf where mf.mealid = :mealId", Mealfood.class).setParameter("mealId", meal);
                List<Mealfood> mealFoodList = query.getResultList();

                for (Mealfood mf : mealFoodList) {
                    // If the mealFood is currently discontinued
                    if (mf.getIsdiscontinued()) {
                        // Toggle it
                        mf.setIsdiscontinued(false);
                    } else {
                        // If the mealFood is currently not discontinued
                        // Toggle it
                        mf.setIsdiscontinued(true);
                    }

                    //Update the objects
                    em.merge(mf);
                }

                // Update it
                em.merge(meal);
                utx.commit();

                //Update the meal in session
                session.setAttribute("meal", meal);

                request.getRequestDispatcher("mealDetailsEdit.jsp").forward(request, response);
                return;

            } catch (ConstraintViolationException e) {
                System.out.println(e.getConstraintViolations());
            } catch (Exception ex) {
                System.out.println("ERROR: Could not discontinue meal: " + ex.getMessage());
                request.setAttribute("errorMsg", "Oops! Meal discontinuation did not succeed for some reason.");
                request.getRequestDispatcher("mealDetailsFinalization.jsp").forward(request, response);
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
