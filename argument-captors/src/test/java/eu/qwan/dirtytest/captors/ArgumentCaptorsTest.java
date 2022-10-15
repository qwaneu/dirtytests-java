package eu.qwan.dirtytest.captors;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArgumentCaptorsTest {

    RecordingInvoiceDao invoiceDao = new RecordingInvoiceDao();
    UUIDGenerator uuidGenerator = new StaticUUIDGenerator();
    InvoiceService service = new InvoiceService(invoiceDao, uuidGenerator);

    @Test
    public void testExecute() {
        service.execute("recipient", List.of(
            new InvoiceLine("consulting", 15000.0),
            new InvoiceLine("training", 5000.0)
        ));

        var event = invoiceDao.recordedEvent;
        assertThat(event, instanceOf(InvoiceCreatedEvent.class));
        assertThat(event.getId(), is(not(nullValue())));
        assertThat(event.getCreatedAt(), is(not(nullValue())));
        assertThat(((InvoiceCreatedEvent)event).getAmountDue(), is(20000.0));
        assertThat(((InvoiceCreatedEvent)event).getServices().size(), is(2));
        assertThat(((InvoiceCreatedEvent)event).getAmounts().size(), is(2));
    }

    @Test
    public void testDiscount() {
        service.execute("recipient", List.of(
            new InvoiceLine("consulting", 15000.0),
            new InvoiceLine("training", 5000.0),
            new InvoiceLine("mentoring", 10000.0)
        ));

        var event = invoiceDao.recordedEvent;
        assertThat(event, instanceOf(InvoiceCreatedEvent.class));
        assertThat(event.getId(), is(not(nullValue())));
        assertThat(event.getCreatedAt(), is(not(nullValue())));
        assertThat(((InvoiceCreatedEvent)event).getAmountDue(), is(27000.0));
        assertThat(((InvoiceCreatedEvent)event).getServices().size(), is(3));
        assertThat(((InvoiceCreatedEvent)event).getAmounts().size(), is(3));
    }

    @Test
    public void testExecuteNoEvent() {
        service.execute("recipient", List.of());

        assertNull(invoiceDao.recordedEvent);
    }

    static class RecordingInvoiceDao implements InvoiceDao {
        InvoiceEvent recordedEvent;

        @Override
        public void insert(InvoiceEvent event) {
            recordedEvent = event;
        }
    }

    static class StaticUUIDGenerator implements UUIDGenerator {

        @Override
        public UUID generate() {
            return UUID.fromString("c6356c33-6a4f-4bda-8141-1e78041e35a4");
        }
    }
}
