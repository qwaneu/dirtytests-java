package eu.qwan.dirtytest.mocks;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserPasswordResetTest {
    @Test
    public void sendNotification() {
        EmailFactory emailFactory = mock(EmailFactory.class);
        Email email = mock(Email.class);
        Mailer mailer = mock(Mailer.class);
        UserRepository userRepository = mock(UserRepository.class);
        User user = mock(User.class);
        when(userRepository.byId("user-id")).thenReturn(Optional.of(user));
        when(user.getEmailAddress()).thenReturn("user@company.com");
        when(emailFactory.create(eq("user@company.com"), eq("PASSWORD_RESET"), any())).thenReturn(email);
        when(email.send(mailer)).thenReturn(true);
        PasswordResetController ctrl = new PasswordResetController(emailFactory, userRepository, mailer);
        ctrl.resetPassword("user-id");

        verify(email).send(any());
        verify(emailFactory).create(any(), any(), any());
        verify(userRepository).byId("user-id");
    }

    @Test
    public void testNotificationFails() {
        EmailFactory emailFactory = mock(EmailFactory.class);
        Email email = mock(Email.class);
        Mailer mailer = mock(Mailer.class);
        UserRepository userRepository = mock(UserRepository.class);
        User user = mock(User.class);
        when(userRepository.byId("user-id")).thenReturn(Optional.of(user));
        when(user.getEmailAddress()).thenReturn("user@company.com");
        when(emailFactory.create(eq("user@company.com"), eq("PASSWORD_RESET"), any())).thenReturn(email);
        when(email.send(mailer)).thenReturn(false);
        Email email2 = mock(Email.class);
        when(email2.send(mailer)).thenReturn(true);
        when(emailFactory.create("servicedesk@qwan.eu", "SD", null)).thenReturn(email2);
        PasswordResetController ctrl = new PasswordResetController(emailFactory, userRepository, mailer);
        ctrl.resetPassword("user-id");

        verify(email).send(any());
        verify(email2).send(any());
        verify(emailFactory, times(2)).create(any(), any(), any());
        verify(userRepository).byId("user-id");
    }
    @Test
    public void userNotFound() {
        EmailFactory emailFactory = mock(EmailFactory.class);
        Email email = mock(Email.class);
        Mailer mailer = mock(Mailer.class);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.byId("user-id")).thenReturn(Optional.empty());
        when(email.send(mailer)).thenReturn(false);
        when(emailFactory.create("servicedesk@qwan.eu", "SD", null)).thenReturn(email);
        PasswordResetController ctrl = new PasswordResetController(emailFactory, userRepository, mailer);
        ctrl.resetPassword("user-id");

        verify(email).send(any());
        verify(emailFactory).create(any(), any(), any());
        verify(userRepository).byId("user-id");
    }
}
