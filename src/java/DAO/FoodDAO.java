/*
 * This DAO is for both Food and MealFood
 */
package DAO;

import Model.Food;
import Model.Mealfood;
import Exception.IDGenerationException;
import util.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

public class FoodDAO {
    
    public static void main(String[] args) {
        System.out.println("hi");
    }

    // Allows the creation of records (with automation of some fields)
    public static void createFood(String foodName, int calories) {
        try {
            Food food = new Food();
            food.setFoodid(Auto.generateID("F", "food", 7));
            food.setDateadded(Auto.getToday());
            food.setIsdiscontinued(false);
            insertRecord(food);
        } catch (IDGenerationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Assigns a food to a meal
    public static void assignFoodToMeal(String mealId, String foodId) {
        Mealfood mealFood = loadMealFood(mealId, foodId);
        
        // If such a connection is not made yet, make a connection between meal and food
        if (mealFood == null) {
            mealFood = new Mealfood();
            try {
                mealFood.setMealfoodid(Auto.generateID("MF", "mealfood", 12));
                mealFood.setFoodid(FoodDAO.loadRecord(foodId));
                mealFood.setMealid(MealDAO.loadRecord(mealId));
                mealFood.setIsdiscontinued(false);
                mealFood.setQuantity(1);
                insertRecord(mealFood);
            } catch (IDGenerationException ex) {
                System.out.println("Food-Meal assignation failed: " + ex.getMessage());
            }
        }
        // If there's already an existing connection, just increase the quantity by 1
        else{
            mealFood.setQuantity(mealFood.getQuantity()+1);
            updateRecord(mealFood);
        }
    }

    // Insert records
    public static void insertRecord(Food food) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(SQLUtil.generateInsertQuery("food"));
            stmt.setString(1, food.getFoodid());
            stmt.setString(2, food.getFoodname());
            stmt.setDate(3, SQLUtil.getSQLDate(food.getDateadded()));
            stmt.setBoolean(4, food.getIsdiscontinued());
            stmt.setDate(5, null);
            stmt.setInt(6, food.getCalories());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Insert records (associative entity)
    public static void insertRecord(Mealfood mealFood) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement(SQLUtil.generateInsertQuery("mealfood"));
            stmt.setString(1, mealFood.getMealfoodid());
            stmt.setString(2, mealFood.getMealid().getMealid());
            stmt.setString(3, mealFood.getFoodid().getFoodid());
            stmt.setInt(4, mealFood.getQuantity());
            stmt.setBoolean(5, mealFood.getIsdiscontinued());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("MealFood couldn't be created: " + ex.getMessage());
        }
    }

    //getRecord
    public static ArrayList<Food> getRecords() {
        ArrayList<Food> foodList = new ArrayList();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from food");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Food food = new Food();
                food.setFoodid(rs.getString(1));
                food.setFoodname(rs.getString(2));
                food.setDateadded(rs.getDate(3));
                food.setIsdiscontinued(rs.getBoolean(4));
                food.setDatediscontinued(rs.getDate(5));
                food.setCalories(rs.getInt(6));

                foodList.add(food);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return foodList;
    }

    // Returns a mealFood based on mealId and foodId 
    public static Mealfood loadMealFood(String mealId, String foodId) {
        Mealfood mealFood = new Mealfood();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from mealfood where mealid = ? and foodid = ?");
            stmt.setString(1, mealId);
            stmt.setString(2, foodId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                mealFood.setMealfoodid(rs.getString(1));
                mealFood.setMealid(MealDAO.loadRecord(rs.getString(2)));
                mealFood.setFoodid(FoodDAO.loadRecord(rs.getString(3)));
                mealFood.setQuantity(rs.getInt(4));
                mealFood.setIsdiscontinued(rs.getBoolean(5));

                return mealFood;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    

    // find the record with the indicated primary key
    public static Food loadRecord(String id) {
        Food food = new Food();
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("select * from food where foodid = ?");
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                food.setFoodid(rs.getString(1));
                food.setFoodname(rs.getString(2));
                food.setDateadded(rs.getDate(3));
                food.setIsdiscontinued(rs.getBoolean(4));
                food.setDatediscontinued(rs.getDate(5));
                food.setCalories(rs.getInt(6));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return food;
    }

    // update a single record (meal)
    public static void updateRecord(Food food) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("update food set foodname = ?, dateadded = ?, isdiscontinued = ?, discontinueddate = ?, calories = ? where foodid = ?");

            stmt.setString(1, food.getFoodname());
            stmt.setDate(2, SQLUtil.getSQLDate(food.getDateadded()));
            stmt.setBoolean(3, food.getIsdiscontinued());
            stmt.setDate(4, SQLUtil.getSQLDate(food.getDatediscontinued()));
            stmt.setInt(5, food.getCalories());
            stmt.setString(6, food.getFoodid());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // update a single record (mealfood)
    public static void updateRecord(Mealfood mealFood) {
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("update mealfood set mealid = ?, foodid = ?, quantity = ?, isdiscontinued = ? where mealfoodid= ?");

            stmt.setString(1, mealFood.getMealid().getMealid());
            stmt.setString(2, mealFood.getFoodid().getFoodid());
            stmt.setInt(3, mealFood.getQuantity());
            stmt.setBoolean(4, mealFood.getIsdiscontinued());
            stmt.setString(5, mealFood.getMealfoodid());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Does appropriate procedures to discontinue a food
    public static void toggleDiscontinueFood(String id, boolean discontinuationStatus) {
        // Load the record into food object
        Food food = loadRecord(id);

        // Toggle the attribute 
        food.setIsdiscontinued(discontinuationStatus);
        
        // Toggle the attribute of the associative entity (Since food is discontinued, so should its relations)
        toggleDiscontinueMealFood(id, false, discontinuationStatus); 

        // Set the discontinueddate to today
        food.setDatediscontinued(Auto.getToday());

        //Update the record
        updateRecord(food);
        
        // Update the 
    }
    
     // Does appropriate procedures to toggle discontinuation of a set of mealFood records using mealId or orderId
    public static void toggleDiscontinueMealFood(String id, boolean isMealDiscontinued, boolean discontinuationStatus) {
        String primaryKey = "";
        
        // isMealDiscontinued = true : Discontinue all the mealfood records based on mealid; False, discontinue based on foodid
        if(isMealDiscontinued)
            primaryKey = "mealid";
        else
            primaryKey = "foodid";

        //Update the record
        try {
            Connection conn = SQLUtil.connectDB();
            PreparedStatement stmt = conn.prepareStatement("update mealfood set isdiscontinued = ? where " + primaryKey +"= ?");

            stmt.setBoolean(1, discontinuationStatus);
            stmt.setString(2, id);

            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
