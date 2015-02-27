package org.apache.commons.mail;

public class MockEmail extends Email {

	@Override
	public Email setMsg(String msg) throws EmailException {
		return null;
	}

}
