package eu.qwan.dirtytest.captors;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ArgumentCaptorsTest {
    @Test
    public void testExecute() {
        InvoiceDao invoiceDao = mock(FakeInvoiceDao.class);
        InvoiceService service = new InvoiceService();
        service.setInvoiceDao(invoiceDao);
        service.execute("recipient", asList(new InvoiceLine("consulting", 15000.0), new InvoiceLine("training", 5000.0)));
        ArgumentCaptor<InvoiceEvent> captor = ArgumentCaptor.forClass(InvoiceEvent.class);
        verify(invoiceDao).insert(captor.capture());
        InvoiceEvent event = captor.getValue();
        assertTrue(event instanceof InvoiceCreatedEvent);
        assertThat(event.getId(), is(not(nullValue())));
        assertThat(event.getCreatedAt(), is(not(nullValue())));
        assertThat(((InvoiceCreatedEvent)event).getAmountDue(), is(20000.0));
        assertThat(((InvoiceCreatedEvent)event).getServices().size(), is(2));
        assertThat(((InvoiceCreatedEvent)event).getAmounts().size(), is(2));
    }

    @Test
    public void testDiscount() {
        InvoiceDao invoiceDao = mock(FakeInvoiceDao.class);
        InvoiceService service = new InvoiceService();
        service.setInvoiceDao(invoiceDao);
        service.execute("recipient", asList(new InvoiceLine("consulting", 15000.0), new InvoiceLine("training", 5000.0), new InvoiceLine("mentoring", 10000.0)));
        ArgumentCaptor<InvoiceEvent> captor = ArgumentCaptor.forClass(InvoiceEvent.class);
        verify(invoiceDao).insert(captor.capture());
        InvoiceEvent event = captor.getValue();
        assertTrue(event instanceof InvoiceCreatedEvent);
        assertThat(event.getId(), is(not(nullValue())));
        assertThat(event.getCreatedAt(), is(not(nullValue())));
        assertThat(((InvoiceCreatedEvent)event).getAmountDue(), is(27000.0));
        assertThat(((InvoiceCreatedEvent)event).getServices().size(), is(3));
        assertThat(((InvoiceCreatedEvent)event).getAmounts().size(), is(3));
    }

    @Test
    public void testExecuteNoEvent() {
        InvoiceDao invoiceDao = mock(FakeInvoiceDao.class);
        InvoiceService service = new InvoiceService();
        service.setInvoiceDao(invoiceDao);
        service.execute("recipient", new ArrayList<>());
        verify(invoiceDao, times(0)).insert(any());
    }

    class FakeInvoiceDao implements InvoiceDao {
        @Override
        public void insert(InvoiceEvent event) {

        }
    }
}
