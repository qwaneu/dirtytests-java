package eu.qwan.async.processor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import eu.qwan.async.Message;

public class MessageProcessor implements Runnable {
	private final Queue<Message> outputQueue = new ConcurrentLinkedQueue<Message>();
	private final Queue<Message> inputQueue = new ConcurrentLinkedQueue<Message>();

	volatile private boolean running;
	volatile private Thread thread;

	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() throws InterruptedException {
		running = false;
		thread.join();
	}

	public void enqueue(Message message) {
		inputQueue.offer(message);
	}

	public Message dequeue() {
		return outputQueue.poll();
	}

	public void run() {
		while (running) {
			if (inputQueue.isEmpty())
				continue;

			String value = inputQueue.remove().getValue();
			StringBuffer result = new StringBuffer(value.length());
			for (int i = value.length() - 1; i >= 0; i--) {
				result.append(value.charAt(i));
				try {
					Thread.sleep(30); // this is a lengthy operation
				} catch (InterruptedException e) {
					e.printStackTrace(); // poor mans log
				}
			}
			outputQueue.offer(new Message(result.toString()));
		}
	}
}
