package org.apache.commons.mail;


public class MockEmail extends Email {

	@Override
	public Email setMsg(String msg) throws EmailException {
		return null;
	}

	/**
	 * Sends the previously created MimeMessage to the SMTP server.
	 *
	 * @return the message id of the underlying MimeMessage
	 * @throws IllegalArgumentException
	 *             if the MimeMessage has not been created
	 * @throws EmailException
	 *             the sending failed
	 */
	@Override
	public String sendMimeMessage() throws EmailException {
		EmailUtils.notNull(this.message, "MimeMessage has not been created yet");

		try {
			FakeTransport.send(this.message);
			return this.message.getMessageID();
		} catch (Throwable t) {
			String msg = "Sending the email to the following server failed : " + this.getHostName() + ":" + this.getSmtpPort();

			throw new EmailException(msg, t);
		}
	}

}
