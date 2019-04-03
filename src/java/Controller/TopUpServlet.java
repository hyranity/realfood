/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Student;
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
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author mast3
 */
@WebServlet(name = "TopUpServlet", urlPatterns = {"/TopUpServlet"})
public class TopUpServlet extends HttpServlet {
    
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

        // Make sure staff is logged in
        HttpSession session = request.getSession(false);
        if (session.getAttribute("staff") == null) {
            request.setAttribute("errorMsg", "Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

        try {
            String studentId = request.getParameter("studentId");
            int creditAmt = Integer.parseInt(request.getParameter("cashAmt")) * 100;
            
            Student stud = new Student();
            stud = em.find(Student.class, studentId);
            
            // If the student ID is incorrect, show error messages and redirect to the previous page
            if(stud == null){
                request.setAttribute("errorMsg", "Oops! This student does not exist. Ensure that the student ID is correct.");
                request.getRequestDispatcher("topUp.jsp").forward(request, response);
                return;
            }
            
            
            // Update the credit amount
            stud.setCredits(stud.getCredits() + creditAmt);
            
            // Save it.
            utx.begin();
            em.merge(stud);
            utx.commit();
            
            request.setAttribute("successMsg", "Success! Student " + stud.getStudentid() + "'s credits have been topped up.");
            request.getRequestDispatcher("topUp.jsp").forward(request, response);
            return;
            
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "Oops! Ensure that you've entered the cash amount in numbers only.");
            request.getRequestDispatcher("topUp.jsp").forward(request, response);
            return;
        } catch (Exception e) {
            request.setAttribute("errorMsg", "Oops! Something went wrong. Ensure that you've keyed in everything correctly.");
            request.getRequestDispatcher("topUp.jsp").forward(request, response);
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
