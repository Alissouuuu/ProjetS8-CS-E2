package utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.Session;


public class MailSender {

    public static boolean envoyerEmail(String sujet, String contenu, String destinataire) {
        final String expediteurEmail = "ange.esig@gmail.com"; 
        final String motDePasse = "lcxdgfisahgecjzt"; // mot de passe d'application Gmail
        
//lcxd gfis ahge cjzt
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(expediteurEmail, motDePasse);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(expediteurEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setContent(contenu, "text/plain; charset=UTF-8");


            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
