package eu.qwan.async.eventdriven;

import eu.qwan.async.Message;

public interface MessageProcessorListener {
	void messageProcessed(Message result);
}
