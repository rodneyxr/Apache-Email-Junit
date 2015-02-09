package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

// ============= Pass =============
// Email addBcc(String... emails)
// Email addCc(String email)
// void addHeader(String name, String value)
// Email addReplyTo(String email, String name)
// void buildMimeMessage()
// String getHostName()
// Session getMailSession()
// Date getSentDate()
// int getSocketConnectionTimeout()

// Email setFrom(String email)

// ============= Fail =============
// String send()
// void updateContentType(String aContentType)

public class EmailTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private MockEmail email;
	private final String[] VALID_TEST_EMAILS = { "test1@email.com", "test2@email.com", "test3@email.com" };
	private final String[] VALID_TEST_NAMES = { "test1", "test2", "test3" };

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
		// create the expected list of addresses
		ArrayList<InternetAddress> expectedList = new ArrayList<InternetAddress>();

		// fill expected list
		for (String e : VALID_TEST_EMAILS)
			expectedList.add(new InternetAddress(e));

		// add valid e-mails to mock e-mail
		email.addBcc(VALID_TEST_EMAILS);

		// assert expected results
		assertEquals(expectedList, email.getBccAddresses());
	}

	/**
	 * Email addCc(String email)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddCc() throws Exception {
		// create the expected list of addresses
		ArrayList<InternetAddress> expectedList = new ArrayList<InternetAddress>();

		// fill expected list and add valid e-mails to mock
		for (String e : VALID_TEST_EMAILS) {
			expectedList.add(new InternetAddress(e));
			email.addCc(e);
		}

		// assert expected results
		assertEquals(expectedList, email.getCcAddresses());
	}

	/**
	 * void addHeader(String name, String value)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddHeader() throws Exception {
		// create the expected header
		Map<String, String> expectedHeaders = new HashMap<String, String>();

		// fill the expected header
		expectedHeaders.put("X-Mailer", "Sendmail");
		expectedHeaders.put("X-Priority", "1");
		expectedHeaders.put("Disposition-Notification-To", "user@domain.net");

		// add headers to mock
		email.addHeader("X-Mailer", "Sendmail");
		email.addHeader("X-Priority", "1");
		email.addHeader("Disposition-Notification-To", "user@domain.net");

		// assert expected results
		assertEquals(expectedHeaders, email.headers);
	}

	/**
	 * Email addReplyTo(String email, String name)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddReplyTo() throws Exception {
		// create expected list
		ArrayList<InternetAddress> expectedAddresses = new ArrayList<InternetAddress>();

		// fill expected Internet Address list and add replyTo to mock
		for (int i = 0, size = VALID_TEST_EMAILS.length; i < size; i++) {
			expectedAddresses.add(new InternetAddress(VALID_TEST_EMAILS[i], VALID_TEST_NAMES[i]));
			email.addReplyTo(VALID_TEST_EMAILS[i], VALID_TEST_NAMES[i]);
		}

		// assert expected results
		assertEquals(expectedAddresses, email.getReplyToAddresses());
	}

	/**
	 * void buildMimeMessage()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessage() throws Exception {
		// set up mock to build MimeMessage
		email.setHostName("test.hostname.com");
		email.setFrom(VALID_TEST_EMAILS[0], VALID_TEST_NAMES[0]);
		email.addTo(VALID_TEST_EMAILS[1], VALID_TEST_NAMES[1]);

		// assert MimeMessage has not been built
		assertTrue("MimeMessage already exists", email.getMimeMessage() == null);

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);

		// expect exception if we try to build MimeMessage after it has already been built
		exception.expect(IllegalStateException.class);
		email.buildMimeMessage();
	}

	/**
	 * String getHostName()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetHostName() throws Exception {
		// create the expected hostname
		String hostname = "test.hostname.com";

		// set mock hostname
		email.setHostName(hostname);

		// assert hostname matches what was set
		assertEquals(hostname, email.getHostName());
	}

	/**
	 * Session getMailSession()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMailSession() throws Exception {
		// create the expected session
		Session expectedSession = Session.getInstance(new Properties());

		// assert the session is not null
		assertTrue("Session is null", expectedSession != null);

		// set the mock's session
		email.setMailSession(expectedSession);

		// assert the expected session was returned by the mock
		assertEquals(expectedSession, email.getMailSession());
	}

	/**
	 * Date getSentDate()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetSentDate() throws Exception {
		// consider elapsed time between date calls
		long startTime = System.nanoTime(); // save start time (nanoseconds for precision)
		Date expectedDefaultDate = new Date(); // expected default sent date
		Date defaultSentDate = email.getSentDate(); // get mock's default sent date
		long endTime = System.nanoTime(); // save end time (nanoseconds for precision)

		// approximate time between date calls (convert nanoseconds to milliseconds)
		float deltaTime = (endTime - startTime) / 1000000f;
		// if difference > deltaTime then dates are different
		long difference = Math.abs(defaultSentDate.getTime() - expectedDefaultDate.getTime());

		// if difference <= deltaTime then we accept that as an equal date
		assertTrue("Unexpected default date", difference <= deltaTime);

		// test a non-default date
		Date expectedDate = new Date(0);

		// set the mock's sent date
		email.setSentDate(expectedDate);

		// assert dates match
		assertEquals(expectedDate, email.getSentDate());
	}

	/**
	 * int getSocketConnectionTimeout()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		// assert default timeout
		assertEquals(EmailConstants.SOCKET_TIMEOUT_MS, email.getSocketConnectionTimeout());

		// create the expected timeout
		final int EXPECTED_TIMEOUT = 10000;

		// change the mock's timeout
		email.setSocketConnectionTimeout(EXPECTED_TIMEOUT);

		// assert the mock returns the expected timeout
		assertEquals(EXPECTED_TIMEOUT, email.getSocketConnectionTimeout());
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
		for (String address : VALID_TEST_EMAILS) {

			// create expected InternetAddress
			InternetAddress expectedAddress = new InternetAddress(address);

			// set the sender address
			email.setFrom(address);

			// assert mock's fromAddress equals expected address
			assertEquals(expectedAddress, email.getFromAddress());
		}
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
