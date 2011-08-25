package de.back2heaven.udp;

import java.util.ArrayList;

public enum MessageFlag {
	ACK(1), ERROR(2), DATA(3), FRAGMENTATION(4), STARTWINDOW(5), STOPWINDOW(6), ROUTED(
			07), NOTIFY(8), RECOVER(9), TRAP(10), BROADCAST(11), CRYPTED(12),PROTOCOL(13);
	private int flagValue;

	private MessageFlag(int flag) {
		flagValue = (1 << (flag-1));
	}

	public byte[] get() {
		byte a = (byte) ((flagValue & 0xFF) >> 8);
		byte b = (byte) flagValue;
		return new byte[] { a, b };
	}

	public boolean has(byte data) {
		return (flagValue & data)>0;
	}

	public byte merged(byte data) {
		return (byte) (data | flagValue);
	}

	public static MessageFlag[] parse(byte header) {
		ArrayList<MessageFlag> flags = new ArrayList<>();
		for (MessageFlag mf : values()) {
			if (mf.has(header)) {
				flags.add(mf);
			}
		}
		return flags.toArray(new MessageFlag[0]);
	}
}
