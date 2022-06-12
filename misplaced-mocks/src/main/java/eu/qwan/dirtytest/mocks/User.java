package eu.qwan.dirtytest.mocks;

public class User {
    private final String id;
    private final String emailAddress;

    public User(String id, String emailAddress) {
        this.id = id;
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getId() {
        return id;
    }
}
