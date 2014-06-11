package org.nelo.utils;


import org.nelo.entities.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private MailSender mailSender;

    public void sendMail(String from, String to, String subject, String msg) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        try {
            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEmailForInternRegistration(UserAccount userAccount) {
        String from = "blacksea.nelo@gmail.com";
        String to = userAccount.getEmail();
        String subject = "Welcome " + userAccount.getFirstName();
        String content = "Welcome " + userAccount.getFirstName() + " " + userAccount.getLastName() + ",\r\n\r\n" +
                "You are successful registered as " + userAccount.getRole().getDescription() + " at Black Sea Hotel.\r\n" +
                "Your password is: " + userAccount.getUserPassword() + "\r\n\r\n" +
                "Have a nice day, " + "\r\n" +
                "Black Sea Administration";

        sendMail(from, to, subject, content);
    }

}
