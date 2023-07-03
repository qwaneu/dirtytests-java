package eu.qwan.dirtytest.mocks;

public class SendEmailFailed extends Exception {
    public SendEmailFailed(Throwable cause) {
        super("Sending email failed", cause);
    }
}
