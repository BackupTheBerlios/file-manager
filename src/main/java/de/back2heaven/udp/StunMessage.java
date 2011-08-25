package de.back2heaven.udp;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class StunMessage implements NetIOMessage {

	private byte[] data;

	public StunMessage(byte[] data) {
		this.data = data;
	}

	public StunMessage(InetAddress address, int port) {
		this(address, port, null);
	}

	public StunMessage(InetAddress address, int port, StunMessage next) {
		int size = 1 + 4 + 4;
		if (next != null) {
			size += next.getSize();
		}
		boolean isIPv6 = address instanceof Inet6Address;

		if (isIPv6) {
			// 16 statt 4
			size += 12; // sonst keine änderungen
		}

		data = new byte[size];

		data[0] = isIPv6 ? (byte) 1 : 0;
		// ipaddresse ist entweder v4 oder v6
		byte[] add = address.getAddress();
		// addresse nun gespeichert.
		System.arraycopy(add, 0, data, 1, add.length);

		// port schreiben
		byte[] intport = ByteBuffer.allocate(4).putInt(port).array();

		System.arraycopy(intport, 0, data, 1 + add.length, intport.length);

		// nestet data
		if (next != null) {
			byte[] nextB = next.getBytes();
			System.arraycopy(nextB, 0, data, 1 + add.length + intport.length,
					nextB.length);
		}

	}

	public boolean isIPv6() {
		return data[0] > 0;
	}

	public InetAddress getAddress() throws UnknownHostException {
		byte[] addr = new byte[isIPv6() ? 16 : 4];
		System.arraycopy(data, 1, addr, 0, addr.length);
		return InetAddress.getByAddress(addr);
	}

	public int getPort() {
		byte[] port = new byte[4];
		System.arraycopy(data, isIPv6() ? 17 : 5, port, 0, port.length);
		return ByteBuffer.wrap(port).getInt();
	}

	public int getSize() {
		return data.length;
	}

	public StunMessage getNext() {
		int pSize = 1 + (isIPv6() ? 16 : 4) + 4; // größer als dies dann next
		int z = data.length - pSize;
		if (z > 0) {
			byte[] v = new byte[z];
			System.arraycopy(data, pSize, v, 0, v.length);
			return new StunMessage(v);
		}

		return null;

	}

	@Override
	public byte[] getBytes() {
		return data;
	}
}
