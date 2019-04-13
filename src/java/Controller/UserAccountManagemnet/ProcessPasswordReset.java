/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.UserAccountManagemnet;

import Model.Staff;
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
import util.Hasher;

/**
 *
 * @author mast3
 */
@WebServlet(name = "ProcessPasswordReset", urlPatterns = {"/ProcessPasswordReset"})
public class ProcessPasswordReset extends HttpServlet {

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

        String token = "";
        String inputToken = "";
        String newPassword = "";

        try {
            token = (String) session.getAttribute("token");
            inputToken = request.getParameter("inputToken");
            
            
            if (inputToken == null) {
                System.out.println("Input token is null");
                request.setAttribute("errorMsg", "No password reset is in progress.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
                
            }
        } catch (Exception ex) {
            // Display error messages if any
            request.setAttribute("errorMsg", "No password reset is in progress.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (!token.equals(inputToken)) {
            System.out.println("token is different");
            request.setAttribute("errorMsg", "Oops! Password reset failed. Make sure you click the link in your email.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            // Hash the new password
            Hasher hasher = new Hasher(request.getParameter("password"));

            // Get the data
            String type = (String) session.getAttribute("type");
            Student stud = new Student();
            Staff staff = new Staff();

            if (type.equalsIgnoreCase("staff")) {
                // If user is staff, reset password of staff
                staff = (Staff) session.getAttribute("user");
                staff.setPassword(hasher.getHashedPassword());
                staff.setPasswordsalt(hasher.getSalt());
                utx.begin();
                em.merge(staff);
                utx.commit();
                
                
            } else if (type.equalsIgnoreCase("student")) {
                // If user is staff, reset password of staff
                stud = (Student) session.getAttribute("user");
                stud.setPassword(hasher.getHashedPassword());
                stud.setPasswordsalt(hasher.getSalt());
                utx.begin();
                em.merge(stud);
                utx.commit();
            } else {
                //ERROR, unknown user detected
                request.setAttribute("errorMsg", "Oops! Something went wrong during resetting password.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            
            //Password success
            System.out.println("Password reset!");
            session.invalidate();
            request.setAttribute("successMsg", "Your password has been reset.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;

        } catch (Exception ex) {
            // Display error messages if any
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
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
