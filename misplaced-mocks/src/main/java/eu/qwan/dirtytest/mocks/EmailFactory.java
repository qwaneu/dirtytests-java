package eu.qwan.dirtytest.mocks;

import java.util.HashMap;
import java.util.Map;

public class EmailFactory {
    private static final String SENDER = "info@qwan.eu";

    private final Map<String, String> bodies = new HashMap<String, String>() {{
        put("PASSWORD_RESET", "Hello %s, \nPlease click this link to reset your password: %s\nKind regards,\nQWAN");
        put("SD", "User %s wants to reset his/her password, but sending the email failed");
    }};

    private final Map<String, String> subjects = new HashMap<String, String>() {{
        put("PASSWORD_RESET", "Password reset");
        put("SD", "Problem notification");
    }};

    public Email create(String receiver, String type, String link) {
        String body = String.format(bodies.get(type), receiver, link);
        return new Email(SENDER, receiver, subjects.get(type), body);
    }
}
