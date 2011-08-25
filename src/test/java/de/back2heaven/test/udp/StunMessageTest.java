package de.back2heaven.test.udp;

import java.net.InetAddress;

import org.junit.Assert;
import org.junit.Test;

import de.back2heaven.udp.StunMessage;

public class StunMessageTest {

	@Test
	public void testLoopBack() throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		int port = 65000;
		StunMessage m = new StunMessage(addr, port);
		Assert.assertEquals(port, m.getPort());
		Assert.assertEquals(addr, m.getAddress());
	}
	

	@Test
	public void testLoopBackNested() throws Exception {
		InetAddress addr = InetAddress.getLocalHost();
		int port = 65000;
		InetAddress addr2 = InetAddress.getLocalHost();
		int port2 = 65111;
		StunMessage m = new StunMessage(addr, port);
		
		StunMessage m2 = new StunMessage(addr2,port2,m);
		
		StunMessage n = m2.getNext();
		Assert.assertEquals(port, n.getPort());
		Assert.assertEquals(addr, n.getAddress());
		

		Assert.assertEquals(port2, m2.getPort());
		Assert.assertEquals(addr2, m2.getAddress());
	}
}
