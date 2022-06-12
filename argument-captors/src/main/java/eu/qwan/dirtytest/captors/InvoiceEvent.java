package eu.qwan.dirtytest.captors;

import java.time.LocalDateTime;

public interface InvoiceEvent {
    String getId();
    LocalDateTime getCreatedAt();
}
