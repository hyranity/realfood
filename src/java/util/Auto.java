/*
 * The purpose of this util class is to simplify programming.
 */
package util;

import Exception.IDGenerationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;


public class Auto {
    
    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Calendar today = Auto.dateToCal(Auto.getToday());
        System.out.println(daysBetween(today, cal));
    }
    
    public static LocalDate getLocalDate(Calendar cal){
        TimeZone tz = cal.getTimeZone();
        DateTimeZone jodaTz = DateTimeZone.forID(tz.getID());
        DateTime dateTime = new DateTime (cal.getTimeInMillis(), jodaTz);
        
        return dateTime.toLocalDate();
    }
    
    public static int daysBetween(Calendar cal1, Calendar cal2){
        return Days.daysBetween(getLocalDate(cal1), getLocalDate(cal2)).getDays();
    }
    
    
    // Returns today's date as Date object
    public static Date getToday(){
        Date date = new Date();
        return date;
    }
    
    // Get month as string, return int
    public static int getMonthInt(String month) {
        int monthNum = 0;

        if (month.equalsIgnoreCase("january")) {
            monthNum = 0;
        } else if (month.equalsIgnoreCase("february")) {
            monthNum = 1;
        } else if (month.equalsIgnoreCase("march")) {
            monthNum = 2;
        } else if (month.equalsIgnoreCase("april")) {
            monthNum = 3;
        } else if (month.equalsIgnoreCase("may")) {
            monthNum = 4;
        } else if (month.equalsIgnoreCase("june")) {
            monthNum = 5;
        } else if (month.equalsIgnoreCase("july")) {
            monthNum = 6;
        } else if (month.equalsIgnoreCase("august")) {
            monthNum = 7;
        } else if (month.equalsIgnoreCase("september")) {
            monthNum = 8;
        } else if (month.equalsIgnoreCase("october")) {
            monthNum = 9;
        } else if (month.equalsIgnoreCase("november")) {
            monthNum = 10;
        } else if (month.equalsIgnoreCase("december")) {
            monthNum = 11;
        } else {
            monthNum = -1;
        }

        return monthNum;
    }
    
    // Shortens the equalsIgnoreCase method
    public static boolean eqIgnore(String str1, String str2){
        if(str1.equalsIgnoreCase(str2))
            return true;
        else
            return false;
    }
    
    public static String dateToString(Date date){
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        int year = cal.get(Calendar.YEAR);
        
        return  day + " " + month + ", " + year;
    }
    
    //Generate ID based on total existing records
    public static String generateID(String prefix, String tableName, int size) throws IDGenerationException{
        int count = SQLUtil.countRecords(tableName);
        
        if(size <= prefix.length())
                throw new IDGenerationException();
        
        
        int totalEntries = count;
        totalEntries += 1;
        
        String id = "";
        String num = "" + totalEntries;
        
        for(int i=0; i<(8-num.length()); i++){
            id +="0";
        }
        
        return prefix + id + num;
    }
    
        // Generate ID based on a number of records (Normally used for deletion)
        public static String generateID(String prefix, int size, int count) throws IDGenerationException{
           String countStr = count + "";
        
        if(size <= prefix.length())
                throw new IDGenerationException();
        if(size <= countStr.length() )
            throw new IDGenerationException();
        
       int totalEntries = count+1;
        
        String id = "";
        String num = "" + totalEntries;
        
        for(int i=0; i<(size - num.length() - prefix.length()); i++){
            id +="0";
        }
        
        return prefix + id + num;
    }
    
    public static String extractIdNum(String id, char prefix){
        String currentId = null;
        int i=0;
        
        while(id.charAt(i)=='0' || id.charAt(i)==prefix){
          currentId = id.substring(i+1, id.length());
          i++;
        }

        return currentId;
     }
    
    public static String extractIdNum(String id, char prefix1, char prefix2){
        String currentId = null;
        int i=0;
        
        while(id.charAt(i)=='0' || id.charAt(i)==prefix1 || id.charAt(i) == prefix2){
          currentId = id.substring(i+1, id.length());
          i++;
        }

        return currentId;
     }
    
    public static void print(String text){
        System.out.println(text);
    }
    
        // The following 2 methods of code are thanks to: https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
    //Converts object to an array of bytes
    public static byte[] getByteArray(Serializable obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] byteArray = null;

        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            byteArray = bos.toByteArray();
            bos.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return byteArray;
    }

    // Converts an array of bytes to an object
    public static Object getObject(byte[] byteArray) {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInput in = null;
        Object obj = null;
        try {
            in = new ObjectInputStream(bis);
            obj = in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return obj;
    }
    
    public static Calendar dateToCal(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        return cal;
    }
    
    public static Date calToDate(Calendar cal){
        Date date = cal.getTime();
        return date;
    }
    
    
    
   
}
