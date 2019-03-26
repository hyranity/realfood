package DAO;

import Model.Food;
import Model.Meal;
import static DAO.FoodDAO.toggleDiscontinueMealFood;
import Exception.IDGenerationException;
import java.sql.Blob;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Auto;
import util.SQLUtil;

public class MealDAO {
    
    public static void main(String[] args){
        
    }
    
    //Create a new meal record
    public static void createMeal(Meal meal, ArrayList<Food> foodList){
        try {
            meal.setMealid(Auto.generateID("M", "meal", 10));
            meal.setIsdiscontinued(false);
            meal.setDateadded(Auto.getToday());
            meal.setDatediscontinued(null);
           //Set it 0 first
           meal.setTotalcalories(0);
           
           //Perform INSERT query
           insertRecord(meal);
           //Assign all chosen food to the meal
           for(Food food : foodList){
               FoodDAO.assignFoodToMeal(meal.getMealid(), food.getFoodid());
               
           }
           
           //Update the totalCalorie of the meal
           updateTotalCalories(meal.getMealid());
           
        } catch (IDGenerationException ex) {
            System.out.println("Meal cannot be created: " + ex.getMessage());
        }
    }
    
    //Updates the totalCalories of a specific meal
    public static void updateTotalCalories(String mealId){
        try {
            Connection conn = SQLUtil.connectDB();
            // Select the totalCalories by food.calories x mealfood.quantity of a specific meal and update that meal.totalcalories with the result
            PreparedStatement stmt = conn.prepareStatement("update meal set totalcalories = (select sum(f.CALORIES*mf.QUANTITY) from meal m, mealfood mf, food f where f.isDiscontinued = false and f.FOODID = mf.FOODID and mf.MEALID = m.MEALID and m.MEALID = ?) where mealid = ?");
            stmt.setString(1, mealId);
            stmt.setString(2, mealId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Cannot update totalCalories: " + ex.getMessage());
        }
    }
    
       //Updates the totalCalories of all meals
    public static void updateTotalCalories(){
        ArrayList<Meal> mealList = getRecords();
        
        for(Meal meal : mealList){
            updateTotalCalories(meal.getMealid());
        }
    }
    
    //Insert a new meal record
    public static void insertRecord(Meal meal){
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(SQLUtil.generateInsertQuery("meal"));
            stmt.setString(1, meal.getMealid());
            stmt.setString(2, meal.getMealname());
            stmt.setString(3, meal.getDescription());
            stmt.setInt(4, meal.getPrice());
            stmt.setBoolean(5, meal.getIsdiscontinued());
            stmt.setDate(6, SQLUtil.getSQLDate(meal.getDateadded()));
            stmt.setDate(7, null);
            stmt.setInt(8, meal.getTotalcalories());
            stmt.setString(9, meal.getMealimagelink());
            stmt.setBoolean(10, meal.getIsbreakfast());
            stmt.setBoolean(11, meal.getIslunch());
            
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        //getRecord
    public static ArrayList<Meal> getRecords() {
        ArrayList<Meal> mealList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from meal");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Meal meal = new Meal();
                meal.setMealid(rs.getString(1));
                meal.setMealname(rs.getString(2));
                meal.setDescription(rs.getString(3));
                meal.setPrice(rs.getInt(4));
                meal.setIsdiscontinued(rs.getBoolean(5));
                meal.setDateadded(rs.getDate(6));
                meal.setDatediscontinued(rs.getDate(7));
                meal.setTotalcalories(rs.getInt(8));
                meal.setMealimagelink(rs.getString(9));
                meal.setIsbreakfast(rs.getBoolean(10));
                meal.setIslunch(rs.getBoolean(11));
                mealList.add(meal);
            }
        } catch (SQLException ex) {
            System.out.println("Could not retrieve meal records: " + ex.getMessage());
        }
        
        return mealList;
    }
    
      // find the record with the indicated primary key
    public static Meal loadRecord(String id) {
        Meal meal = new Meal();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from meal where mealid = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                
                meal.setMealid(rs.getString(1));
                meal.setMealname(rs.getString(2));
                meal.setDescription(rs.getString(3));
                meal.setPrice(rs.getInt(4));
                meal.setIsdiscontinued(rs.getBoolean(5));
                meal.setDateadded(rs.getDate(6));
                meal.setDatediscontinued(rs.getDate(7));
                meal.setTotalcalories(rs.getInt(8));
                meal.setMealimagelink(rs.getString(9));
                meal.setIsbreakfast(rs.getBoolean(10));
                meal.setIslunch(rs.getBoolean(11));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return meal;
    }
    
        // update a single record
    public static void updateRecord(Meal meal) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("update meal set mealname = ?, description = ?, price = ?, isdiscontinued = ?, dateadded = ?, datediscontinued = ?, totalcalories = ?, mealimagelink = ?, isbreakfast = ?, islunch = ? where mealid = ?");
            
                
            stmt.setString(1, meal.getMealname());
            stmt.setString(2, meal.getDescription());
            stmt.setInt(3, meal.getPrice());
            stmt.setBoolean(4, meal.getIsdiscontinued());
            stmt.setDate(5, SQLUtil.getSQLDate(meal.getDateadded()));
            stmt.setDate(6, SQLUtil.getSQLDate(meal.getDatediscontinued()));
            stmt.setInt(7, meal.getTotalcalories());
            stmt.setString(8, meal.getMealimagelink());
            stmt.setString(9, meal.getMealid());
            stmt.setBoolean(10, meal.getIsbreakfast());
            stmt.setBoolean(11, meal.getIslunch());
           
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
     // Does appropriate procedures to discontinue a meal
    public static void discontinueMeal(String id, boolean discontinuationStatus){
        // Load the record into meal object
        Meal meal = loadRecord(id);
        
        // Set isdiscontinued = true
        meal.setIsdiscontinued(discontinuationStatus);
        
         // Toggle the attribute of the associative entity (Since meal is discontinued, so should its relations)
        toggleDiscontinueMealFood(id, true, discontinuationStatus);
        
        // Set the discontinueddate to today
        meal.setDatediscontinued(Auto.getToday());
        
        //Update the record
        updateRecord(meal);
    }
}
