package util;

import java.util.Random;

//Algorithm made possible thanks to polygenelubricants @ https://stackoverflow.com/questions/2626835/is-there-functionality-to-generate-a-random-character-in-java
public class CodeGenerator {
    String charPool; //Size of the character pool from which the characters are chosen form to create the code in a random manner
    
    
    public CodeGenerator(String poolSize){
        if(poolSize.equalsIgnoreCase("numletters"))
            charPool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        else if(poolSize.equalsIgnoreCase("num"))
            charPool = "0123456789";
        else
            charPool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    }
    
    public CodeGenerator(){
        charPool = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    }
    
    public String generateCode(){
        
        Random r = new Random();
        String code = "";
        
        for(int i=0; i<6; i++){
            code += charPool.charAt(r.nextInt(charPool.length()));
        }
        
        return code;
    }
    
    public String generateCode(int length){
        
        Random r = new Random();
        String code = "";
        
        for(int i=0; i<length; i++){
            code += charPool.charAt(r.nextInt(charPool.length()));
        }
        
        return code;
    }
}
