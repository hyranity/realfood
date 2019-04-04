/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Model.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class AdminReset {

    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;

    public static void main(String[] args) {
        AdminReset ar = new AdminReset();
       ar.getManager();
    }
    
    public void getManager(){
        try {
            utx.begin();
            //Searches the "external database" School System for an enrolled student with the given ID.
            TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.staffid = :staffid and s.staffrole = :role", Staff.class).setParameter("staffid", "EMPMAN").setParameter("role", "manager");
            Staff manager = query.getSingleResult();
            utx.commit();
            System.out.println(manager.getFirstname());
        } catch (NotSupportedException ex) {
            Logger.getLogger(AdminReset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(AdminReset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(AdminReset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(AdminReset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(AdminReset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(AdminReset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdminReset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void resolveAdmin() {
        Staff staff = getAdminAccount();

        
        if (staff == null) {
            staff = createAdminAccount();

            try {
                utx.begin();
                em.persist(staff);
                utx.commit();
            } catch (Exception e) {
                System.out.println("Unable to resolve admin account: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            staff = createAdminAccount();

            try {
                utx.begin();
                em.merge(staff);
                utx.commit();
            } catch (Exception e) {
                System.out.println("Unable to resolve admin account: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Staff createAdminAccount() {
        // Set default values
        Staff staff = new Staff();
        staff.setStaffid("EMPMAN");
        staff.setEmail("M");
        staff.setDatejoined(Auto.getToday());
        staff.setGender('M');
        staff.setFirstname("Admin");
        staff.setLastname("Admin");
        staff.setMykad("Admin");
        staff.setStaffrole("manager");
        staff.setIshired(true);

        // Set a default password & password salt
        Hasher hasher = new Hasher("rfmanager");
        staff.setPassword(hasher.getHashedPassword());
        staff.setPasswordsalt(hasher.getSalt());

        return staff;
    }

    public Staff getAdminAccount() {
        Staff manager = new Staff();

        try {
            TypedQuery<Staff> query = em.createQuery("SELECT s FROM Staff s WHERE s.staffid = :staffid and s.staffrole = :role", Staff.class).setParameter("staffid", "EMPMAN").setParameter("role", "manager");
             manager = query.getSingleResult();

        } catch (Exception e) {
            System.out.println("ERROR: Unable to get admin account: " + e.getMessage());
            e.printStackTrace();
        }

        return manager;
    }

}
