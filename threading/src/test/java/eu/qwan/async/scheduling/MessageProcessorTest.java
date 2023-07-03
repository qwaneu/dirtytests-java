package eu.qwan.async.scheduling;

import eu.qwan.async.Message;
import eu.qwan.async.eventdriven.MessageProcessorListener;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MyProcessorListener implements MessageProcessorListener {
    private Message result;

    @Override
    public void messageProcessed(Message result) {
        this.result = result;
    }

    public Message getResult() {
        return result;
    }
}

public class MessageProcessorTest {
    @Test
    public void invertsMessage_naive() throws Exception {
        MyProcessorListener listener = new MyProcessorListener();

        MessageProcessor processor = new MessageProcessor(listener);
        Calendar current = Calendar.getInstance();

        processor.processMessage(new Message("123"), current.get(Calendar.HOUR),current.get(Calendar.MINUTE) + 1);

        Thread.sleep(70 * 1000);
        assertThat(listener.getResult(), equalTo(new Message("321")));
    }
}
