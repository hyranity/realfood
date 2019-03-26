/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtendedClasses;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

public class Meal extends Model.Meal{
    
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    public void updateTotalCalories(){
        em.createQuery("update meal set totalcalories = (select sum(f.CALORIES*mf.QUANTITY) from meal m, mealfood mf, food f where f.isDiscontinued = false and f.FOODID = mf.FOODID and mf.MEALID = m.MEALID and m.MEALID LIKE :mealid) where mealid like :mealid").setParameter("mealid", this.getMealid()).executeUpdate(); 
    }
}
