/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.common;

import com.model.admin.UserDB;
import com.model.notification.Notification;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ETS-4
 */
public class MailUtil {

    private static final String MAIL_ID = "";
    private static final String MAIL_PASSWORD = "";
    private static final String MAIL_SMTP_HOST = "smtp.zoho.com";
    private static final String MAIL_VERIFY_LINK = "http://localhost:8084/rvn/verifyEmail?";
    private static final String MAIL_BASE_URL = "http://localhost:8084/rvn/";

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

        Pattern pat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public static boolean sendVerificationMail(String to, String subject, int userId, String verificationId) throws UnsupportedEncodingException {
        boolean status = false;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", MAIL_SMTP_HOST);
        props.put("mail.smtp.port", "587");
        //props.put("mail.debug", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_ID, MAIL_PASSWORD);
            }
        });

        String link = MAIL_VERIFY_LINK + "scope=activation&userId=" + userId + "&verifyId=" + verificationId;

        StringBuilder bodyText = new StringBuilder();
        bodyText.append("<div>")
                .append("  Dear User<br/><br/>")
                .append("  Thank you for registration. Your mail(" + to + ") is under verification<br/>")
                .append("  Please click <a href=\"" + link + "\">here</a> or open below link in browser<br/>")
                .append("  <a href=\"" + link + "\">" + link + "</a>")
                .append("  <br/><br/>")
                .append("  Thanks,<br/>")
                .append("  Mahindra Team")
                .append("</div>");

        try {
            //System.out.println("Mail!!!");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MAIL_ID,"Alias Name"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(bodyText.toString(), "text/html; charset=utf-8");

            Transport.send(message);
            status = true;
            //System.out.println("Mail Sent Succesfully!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
    
    public static boolean sendNotificationMail(List<String> to, String subject, Notification notification) throws UnsupportedEncodingException {
        boolean status = false;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", MAIL_SMTP_HOST);
        props.put("mail.smtp.port", "587");
        //props.put("mail.debug", "true");

        Session session = Session.getInstance(props,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_ID, MAIL_PASSWORD);
            }
        });
        
        String userName = UserDB.getUserNamebyID(notification.getSender_id());
        StringBuilder bodyText = new StringBuilder();
        bodyText.append("<div>")
                .append("  Dear User,<br/><br/>")
                .append("  "+userName+" has created "+VersionType.fromId(notification.getVersion_type_id())+" "+notification.getVersion_id()+" Succesfully<br/>")
                .append("  <br/><br/>")
                .append("  Thanks,<br/>")
                .append("  Mahindra Team")
                .append("</div>");
        
        try {
            //System.out.println("Mail!!!");
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(MAIL_ID,"Alias Name"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(String.join(",", to),true));
            message.setSubject(subject);
            message.setContent(bodyText.toString(), "text/html; charset=utf-8");

            Transport.send(message);
            status = true;
            System.out.println("Mail Sent Succesfully!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return status;
    }
}
