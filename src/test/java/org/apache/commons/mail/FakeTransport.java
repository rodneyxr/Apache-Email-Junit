package org.apache.commons.mail;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.URLName;

public class FakeTransport extends Transport {

	private static Message lastMessageReceived;

	public FakeTransport(Session session, URLName urlname) {
		super(session, urlname);
	}

	@Override
	public void sendMessage(Message arg0, Address[] arg1) throws MessagingException {

	}

	public static void send(Message msg) {
		FakeTransport.lastMessageReceived = msg;
	}

	public static Message getLastMessageReceived() {
		return lastMessageReceived;
	}

}
