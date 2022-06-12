package eu.qwan.exercises.dirtytests.obscure.request;

public class AssignCarrierRequest {
  private String trn;
  private OrganisationDto carrier;

  public String getTrn() {
    return trn;
  }

  public OrganisationDto getCarrier() {
    return carrier;
  }

  public void setTrn(String trn) {
    this.trn = trn;
  }

  public void setCarrier(OrganisationDto carrier) {
    this.carrier = carrier;
  }
}
