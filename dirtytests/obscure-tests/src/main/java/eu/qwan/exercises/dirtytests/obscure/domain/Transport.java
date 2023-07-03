package eu.qwan.exercises.dirtytests.obscure.domain;

public class Transport {
  private TransportOrganisation owner;
  private String                transportReferenceNumber;
  private TransportOrganisation carrier;

  public TransportOrganisation getCarrier() {
    return carrier;
  }

  public void setCarrier(TransportOrganisation carrier) {
    this.carrier = carrier;
  }

  public TransportOrganisation getOwner() {
    return owner;
  }

  public void setOwner(TransportOrganisation owner) {
    this.owner = owner;
  }

  public String getTransportReferenceNumber() {
    return transportReferenceNumber;
  }

  public void setTransportReferenceNumber(String transportReferenceNumber) {
    this.transportReferenceNumber = transportReferenceNumber;
  }
}
