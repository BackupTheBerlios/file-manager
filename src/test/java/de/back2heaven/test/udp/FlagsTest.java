package de.back2heaven.test.udp;

import org.junit.Assert;
import org.junit.Test;

import de.back2heaven.udp.MessageFlag;

public class FlagsTest {

	@Test
	public void ackTest(){
		byte header = 1;
		MessageFlag[] mfs = MessageFlag.parse(header);
		Assert.assertArrayEquals(mfs, new MessageFlag[]{MessageFlag.ACK});
	}
	
}
