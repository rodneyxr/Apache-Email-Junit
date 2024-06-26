package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

// ============= Pass =============
// 100%: 	Email addBcc(String... emails)
// 100%:	Email addCc(String email)
// 100%:	void addHeader(String name, String value)
// 100%:	Email addReplyTo(String email, String name)
// 94.2%	void buildMimeMessage()
// 100%:	String getHostName()
// 100%:	Session getMailSession()
// 100%:	Date getSentDate()
// 100%:	int getSocketConnectionTimeout()
// 100%:	String send()
// 100%:	Email setFrom(String email)
// 100%:	void updateContentType(String aContentType)

// ============= Fail =============

/**
 * 
 * @author Rodney Rodriguez
 *
 */
public class EmailTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	private MockEmail email;
	private final String[] VALID_TEST_EMAILS = { "test1@email.com", "test2@email.com", "test3@email.com" };
	private final String[] VALID_TEST_NAMES = { "test1", "test2", "test3" };
	private final String[] EMPTY_STRINGS = { "", "", "" };
	private final String EMPTY_STRING = "";

	private void prepMimeMessage() throws Exception {
		// set up mock to build MimeMessage
		email.setHostName("test.hostname.com");
		email.setFrom(VALID_TEST_EMAILS[0], VALID_TEST_NAMES[0]);
		email.addTo(VALID_TEST_EMAILS[1], VALID_TEST_NAMES[1]);
		email.setSubject("Test Subject");
	}

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
	 * Email addBcc(String... emails)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddBccNullEmails() throws Exception {
		String[] nullEmails = null;

		// expect an exception from an empty string array
		exception.expect(EmailException.class);

		// add empty e-mails to mock e-mail
		email.addBcc(nullEmails);
	}

	/**
	 * Email addBcc(String... emails)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddBccEmptyEmails() throws Exception {
		String[] emptyEmails = {};

		// expect an exception from an empty string array
		exception.expect(EmailException.class);

		// add empty e-mails to mock e-mail
		email.addBcc(emptyEmails);
	}

	/**
	 * test addBcc(String... emails) for null input
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddBccNoEmails() throws Exception {
		// expect an email exception from null input
		exception.expect(EmailException.class);
		// add null String array
		email.addBcc(EMPTY_STRINGS);

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
	 * test addCc(String email) for empty string
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddCcNoEmails() throws Exception {
		// expect an email exception from null input
		exception.expect(EmailException.class);
		// add null string
		email.addCc(EMPTY_STRING);
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
	 * test addHeader(String name, String value) for empty name string
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddHeaderEmptyName() throws Exception {
		// expect IllegalArgumentException from empty name input
		exception.expect(IllegalArgumentException.class);

		// add header with empty name input
		email.addHeader(EMPTY_STRING, "SendMail");
	}

	/**
	 * test addHeader(String name, String value) for empty name string
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddHeaderEmptyValue() throws Exception {
		// expect IllegalArgumentException from empty value input
		exception.expect(IllegalArgumentException.class);

		// add header with empty value input
		email.addHeader("X-Mailer", EMPTY_STRING);
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
	 * test addReplyTo(String email) for empty string
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddReplyToEmptyEmail() throws Exception {
		// expect an email exception from null input
		exception.expect(EmailException.class);
		// add null string
		email.addReplyTo(EMPTY_STRING);
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
		email.addCc(VALID_TEST_EMAILS[0]);
		email.addBcc(VALID_TEST_EMAILS[0]);
		email.addReplyTo(VALID_TEST_EMAILS[0]);
		email.addHeader("X-Mailer", "Sendmail");

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
	 * void buildMimeMessage()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageBodyWithoutContent() throws Exception {
		prepMimeMessage();
		email.emailBody = new MimeMultipart();

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * void buildMimeMessage()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageValidContentType() throws Exception {
		prepMimeMessage();
		// email.setContent("Test HTML Content", EmailConstants.TEXT_HTML);
		email.emailBody = new MimeMultipart();
		email.contentType = EmailConstants.TEXT_HTML;

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * void buildMimeMessage() with no receivers specified
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageNoReceivers() throws Exception {
		// expect exception thrown when no receivers are specified
		exception.expect(EmailException.class);

		email.setHostName("test.hostname.com");
		email.setFrom(VALID_TEST_EMAILS[0]);
		email.buildMimeMessage();
	}

	/**
	 * test void buildMimeMessage() when charset is empty
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageValidSubject() throws Exception {
		prepMimeMessage();

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * test void buildMimeMessage() when charset is set
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageValidCharset() throws Exception {
		prepMimeMessage();
		email.setCharset(EmailConstants.UTF_8);

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * test void buildMimeMessage() when content type is a String
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageTextPlainContent() throws Exception {
		prepMimeMessage();
		email.setContent("Test Content", EmailConstants.TEXT_PLAIN);

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * test void buildMimeMessage() when valid content is set
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageValidContent() throws Exception {
		prepMimeMessage();
		email.setContent(new EmailAttachment(), "image/jpeg");

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * test void buildMimeMessage() when valid content is set
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageEmptyContent() throws Exception {
		prepMimeMessage();
		email.setContent(new InternetAddress(), EmailConstants.TEXT_SUBTYPE_HTML);

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * test void buildMimeMessage() when valid content is set
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageNullBody() throws Exception {
		prepMimeMessage();
		email.setContent(new InternetAddress(), EmailConstants.TEXT_SUBTYPE_HTML);

		// build MimeMessage
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * test void buildMimeMessage() with no from Address
	 *
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageNoFromAddress() throws Exception {
		exception.expect(EmailException.class);
		email.setHostName("test.hostname.com");
		// build MimeMessage
		email.buildMimeMessage();

		email.setFrom(VALID_TEST_EMAILS[0], VALID_TEST_NAMES[0]);
		email.buildMimeMessage();

		// assert build was successful
		assertTrue("MimeMessage is null", email.getMimeMessage() != null);
	}

	/**
	 * test void buildMimeMessage() with popBeforeSmtp set true and invalid Authentication
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBuildMimeMessageStoreInvalidAuth() throws Exception {
		prepMimeMessage();
		email.popBeforeSmtp = true;

		// expect Messaging Exception due to invalid authentication
		exception.expectMessage("AuthenticationFailedException");

		// build the MimeMessage
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
	 * String getHostName()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetHostNameValidSession() throws Exception {
		// create the session to
		Session session = Session.getInstance(new Properties());
		email.setMailSession(session);

		// assert hostname matches what was set
		assertEquals(session.getProperty(Email.MAIL_HOST), email.getHostName());
	}

	/**
	 * String getHostName()
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetHostNameNull() throws Exception {
		// assert hostname returns null
		assertEquals(null, email.getHostName());
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
	 * test Session getMailSession() for empty host
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMailSession2() throws Exception {
		email.setHostName("test.hostname.com");
		email.setStartTLSEnabled(true);
		email.setStartTLSRequired(true);
		email.setSendPartial(true);
		email.authenticator = new Authenticator() {
		};
		email.setSSLOnConnect(true);
		email.setSSLCheckServerIdentity(true);
		email.setBounceAddress(VALID_TEST_EMAILS[0]);
		email.setSocketTimeout(0);
		email.setSocketConnectionTimeout(0);

		email.getMailSession();

		// assert the expected session was returned by the mock
		assertFalse("Session should not be null", email.getMailSession().equals(null));
	}

	/**
	 * test Session getMailSession() for empty host
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetMailSessionEmptyHost() throws Exception {
		// set host name to null to throw an exception
		email.setHostName(EMPTY_STRING);

		// expect an EmailException
		exception.expect(EmailException.class);

		// this through throw an exception caused by null host name
		email.getMailSession();
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
		// set up mock to build MimeMessage
		prepMimeMessage();

		email.socketConnectionTimeout = 1;

		// send the mock e-mail
		email.send();

		// assert the message was sent
		assertEquals("Message does not match", email.message, FakeTransport.getLastMessageReceived());
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
		// create expected content type
		String expectedContentType = "contentType1";

		// set the mock email's content type
		email.updateContentType(expectedContentType);

		// assert mock's content type matches expected content type
		assertEquals(expectedContentType, email.contentType);
	}

	/**
	 * void updateContentType(String aContentType)
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateContentTypeTextSpace() throws Exception {
		// create expected content type
		String expectedContentType = EmailConstants.TEXT_PLAIN + "[; charset=UTF-8] ";
		email.setCharset(EmailConstants.UTF_8);

		// set the mock email's content type
		email.updateContentType(expectedContentType);

		// assert mock's content type matches expected content type
		assertEquals(expectedContentType, email.contentType);
	}

	/**
	 * void updateContentType(String aContentType)
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateContentTypeTextNoSpace() throws Exception {
		// create expected content type
		String expectedContentType = EmailConstants.TEXT_PLAIN + "[; charset=UTF-8]";
		email.setCharset(EmailConstants.UTF_8);

		// set the mock email's content type
		email.updateContentType(expectedContentType);

		// assert mock's content type matches expected content type
		assertEquals(expectedContentType, email.contentType);
	}

	/**
	 * void updateContentType(String aContentType)
	 *
	 * @throws Exception
	 */
	@Test
	public void testUpdateContentTypeValidTextAndCharset() throws Exception {
		// create expected content type
		String expectedContentType = EmailConstants.TEXT_PLAIN;
		email.setCharset(EmailConstants.UTF_8);

		// set the mock email's content type
		email.updateContentType(EmailConstants.TEXT_PLAIN);

		// assert mock's content type matches expected content type
		assertEquals(expectedContentType + "; charset=UTF-8", email.contentType);
	}

}
