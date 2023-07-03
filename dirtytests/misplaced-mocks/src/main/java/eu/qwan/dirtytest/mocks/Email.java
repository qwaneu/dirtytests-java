package eu.qwan.dirtytest.mocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Email {
    private static final Logger LOG = LoggerFactory.getLogger(Email.class);
    private final String sender;
    private final String receiver;
    private final String subject;
    private final String body;

    public Email(String sender, String receiver, String subject, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public boolean send(Mailer mailer) {
        try {
            mailer.send(receiver, sender, subject, body);
            return true;
        } catch (SendEmailFailed sendEmailFailed) {
            LOG.error("Email failure: {}", sendEmailFailed.getMessage());
            return false;
        }
    }
}
