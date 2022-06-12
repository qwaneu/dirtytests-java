package eu.qwan.dirtytest.captors;

import java.time.LocalDateTime;
import java.util.List;

public class InvoiceCreatedEvent implements InvoiceEvent {
    private final String                   id;
    private final LocalDateTime            createdAt;
    private final String                   sender;
    private final String                   recipient;
    private final String                   vat;
    private final List<ServiceDescription> services;
    private final List<ServiceAmount>      amounts;
    private final double                   amountDue;

    public InvoiceCreatedEvent(String id, LocalDateTime createdAt, String sender, String recipient, String vat, List<ServiceDescription> services, List<ServiceAmount> amounts, double amountDue) {
        this.id = id;
        this.createdAt = createdAt;
        this.sender = sender;
        this.recipient = recipient;
        this.vat = vat;
        this.services = services;
        this.amounts = amounts;
        this.amountDue = amountDue;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public List<ServiceDescription> getServices() {
        return services;
    }

    public List<ServiceAmount> getAmounts() {
        return amounts;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getVat() {
        return vat;
    }
}
