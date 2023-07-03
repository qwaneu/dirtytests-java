package eu.qwan.async.eventdriven;

import eu.qwan.async.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MessageProcessorTest {
    Message result = null;

    @Test
    public void invertsMessage_naive() throws Exception {
        MessageProcessor messageProcessor = new MessageProcessor();
        messageProcessor.processMessage(new Message("123"), message -> result = message);
        Thread.sleep(1000);
        assertThat(result, equalTo(new Message("321")));
    }
}
