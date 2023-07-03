package eu.qwan.async.scheduling;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import eu.qwan.async.Message;
import eu.qwan.async.eventdriven.MessageProcessorListener;

public class MessageProcessor {
	private final MessageProcessorListener listener;

	public MessageProcessor(MessageProcessorListener listener) {
		this.listener = listener;
	}

	public void processMessage(Message message, int atHour, int atMinute) {
		processMessage(message, deltaInMs(atHour, atMinute));
	}

	private static long deltaInMs(int hour, int minute) {
		Calendar current = Calendar.getInstance();

		Calendar wakeUpTime = Calendar.getInstance();
		wakeUpTime.set(Calendar.HOUR, hour);
		wakeUpTime.set(Calendar.MINUTE, minute);

		return wakeUpTime.getTimeInMillis() - current.getTimeInMillis();
	}

	public void processMessage(Message message, long ms) {
		var timerTask = new TimerTask() {
			@Override
			public void run() {
				String value = message.getValue();
				StringBuffer result = new StringBuffer(value.length());
				for (int i = value.length() - 1; i >= 0; i--) {
					result.append(value.charAt(i));
				}
				listener.messageProcessed(new Message(result.toString()));
			}
		};
		new Timer().schedule(timerTask, ms);
	}
}
