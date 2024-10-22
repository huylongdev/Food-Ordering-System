package util;

import java.util.Properties;
import java.util.Random;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import model.Account;

public class Email {
    private static final String fromEmail = "tientpde180866@fpt.edu.vn";
    private static final String password = "yqve wbss xvde mfns";

    public static String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static boolean sendEmail(Account user) {
        String toEmail = user.getEmail();

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + user.getCode());

            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public static boolean sendEmailResetPassword(String email, String resetLink) {
        String toEmail = email;

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Reset Your Password");
            message.setText("Xin chào,\n\n"
                    + "Chúng tôi nhận được yêu cầu thiết lập lại mật khẩu cho tài khoản Foodie của bạn.\n\n"
                    + "Nhấn vào đường dẫn sau để thiết lập mật khẩu mới cho tài khoản Foodie của bạn:\n"
                    + resetLink + "\n\n"
                    + "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.");

            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public static boolean sendEmailNotifying(String email, String content) {

        String toEmail = email;

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject("New Status Of Your Order!");
            message.setText("Dear, \n" + content + "\nThank you for choosing us, and we look forward to serving you again.\nBest regards,\nFoodie Ordering System.");



            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public static boolean sendEmailToApproveRegisterRestaurant(String ownerName, String email) {

        String toEmail = email;

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject("Notification of Shop Registration on the Order Platform");
            message.setText("Dear " + ownerName + ",\n\n"
                + "We are pleased to inform you that your shop registration on our order platform has been successfully approved.\n\n"
                + "You can now access and manage your shop on Foodie 24/7.\n\n"
                + "If you have any questions or require further assistance,  please feel free to contact us via email huydqds180257@fpt.edu.vn or support phone number 0795993433.\n\n"
                + "Thank you for choosing our platform, and we look forward to a successful collaboration.\n\n"
                + "Best regards,\n"
                + "Support Team\n"
                + "Foodie");


            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public static boolean sendEmailToRejectRegisterRestaurant(String ownerName, String email, String reason) {
        String toEmail = email;

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Notification of Shop Registration on the Order Platform");
            message.setText("Dear " + ownerName + ",\n\n"
                + "We regret to inform you that your shop registration on our order platform has not been accepted.\n\n"
                + "Reason for rejection: " + reason + "\n\n"
                + "You may review your registration information and make the necessary adjustments. If you require further information or assistance, please feel free to contact us via email huydqds180257@fpt.edu.vn or support phone number 0795993433.\n\n"
                + "We look forward to hearing from you soon and hope for the opportunity to cooperate in the future.\n\n"
                + "Best regards,\n"
                + "Support Team\n"
                + "Foodie");



            Transport.send(message);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
