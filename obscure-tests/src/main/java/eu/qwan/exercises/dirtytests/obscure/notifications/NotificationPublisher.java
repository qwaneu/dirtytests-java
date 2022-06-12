package eu.qwan.exercises.dirtytests.obscure.notifications;

import eu.qwan.exercises.dirtytests.obscure.domain.Transport;

public interface NotificationPublisher {
  void sendYouHaveBeenAssignedAsCarierNotification(Transport transport, String organisationReferenceNumber);
}
