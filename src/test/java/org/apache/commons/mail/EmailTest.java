package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Test;

// ============= Pass =============
// Email addBcc(String... emails)

// ============= Fail =============
// Email addCc(String email)
// void addHeader(String name, String value)
// Email addReplyTo(String email, String name)
// void buildMimeMessage()
// String getHostName()
// Session getMailSession()
// Date getSentDate()
// int getSocketConnectionTimeout()
// String send()
// Email setFrom(String email)
// void updateContentType(String aContentType)

public class EmailTest {

	private MockEmail email;
	private final String[] VALID_EMAILS = { "test1@email.com", "test2@email.com", "test3@email.com" };

	@Before
	public void setUp() throws Exception {
		email = new MockEmail();
	}

	/**
	 * Email addBcc(String... emails)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddBcc() throws Exception {
		// expected list of addresses
		ArrayList<InternetAddress> expectedList = new ArrayList<InternetAddress>();

		// fill expected list
		for (String e : VALID_EMAILS)
			expectedList.add(new InternetAddress(e));

		// add valid e-mails to mock e-mail
		email.addBcc(VALID_EMAILS);

		// assert same contents
		assertEquals(email.getBccAddresses(), expectedList);
	}

	/**
	 * Email addCc(String email)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddCc() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * void addHeader(String name, String value)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddHeader() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * Email addReplyTo(String email, String name)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddReplyTo() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * void buildMimeMessage()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessage() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * String getHostName()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetHostName() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * Session getMailSession()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMailSession() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * Date getSentDate()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetSentData() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * int getSocketConnectionTimeout()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * String send()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSend() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * Email setFrom(String email)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetFrom() throws Exception {
		fail("not yet implemented");
	}

	/**
	 * void updateContentType(String aContentType)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateContentType() throws Exception {
		fail("not yet implemented");
	}

}
