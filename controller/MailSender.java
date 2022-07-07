package controller;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import dao.SettingsDao;

public class MailSender {
	private String host = "smtp.gmail.com";
	private String port = "465";
	private String from = "noreply.maxlechien@gmail.com";
	private String subject = "[Max!] Détection d'un aboiement";
	private String text = "Votre chien vient d'aboyer. \r\n \r\n Max!";
	private SettingsDao settingsDao = new SettingsDao();
	
	public void send() throws AddressException, MessagingException {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
        String password = GetPassword();
        String to = GetRecipient();
        
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }

        });
        
        // Used to debug SMTP issues
        session.setDebug(true);

        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject(subject);

        // Now set the actual message
        message.setText(text);

        System.out.println("Sending...");
        // Send message
        Transport.send(message);
        System.out.println("Sent message successfully");

        
	}
	
	private String GetPassword() {
		return settingsDao.GetSettings().getEmailKey();
	}
	
	private String GetRecipient() {
		return settingsDao.GetSettings().getEmailAddress();
	}
}
