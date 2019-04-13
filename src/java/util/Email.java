package util;

// This code is possible thanks to : https://www.tutorialspoint.com/java/java_sending_email.htm
// As well as https://www.javatpoint.com/example-of-sending-email-using-java-mail-api-through-gmail-server
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {

    private String from = "realfoodcanteen@gmail.com";
    private String host = "smtp.gmail.com";
    private MimeMessage message;

    public Email(String recipient, String title, String body) {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        
        // Create session
        Session session = createSession(props);
        
        // Construct the email itself
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(title);
            message.setText(body);

        } catch (MessagingException ex) {
            // Display error messages if any
            ex.printStackTrace();
        }
    }
    
    public void sendEmail(){
        try {
            // Send message
            Transport.send(message);
        } catch (Exception ex) {
            // Display error messages if any
            ex.printStackTrace();
        }
    }
    
    private Session createSession(Properties props){
        return Session.getInstance(props, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(from, "Jl303803");
            }
        });
        
        
        
    }
    
    public static void main(String[] main){
        Email email = new Email("johannljx@gmail.com", "YO!", "DON DA YO!");
        email.sendEmail();
    }


}
