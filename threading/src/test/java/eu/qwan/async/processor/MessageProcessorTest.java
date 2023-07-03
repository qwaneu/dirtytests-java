package eu.qwan.async.processor;

import eu.qwan.async.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MessageProcessorTest {
    MessageProcessor processor = new MessageProcessor();

    @Test
    public void invertsMessage_naive() throws Exception {
        processor.start();
        processor.enqueue(new Message("123"));
        Thread.sleep(1000);
        Message result = processor.dequeue();
        assertThat(result, equalTo(new Message("321")));
    }

    @Test
    public void processesThemInOrder_naive() throws Exception {
        processor.start();
        processor.enqueue(new Message("123"));
        processor.enqueue(new Message("456"));
        Thread.sleep(1000);
        Message result = processor.dequeue();
        assertThat(result, equalTo(new Message("321")));
        Thread.sleep(1000);
        result = processor.dequeue();
        assertThat(result, equalTo(new Message("654")));
    }
}
