package eu.qwan.exercises.dirtytests.obscure.service;

import eu.qwan.exercises.dirtytests.obscure.domain.OrganisationType;
import eu.qwan.exercises.dirtytests.obscure.domain.Transport;
import eu.qwan.exercises.dirtytests.obscure.domain.TransportOrganisation;
import eu.qwan.exercises.dirtytests.obscure.notifications.NotificationPublisher;
import eu.qwan.exercises.dirtytests.obscure.process.*;
import eu.qwan.exercises.dirtytests.obscure.process.Process;
import eu.qwan.exercises.dirtytests.obscure.repositories.ProcessRepository;
import eu.qwan.exercises.dirtytests.obscure.repositories.TransportRepository;
import eu.qwan.exercises.dirtytests.obscure.request.AssignCarrierRequest;
import eu.qwan.exercises.dirtytests.obscure.request.OrganisationDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
public class CarrierProcessorTest {

  private Task    assignmentCarrierTask;
  private Process process;

  @Mock
  private ProcessRepository processRepository;

  private CarrierProcessor carrierProcessor = new CarrierProcessor();
  private TransportOrganisation          transportOrganisation;
  private Transport                      transport;
  @Mock
  private AssignCarrierProcessController assignCarrierProcessController;
  @Mock
  private TransportRepository            transportRepository;
  private AssignCarrierRequest           assignCarrierRequest;
  @Mock
  private CarrierUpdater                 carrierUpdaterMock;
  @Mock
  private NotificationPublisher          notificationPublisher;

  @Test
  public void processCarrier() {
    initMocks("CAR1", "CAR2", true);

    carrierProcessor.updateProcess(transport, AssignmentTaskState.NOMINATED);
    verify(assignCarrierProcessController, times(1)).//
        changeState(process, assignmentCarrierTask, AssignmentTaskState.NOMINATED, null);
  }

  @Test
  public void changeToSameCarrier() {
    initMocks("CAR1", "CAR1", true);

    carrierProcessor.processAssignCarrierRequest(assignCarrierRequest);
    verify(assignCarrierProcessController, times(0)).//
        changeState(any(Process.class), any(Task.class), any(AssignmentTaskState.class), eq(null));
  }

  @Test
  public void changeCarrierToOther() {
    initMocks("CAR1", "CAR2", true);

    carrierProcessor.processAssignCarrierRequest(assignCarrierRequest);
    verify(assignCarrierProcessController, times(1)).//
        changeState(any(Process.class), any(Task.class), any(AssignmentTaskState.class), eq(null));
    verify(notificationPublisher, times(1)).//
        sendYouHaveBeenAssignedAsCarierNotification(transport, "CAR2");
  }

  @Test
  public void changeCarrierToOtherStateChangeNotAllowed() {
    initMocks("CAR1", "CAR2", false);

    carrierProcessor.processAssignCarrierRequest(assignCarrierRequest);
    verify(assignCarrierProcessController, times(0)).//
            changeState(any(Process.class), any(Task.class), any(AssignmentTaskState.class), eq(null));
  }

  private void initMocks(String transportOrgId, String carrierOrgId, boolean stateChangeAllowed) {
    assignmentCarrierTask = mock(AssignmentCarrierTask.class);
    when(assignmentCarrierTask.isStateChangeAllowed(any(AssignmentTaskState.class))).thenReturn(stateChangeAllowed);
    when(assignmentCarrierTask.getState()).thenReturn(AssignmentTaskState.NOMINATED);

    process = mock(Process.class);
    when(process.getTask(any(AssignmentTaskDefinition.class))).thenReturn(assignmentCarrierTask);

    when(processRepository.findByDefinitionAndBusinessObject(any(ProcessDefinition.class), anyString())).thenReturn(process);

    transportOrganisation = mock(TransportOrganisation.class);
    when(transportOrganisation.getOrganisationReferenceNumber()).thenReturn(transportOrgId);
    when(transportOrganisation.getOrganisationType()).thenReturn(OrganisationType.CARRIER);

    transport = new Transport();
    transport.setOwner(transportOrganisation);
    transport.setCarrier(transportOrganisation);
    transport.setTransportReferenceNumber("TRN");

    when(transportRepository.findByTrn(any())).thenReturn(transport);

    OrganisationDto organisation = new OrganisationDto();
    organisation.setOrn(carrierOrgId);

    assignCarrierRequest = new AssignCarrierRequest();
    assignCarrierRequest.setTrn("TRN");
    assignCarrierRequest.setCarrier(organisation);

    carrierProcessor.setController(assignCarrierProcessController);
    carrierProcessor.setProcessRepository(processRepository);
    carrierProcessor.setTransportRepository(transportRepository);
    carrierProcessor.setCarrierUpdater(carrierUpdaterMock);
    carrierProcessor.setNotificationPublisher(notificationPublisher);
  }

}
