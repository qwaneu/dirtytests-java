package eu.qwan.dirtytest.mocks;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mailer {
    public void send(String to, String from, String subject, String body) throws SendEmailFailed {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "11.22.33.44");
        Session session = Session.getDefaultInstance(properties);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new SendEmailFailed(e);
        }
    }
}
