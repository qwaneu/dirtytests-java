package eu.qwan.dirtytest.captors;

public class InvoiceLine {
    private final String service;
    private final double amount;

  public InvoiceLine(String service, double amount) {
    this.service = service;
    this.amount = amount;
  }

  public String getService() {
    return service;
  }

  public double getAmount() {
    return amount;
  }
}
