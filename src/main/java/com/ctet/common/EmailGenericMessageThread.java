package com.ctet.common;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailGenericMessageThread implements Runnable {
	private String emailSentTo;

	private String subject;

	private String message;

	String emailSentCC;

	PropertyConfig config;

	String pdfAttachmentFile;

	String pdfAttachmentFileName;
	String ccArray[];
	static Logger logger = LoggerFactory.getLogger(EmailGenericMessageThread.class);

	boolean setStatus = false;

	static String from1 = "gulfarooqui7326@gmail.com";
	static String pwd1 = "ddhdehazbllqootm";

	static String from2 = "gulfarooqui09@gmail.com";
	static String pwd2 = "3mBXgY*h";

	private static String defaultSender = "gulfarooqui1@gmail.com";

	private static synchronized FromSender getFromEmailSender() {
		if (defaultSender.equalsIgnoreCase("gulfarooqui7326@gmail.com")) {
			FromSender fromSender = new FromSender();
			fromSender.setEmail(from1);
			fromSender.setPassword(pwd1);
			defaultSender = "gulfarooqui1@gmail.com";
			logger.info("email sent on behalf of " + fromSender.getEmail());
			return fromSender;
		} else if (defaultSender.equalsIgnoreCase("gulfarooqui09@gmail.com")) {
			FromSender fromSender = new FromSender();
			fromSender.setEmail(from2);
			fromSender.setPassword(pwd2);
			defaultSender = "gulfarooqui1@gmail.com";
			logger.info("email sent on behalf of " + fromSender.getEmail());
			return fromSender;

		} else {
			FromSender fromSender = new FromSender();
			fromSender.setEmail(from1);
			fromSender.setPassword(pwd1);
			logger.info("can not come here....email sent on behalf of " + fromSender.getEmail());
			return fromSender;
		}
	}

	public EmailGenericMessageThread(String emailSentTo, String subject, String message,
			PropertyConfig propertyConfig) {
		this.emailSentTo = emailSentTo;
		this.subject = subject;
		this.message = message;
		config = propertyConfig;

	}

	public EmailGenericMessageThread(String emailSentTo, String subject, String message, String cc,
			PropertyConfig propertyConfig) {
		this.emailSentTo = emailSentTo;
		this.subject = subject;
		this.message = message;
		this.emailSentCC = cc;
		config = propertyConfig;
	}

	public EmailGenericMessageThread(String emailSentTo, String subject, String message, String cc,
			PropertyConfig propertyConfig, String pdfAttachmentFile, String pdfAttachmentFileName) {
		this.emailSentTo = emailSentTo;
		this.subject = subject;
		this.message = message;
		this.emailSentCC = cc;
		config = propertyConfig;
		this.pdfAttachmentFile = pdfAttachmentFile;
		this.pdfAttachmentFileName = pdfAttachmentFileName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {

			SimpleEmail email = new SimpleEmail();

			String host = config.getHostName();
			email.setHostName(host);
			FromSender fromSender = getFromEmailSender();
			String from = fromSender.getEmail();
			String pass = fromSender.getPassword();
			email.setAuthentication(from, pass);
			String smtpPort = config.getSmtpPort();
			email.setDebug(true);
			email.setSmtpPort(Integer.parseInt(smtpPort));
			email.setSSLOnConnect(true);

			email.addTo(emailSentTo);

			email.setFrom("gulfarooqui7326@gmail.com", "Gulrez");
			email.setSubject(subject);
			email.setMsg(message);
			email.send();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("problem in sending mail to " + emailSentTo, e);

//			throw new AssessmentGenericException("Can not send Email", e);
		}
	}

	public String getEmailSentCC() {
		return emailSentCC;
	}

	public void setEmailSentCC(String emailSentCC) {
		this.emailSentCC = emailSentCC;
	}

	public String[] getCcArray() {
		return ccArray;
	}

	public void setCcArray(String[] ccArray) {
		this.ccArray = ccArray;
	}

	public boolean isSetStatus() {
		return setStatus;
	}

	public void setSetStatus(boolean setStatus) {
		this.setStatus = setStatus;
	}

}

class FromSender {
	String email;

	String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
