package eu.qwan.async;

public class Message {
	private final String value;

	public Message(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Message(" + value + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Message))
			return false;
		Message other = (Message) obj;
		return value.equals(other.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
}
