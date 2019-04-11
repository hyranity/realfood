/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Studentorder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import javax.transaction.UserTransaction;
import util.CodeGenerator;

/**
 *
 * @author mast3
 */
@WebServlet(urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet {

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
        // COUPON CODE GENERATION
        List<Studentorder> currentOrderList = new ArrayList();     // Get the current order list
        Studentorder[] soA = new Studentorder[10];
        for (int i = 0; i < 10; i++) {
            soA[i] = new Studentorder();

        }
        
        String couponCode = "";
        int loopCount = 0;
        // Create the orders. One order for each date
        for (int i = 0; i < soA.length; i++) {
            loopCount = 0;
            // COUPON CODE GENERATION
            boolean codeExists = false;
            boolean codeExistsInDB = false;
            do {
                codeExists = false;
                codeExistsInDB = false;
                loopCount++;
                try {
                    //Generate coupon code
                    CodeGenerator codeGenerator = new CodeGenerator("num");
                    couponCode = codeGenerator.generateCode(1);

                    // Check database to ensure that there's no duplication
                    TypedQuery<Studentorder> checkQuery = em.createQuery("SELECT so FROM Studentorder so WHERE so.couponcode = :couponCode", Studentorder.class).setParameter("couponCode", couponCode);
                    Studentorder tempStudentOrder = checkQuery.getSingleResult();
                  
                        if (tempStudentOrder.getOrderid() != null) {
                            codeExists = true;
                            codeExistsInDB = true;
                            System.out.println("Same coupon in DB! = " + couponCode);
                        }
                        else
                            codeExists = false;

                } catch (NoResultException e) {
                    // No problem if no results or is null
                }
                
                if(!codeExistsInDB){
                 // Check the orderList to ensure that there's also no duplication
                    for (int j = 0; j < currentOrderList.size(); j++) {
                        if (currentOrderList.get(j).getCouponcode().equals(couponCode)) {
                            codeExists = true;
                            break;
                        }
                        else{
                            codeExists = false;
                        }
                    }
                }
                 if(loopCount == 100){
                    System.out.println("ERROR: LOOP BREAK TO STOP INFINITE LOOP! ");
                     System.out.println("");
                    break;
                }
                
            } while (codeExists || codeExistsInDB);

            if(!codeExists){
            soA[i].setCouponcode(couponCode);
            currentOrderList.add(soA[i]);
            System.out.println(couponCode);
            }
            else{
                System.out.println("ERROR: Cannot add code " + couponCode + " as it already exists.");
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
