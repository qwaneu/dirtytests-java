package eu.qwan.dirtytest.captors;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArgumentCaptorsTest {

    private static final String UUID_UT = "c6356c33-6a4f-4bda-8141-1e78041e35a4";
    private static final LocalDateTime NOW = LocalDateTime.now();

    RecordingInvoiceDao invoiceDao = new RecordingInvoiceDao();
    UUIDGenerator uuidGenerator = new StaticUUIDGenerator(UUID_UT);
    Clock clock = new StoppedClock(NOW);
    InvoiceService service = new InvoiceService(invoiceDao, uuidGenerator, clock);

    @Test
    public void testExecute() {
        service.execute("recipient", List.of(
            new InvoiceLine("consulting", 15000.0),
            new InvoiceLine("training", 5000.0)
        ));

        var event = invoiceDao.recordedEvent;
        assertThat(event, instanceOf(InvoiceCreatedEvent.class));
        assertThat(event.getId(), is(UUID_UT));
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
        assertThat(event.getId(), is(UUID_UT));
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

        private final UUID uuid;

        public StaticUUIDGenerator(String uuid) {
            this.uuid = UUID.fromString(uuid);
        }

        @Override
        public UUID generate() {
            return uuid;
        }
    }

    static class StoppedClock implements Clock {

        private final LocalDateTime now;

        public StoppedClock(LocalDateTime now) {
            this.now = now;
        }

        @Override
        public LocalDateTime getNow() {
            return now;
        }
    }
}
