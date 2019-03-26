package util;

import java.security.*;

// This class is made possible thanks to Michael Wiggins @ https://stackoverflow.com/questions/29782631/simple-password-encryption-how-do-i-do

/* 
Note: For password checking, use the SAME salt for the entered password as the one being entered the first time
Hence need to store the original password's salt

Example:
Hasher hasher = new Hasher("hi");       < = Original password at account registration
Hasher hasher2 = new Hasher("hi", hasher.getSalt()); < = password entered at login
*/

public class Hasher {
    private String salt;
    private String password;
    private String hashedPassword;
    
    //no-arg constructor
    public Hasher(){
        
    }
    
    //parameterized constructor for first-time password storing
    public Hasher(String password){
        try{
            hash(password);
        }
        catch(NoSuchAlgorithmException | NoSuchProviderException ex){
            System.out.println(ex);
        }
    }
    
    //parameterized constructor for  password verification
    public Hasher(String password, String salt){
        try{
            hash(password, salt);
        }
        catch(NoSuchAlgorithmException | NoSuchProviderException ex){
            System.out.println(ex);
        }
    }

    //Process the hashing algorithm with a random salt and store result in data fields
    public void hash(String password) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.salt = createSalt();
        this.hashedPassword = hashPassword(password, salt);
    }
    
    //Process the hashing algorithm with a pre-determined salt and store result in data fields
    public void hash(String password, String salt) throws NoSuchAlgorithmException, NoSuchProviderException { 
        this.hashedPassword = hashPassword(password, salt);
    }

    //hashes the password
    public String hashPassword(String password, String salt) {
        String hashedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());

            //Convert the bytes to hex
            StringBuilder str = new StringBuilder();

            for (int i = 0; i < bytes.length; i++) {
                str.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            hashedPassword = str.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }

        return hashedPassword;
    }

    //Randomly creates the salt
    public String createSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        String saltStr = null;
        
        //Create a randomizer
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        
        //Create a byte array to store salt
        byte[] salt = new byte[16];
        
        //Using the generator, a random salt is generated
        sr.nextBytes(salt);
        
        
        //Convert the array of bytes of the salt into a string
        //Thanks to CoasterChris & omerkudat @ https://stackoverflow.com/questions/1536054/how-to-convert-byte-array-to-string-and-vice-versa
        try{
             saltStr = new String(salt, "UTF-8");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return saltStr;
    }
    
    //Setters
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setSalt(String salt){
        this.salt = salt;
    }
    
    public void setHashedPassword(String hashedPassword){
        this.hashedPassword = hashedPassword;
    }
    
    //Getters
    public String getPassword(){
        return this.password;
    }
    
    public String getSalt(){
        return this.salt;
    }
    public String getHashedPassword(){
        return this.hashedPassword;
    }
}
