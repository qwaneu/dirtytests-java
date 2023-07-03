package eu.qwan.dirtytest.captors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceService {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceService.class);
    private InvoiceDao invoiceDao;

    public void setInvoiceDao(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    public void execute(String recipient, List<InvoiceLine> invoiceLines) {
        if (!invoiceLines.isEmpty()) {
            List<ServiceDescription> services = new ArrayList<>();
            List<ServiceAmount> amounts = new ArrayList<>();
            invoiceLines.forEach(l -> {
                services.add(new ServiceDescription(l.getService()));
                amounts.add(new ServiceAmount(l.getAmount()));
            });
            double total = invoiceLines.stream().mapToDouble(InvoiceLine::getAmount).sum();
            if (total > 20000) total = total * 0.9;
            InvoiceCreatedEvent event = new InvoiceCreatedEvent(UUID.randomUUID().toString(), LocalDateTime.now(), "Agile Training & Coaching Inc.", recipient, "EU12345678", services, amounts, total);
            invoiceDao.insert(event);
        } else {
            LOG.info("No invoice lines");
        }
    }
}
