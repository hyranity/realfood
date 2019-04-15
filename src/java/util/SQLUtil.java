/**
 * This utility class serves to make programming SQL stuff easier.
 */
package util;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLUtil {

    // Counts how many records are there in a table
    public static int countRecords(String tableName) {
        int recordCount = 0;
        try {
            Connection conn = connectDB();
            PreparedStatement stmt = conn.prepareStatement("select count(*) as totalRecords from " + tableName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                recordCount = rs.getInt("totalRecords");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return recordCount;
    }
    
    

    // Deletes all records of a table
    public static int clearAll(String tableName) {
        int recordCount = 0;
        try {
            Connection conn = connectDB();
            PreparedStatement stmt = conn.prepareStatement("delete from " + tableName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return recordCount;
    }

    // Counts how many records are there in a table based on a custom query
    public static int countRecordsWithCustomQuery(String customQuery) {
        int recordCount = 0;
        try {
            Connection conn = connectDB();
            PreparedStatement stmt = conn.prepareStatement(customQuery);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                recordCount = rs.getInt("totalRecords");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return recordCount = 0;
    }

    


    public static Connection connectDB() throws SQLException {
        return DriverManager.getConnection("jdbc:derby://localhost:1527/realfood", "taruc", "taruc");
    }

    // This code is possible thanks for info from https://stackoverflow.com/questions/2614416/how-to-get-the-number-of-columns-from-a-jdbc-resultset
    public static int countColumns(String tableName) throws SQLException {
        Connection conn = connectDB();
        PreparedStatement stmt = conn.prepareStatement("select * from " + tableName);
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();

        return rsmd.getColumnCount();
    }

    // Generates the INSERT query based on the table.
    public static String generateInsertQuery(String tableName) {
        String query = null;
        try {
            Connection conn = connectDB();
            int columnCount = countColumns(tableName); // Obtain the number of columns of the specified table
            String attributes = "?";

            // For each column, add a '?' into the query
            if (columnCount > 1) {
                for (int i = 1; i < columnCount; i++) {
                    attributes += ",?";
                }

            }

            // Combine the query with the variables
            query = "insert into " + tableName + " values(" + attributes + ")";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return query;
    }

    public static java.sql.Date getSQLDate(java.util.Date date) {
        java.sql.Date SQLdate = new java.sql.Date(date.getTime());

        return SQLdate;
    }



}
