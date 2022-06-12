package eu.qwan.exercises.dirtytests.obscure.service;

import eu.qwan.exercises.dirtytests.obscure.domain.Transport;
import eu.qwan.exercises.dirtytests.obscure.notifications.NotificationPublisher;
import eu.qwan.exercises.dirtytests.obscure.process.AssignmentTaskDefinition;
import eu.qwan.exercises.dirtytests.obscure.process.AssignmentTaskState;
import eu.qwan.exercises.dirtytests.obscure.process.Process;
import eu.qwan.exercises.dirtytests.obscure.process.ProcessDefinition;
import eu.qwan.exercises.dirtytests.obscure.repositories.ProcessRepository;
import eu.qwan.exercises.dirtytests.obscure.process.Task;
import eu.qwan.exercises.dirtytests.obscure.repositories.TransportRepository;
import eu.qwan.exercises.dirtytests.obscure.request.AssignCarrierRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarrierProcessor {
  private static final Logger LOG = LoggerFactory.getLogger(CarrierProcessor.class);

  private AssignCarrierProcessController controller;
  private ProcessRepository              processRepository;
  private TransportRepository            transportRepository;
  private CarrierUpdater                 carrierUpdater;
  private NotificationPublisher          notificationPublisher;

  public void processAssignCarrierRequest(AssignCarrierRequest assignCarrierRequest) {
    Transport transport = transportRepository.findByTrn(assignCarrierRequest.getTrn());
    boolean carrierUpdated = !transport.getCarrier().getOrganisationReferenceNumber().equals(assignCarrierRequest.getCarrier().getOrn());
    if(carrierUpdated) {
      carrierUpdater.updateCarrier(transport, assignCarrierRequest);
      updateProcess(transport, AssignmentTaskState.NOMINATED);
      notificationPublisher.sendYouHaveBeenAssignedAsCarierNotification(transport, assignCarrierRequest.getCarrier().getOrn());
    }
  }

  public void updateProcess(Transport transport, AssignmentTaskState taskState) {
    final Process process = processRepository
        .findByDefinitionAndBusinessObject(ProcessDefinition.CARRIER_ASSIGNMENT, transport.getTransportReferenceNumber());
    final Task task = process.getTask(AssignmentTaskDefinition.ASSIGN_CARRIER);
    if (task.isStateChangeAllowed(AssignmentTaskState.NOMINATED)) {
      controller.changeState(process, task, AssignmentTaskState.NOMINATED, null);
    } else {
      LOG.info("State change not allowed from state:" + task.getState().getName() + " to state:"
                      + taskState.getName());
    }
  }

  public void setController(AssignCarrierProcessController controller) {
    this.controller = controller;
  }

  public void setProcessRepository(ProcessRepository processRepository) {
    this.processRepository = processRepository;
  }

  public void setTransportRepository(TransportRepository transportRepository) {
    this.transportRepository = transportRepository;
  }

  public void setCarrierUpdater(CarrierUpdater carrierUpdater) {
    this.carrierUpdater = carrierUpdater;
  }

  public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
    this.notificationPublisher = notificationPublisher;
  }
}
