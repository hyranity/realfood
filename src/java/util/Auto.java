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


public class Auto {
    
    public static void main(String[] args){
        try {
            System.out.println(generateID("MFsdsd", 12, 10));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // Returns today's date as Date object
    public static Date getToday(){
        Date date = new Date();
        return date;
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
    
    public Calendar dateToCal(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        return cal;
    }
    
    public Date calToDate(Calendar cal){
        Date date = cal.getTime();
        return date;
    }
    
    
    
   
}
