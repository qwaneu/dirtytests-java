package eu.qwan.dirtytest.mocks;

import java.util.Optional;
import java.util.UUID;

public class PasswordResetController {
    private static final String SERVICEDESK = "servicedesk@qwan.eu";
    
    private final EmailFactory emailFactory;
    private final UserRepository userRepository;
    private final Mailer mailer;

    public PasswordResetController(EmailFactory emailFactory, UserRepository userRepository, Mailer mailer) {
        this.emailFactory = emailFactory;
        this.userRepository = userRepository;
        this.mailer = mailer;
    }

    public void resetPassword(String userId) {
        Optional<User> user = userRepository.byId(userId);
        user.ifPresent(u -> {
            String link = createPasswordResetLink();
            boolean sendSuccess = sendEmailTo(u, link);
            if (!sendSuccess) {
                sendEmailToSD();
            }
        } );
        user.orElseGet(() -> {
            sendEmailToSD();
            return null;
        });
    }

    private String createPasswordResetLink() {
        return UUID.randomUUID().toString();
    }

    private boolean sendEmailTo(User u, String link) {
        Email email = emailFactory.create(u.getEmailAddress(), "PASSWORD_RESET", link);
        return email.send(mailer);
    }

    private void sendEmailToSD() {
        Email sdEmail = emailFactory.create(SERVICEDESK, "SD", null);
        sdEmail.send(mailer);
    }
}
