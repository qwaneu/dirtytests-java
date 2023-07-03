package eu.qwan.async.eventdriven;

import eu.qwan.async.Message;

public class MessageProcessor {

	public void processMessage(final Message message,
			final MessageProcessorListener listener) {
		new Thread(new Runnable() {
			public void run() {
				String value = message.getValue();
				StringBuffer result = new StringBuffer(value.length());
				for (int i = value.length() - 1; i >= 0; i--) {
					result.append(value.charAt(i));
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace(); // poor mans log
					}
				}
				listener.messageProcessed(new Message(result.toString()));
			}

		}).start();
	}
}
