package eu.qwan.dirtytest.captors;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ArgumentCaptorsTest {

    RecordingInvoiceDao invoiceDao = new RecordingInvoiceDao();
    InvoiceService service = new InvoiceService();

    @Test
    public void testExecute() {
        service.setInvoiceDao(invoiceDao);
        service.execute("recipient", List.of(
            new InvoiceLine("consulting", 15000.0),
            new InvoiceLine("training", 5000.0)
        ));

        InvoiceEvent event = invoiceDao.recordedEvent;
        assertTrue(event instanceof InvoiceCreatedEvent);
        assertThat(event.getId(), is(not(nullValue())));
        assertThat(event.getCreatedAt(), is(not(nullValue())));
        assertThat(((InvoiceCreatedEvent)event).getAmountDue(), is(20000.0));
        assertThat(((InvoiceCreatedEvent)event).getServices().size(), is(2));
        assertThat(((InvoiceCreatedEvent)event).getAmounts().size(), is(2));
    }

    @Test
    public void testDiscount() {
        service.setInvoiceDao(invoiceDao);
        service.execute("recipient", List.of(
            new InvoiceLine("consulting", 15000.0),
            new InvoiceLine("training", 5000.0),
            new InvoiceLine("mentoring", 10000.0)
        ));

        InvoiceEvent event = invoiceDao.recordedEvent;
        assertTrue(event instanceof InvoiceCreatedEvent);
        assertThat(event.getId(), is(not(nullValue())));
        assertThat(event.getCreatedAt(), is(not(nullValue())));
        assertThat(((InvoiceCreatedEvent)event).getAmountDue(), is(27000.0));
        assertThat(((InvoiceCreatedEvent)event).getServices().size(), is(3));
        assertThat(((InvoiceCreatedEvent)event).getAmounts().size(), is(3));
    }

    @Test
    public void testExecuteNoEvent() {
        service.setInvoiceDao(invoiceDao);
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
}
