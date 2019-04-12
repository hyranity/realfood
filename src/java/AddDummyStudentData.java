/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Schoolsystemstudent;
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
import util.Auto;

/**
 *
 * @author mast3
 */
@WebServlet(urlPatterns = {"/AddDummyStudentData"})
public class AddDummyStudentData extends HttpServlet {
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
        
        // JUST RUN THIS THING TO ADD DUMMY STUDENT DATA
        try {
            
            Schoolsystemstudent ss = new Schoolsystemstudent();
            ss.setDatejoined(Auto.getToday());
            ss.setFirstname("Johann");
            ss.setLastname("Lee");
            ss.setIsenrolled(true);
            ss.setGender('M');
            ss.setMykad("00000-00-0000");
            ss.setStudentemail("johannljx@test.edu.my");
            ss.setStudentid("STU01");
            utx.begin();
            em.persist(ss);
            utx.commit();
           
            ss.setDatejoined(Auto.getToday());
            ss.setFirstname("Ryan");
            ss.setLastname("Koroh");
            ss.setIsenrolled(true);
            ss.setGender('M');
            ss.setMykad("00000-00-0000");
            ss.setStudentemail("ryanirk@test.edu.my");
            ss.setStudentid("STU02");
            utx.begin();
            em.persist(ss);
            utx.commit();
            
            ss.setDatejoined(Auto.getToday());
            ss.setFirstname("John");
            ss.setLastname("Cena");
            ss.setIsenrolled(false);
            ss.setGender('M');
            ss.setMykad("00000-00-0000");
            ss.setStudentemail("johncena@test.edu.my");
            ss.setStudentid("STU03");
            utx.begin();
            em.persist(ss);
            utx.commit();
            
            // Reset admin
            response.sendRedirect("AdminResetServlet");
            
        } catch (Exception e) {
            e.printStackTrace();
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
